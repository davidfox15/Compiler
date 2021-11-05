package com.fox.rgr_tf.compiler.tree;

import com.fox.rgr_tf.compiler.model.Lexeme;

import java.util.List;

public class MTree {
    private Node root;
    private Node current;

    public MTree() {
        this.root = new Node();
        this.current = this.root;
        root.setParent(root);
    }

    public void createTree(List<Lexeme> lexemes,Node current){
        if(lexemes.size()==0)
            return;
        if(lexemes.size()==1){
            current.setValue(lexemes.get(0));
        } else {
            for(int i=0;i<lexemes.size();i++){
                Lexeme tlexeme = lexemes.get(i);
                if(tlexeme.getLexeme().equals('(')){
                    current.setLeft(new Node(current));
                    current = current.getLeft();
                    continue;
                }
                if(tlexeme.isOperator()){
                    current.setValue(tlexeme);
                    current.setRight(new Node(current));
                    current = current.getRight();
                    continue;
                }
                if(tlexeme.isVal()||tlexeme.isNumber()){
                    if(root == current.getParent())
                            current.setLeft(new Node(tlexeme, current));
                    else
                    {
                        current.setValue(tlexeme);
                        current = current.getParent();
                    }
                    continue;
                }
                if(tlexeme.getLexeme().equals(')')){
                    current = current.getParent();
                    continue;
                }

            }
        }
    }

    public Node getRoot(){
        return root;
    }

    @Override
    public String toString() {
        return "MTree\n "+ current;
    }
}
