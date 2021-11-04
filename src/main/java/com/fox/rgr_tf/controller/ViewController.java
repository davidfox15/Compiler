package com.fox.rgr_tf.controller;

import com.fox.rgr_tf.compiler.model.Lexem;
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
    // Таблица лексем
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
    // инициализируем форму данными
    private void createLexemTable() {
        // Добавление данных из модели
        ObservableList<Lexem> Data = FXCollections.observableArrayList();
        for (Lexem lexem: CodeParser.lexemTable) {
            Data.add(lexem);
        }

        columnId.setCellValueFactory(new PropertyValueFactory<Lexem, Integer>("id"));
        columnLexem.setCellValueFactory(new PropertyValueFactory<Lexem, String>("name"));
        columnType.setCellValueFactory(new PropertyValueFactory<Lexem, String>("type"));

        // заполняем таблицу данными
        tableLexem.setItems(Data);
    }


    // Таблица с HashMap
    private ObservableList<Lexem> HashData = FXCollections.observableArrayList();
    @FXML
    public TableView<Lexem> hashTable;
    @FXML
    public TableColumn<Lexem, String> hashkey;
    @FXML
    public TableColumn<Lexem, String> hashvalue;

    private void createHashTable(){
        // Добавление данных из модели
        ObservableList<Lexem> Data = FXCollections.observableArrayList();
        CodeParser.Hash.forEach(
                (k, v) -> Data.add(new Lexem(k,v)));

        hashkey.setCellValueFactory(new PropertyValueFactory<Lexem, String>("name"));
        hashvalue.setCellValueFactory(new PropertyValueFactory<Lexem, String>("type"));

        // заполняем таблицу данными
        hashTable.setItems(Data);
    }


    public void buttonClick(MouseEvent mouseEvent) {
        if (!textAreaInput.getText().isBlank()) {
            String code = textAreaInput.getText();
            CodeParser.parseLexem(code);
            createLexemTable();
            createHashTable();
        }
    }
}
