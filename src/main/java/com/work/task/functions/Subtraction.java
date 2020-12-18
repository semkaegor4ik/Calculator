package com.work.task.functions;

import lombok.Data;

@Data
public class Subtraction implements Function {
    private int priority = 1;

    @Override
    public double count(double first, double second) {
        return first - second;
    }
}
