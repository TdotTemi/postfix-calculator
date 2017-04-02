package postfixcalculator;

import java.util.ArrayList;

/**
 *
 * @author Temi
 */

public class Client {

    public static void main(String[] args) {
        
        PostFixCalculator calculator = new PostFixCalculator();
        String infix = calculator.getInfix();
        System.out.println("Infix : " + infix);
        
        try{
            String postFix = calculator.covertToPostFix(infix);
            System.out.println("Postfix : " + postFix);
            double result = calculator.evaluatePostFix(postFix);
            System.out.println("Evaluated Postfix expression is : " + result);
        }catch(InvalidOperatorException e){
            System.out.println(e.getMessage());
        }
  
    }
    
}
