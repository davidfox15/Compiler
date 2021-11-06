package com.fox.rgr_tf.compiler.tree;

import com.fox.rgr_tf.compiler.model.Lexeme;

public class Node {
    private Node parent;
    private Lexeme value;
    private Node right;
    private Node left;


    public Node(Lexeme value, Node parent) {
        this.value = value;
        this.parent = parent;
    }

    public Node(Node parent) {
        this.parent = parent;
    }

    public Node() {
    }

    public Lexeme getValue() {
        return value;
    }

    public void setValue(Lexeme value) {
        this.value = value;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        String out = "";
        if (value != null)
            out += value + " ";
        if (right != null)
            out += value + " right=" + right + '\n';
        if (left != null)
            out += value + " left=" + left + '\n';
        return out;
    }
}

