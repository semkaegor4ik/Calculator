package com.work.task.functions;

import lombok.Data;

@Data
public class Division implements Function {
    private int priority = 2;

    @Override
    public double count(double first, double second) {
        return first / second;
    }
}
