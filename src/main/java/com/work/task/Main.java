package com.work.task;


public class Main {
    public static void main(String[] args) {
        History history = new History();
        history.createTable();
        history.addFormula("5+3+8");
        System.out.println(history.getHistory());
    }
}
