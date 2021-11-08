package com.fox.rgr_tf.compiler.tree;

import com.fox.rgr_tf.compiler.model.Lexeme;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Tree {
    private Node root;
    private Node current;

    public Tree() {
        this.root = new Node();
        this.current = this.root;
        root.setParent(root);
    }

    public void createTree(List<Lexeme> lexemes) {
        System.out.println(lexemes);
        if (lexemes.size() == 0)
            return;
        if (lexemes.size() == 1) {
            current.setValue(lexemes.get(0));
        } else {
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme tlexeme = lexemes.get(i);
                if (tlexeme.isLoop()) {
                    current.setValue(tlexeme);
                    continue;
                }
                if (tlexeme.getLexeme().equals("(")) {
                    current.setLeft(new Node(current));
                    current = current.getLeft();
                    continue;
                }
                if (tlexeme.isOperator()) {
                    current.setValue(tlexeme);
                    current.setRight(new Node(current));
                    current = current.getRight();
                    continue;
                }
                if (tlexeme.isVal() || tlexeme.isNumber()) {
                    if (lexemes.size() - i > 1 && !lexemes.get(i+1).getLexeme().equals(")"))
                        current.setLeft(new Node(tlexeme, current));
                    else {
                        current.setValue(tlexeme);
                        current = current.getParent();
                    }
                    continue;
                }
                if (tlexeme.getLexeme().equals(")")) {
                    current = current.getParent();
                    if(lexemes.size() - i <= 1)
                    {
                        Node tmp = current.getLeft();
                        current = current.getParent();
                        current.setRight(tmp);
                    }
                    continue;
                }

            }
        }
    }


    public Node newTree(List<Lexeme> lexemes) {
        Node troot = new Node(current);
        current = troot;
        System.out.println(lexemes);
        if (lexemes.size() == 0)
            return null;
        if (lexemes.size() == 1) {
            current.setValue(lexemes.get(0));
        } else {
            for (int i = 0; i < lexemes.size(); i++) {
                Lexeme tlexeme = lexemes.get(i);
                if(tlexeme.getLexeme().equals(":="))
                {
                    root = troot;
                    current.setValue(tlexeme);
                    lexemes = lexemes.subList(i+1, lexemes.size());
                    current.setRight(newTree(reverseList(lexemes,i)));
                    return root;
                }
                if (tlexeme.isVal() || tlexeme.isNumber()) {
                    if (lexemes.size() - i > 1 && !lexemes.get(i+1).getLexeme().equals("("))
                        current.setLeft(new Node(tlexeme, current));
                    else {
                        current.setValue(tlexeme);
                        current = current.getParent();
                    }
                    continue;
                }
                if (tlexeme.getLexeme().equals("(")) {
                    current.setLeft(new Node(current));
                    current = current.getLeft();
                    continue;
                }
                if (tlexeme.isOperator()) {
                    current.setValue(tlexeme);
                    current.setRight(new Node(current));
                    current = current.getRight();
                    continue;
                }

                if (tlexeme.getLexeme().equals(")")) {
                    current = current.getParent();
                    if(lexemes.size() - i <= 1)
                    {
                        Node tmp = current.getLeft();
                        current = current.getParent();
                        current.setRight(tmp);
                    }
                    continue;
                }
            }
        }
        return troot;
    }

    private List<Lexeme> reverseList(List<Lexeme> lexemes,int i){
        Collections.reverse(lexemes);
        for (Lexeme lex :
                lexemes) {
            if (lex.getLexeme().equals(")")){ lex.setName("("); continue;}
            if (lex.getLexeme().equals("(")) lex.setName(")");
        }
        return lexemes;
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "MTree\n " + root;
    }
}
