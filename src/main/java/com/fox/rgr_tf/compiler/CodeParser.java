package com.fox.rgr_tf.compiler;

import com.fox.rgr_tf.compiler.model.Lexeme;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeParser {
    private static HashMap<String, String> Hash;
    private static List<Lexeme> lexemeTable;

    private static String regexLine = "(\\w+\\.?\\d*)|:=|([+/*<=>-])|[{}()]|;";
    // Разделение кода над блоки
    private static String regexBlocks = "(\\w|:=|\\d.\\d||[{}()+/*<=>-]|[ ]*)*[;{}]";

    private static void tableAdd(String lexem, String type) {
        lexemeTable.add(new Lexeme(lexemeTable.size(), lexem, type));
        Hash.put(lexem, type);
    }

    public static void findType(String lexem) {
        // Проверка на число или переменную
        if (lexem.matches("\\d+\\.?\\d*")) {
            // Проверка на содержание точки
            if (lexem.contains("."))
                tableAdd(lexem, "DOUBLE");
            else
                tableAdd(lexem, "INT");
            return;
        } else if (lexem.matches("[A-Za-z]+\\w*")) {
            tableAdd(lexem, "VALUE");
            return;
        }
        // Проверка на оператор
        if (lexem.matches("[+/*<=>-]")) {
            tableAdd(lexem, "OPERATOR");
            return;
        }
        if (lexem.matches("[{}]")) {
            tableAdd(lexem, "BLOCKS");
            return;
        }
        if (lexem.matches("[()]")) {
            tableAdd(lexem, "BRACKET");
            return;
        }
        if (lexem.equals(";")) {
            tableAdd(lexem, "END_OPERATOR");
            return;
        }
        if (lexem.equals(":=")) {
            tableAdd(lexem, "OPERATOR");
            return;
        } else {
            System.out.println("Not find type of object!!!");
        }
    }

    public static void parseLexem(String code) {
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
}
