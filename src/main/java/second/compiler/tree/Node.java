package second.compiler.tree;


import second.compiler.lexis.Lexeme;

public class Node {
    private Lexeme value;
    private Node right;
    private Node left;
    private Node parent;

    public Node(Lexeme value, Node parent) {
        this.value = value;
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

    public boolean isNullableChild() {
        return left == null || right == null;
    }
}
