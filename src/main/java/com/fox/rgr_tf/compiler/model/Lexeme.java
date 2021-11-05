package com.fox.rgr_tf.compiler.model;

public class Lexeme {

    private int id;
    private String name;
    private String type;

    public Lexeme(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Lexeme(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return '[' + name + ']';
    }


    public String getLexeme() {
        return name;
    }

    public boolean isSign() {
        if (type.equals("END_OPERATOR")) return true;
        else return false;
    }

    public boolean isOperator() {
        if (type.equals("OPERATOR")) return true;
        else return false;
    }

    public boolean isVal() {
        if (type.equals("VALUE")) return true;
        else return false;
    }

    public boolean isNumber() {
        if (type.equals("INT") || type.equals("DOUBLE")) return true;
        else return false;
    }
}
