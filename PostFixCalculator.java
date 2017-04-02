package postfixcalculator;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Temi
 * This class represents a PostFix Calculator
 * This PostFix Calculator can converter an infix with single or multiple digits into a post fix
 */

public class PostFixCalculator {
    
    private final HashMap<String,Integer> priorityMap;
    
    public PostFixCalculator(){
        priorityMap = new HashMap<>();
        priorityMap.put("+", 1);
        priorityMap.put("-", 1);
        priorityMap.put("*", 2);
        priorityMap.put("/", 2);
    }
    
    /*
     *  Gets infix input from user
     */
    
    public String getInfix(){
        Scanner scanner = new Scanner (System.in);    
        String input = scanner.next();
        String postfix = "";
        
        while (!input.equalsIgnoreCase("end")){
            postfix += input + " ";
            
            input = scanner.next();
        }
        return postfix;
    }
    
    /*
     *  Returns true if s is int
     */
    
    public boolean isInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    /*
     *  Returns true if s is double
     */
    
    public boolean isDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    /*
     *  Returns true if one of valid operators
     */
    
    public boolean isValidOperator(String s){
        if(s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-") || s.equals("(") || s.equals(")")){
            return true;
        }
        return false;
    }
    
    /*
     * Returns true if key1's value is greater priority than key2's value
     */
    
    public boolean isHigherPriority(String key1, String key2){
        int value1 = priorityMap.get(key1);
        int value2 = priorityMap.get(key2);
        if (value1 > value2){
            return true;
        }
        return false;
    }
    
    /*
     * Converts infix into postfix 
     * Throws InvalidOperationException if invalid operation is inputed
     */
    public String covertToPostFix(String infix) throws InvalidOperatorException{
        String postFix = "";
        Stack<String> stack = new Stack<>();
        
        //array split on space
        String [] postFixArray = infix.split(" ");
        for(int i = 0; i< postFixArray.length; i++){
            String character = postFixArray[i];
            
            //if operand
            if(isInt(character) || isDouble(character)){
                postFix += character + " " ;
            }
            
            //else operator 
            else{
                
                //check if valid operator - so can put in stack
                if(isValidOperator(character)){
                    
                    //check if empty
                    if(stack.isEmpty()){
                        stack.push(character);
                    }
                    
                    //check if open parenthesis
                    else if(character.equals("(")){
                        stack.push(character);
                    }
                    
                    //check if closed parenthesis
                    else if(character.equals(")")){
                        String topObject = stack.peek();
                        
                        //pop from stack until open parenthesis is on the top
                        while(!topObject.equals("(")){
                            postFix += topObject + " " ;
                            stack.pop();
                            topObject = stack.peek();
                        }
                        
                        //pop the open parenthesis
                        stack.pop();
                    }
                    
                    //check priority
                    else{
                        
                        String topObject = stack.peek();
                        
                        //if the top object is open parenthesis
                        if(topObject.equals("(")){
                            stack.push(character);
                        }
                        //check if character is higher priority
                        else if(isHigherPriority(character,topObject)){
                            stack.push(character);
                        }
                        
                        //if character is of lower or equal priority
                        else{
                            
                            //pop from stack until character is of equal or higher priority than topObject
                            while(isHigherPriority(topObject,character)){
                                postFix += topObject + " ";
                                stack.pop();
                                
                                //check if the stack is empty
                                if(!stack.isEmpty()){
                                    topObject = stack.peek();
                                }else{
                                    break;
                                }
                            } //end while
                            // character is of higher or equal priorty than the top object
                            stack.push(character);
                        }
                    }
                }
                //not valid operator
                else{
                    throw new InvalidOperatorException("Invalid Operator : " + character);

                }
            }

        }
        
        //pop remaining characters in stack
        while(!stack.isEmpty()){
            postFix += stack.peek() + " ";
            stack.pop();
        }
        
        return postFix;
    }
    
    /*
     *  Returns evaluated post fix
     *  Returns - 1 if invalid operator found
     */
    
    public double evaluatePostFix(String postFix){
        
        
        double answer = 0;
        String [] postFixArray = postFix.split(" ");
        Stack<String> stack = new <String> Stack();
        
        for (int i = 0; i < postFixArray.length; i++) {
            String character = postFixArray[i];
            
            // check if character
            if (isInt(character) || isDouble(character)) {
                stack.push(character);
            
            // its operator
            } else {
                double firstNumber = Double.parseDouble(stack.pop());
                double secondNumber = Double.parseDouble(stack.pop());
                double result = 0;
                
                //carry out operation 
                try{
                    result = applyOperation(firstNumber,secondNumber,character);
                }catch(InvalidOperatorException e){
                    System.out.println(e.getMessage());
                }

                //push result to stack
                stack.push(Double.toString(result));
            }
        }
        
        //remove answer from the stack
        answer = Double.parseDouble(stack.peek());
        stack.pop();

        return answer;
    }
    
    /*
     * Apply operation to firstNumber and secondNumber
     * Throws InvalidOperationException if invalid operation is inputed
     */
    public double applyOperation(double firstNumber,double secondNumber,String operation) throws InvalidOperatorException{
        double result = 0;

        //carry out operation
        switch (operation) {
            case "+":
                result = secondNumber + firstNumber;
                break;
            case "-":
                result = secondNumber - firstNumber;
                break;
            case "*":
                result = secondNumber * firstNumber;
                break;
            case "/":
                result = secondNumber / firstNumber;
                break;
            //invalid operator
            default:
                throw new InvalidOperatorException("Invalid Operation : " + operation);
        }

        throw new InvalidOperatorException("Invalid Operation : " + operation); //should not reach
    }
}
