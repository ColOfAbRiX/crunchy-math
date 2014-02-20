package com.colofabrix.mathparser.expression;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * It represents an operand in an expression
 * 
 * <p>An operand can be a number or a variable name</p>
 * 
 * @author Fabrizio Colonna
 */
public class Operand extends ExpressionEntry {

	/**
	 * Code to identify the object type
	 */
	public static final int OPERAND_CODE = 1;
	
	private Memory memory;
	private String varName = null;
	private Double value = null;
	
	/**
	 * This constructor creates an operand which contains a variable
	 * 
	 * @param varName Variable name
	 * @param varValue Variable value
	 * @param memory Reference to the memory
	 */
	public Operand( String varName, ExpressionEntry varValue, Memory memory ) {
		this( varName, varValue, true, null, memory );
	}
	
	/**
	 * This constructor creates an operand which contains a variable
	 * 
	 * @param varName Variable name
	 * @param memory Reference to the memory
	 */
	public Operand( String varName,  Memory memory ) {
		this( varName, null, false, null, memory );
	}
	
	/**
	 * This constructor creates an operand which contains a number
	 * 
	 * @param number The number to store
	 */
	public Operand( Double number ) {
		this( null, null, false, number, null );
	}
	
	/**
	 * Generic constructor
	 * 
	 * @param varName Variable name
	 * @param varValue Variable value
	 * @param number The number to store
	 * @param memory Reference to the memory
	 */
	protected Operand( String varName, ExpressionEntry varValue, boolean setVarValue, Double number, Memory memory ) {
		this.value = number;
		this.varName = varName;
		this.memory = memory;
		
		if( this.isVariable() && setVarValue )
			this.memory.setValue( this.varName, varValue );
	}
	
	/**
	 * Returns a code to identify the type of ExprEntry that this class represents
	 * 
	 * @return An integer indicating the ExprEntry type
	 */
	@Override
	public int getEntryType() {
		return Operand.OPERAND_CODE;
	}
	
	/**
	 * Gets the numeric value that this object containts
	 * 
	 * <p>If the object contains a variable, this will be resolved and its content returned. If the
	 * objects contains a number, this will be returned. If the referenced variable is empty, the
	 * function will return <code>null</code><br/>
	 * A referenced memory address can contain another Operand, which means that the value will be dereferenced
	 * again until a numeric value is encountered</p>
	 * 
	 * @return The number stored or <code>null</code>
	 * @throws ExpressionException When there is a mismatch between the variable, the memory or between internal fields
	 */
	public Double getNumericValue() throws ExpressionException {
		if( this.isVariable() ) {
			if( memory == null )
				throw new ExpressionException();
			
			ExpressionEntry memoryValue = this.memory.getValue( this.varName );
			if( memoryValue.getEntryType() != Operand.OPERAND_CODE )
				throw new ExpressionException();
			
			return ((Operand)memoryValue).getNumericValue();
		}		
			
		if( this.value != null ) 
			return this.value;
		
		throw new ExpressionException();
	}
	
	/**
	 * Gets the name of the variable stored.
	 * 
	 * @return A string containing the name of the variable or <code>null</code> if the object contains a number
	 */
	public String getVariableName() {
		return this.varName;
	}
	
	/**
	 * Checks if the objects contains a variable
	 * 
	 * @return <code>true</code> if the object contain a variable and a valid memory reference
	 */
	public boolean isVariable() {
		return (this.memory != null) && (this.varName != null) && (this.value == null);
	}

	/**
	 * Get a string representation of the entry
	 * 
	 * <p>The string representation is commonly used to create output expressions</p>
	 * 
	 * @returns A string containing a representation of the object.
	 */
	@Override
	public String toString() {
		if( this.isVariable() )
			return this.getVariableName();
		
		try {
			return this.getNumericValue().toString();
		}
		catch (ExpressionException e) {
			return "";
		}
	}
}
