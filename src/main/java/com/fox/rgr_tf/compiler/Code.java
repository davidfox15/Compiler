package com.fox.rgr_tf.compiler;

import com.fox.rgr_tf.compiler.model.Lexeme;
import com.fox.rgr_tf.compiler.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code {
    public static String mgetAssembler(Tree tree) {
        System.out.println(tree.toList());
        return "";
    }

    public static String getAssembler(Tree tree1) {
        if (tree1.getRoot().getValue() == null)
            return "";
        int elID = 1;
        var table = new HashMap<Integer, String>();
        var tree = tree1.toList();
        for (int i = 0; i < tree.size(); i++) {
            var builder = new StringBuilder();
            var line = tree.get(i).getLexeme();
            var parsed = Calculator.inf2postf(line)
                    .replace("*", " *")
                    .replace("/", " /")
                    .replace("-", " -")
                    .replace("+", " +")
                    .split(" ");
            int ks = 0;
            for (var el : parsed) {
                switch (el) {
                    case "+": {
                        builder.append("ADD $" + elID + "\n");
                        elID++;
                        break;
                    }
                    case "*": {
                        builder.append("MPY $" + elID + "\n");
                        elID++;
                        break;
                    }
                    case "-": {
                        builder.append("SUB $" + elID + "\n");
                        elID++;
                        break;
                    }
                    case "/": {
                        builder.append("DIV $" + elID + "\n");
                        elID++;
                        break;
                    }
                    case "=": {
                        builder.append("STORE $" + elID + "\n");
                        elID++;
                        break;
                    }
                    default: {
                        if (el.isEmpty()) continue;
                        if (ks > 0) {
                            if (el.startsWith("n")) {
                                var number = el.toCharArray()[1];
                                builder.append(table.get(Integer.parseInt(String.valueOf(number))));
                            } else {
                                builder.append("LOAD " + el + "\n");
                            }
                        } else {
                            builder.append("LOAD " + el + "\n");
                            builder.append("STORE $" + elID + "\n");
                            ks++;
                        }
                        break;
                    }
                }
                table.put((i + 1), builder.toString());
            }
        }
        var result = table.get(table.size() - 1) + "STORE ";
        return result;
    }

    public static String getOptimizedAssembler(Tree tree1) {
        var table = new ArrayList<String>();
        var tree = tree1.toList();
        for (int i = 0; i < tree.size(); i++) {
            var builder = new StringBuilder();
            var line = tree.get(i).getLexeme();
            var parsed = Calculator.inf2postf(line)
                    .replace("*", " *")
                    .replace("/", " /")
                    .replace("-", " -")
                    .replace("+", " +")
                    .replace("  ", " ")
                    .split(" ");
            var stack = new Stack<String>();
            for (var el : parsed) {
                switch (el) {
                    case "+": {
                        var one = stack.pop();
                        var two = stack.pop();
                        var res = "";
                        if (one.contains("n")) {
                            res = "ADD " + two + "\n";
                        } else if (two.contains("n")) {
                            res = "ADD " + one + "\n";
                        } else {
                            res = "LOAD " + two + "\nADD " + one + "\n";
                        }
                        table.add(res);
                        break;
                    }
                    case "*": {
                        var one = stack.pop();
                        var two = stack.pop();
                        var res = "";
                        if (one.contains("n")) {
                            res = "MPY " + two + "\n";
                        } else if (two.contains("n")) {
                            res = "MPY " + one + "\n";
                        } else {
                            res = "LOAD " + two + "\nMPY " + one + "\n";
                        }
                        table.add(res);
                        break;
                    }
                    case "-": {
                        var one = stack.pop();
                        var two = stack.pop();
                        var res = "";
                        if (one.contains("n")) {
                            res = "SUB " + two + "\n";
                        } else if (two.contains("n")) {
                            res = "SUB " + one + "\n";
                        } else {
                            res = "LOAD " + two + "\nSUB " + one + "\n";
                        }
                        table.add(res);
                        break;
                    }
                    case "/": {
                        var one = stack.pop();
                        var two = stack.pop();
                        var res = "";
                        if (one.contains("n")) {
                            res = "DIV " + two + "\n";
                        } else if (two.contains("n")) {
                            res = "DIV " + one + "\n";
                        } else {
                            res = "LOAD " + two + "\nDIV " + one + "\n";
                        }
                        table.add(res);
                        break;
                    }
                    case "=": {
                        break;
                    }
                    default: {
                        stack.push(el);
                        break;
                    }
                }
            }
        }
        table.add("STORE ");
        var builderS = new StringBuilder();
        for (var el : table) {
            builderS.append(el);
        }
        return builderS.toString();
    }

    public static String getCode(List<Lexeme> lexemes) {
        //System.out.println("STEP\n");
        int elID = 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lexemes.size(); i++) {
            //System.out.println(lexemes.get(i).getLexeme());
            String el = lexemes.get(i).getLexeme();
            switch (el) {
                case "<":
                    builder.append("CMP " + lexemes.get(i - 1).getLexeme() + "\n");
                    break;
                case "do":
                    builder.append("loop;\n");
                    break;
                case "while":
                    builder.append("JGE loop;\n");
                    break;
                case "+":
                    builder.append("ADD $" + elID + "\n");
                    break;
                case "*":
                    builder.append("MPY $" + elID + "\n");
                    break;
                case "-":
                    builder.append("SUB $" + elID + "\n");
                    break;
                case "/":
                    builder.append("DIV $" + elID + "\n");
                    break;
                case ":=":
                    builder.append("STORE " + lexemes.get(i - 1).getLexeme() + "\n");
                    break;
                default:
                    if (lexemes.get(i).isVal() || lexemes.get(i).isNumber()) {
                        if (i + 1 < lexemes.size() && (!lexemes.get(i + 1).getLexeme().equals(":=")))
                            builder.append("LOAD " + el + "\n");
                        if (i + 1 < lexemes.size() && (lexemes.get(i + 1).isNumber() || lexemes.get(i + 1).isVal())) {
                            elID++;
                            builder.append("STORE $" + elID + "\n");
                        }
                    }
                    break;
            }
        }
        return builder.toString();
    }

    public static String getOptCode(List<Lexeme> lexemes) {
        String str="";
        System.out.println("STEP\n");
        int elID = 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lexemes.size(); i++) {
            String el = lexemes.get(i).getLexeme();
            System.out.println(el);
            switch (el) {
                case "<":
                    i++;
                    builder.append("CMP " + lexemes.get(i).getLexeme() + "\n");
                    break;
                case "do":
                    builder.append("loop;\n");
                    break;
                case "while":
                    str="JGE loop;\n";
                    break;
                case "+":
                    i++;
                    builder.append("ADD " + lexemes.get(i).getLexeme() + "\n");
                    break;
                case "*":
                    i++;
                    builder.append("MPY " + lexemes.get(i).getLexeme() + "\n");
                    break;
                case "-":
                    i++;
                    builder.append("SUB " + lexemes.get(i).getLexeme() + "\n");
                    break;
                case "/":
                    i++;
                    builder.append("DIV " + lexemes.get(i).getLexeme() + "\n");
                    break;
                case ":=":
                    i++;
                    builder.append("STORE " + lexemes.get(i).getLexeme() + "\n");
                    break;
                default:
                    if (lexemes.get(i).isVal() || lexemes.get(i).isNumber()) {
                        if (i + 1 < lexemes.size() && (!lexemes.get(i + 1).getLexeme().equals(":=")))
                            builder.append("LOAD " + el + "\n");
                    }
                    break;
            }
        }
        builder.append(str);
        return builder.toString();
    }
}
