package com.work.task.controllers;

import com.work.task.Calculator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class HistoryController {
    @FXML
    private TextArea history;
    @FXML
    private Button cleanHistory;

    private final Calculator calculator = Calculator.getInstance();

    @FXML
    void initialize() {
        history.setText("");
        calculator.getLastOperations().forEach(str->{
            history.appendText(str+"\n");
        });

        cleanHistory.setOnAction(event -> {
            calculator.clearHistory();
            history.setText("");
        });
    }

}
