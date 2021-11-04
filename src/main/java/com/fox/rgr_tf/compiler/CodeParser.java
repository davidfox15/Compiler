package com.fox.rgr_tf.compiler;

import com.fox.rgr_tf.compiler.model.Lexem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeParser {

    public static HashMap<String, String> Hash;
    public static ArrayList<Lexem> lexemTable;

    private static String regexLine = "(\\w+\\.?\\d*)|:=|([+/*-<=>])|[{}()]|;";
    // Разделение кода над блоки
    private static String regexBlocks = "(\\w|\\d.\\d|[{}+/*-<=>()]|[ ]*)*[;{}]";

    private static void tableAdd(String lexem, String type){
        lexemTable.add(new Lexem(lexemTable.size(),lexem,type));
        Hash.put(lexem,type);
    }

    public static void findType(String lexem) {
        // Проверка на число или переменную
        if (lexem.matches("\\d+\\.?\\d*")) {
            // Проверка на содержание точки
            if (lexem.contains("."))
                tableAdd(lexem,"DOUBLE");
            else
                tableAdd(lexem,"INT");
            return;
        } else if (lexem.matches("[A-Za-z]+\\w*")) {
            tableAdd(lexem,"VLAUE");
            return;
        }
        // Проверка на оператор
        if (lexem.matches("[+/*-<=>]")) {
            tableAdd(lexem,"OPERATOR");
            return;
        }
        if(lexem.matches("[{}]")){
            tableAdd(lexem,"BLOCKS");
            return;
        }
        if (lexem.matches("[()]")) {
            tableAdd(lexem,"EXPRESSION");
            return;
        }
        if (lexem.equals(";")) {
            tableAdd(lexem,"STREND");
            return;
        }
        if (lexem.equals(":=")) {
            tableAdd(lexem,"ASSIGNMENT");
            return;
        } else {
            System.out.println("Not find type of object!!!");
        }
    }

    public static void parseLexem(String code) {
        lexemTable = new ArrayList<>();
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
}
