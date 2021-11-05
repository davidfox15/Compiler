package com.fox.rgr_tf.compiler.exceptions;

import com.fox.rgr_tf.compiler.model.Lexeme;

import java.util.List;

public abstract class Syntax {
    private static String lexemesToString(List<Lexeme> lexemes) {
        StringBuilder sb = new StringBuilder();
        lexemes.forEach(lexeme -> sb.append(lexeme.getLexeme()));
        return sb.toString();
    }

    private static boolean leftIsOperator(List<Lexeme> lexemes, int i) {
        for (int j = i; j > 0; j--) {
            if (lexemes.get(j).isOperator() && !lexemes.get(j).getLexeme().equals(":=")) {
                return true;
            }
        }
        return false;
    }

    public static void syntaxAnalysis(List<Lexeme> lexemes) throws SyntaxException {
        for (int i = 0; i < lexemes.size(); i++) {
            Lexeme lexeme = lexemes.get(i);
            if (lexeme.isOperator()) {
                if (i == 0 || i == lexemes.size() - 1) {
                    throw new SyntaxException(lexemes.get(i).getLexeme(), lexemesToString(lexemes));
                }
            }
            if (i <=2 && lexemes.get(i).getLexeme().equals(";")) {
                //if (lexemes.size() != 2)
                throw new SyntaxException(lexemes.get(i).getLexeme(), lexemesToString(lexemes));
            } else if (i > 0) {
                if (lexeme.getLexeme().equals(":=")) {
                    if (leftIsOperator(lexemes, i))
                        throw new SyntaxException(lexemes.get(i).getLexeme(), lexemesToString(lexemes));
                }
                if (lexeme.isOperator()) {
                    if (!lexemes.get(i - 1).isVal() && !lexemes.get(i - 1).isNumber() && !lexemes.get(i - 1).getLexeme().equals(")"))
                        throw new SyntaxException(lexemes.get(i - 1).getLexeme() + lexeme.getLexeme(),
                                lexemesToString(lexemes));
                    if (i >= lexemes.size() - 1 || (!lexemes.get(i + 1).isVal()
                            && !lexemes.get(i + 1).isNumber() && !lexemes.get(i + 1).getLexeme().equals("(")))
                        throw new SyntaxException(lexemes.get(i).getLexeme() + lexemes.get(i + 1).getLexeme(),
                                lexemesToString(lexemes));
                }
            }
        }
    }
}
