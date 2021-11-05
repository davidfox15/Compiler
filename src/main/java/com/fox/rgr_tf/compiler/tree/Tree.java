package com.fox.rgr_tf.compiler.tree;

import com.fox.rgr_tf.compiler.exceptions.SyntaxException;
import com.fox.rgr_tf.compiler.model.Lexeme;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private Node root;
    private Node current;
    private boolean cmp;
    private int l;


    public Tree() {
        root = new Node();
        current = root;
        l = 0;
        cmp = false;
    }

    public void insertLeft(Lexeme value) {
        current.setLeft(new Node(value, current));
        current = current.getLeft();
    }

    public void insertRight(Lexeme value) {
        current.setRight(new Node(value, current));
        current = current.getRight();
    }

    public void toParent() {
        if (current.getParent() != null) {
            current = current.getParent();
        }
    }

    public void toRight() {
        if (current.getRight() == null) {
            throw new IndexOutOfBoundsException();
        }
        current = current.getRight();
    }

    public void toLeft() {
        if (current.getLeft() == null) {
            throw new IndexOutOfBoundsException();
        }
        current = current.getLeft();
    }

    public Node getRoot() {
        return root;
    }

    public Node getCurrent() {
        return current;
    }

    public void setRootValue(Lexeme value) {
        root.setValue(value);
    }

    public void insertToNullLeft(Lexeme value) {
        if (current.getLeft() == null) {
            insertLeft(value);
            toParent();
        } else {
            current.setRight(new Node(null, current));
            current = current.getRight();
            insertLeft(value);
            toParent();

        }
    }

    public void setCurrentValue(Lexeme value) {
        current.setValue(value);
    }

    public void setRoot(Node root) {
        this.root = root;
    }


    public Tree getTreeForExp(List<Lexeme> lexemes) {
        root = new Node();
        listToTree(lexemes, root);
        return this;
    }

    private void listToTree(List<Lexeme> lexemes, Node current) {
        if(lexemes.size()==0)
            return;
        if (lexemes.size() == 1) {
            current.setValue(lexemes.get(0));
        } else {
            int indexLowPriority = 0;
            int currentLowPriority = 100;
            int currentPriority = 0;
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme lexeme = lexemes.get(i);
                if (lexeme.getLexeme().equals("(")) {
                    currentPriority++;
                    continue;
                }
                if (lexeme.getLexeme().equals(")")) {
                    currentPriority--;
                    continue;
                }
                if (lexeme.isSign() || lexeme.isOperator()) {
                    int pr = getPriorityForLexeme(lexeme, currentPriority);
                    if (pr <= currentLowPriority) {
                        currentLowPriority = pr;
                        indexLowPriority = i;
                    }
                }
            }
            if (lexemes.get(0).getLexeme().equals("(") && lexemes.get(lexemes.size() - 1).getLexeme().equals(")")) {
                lexemes = lexemes.subList(1, lexemes.size() - 1);
                indexLowPriority--;
            }
            current.setValue(lexemes.get(indexLowPriority));
            Node left = new Node();
            left.setParent(current);
            listToTree(lexemes.subList(0, indexLowPriority), left);
            Node right = new Node();
            right.setParent(current);
            listToTree(lexemes.subList(indexLowPriority + 1, lexemes.size()), right);
            current.setLeft(left);
            current.setRight(right);
        }
    }


    private int getPriorityForLexeme(Lexeme lexeme, int currentPriority) {
        if (lexeme.getLexeme().equals(":=")){
            return 0;
        } else if (lexeme.getLexeme().equals("*") || lexeme.getLexeme().equals("/")) {
            return 2 + currentPriority;
        } else {
            return 1 + currentPriority;
        }
    }

    public String getStringForCodeGenerator(int l) {
        this.l = ++l;
        return isCmp() ? getCodeGenCmp(root) : getCodeGenExpr(root);
    }

    private String getCodeGenCmp(Node current) {
        if (current == null) return "";
        if (current.getValue().isVal() || current.getValue().isNumber()) {
            return current.getValue().getLexeme();
        }
        if (current.getValue().isOperator() || current.getValue().isSign()) {
            String cmpStr = "CMP " + getCodeGenCmp(current.getLeft()) + "," + getCodeGenCmp(current.getRight()) + ";";
            return switch (current.getValue().getLexeme()) {
                case ">=" -> cmpStr + " JGE loop;";
                case "<=" -> cmpStr + " JLE loop;";
                case ">" -> cmpStr + " JG loop;";
                case "<" -> cmpStr + " JL loop;";
                case "==" -> cmpStr + " JE loop;";
                case "!=" -> cmpStr + " JNE loop;";
                default -> throw new IllegalStateException("Unexpected value: " + current.getValue().getLexeme());
            };
        }
        return null;
    }

    private String getCodeGenExpr(Node current) {
        if (current == null) return  "";

        if (current.getValue().isVal() || current.getValue().isNumber()) {
            return current.getValue().getLexeme() + ";";
        }

        if (current.getValue().isOperator() && !current.getValue().getLexeme().equals("=")) {
            int i = l++;
            return switch (current.getValue().getLexeme()) {
                case "+" -> getCodeGenExpr(current.getRight()) + " STORE $" + i + "; LOAD " + getCodeGenExpr(current.getLeft()) + " ADD $" + i + ";";
                case "*" -> getCodeGenExpr(current.getRight()) + " STORE $" + i + "; LOAD " + getCodeGenExpr(current.getLeft()) + " MPY $" + i + ";";
                case "-" -> getCodeGenExpr(current.getRight()) + " STORE $" + i + "; LOAD " + getCodeGenExpr(current.getLeft()) + " SUB $" + i + ";";
                case "/" -> getCodeGenExpr(current.getRight()) + " STORE $" + i + "; LOAD " + getCodeGenExpr(current.getLeft()) + " DIV $" + i + ";";
                default -> throw new IllegalStateException("Unexpected value: " + current.getValue().getLexeme());
            };
        }
        else if (current.getValue().isOperator()) {
            return "LOAD " + getCodeGenExpr(current.getRight()) + " STORE " + getCodeGenExpr(current.getLeft());
        }
        return "";
    }


    public int getL() {
        return l;
    }

    public boolean isCmp() {
        return cmp;
    }

    public void setCmp(boolean cmp) {
        this.cmp = cmp;
    }


    @Override
    public String toString() {
        return "Tree----" +
                "\nroot=" + root +
                "\ncurrent=" + current +
                "\ncmp=" + cmp +
                "\nl=" + l;
    }
}
