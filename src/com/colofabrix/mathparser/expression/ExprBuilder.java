package com.colofabrix.mathparser.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;

import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.org.ExpressionException;

public class ExprBuilder {

	Operators operators;
	
	public ExprBuilder( Operators operators ) {
		this.operators = operators;
	}
	
	public Stack<ExprEntry> buildExpression( Collection<String> expression ) throws ExpressionException {
		Stack<ExprEntry> result = new Stack<ExprEntry>();

		for( String word: expression ) {
			
			// Create from Operator - This must be the first check, because the variable check can match operators!
			if( this.operators.isOperator(word) )
				result.add(
					ExprEntry.createExpression( (Operator)this.operators.fromName(word).clone() ) );
			
			// Create from number
			else if( word.matches(MathParser.NUMBER_REGEX) ) 
				result.add(
					ExprEntry.createExpression(Double.parseDouble(word)) );

			// Create from variable
			else if( word.matches(MathParser.VARIABLE_REGEX) )
				result.add(
					ExprEntry.createExpression(word) );

			// Create from composite expression
			else
				result.add(
					ExprEntry.createExpression(this.buildExpression(word)) );
		}		
		
		return result;
	}
	
	public Stack<ExprEntry> buildExpression( String expression ) throws ExpressionException {
		// The input string is split in its forming components and translated in object-form
		Matcher m = this.operators.getParsingRegex().matcher( expression );
		List<String> allMatches = new ArrayList<String>();
		
		// Saves all matches in a list
		while( m.find() )
			allMatches.add( m.group() );
		
		return this.buildExpression( allMatches );
	}
}
