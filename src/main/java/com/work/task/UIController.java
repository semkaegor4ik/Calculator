package com.work.task;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UIController {

    @FXML
    private Button oneButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button fifeButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button sevenButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button nineButton;

    @FXML
    private Button pointButton;

    @FXML
    private Button zeroButton;

    @FXML
    private Button resultButton;

    @FXML
    private Button sumButton;

    @FXML
    private Button subtractionButton;

    @FXML
    private Button multiplicationButton;

    @FXML
    private Button divisionButton;

    @FXML
    private Button openButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button powerButton;

    @FXML
    private TextField textField;

    private final Calculator calculator = new Calculator(this);

    @FXML
    void initialize() {
        textField.setText(calculator.getFormula().toString());
        zeroButton.setOnAction(event -> calculator.addSymbol('0'));
        oneButton.setOnAction(event -> calculator.addSymbol('1'));
        twoButton.setOnAction(event -> calculator.addSymbol('2'));
        threeButton.setOnAction(event -> calculator.addSymbol('3'));
        fourButton.setOnAction(event -> calculator.addSymbol('4'));
        fifeButton.setOnAction(event -> calculator.addSymbol('5'));
        sixButton.setOnAction(event -> calculator.addSymbol('6'));
        sevenButton.setOnAction(event -> calculator.addSymbol('7'));
        eightButton.setOnAction(event -> calculator.addSymbol('8'));
        nineButton.setOnAction(event -> calculator.addSymbol('9'));

        openButton.setOnAction(event -> calculator.addSymbol('('));
        closeButton.setOnAction(event -> calculator.addSymbol(')'));
        sumButton.setOnAction(event -> calculator.addSymbol('+'));
        subtractionButton.setOnAction(event -> calculator.addSymbol('-'));
        divisionButton.setOnAction(event -> calculator.addSymbol('/'));
        multiplicationButton.setOnAction(event -> calculator.addSymbol('*'));
        powerButton.setOnAction(event -> calculator.addSymbol('^'));

        resultButton.setOnAction(event -> textField.setText(String.valueOf(calculator.start())));
    }

}
