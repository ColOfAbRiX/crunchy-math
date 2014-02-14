package com.colofabrix.mathparser;

import java.util.*;

import com.colofabrix.mathparser.org.*;

public class Main {

    public static void main( String[] args ) {

		try {
	    	MathParser test = new MathParser();
	        ArrayList<String> expressions = new ArrayList<String>();
	        
	        expressions.add( "3 * (2 + 1)" );							// 3 2 1 + *
	        //expressions.add( "0 Int 1 Sin(x) x 0.000001" );			// Not working
	        //expressions.add( "0 Int " + Math.PI + " x x 0.000001" );	// Not working
	        //expressions.add( "-1 Int 1 x x 0.000001" );				// Not working
	        expressions.add( "0 Int 1 x x 0.000001" );					// 0 1 x x 0.0000001 5#Int
	        expressions.add( "x = 2 * Sin 5 / 1 + 6 - 3" ); 		 	// x 2 5 #Sin 1 / * 6 + 3 - =
	        expressions.add( "y = x ^ 2" );                				// y x 2 ^ =
	        expressions.add( "x = (2 * 5) + Sin 6" );					// x 2 5 * 6 #Sin + =
	        expressions.add( "x = 2 * (5 + Sin 6)" );					// x 2 5 6 #Sin + * =
	        expressions.add( "x = 2 * (5 + Sin (6))" );					// x 2 5 6 #Sin + * =
	        expressions.add( "x = 2 * (5 + Sin 6) / 7" );				// x 2 5 6 #Sin + 7 / * =
	        expressions.add( "x = 2 * (5 + Sin 6) / (7 + 1)" );			// x 2 5 6 #Sin + 7 1 + / * =
	        
	        for( String expr: expressions ) {
		        Stack<String> tmp = test.ConvertToPostfix( expr );
		        System.out.println( expr + " -> " + tmp.toString() );
		        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
	        }
		}
		catch (ExpressionException | ConfigException e) {
			e.printStackTrace();
		}
    }
    
}
