package com.fox.rgr_tf;

import com.fox.rgr_tf.compiler.Lexem;
import com.fox.rgr_tf.compiler.CodeParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ViewController {

    private ObservableList<Lexem> Data = FXCollections.observableArrayList();
    @FXML
    public TextArea textAreaInput;
    @FXML
    public TableView<Lexem> tableLexem;
    @FXML
    public TableColumn<Lexem, Integer> columnId;
    @FXML
    public TableColumn<Lexem, String> columnLexem;
    @FXML
    public TableColumn<Lexem, String> columnType;

    public void buttonClick(MouseEvent mouseEvent) {
        if (!textAreaInput.getText().isBlank()) {
            String code = textAreaInput.getText();
            CodeParser.parseLexem(code);
            createTable();
        }
    }

    // инициализируем форму данными
    private void createTable() {
        initData();

        columnId.setCellValueFactory(new PropertyValueFactory<Lexem, Integer>("id"));
        columnLexem.setCellValueFactory(new PropertyValueFactory<Lexem, String>("name"));
        columnType.setCellValueFactory(new PropertyValueFactory<Lexem, String>("type"));

        // заполняем таблицу данными
        tableLexem.setItems(Data);
    }
    private void initData() {
        for (Lexem lexem: CodeParser.lexemTable) {
            Data.add(lexem);
        }
    }
}
