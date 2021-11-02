package com.fox.rgr_tf;

import com.fox.rgr_tf.compiler.*;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ViewController {


    public TextArea textAreaInput;

    public void buttonClick(MouseEvent mouseEvent) {
        if (!textAreaInput.getText().isBlank()) {
            String code = textAreaInput.getText();
            Lexem.tableCreate(code);
        }
    }
}
