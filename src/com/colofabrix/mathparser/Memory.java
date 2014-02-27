package com.colofabrix.mathparser;

import java.util.*;

import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;

/**
 * It represents the memory containing the variables that the parser uses
 * 
 * @author Fabrizio Colonna
 */
public class Memory {
	
	private Map<String, ExpressionEntry> memory = new HashMap<>();
	
	/**
	 * Default value for the non-defined variables
	 */
	public static final Operand DEFAULT_VALUE = new Operand( 0.0 );
	
	/**
	 * Name of the variable containing the value of the previous calculation
	 */
	public static final String ANSWER_VARIABLE = "Ans";
		
	/**
	 * The constructor initializes the memory with only the ANSWER_VARIABLE variable with the value DEFAULT_VALUE
	 */
	public Memory() {
		this.setValue( Memory.ANSWER_VARIABLE, Memory.DEFAULT_VALUE );
	}
	
	/**
	 * Gets the value of a memory address or the default value
	 * 
	 * @param address The name of the variable to get
	 * @return The value of the memory address corresponding to the variable or DEFAULT_VALUE
	 * @see Memory#DEFAULT_VALUE
	 */
	public ExpressionEntry getValueOrDefault( String address ) {
		if( !this.memory.containsKey(address) )
			return Memory.DEFAULT_VALUE;
		
		ExpressionEntry tmp = this.getValue( address );
		
		if( tmp == null )
			return Memory.DEFAULT_VALUE;
		
		return tmp;
	}
	
	/**
	 * Gets the value of a memory address
	 * 
	 * @param address The name of the variable to get
	 * @return The value of the memory address corresponding to the variable or <code>null</code>
	 */
	public ExpressionEntry getValue( String address ) {
		if( !this.memory.containsKey(address) )
			return null;
		
		return this.memory.get( address );
	}
	
	/**
	 * Sets a value of a memory address
	 * 
	 * <p>If the value that is going to be set is <code>null</code> the corresponding memory address
	 * will be removed from the memory</p>
	 * 
	 * @param address The name of the variable to set
	 * @param value The value to be set. If <code>null</code> the memory address will be deleted
	 * @return The value that has been assigned
	 */
	public ExpressionEntry setValue( String address, ExpressionEntry value ) {
		if( value != null )
			this.memory.put( address, value );
		
		else if ( address.equals(Memory.ANSWER_VARIABLE) )
			this.memory.put( Memory.ANSWER_VARIABLE, Memory.DEFAULT_VALUE );
		
		else
			this.memory.remove( address );
		
		return value;
	}

	/**
	 * Gets a reference to the hashmap which stores the memory
	 * 
	 * @return A Map object containing the memory of the parser
	 */
	public Map<String, ExpressionEntry> getDirectMemoryReference() {
		return this.memory;
	}
}
