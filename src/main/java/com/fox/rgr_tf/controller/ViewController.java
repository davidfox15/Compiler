package com.fox.rgr_tf.controller;

import com.fox.rgr_tf.compiler.model.Lexeme;
import com.fox.rgr_tf.compiler.CodeParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import second.compiler.codegenerator.CodeGenerator;
import second.compiler.codegenerator.CodeGeneratorOutput;
import second.compiler.codegenerator.CodeOptimization;
import second.compiler.lexis.Lexis;
import second.compiler.lexis.LexisResult;
import second.compiler.syntax.Syntax;
import second.compiler.syntax.SyntaxOutput;

public class ViewController {
    CodeGeneratorOutput codeGeneratorOutput;
    // Таблица лексем
    @FXML
    public TextArea textAreaInput;
    @FXML
    public TableView<Lexeme> tableLexem;
    @FXML
    public TableColumn<Lexeme, Integer> columnId;
    @FXML
    public TableColumn<Lexeme, String> columnLexem;
    @FXML
    public TableColumn<Lexeme, String> columnType;
    @FXML
    public Button optimizeCode;
    @FXML
    public Button createCode;

    // инициализируем форму данными
    private void createLexemTable() {
        // Добавление данных из модели
        ObservableList<Lexeme> Data = FXCollections.observableArrayList();
        for (Lexeme lexeme : CodeParser.getLexemeTable()) {
            Data.add(lexeme);
        }

        columnId.setCellValueFactory(new PropertyValueFactory<Lexeme, Integer>("id"));
        columnLexem.setCellValueFactory(new PropertyValueFactory<Lexeme, String>("name"));
        columnType.setCellValueFactory(new PropertyValueFactory<Lexeme, String>("type"));

        // заполняем таблицу данными
        tableLexem.setItems(Data);
    }


    // Таблица с HashMap
    private ObservableList<Lexeme> HashData = FXCollections.observableArrayList();
    @FXML
    public TableView<Lexeme> hashTable;
    @FXML
    public TableColumn<Lexeme, String> hashkey;
    @FXML
    public TableColumn<Lexeme, String> hashvalue;

    // инициализируем форму данными
    private void createHashTable() {
        // Добавление данных из модели
        ObservableList<Lexeme> Data = FXCollections.observableArrayList();
        CodeParser.getHash().forEach(
                (k, v) -> Data.add(new Lexeme(k, v)));

        hashkey.setCellValueFactory(new PropertyValueFactory<Lexeme, String>("name"));
        hashvalue.setCellValueFactory(new PropertyValueFactory<Lexeme, String>("type"));

        // заполняем таблицу данными
        hashTable.setItems(Data);
    }

    @FXML
    public TextArea generateTextField;
    @FXML
    public TextArea TreeTextField;

    private void generateCode() {
        String str = CodeParser.generateTrees();
        TreeTextField.setText(str);
    }


    public void buttonClick(MouseEvent mouseEvent) {
        if (!textAreaInput.getText().isBlank()) {
            String code = textAreaInput.getText();
            CodeParser.parseLexeme(code);
            createLexemTable();
            createHashTable();
            generateCode();
        }
    }



    private void clickAnalysisDoWhile() {
        if (!textAreaInput.getText().isBlank()) {
            try {
                LexisResult lexisResult = Lexis.analysisDoWhile(textAreaInput.getText());
                //textAreaLexemesDoWhile.setText(lexisResult.getLexemesString());
                Syntax syntax = new Syntax(lexisResult);
                SyntaxOutput syntaxOutput = syntax.syntaxAnalysisDoWhile();
                CodeGenerator codeGenerator = new CodeGenerator(syntaxOutput);
                codeGeneratorOutput = codeGenerator.generateCodeDoWhile();
                generateTextField.setText(codeGeneratorOutput.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
                generateTextField.setText(ex.getMessage());
            }
        }
    }

    public void buttonCreateCode(MouseEvent mouseEvent) {
        clickAnalysisDoWhile();
    }

    public void buttonOptimizeCode(MouseEvent mouseEvent) {
        if (!generateTextField.getText().isBlank()) {
            CodeOptimization codeOptimization = new CodeOptimization(codeGeneratorOutput);
            String s = codeOptimization.optimization().toString();
            generateTextField.setText(s);
        }
    }
}
