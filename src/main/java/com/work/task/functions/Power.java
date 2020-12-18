package com.work.task.functions;

import lombok.Data;

@Data
public class Power implements Function {
    private int priority = 3;

    @Override
    public double count(double first, double second) {
        return Math.pow(first, second);
    }
}
