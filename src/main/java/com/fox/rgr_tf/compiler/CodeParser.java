package com.fox.rgr_tf.compiler;

import com.fox.rgr_tf.compiler.model.Lexeme;
import com.fox.rgr_tf.compiler.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeParser {
    private static HashMap<String, String> Hash;
    private static List<Lexeme> lexemeTable;
    private static List<Tree> trees;

    private static final String regexLine = "(\\w+\\.?\\d*)|:=|([+/*<=>-])|[{}()]|;";
    // Разделение кода над блоки
    private static final String regexBlocks = "(\\w|:=|\\d.\\d||[{}()+/*<=>-]|[ ]*)*[;{}]";

    private static void tableAdd(String lexeme, String type) {
        lexemeTable.add(new Lexeme(lexemeTable.size(), lexeme, type));
        Hash.put(lexeme, type);
    }

    public static void findType(String lexeme) {
        // Проверка на число или переменную
        if (lexeme.matches("\\d+\\.?\\d*")) {
            // Проверка на содержание точки
            if (lexeme.contains("."))
                tableAdd(lexeme, "DOUBLE");
            else
                tableAdd(lexeme, "INT");
            return;
        } else if (lexeme.matches("[A-Za-z]+\\w*")) {
            if (lexeme.equals("while") || lexeme.equals("do"))
                tableAdd(lexeme, "LOOP");
            else
                tableAdd(lexeme, "VALUE");
            return;
        }
        // Проверка на оператор
        if (lexeme.matches("[+/*-]")) {
            tableAdd(lexeme, "OPERATOR");
            return;
        }
        if (lexeme.matches("[{}]")) {
            tableAdd(lexeme, "BLOCKS");
            return;
        }
        if (lexeme.matches("[()]")) {
            tableAdd(lexeme, "BRACKET");
            return;
        }
        if (lexeme.equals(";")) {
            tableAdd(lexeme, "END_OPERATOR");
            return;
        }
        if (lexeme.equals("=")) {
            tableAdd(lexeme, "OPERATOR");
            return;
        } else {
            System.out.println("Not find type of object!!!");
        }
    }

    public static void parseLexeme(String code) {
        lexemeTable = new ArrayList<>();
        Hash = new HashMap<>();
        // Создаем шаблон с помощью РВ
        Pattern pattern = Pattern.compile(regexBlocks);
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            Pattern linePattern = Pattern.compile(regexLine);
            Matcher lineMatcher = linePattern.matcher(matcher.group(0));
            while (lineMatcher.find()) {
                String str = lineMatcher.group(0);
                findType(str);
            }
        }

    }

    public static HashMap<String, String> getHash() {
        return Hash;
    }

    public static List<Lexeme> getLexemeTable() {
        return lexemeTable;
    }

    public static List<Tree> getTrees() {
        return trees;
    }

    private static void addTOTrees(int begin, int i) {
        Tree tree = new Tree();
        tree.createTree(lexemeTable.subList(begin, i));
        trees.add(tree);
    }

    public static String generateTrees() {
        trees = new ArrayList<>();
        int begin = 0;
        for (int i = 0; i < lexemeTable.size(); i++) {
            if (lexemeTable.get(i).getLexeme().equals("{")) {
                addTOTrees(begin, i);
                begin = i + 1;
                continue;
            }
            if (lexemeTable.get(i).getLexeme().equals(";")) {
                addTOTrees(begin, i);
                begin = i + 1;
                continue;
            }
            if (lexemeTable.get(i).getLexeme().equals("}")) {
                addTOTrees(begin, i);
                begin = i + 1;
                continue;
            }
        }
        String str = "";
        for (Tree tree :
                trees) {
            str += "\n" + tree;
        }
        return str;
    }
}
