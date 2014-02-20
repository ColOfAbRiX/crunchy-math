package com.colofabrix.mathparser;

import java.util.*;

import com.colofabrix.mathparser.expression.CompositeExpression;
import com.colofabrix.mathparser.org.*;

public class Main {

    public static void main( String[] args ) {

		try {
	    	MathParser test = new MathParser();
	        ArrayList<String> expressions = new ArrayList<String>();
	        	        
	        expressions.add( "Int[-1, 0, x, x, 0.000001]" );			// Not working
	        //expressions.add( "Int[0, 1, Sin x, x, 0.000001]" );			// Not working
	        //expressions.add( "Int[0, " + Math.PI + ", Sin x, x, 0.000001]" );// Not working
	        //expressions.add( "-1 Int 1 x x 0.000001" );					// Not working

	        for( String expr: expressions ) {
		        CompositeExpression tmp = test.ConvertToPostfix( expr );
		        System.out.println( expr + " -> " + tmp.toString() );
		        System.out.println( "Result: " + test.ExecutePostfix(tmp) );
	        }
		}
		catch (ExpressionException | ConfigException e) {
			e.printStackTrace();
		}
    }
    
}
