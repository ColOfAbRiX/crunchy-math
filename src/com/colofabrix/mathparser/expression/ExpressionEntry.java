package com.colofabrix.mathparser.expression;

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
	 * @return A string containing a representation of the object.
	 */
	@Override
	public abstract String toString();
	
	/**
	 * Checks if an expression is minimizable
	 * 
	 * <p>An expression is minimizable if it doesn't contain any variable</p>
	 * 
	 * @return <code>true</code> if the expression is minimizable</code>
	 */
	public abstract boolean isMinimizable();

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
			return (ExpressionEntry)operators.fromName( word ).clone();
		
		// Creates a number operand
		else if( word.matches(Operand.NUMBER_REGEX) )
			return new Operand( Double.parseDouble(word) );
		
		// Creates an option
		else if( word.matches(Option.OPTION_REGEX) )
			return new Option( word );
		
		// Creates a variable operand
		else if( word.matches(Operand.VARIABLE_REGEX) )
			return new Operand( word, memory );
		
		// Not recognized
		else
			return null;
	}
}
