package com.colofabrix.mathparser.expression;

import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * This is the holder of the expression
 *
 * @author Fabrizio Colonna
 */
public abstract class ExpressionEntry {

	/**
	 * Find the type of entry the object represents
	 * 
	 * <p>This method is used to identify the type of the object stored in the entry. It must be
	 * overridden by teh derived classes</p>
	 * 
	 * @return An integer wich uniquely identify the entry type
	 */
	public abstract int getEntryType();
	
	/**
	 * Get a string representation of the entry
	 * 
	 * <p>The string representation is commonly used to create output expressions</p>
	 * 
	 * @returns A string containing a representation of the object.
	 */
	@Override
	public abstract String toString();
	
	/**
	 * Creates an instance of ExpressionEntry to hold an 
	 * 
	 * <p>This builder starts from a single string entry, a single token like a number or an operator,
	 * and creates the right ExprEntry object</p>
	 * 
	 * @param word The name of the variable or the number to store in the object
	 * @return A new ExpressionEntry to hold the specified variable or number
	 * @throws ExpressionException 
	 */
	public static ExpressionEntry fromStringEntry( String word, Operators operators, Memory memory ) throws ExpressionException {
		// Creates an operator
		if( operators.isOperator(word) )
			return operators.fromName( word );
		
		// Creates a number operand
		else if( word.matches(MathParser.NUMBER_REGEX) )
			return new Operand( Double.parseDouble(word) );
		
		// Creates a variable operand
		else if( word.matches(MathParser.VARIABLE_REGEX) )
			return new Operand( word, memory );
		
		// Not recognized
		else
			return null;
	}
}
