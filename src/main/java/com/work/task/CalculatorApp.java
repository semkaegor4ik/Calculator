package com.work.task;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("calculator.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root, 275, 378));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
