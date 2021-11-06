package com.fox.rgr_tf.compiler.generator;

import com.fox.rgr_tf.compiler.tree.Node;
import com.fox.rgr_tf.compiler.tree.Tree;

public class Codegenerator {

    public static void generateCode(Node node, int cmd) {
        int tmp = 0;
        String act = "";
        String val = node.getValue().getLexeme();
        // Для присваивания
        if (node.getValue() == null) {
            if (node.getLeft() != null) {
                node.setValue(node.getLeft().getValue());
            }
        }

        if (node.getValue().isVal() || node.getValue().isNumber()) {
            switch (cmd) {
                case 0:// := right
                    act = "LOAD ";
                    break;
                case 1:// := left
                    act = "STORE ";
                    break;
                case 2:// +
                    act = "ADD ";
                    break;
                case 3:// -
                    act = "SUB ";
                    break;
                case 4:// *
                    act = "MPY ";
                    break;
                case 5:// /
                    act = "DIV ";
                    break;
                case 6:// >
                    act = "GREATER ";
                    break;
                case 7:// <
                    act = "<";
                    break;
                case 8:
                    act = "=";
                    break;
                default:
                    break;
            }
            System.out.println(act + node.getValue().getLexeme());
        }
        if (node.getValue().isOperator()) {
            if (val.equals(":=")) {
                generateCode(node.getRight(), 0);
                generateCode(node.getLeft(), 1);
            }
            if (val.equals("+")) {
                generateCode(node.getRight(), 0);
                generateCode(node.getLeft(), 2);
            }
            if (val.equals("-")) {
                generateCode(node.getRight(), 0);
                generateCode(node.getLeft(), 3);
            }
            if (val.equals("*")) {
                generateCode(node.getRight(), 0);
                generateCode(node.getLeft(), 4);
            }
            if (val.equals("/")) {
                generateCode(node.getRight(), 0);
                generateCode(node.getLeft(), 5);
            }
            if (val.equals(">")) {
                generateCode(node.getRight(), 0);
                generateCode(node.getLeft(), 6);
            }
            if (val.equals("<")) {
                generateCode(node.getRight(), 0);
                generateCode(node.getLeft(), 7);
            }
            if (val.equals("=")) {
                generateCode(node.getRight(), 0);
                generateCode(node.getLeft(), 8);
            }

        }
    }

}
