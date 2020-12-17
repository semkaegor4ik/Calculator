package com.work.task;

import lombok.Data;

@Data
public class Division implements Function {
    private int priority = 2;

    public Division(int priority) {
        this.priority += priority;
    }

    @Override
    public double count(double first, double second) {
        return first / second;
    }
}
