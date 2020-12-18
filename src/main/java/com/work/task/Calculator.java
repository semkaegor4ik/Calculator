package com.work.task;

import com.work.task.functions.*;
import lombok.Data;


import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class Calculator {
    private static final Map<Character, Class<? extends Function>> COMANDS = new HashMap<>();

    private StringBuilder formula = new StringBuilder();

    private List<Function> functions = new ArrayList<>();

    private List<Double>  numbers = new ArrayList<>();

    private final List<String> history = new ArrayList<>();

    private final UIController controller;

    static {
        COMANDS.put('+', Sum.class);
        COMANDS.put('-', Subtraction.class);
        COMANDS.put('*', Multiplication.class);
        COMANDS.put('/', Division.class);
        COMANDS.put('^', Power.class);
    }

    public Calculator(UIController controller) {
        this.controller = controller;
    }

    private void parseString(){
        Stack<Character> brackets = new Stack<>();
        int currentPriority = 0;
        char[] characters = formula.toString().toCharArray();
        for (int i = 0; i < characters.length ; i++) {
            if(characters[i]=='(') {
                currentPriority += 3;
                brackets.push('(');
            }
            else if(characters[i]==')') {
                currentPriority -= 3;
                try{
                brackets.pop();
                }
                catch (EmptyStackException ex){
                    throw new IllegalArgumentException();
                }
            }
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
                if(!Character.isDigit(characters[i]))
                    i--;
                numbers.add(Double.parseDouble(String.valueOf(number)));
            }
            else if(COMANDS.containsKey(characters[i])){
                try {
                    Function function = (Function)COMANDS.get(characters[i]).getDeclaredConstructor().newInstance();
                    function.setPriority(function.getPriority()+currentPriority);
                    functions.add(function);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            else
                throw new IllegalArgumentException();                   //
        }
        if(!brackets.empty()){
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
        formula = new StringBuilder();
        numbers = new ArrayList<>();
        functions = new ArrayList<>();
        return result;
    }

    public double start(){
        parseString();
        return calculate();
    }

    public void addSymbol(char symbol){
        if(formula.length()!=0
            &&COMANDS.containsKey(formula.charAt(formula.length()-1))
                && COMANDS.containsKey(symbol))
            throw new IllegalArgumentException();                   //
        else if(formula.length()!=0
                && formula.charAt(formula.length()-1)=='('
                && ((symbol == ')')
                || COMANDS.containsKey(symbol)))
            throw new IllegalArgumentException();
        else if(formula.length()!=0
                &&symbol=='.'
                && Character.isDigit(formula.charAt(formula.length()-1)))
            throw new IllegalArgumentException();

        formula.append(symbol);
    }

    public static void main(String[] args) {/*
        Calculator calculator = new Calculator();
        calculator.setFormula(new StringBuilder("2+4*(6^2-5)"));
        calculator.start();*/
    }
}
