package com.fox.rgr_tf.compiler;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeParser {

    public static ArrayList<Lexem> lexemTable;

    private static String regexLine = "(\\w+\\.?\\d*)|([+/*-])|[()]|;|:=";
    // Разделение по строчно на строки с коцном ;
    private static String regexCode = "(\\w|\\d.\\d|[+/*-=()]|[ ]*)*;";
    // Разделение кода над блоки
    private static String regexBlocks = "(\\w|\\d.\\d|[+/*-<=>()]|[ ]*)*[;{}]";


    public static void findType(String lexem) {
        // Проверка на число или переменную
        if (lexem.matches("\\d+\\.?\\d*")) {
            // Проверка на содержание точки
            if (lexem.contains("."))
                lexemTable.add(new Lexem(lexemTable.size(),lexem,"[DOUBLE]"));
            else
                lexemTable.add(new Lexem(lexemTable.size(),lexem,"[INT]"));
            return;
        } else if (lexem.matches("[A-Za-z]+\\w*")) {
            lexemTable.add(new Lexem(lexemTable.size(),lexem,"[VALUE]"));
            return;
        }
        // Проверка на оператор
        if (lexem.matches("[+*/-]")) {
            lexemTable.add(new Lexem(lexemTable.size(),lexem,"[OPERATOR]"));
            return;
        }
        if (lexem.matches("[()]")) {
            lexemTable.add(new Lexem(lexemTable.size(),lexem,"[EXPRESSION]"));
            return;
        }
        if (lexem.equals(";")) {
            lexemTable.add(new Lexem(lexemTable.size(),lexem,"[END]"));
            return;
        }
        if (lexem.equals(":=")) {
            lexemTable.add(new Lexem(lexemTable.size(),lexem,"[ASSIGNMENT]"));
            return;
        } else {
            System.out.println("Not find type of object!!!");
        }
    }

    public static void parseLexem(String code) {
        lexemTable = new ArrayList<>();
        // Создаем шаблон с помощью РВ
        Pattern pattern = Pattern.compile(regexCode);
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            Pattern linePattern = Pattern.compile(regexLine);
            Matcher lineMatcher = linePattern.matcher(matcher.group(0));
            while (lineMatcher.find()) {
                String str = lineMatcher.group(0);
                // Проверка на число или переменную
                findType(str);
            }
        }

    }
}
