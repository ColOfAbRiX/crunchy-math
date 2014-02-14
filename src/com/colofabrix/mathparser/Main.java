package com.colofabrix.mathparser;

import java.util.Stack;

import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class Main {

    public static void main( String[] args ) {

		try {
	    	MathParser test = new MathParser();
	        String expr;
	        Stack<String> tmp;
	        
	        expr = "0 Int 1 Sin(x) x 0.0000001";			// Not working
	        expr = "0 Int " + Math.PI + " x x 0.0000001";	// Not working
	        expr = "-1 Int 1 x x 0.0000001";				// Not working
	        expr = "0 Int 1 x x 0.0000001";
	        tmp = test.ConvertToPostfix( expr );
	        tmp.set( 2, "Sin x" );
	        System.out.println( expr + " -> " + tmp.toString() );
	        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
	
	        expr = "x = 2 * Sin 5 / 1 + 6 - 3"; 		 	// x 2 5 #Sin 1 / * 6 + 3 - =
	        tmp = test.ConvertToPostfix( expr );
	        System.out.println( expr + " -> " + tmp.toString() );
	        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
	
	        expr = "y = x ^ 2";                				// y x 2 ^ =
	        tmp = test.ConvertToPostfix( expr );
	        System.out.println( expr + " -> " + tmp.toString() );
	        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
	        
	        expr = "x = (2 * 5) + Sin 6";               	 // x 2 5 * 6 #Sin + =
	        tmp = test.ConvertToPostfix( expr );
	        System.out.println( expr + " -> " + tmp.toString() );
	        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
	
	        expr = "x = 2 * (5 + Sin 6)";
	        tmp = test.ConvertToPostfix( expr );
	        System.out.println( expr + " -> " + tmp.toString() );
	        System.out.println( "Result: " +test.ExecutePostfix(tmp) );
	
	        expr = "x = 2 * (5 + Sin (6))";
	        tmp = test.ConvertToPostfix( expr );
	        System.out.println( expr + " -> " + tmp.toString() );
	        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
	
	        expr = "x = 2 * (5 + Sin 6) / 7";
	        tmp = test.ConvertToPostfix( expr );
	        System.out.println( expr + " -> " + tmp.toString() );
	        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
	
	        expr = "x = 2 * (5 + Sin 6) / (7 + 1)";
	        tmp = test.ConvertToPostfix( expr );
	        System.out.println( expr + " -> " + tmp.toString() );
	        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
		}
		catch (ExpressionException | ConfigException e) {
			e.printStackTrace();
		}
    }
    
}
