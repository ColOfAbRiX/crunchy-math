package com.colofabrix.mathparser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;

public class ExpressionBuilder {
	
	private Operators operators;
	
	public ExpressionBuilder( Operators operators ) {
		this.operators = operators;
	}
	
	public ExpressionEntry createFromPostfix( Collection<String> postfix ) {
		Stack<ExpressionEntry> result = new Stack<ExpressionEntry>();

		for( String word: postfix ) {
			
			// Create from number
			if( word.matches(MathParser.NUMBER_REGEX) ) 
				result.add( ExpressionEntry.createExpression(Double.parseDouble(word)) );

			// Create from variable
			else if( word.matches(MathParser.VARIABLE_REGEX) )
				result.add( ExpressionEntry.createExpression(word) );

			// Create from Operator
			else if( this.operators.isOperator(word) ) {
				result.add( ExpressionEntry.createExpression(
						(Operator)this.operators.fromName(word).clone()
				) );
			}
			// Create from composite expression
			else
				result.add( this.createFromPostfix(word) );
		}		
		
		return ExpressionEntry.createExpression( result );
	}
	
	public ExpressionEntry createFromPostfix( String postfix ) {
		return this.createFromPostfix( Arrays.asList(postfix.split("\\s")) );
	}
}
