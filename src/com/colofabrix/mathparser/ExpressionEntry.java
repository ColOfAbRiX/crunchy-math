package com.colofabrix.mathparser;

import java.util.Stack;

/**
 * This is the holder of the expression
 *
 * TODO: Complete here... 
 * 
 * @author Fabrizio Colonna
 */
public class ExpressionEntry {

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
	 * Return the number contained in the object
	 * 
	 * <p>This is valid if and only if <code>{@link ExpressionEntry.isNumber} == true</code></p>
	 * 
	 * @return A double containing the number stored inside the object 
	 */
	public double getNumber() {
		return (double)this.value;
	}
	
	/**
	 * Creates an instance of ExpressionEntry to hold a number
	 * 
	 * @param value The number to store in the object
	 * @return A new ExpressionEntry to hold the specified number
	 */
	public static ExpressionEntry createExpression( double value ) {
		ExpressionEntry tmp = new ExpressionEntry();
		tmp.value = value;
		tmp.number = true;
		return tmp;
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
	 * Return the Operator contained in the object
	 * 
	 * <p>This is valid if and only if <code>{@link ExpressionEntry.isOperator} == true</code></p>
	 * 
	 * @return The instance of Operator contained in the object
	 */
	public Operator getOperator() {
		return (Operator)this.value;
	}

	/**
	 * Creates an instance of ExpressionEntry to hold an Operator
	 * 
	 * @param value The instance of the Operator to store in the object
	 * @return A new ExpressionEntry to hold the specified instance of Operator
	 */
	public static ExpressionEntry createExpression( Operator value ) {
		ExpressionEntry tmp = new ExpressionEntry();
		tmp.value = value;
		tmp.operator = true;
		return tmp;
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
	 * Return the variable name contained in the object
	 * 
	 * <p>This is valid if and only if <code>{@link ExpressionEntry.isVariable} == true</code></p>
	 * 
	 * @return The name of the variable referenced by the object
	 */
	public String getVariable() {
		return (String)this.value;
	}
	
	/**
	 * Creates an instance of ExpressionEntry to hold a variable name
	 * 
	 * @param value The name of the variable to store in the object
	 * @return A new ExpressionEntry to hold the specified variable
	 */
	public static ExpressionEntry createExpression( String value ) {
		ExpressionEntry tmp = new ExpressionEntry();
		tmp.value = value;
		tmp.variable = true;
		return tmp;
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
	 * Return a stack of ExpressionEntry contained in the object
	 * 
	 * <p>This is valid if and only if <code>{@link ExpressionEntry.isComposite} == true</code></p>
	 * 
	 * @return A stack containing one or more ExpressionEntry to represent a composite expression
	 */
	@SuppressWarnings("unchecked")
	public Stack<ExpressionEntry> getComposite() {
		return (Stack<ExpressionEntry>)this.value;
	}
	
	/**
	 * Creates an instance of ExpressionEntry to hold a composite expression
	 * 
	 * @param value A stack of ExpressionEntry
	 * @return A new ExpressionEntry to hold the specified stack
	 */
	public static ExpressionEntry createExpression( Stack<ExpressionEntry>  value ) {
		ExpressionEntry tmp = new ExpressionEntry();
		tmp.value = value;
		tmp.composite = true;
		return tmp;
	}
	
	@Override
	public String toString() {
		if( this.isComposite() ) {
			String output = "";
			for( ExpressionEntry entry: this.getComposite() )
				output += entry.toString();
			return output;
		}
		else {
			return this.value.toString();
		}
	}
}
