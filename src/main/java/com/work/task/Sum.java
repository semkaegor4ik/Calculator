package com.work.task;

import lombok.Data;

@Data
public class Sum implements Function {
    private int priority = 1;
    public Sum(int priority) {
        this.priority += priority;
    }
    @Override
    public double count(double first, double second) {
        return first + second;
    }
}
