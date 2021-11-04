package com.fox.rgr_tf.compiler.model;

public class Lexem {

    private int id;
    private String name;
    private String type;

    public Lexem(String name,String type){
        this.name = name;
        this.type = type;
    }

    public Lexem(int id, String name, String type) {
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
        return "Lexem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


}
