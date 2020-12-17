package com.work.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Calculator {

    private StringBuilder formula = new StringBuilder();

    private final List<Function> functions = new ArrayList<>();

    private final List<Double>  numbers = new ArrayList<>();

    private final List<String> history = new ArrayList<>();


    private void parseString(){
        int currentPriority = 0;
        char[] characters = formula.toString().toCharArray();
        for (int i = 0; i < characters.length ; i++) {
            if(characters[i]=='(')
                currentPriority+=2;
            else if(characters[i]==')')
                currentPriority-=2;
            else if(Character.isDigit(characters[i])){
                StringBuilder number = new StringBuilder();
                number.append(characters[i]);
                if(i < characters.length - 1)
                    i++;
                while ((Character.isDigit(characters[i])
                        || characters[i]=='.')
                        && i < characters.length - 1) {
                    number.append(characters[i]);
                    if(i < characters.length - 1)
                        i++;
                }
                if(i != characters.length-1)
                    i--;
                numbers.add(Double.parseDouble(String.valueOf(number)));
            }
            else if(characters[i]=='/'){
                functions.add(new Division(currentPriority));
            }
            else if(characters[i]=='+'){
                functions.add(new Sum(currentPriority));
            }
            else if(characters[i]=='-'){
                functions.add(new Subtraction(currentPriority));
            }
            else if(characters[i]=='*'){
                functions.add(new Multiplication(currentPriority));
            }
            else
                throw new IllegalArgumentException();
        }
    }

    private double calculate(){
        double result = 0;
        while (functions.size()!=0){
            AtomicInteger maxPriority = new AtomicInteger(Integer.MIN_VALUE);
            AtomicReference<Function> function =new AtomicReference<>();
            functions.forEach(func->{
                if(func.getPriority()> maxPriority.get()){
                    maxPriority.set(func.getPriority());
                    function.set(func);
                }
            });

            result = function.get().count(numbers.get(functions.indexOf(function.get())), numbers.get(functions.indexOf(function.get())+1));
            numbers.remove(functions.indexOf(function.get()));
            numbers.set(functions.indexOf(function.get()), result);
            functions.remove(function.get());
        }
        return result;
    }

    public void start(){
        parseString();
        System.out.println(formula + "=" + calculate());

        formula = new StringBuilder();
    }

    public void addSymbol(char symbol){
        formula.append(symbol);
    }
    public static void main(String[] args) {
    }
}
