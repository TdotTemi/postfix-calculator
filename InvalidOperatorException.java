/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postfixcalculator;

/**
 *
 * @author Admin
 */
public class InvalidOperatorException extends Exception{
    
    public InvalidOperatorException(String message){
        super(message);
    }
}
