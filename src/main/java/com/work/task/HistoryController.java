package com.work.task;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class HistoryController {
    @FXML
    private TextArea history;

    private final Calculator calculator = Calculator.getInstance();

    @FXML
    void initialize() {
        history.setText("");
        calculator.getHistory().forEach(str->{
            history.appendText(str+"\n");
        });
    }

}
