package com.work.task;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Button deleteButton;

    @FXML
    private TextField textField;

    @FXML
    private Button historyButton;

    private final Calculator calculator = Calculator.getInstance();

    private final Map<Character, Button> buttons = new HashMap<>();

    @FXML
    void initialize() {
        buttons.put('0', zeroButton);
        buttons.put('1', oneButton);
        buttons.put('2', twoButton);
        buttons.put('3', threeButton);
        buttons.put('4', fourButton);
        buttons.put('5', fifeButton);
        buttons.put('6', sixButton);
        buttons.put('7', sevenButton);
        buttons.put('8', eightButton);
        buttons.put('9', nineButton);

        buttons.put('(', openButton);
        buttons.put(')', closeButton);
        buttons.put('+', sumButton);
        buttons.put('-', subtractionButton);
        buttons.put('*', multiplicationButton);
        buttons.put('/', divisionButton);
        buttons.put('^', powerButton);
        buttons.put('.', pointButton);


        buttons.forEach((ch, button)-> button.setOnAction(event -> {
            try {
                calculator.addSymbol(ch);
                textField.setText(String.valueOf(calculator.getFormula()));
            }
            catch (IllegalArgumentException ex){
                textField.setText("ошибка ввода");
                calculator.deleteFormula();
                ex.printStackTrace();
            }
        }));


        resultButton.setOnAction(event -> {
            try {
                textField.setText(String.valueOf(calculator.start()));
            }
            catch (IllegalArgumentException ex){
                textField.setText("ошибка ввода");
                calculator.deleteFormula();
                ex.printStackTrace();
            }

        });

        historyButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("history.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        deleteButton.setOnAction(event -> {
            calculator.deleteFormula();
            textField.setText(String.valueOf(calculator.getFormula()));
        });

    }


}
