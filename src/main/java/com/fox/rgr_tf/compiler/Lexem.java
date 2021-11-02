package com.fox.rgr_tf.compiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexem {
    //
    private static String regexLine = "(\\w+\\.?\\d*)|([+/*-])|[()]|;|=";
    // Разделение по строчно на строки с коцном ;
    private static String regexCode = "(\\w|\\d.\\d|[()+*-/=]|[ ]*)*;";

    public static void tableCreate(String code) {
        // Создаем шаблон с помощью РВ
        Pattern pattern = Pattern.compile(regexCode);
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            Pattern linePattern = Pattern.compile(regexLine);
            Matcher lineMatcher = pattern.matcher(code);
            while (lineMatcher.find()) {
                String str = lineMatcher.group(0);
                // Проверка на число или переменную
                if (str.matches("\\d+\\.?\\d*")) {
                    // Проверка на содержание точки
                    if (str.contains("."))
                        ls.addNextLexeme(Lexeme.number(input), About.CONST_FLOAT);
                    else
                        ls.addNextLexeme(Lexeme.number(input), About.CONST_INT);
                }
                else if (input.matches("[A-Za-z]+\\w*"))
                    ls.addNextLexeme(Lexeme.val(input), About.INT);
                // Проверка на оператор
                if (str.matches("[+*/-]")) {
                    lexisResult.addNextLexeme(Lexeme.operator(str), About.OPERATOR);
                } else if (str.matches("[()]")) {
                    lexisResult.addNextLexeme(Lexeme.sign(str), About.BRACKET);
                } else if (str.equals(";")) {
                    lexisResult.addNextLexeme(Lexeme.sign(str), About.END_OPERATOR);
                } else if (str.equals("=")) {
                    lexisResult.addNextLexeme(Lexeme.operator(str), About.OPERATOR);
                } else {
                    throw new MatcherCompileException(str);
                }
            }
        }
    }

}
