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

    private final History history = new History();

    private static Calculator calculator;

    static {
        COMANDS.put('+', Sum.class);
        COMANDS.put('-', Subtraction.class);
        COMANDS.put('*', Multiplication.class);
        COMANDS.put('/', Division.class);
        COMANDS.put('^', Power.class);
    }

    private Calculator() {
        history.createTable();
    }

    public static Calculator getInstance(){
        if(calculator == null)
            calculator = new Calculator();
        return  calculator;
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
            else if(Character.isDigit(characters[i])
                    ||((i==0 || (COMANDS.containsKey(characters[i-1]) && (characters[i-1]!='(' || characters[i-1]!=')')) && characters[i]=='-'))){
                StringBuilder number = new StringBuilder();
                double num = 0;
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
                if(number.charAt(0)=='-'){
                    number.deleteCharAt(0);
                    num = -Double.parseDouble(String.valueOf(number));
                }
                else
                    num = Double.parseDouble(String.valueOf(number));
                numbers.add(num);
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
        if(!brackets.empty()
                || numbers.size()-1 != functions.size()){
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

    public List<String> getHistory(){
        return history.getHistory();
    }

    public double start(){
        parseString();
        double result = calculate();
        String resultStr = String.valueOf(formula.append("="+result));
        history.addFormula(resultStr);
        formula = new StringBuilder();
        numbers = new ArrayList<>();
        functions = new ArrayList<>();
        return result;
    }

    public void addSymbol(char symbol) throws IllegalArgumentException{
        if(formula.length()!=0
                && (((COMANDS.containsKey(formula.charAt(formula.length()-1))
                && COMANDS.containsKey(symbol)) && symbol!='-')
                ||(formula.charAt(formula.length()-1)=='('
                && (symbol == ')'))
                || (symbol=='.'
                && Character.isDigit(formula.charAt(formula.length()-1))))){
            throw new IllegalArgumentException();
        }
        else if((formula.length()==0) && (COMANDS.containsKey(symbol) && symbol!='-'))
            throw new IllegalArgumentException();
        formula.append(symbol);
    }

    public void deleteFormula(){
        formula = new StringBuilder();
    }
}
