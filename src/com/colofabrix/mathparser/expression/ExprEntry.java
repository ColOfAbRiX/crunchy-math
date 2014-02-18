package com.colofabrix.mathparser.expression;

import java.util.Stack;

import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * This is the holder of the expression
 *
 * TODO: Complete here... 
 * 
 * @author Fabrizio Colonna
 */
public class ExprEntry {

	private Object value = null;
	private boolean number = false;
	private boolean operator = false;
	private boolean variable = false;
	private boolean composite = false;
	
	/**
	 * Checks if the expression is a number
	 * 
	 * @return <code>true</code> if the objects contains a number, <code>false</code> otherwise.
	 */
	public boolean isNumber() {
		return this.number;
	}
	
	/**
	 * Checks if the expression is an Operator
	 * 
	 * @return <code>true</code> if the objects contains an Operator, <code>false</code> otherwise.
	 */
	public boolean isOperator() {
		return this.operator;
	}
	
	/**
	 * Checks if the expression is a variable
	 * 
	 * @return <code>true</code> if the objects contains a variable name, <code>false</code> otherwise.
	 */
	public boolean isVariable() {
		return this.variable;
	}

	/**
	 * Checks if the expression is a composite expression
	 * 
	 * @return <code>true</code> if the objects contains more expressions, <code>false</code> otherwise.
	 */
	public boolean isComposite() {
		return this.composite;
	}

	/**
	 * Return the number contained in the object
	 * 
	 * <p>This is valid if and only if <code>{@link ExprEntry.isNumber} == true</code></p>
	 * 
	 * @return A double containing the number stored inside the object 
	 */
	public double getNumber() {
		return (double)this.value;
	}

	/**
	 * Return the Operator contained in the object
	 * 
	 * <p>This is valid if and only if <code>{@link ExprEntry.isOperator} == true</code></p>
	 * 
	 * @return The instance of Operator contained in the object
	 */
	public Operator getOperator() {
		return (Operator)this.value;
	}

	/**
	 * Return the variable name contained in the object
	 * 
	 * <p>This is valid if and only if <code>{@link ExprEntry.isVariable} == true</code></p>
	 * 
	 * @return The name of the variable referenced by the object
	 */
	public String getVariable() {
		return (String)this.value;
	}
	
	/**
	 * Return a stack of ExpressionEntry contained in the object
	 * 
	 * <p>This is valid if and only if <code>{@link ExprEntry.isComposite} == true</code></p>
	 * 
	 * @return A stack containing one or more ExpressionEntry to represent a composite expression
	 */
	@SuppressWarnings("unchecked")
	public Stack<ExprEntry> getComposite() {
		return (Stack<ExprEntry>)this.value;
	}

	@Override
	public String toString() {
		if( this.isComposite() ) {
			String output = "";
			for( ExprEntry entry: this.getComposite() )
				output += entry.toString();
			return output;
		}
		else {
			return this.value.toString();
		}
	}
	
	/**
	 * Creates an instance of ExpressionEntry to hold a number
	 * 
	 * @param value The number to store in the object
	 * @return A new ExpressionEntry to hold the specified number
	 */
	public static ExprEntry createExpression( double value ) {
		ExprEntry tmp = new ExprEntry();
		tmp.value = value;
		tmp.number = true;
		return tmp;
	}

	/**
	 * Creates an instance of ExpressionEntry to hold an Operator
	 * 
	 * @param value The instance of the Operator to store in the object
	 * @return A new ExpressionEntry to hold the specified instance of Operator
	 */
	public static ExprEntry createExpression( Operator value ) {
		ExprEntry tmp = new ExprEntry();
		tmp.value = value;
		tmp.operator = true;
		return tmp;
	}
	
	/**
	 * Creates an instance of ExpressionEntry to hold a variable name or a number
	 * 
	 * @param value The name of the variable or the number to store in the object
	 * @return A new ExpressionEntry to hold the specified variable or number
	 * @throws ExpressionException 
	 */
	public static ExprEntry createExpression( String value ) throws ExpressionException {
		ExprEntry tmp = new ExprEntry();
		
		if( value.matches(MathParser.NUMBER_REGEX) )
			tmp.number = true;
			
		else if( value.matches(MathParser.VARIABLE_REGEX) )
			tmp.variable = true;
			
		else
			throw new ExpressionException();
		
		tmp.value = value;
		return tmp;
	}
		
	/**
	 * Creates an instance of ExpressionEntry to hold a composite expression
	 * 
	 * @param value A stack of ExpressionEntry
	 * @return A new ExpressionEntry to hold the specified stack
	 */
	public static ExprEntry createExpression( Stack<ExprEntry>  value ) {
		ExprEntry tmp = new ExprEntry();
		tmp.value = value;
		tmp.composite = true;
		return tmp;
	}
}
