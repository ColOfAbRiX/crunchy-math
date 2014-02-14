package com.colofabrix.mathparser;

import java.util.*;

/**
 * It represents the memory containing the variables that the parser uses
 * 
 * @author Fabrizio Colonna
 */
public class Memory {
	
	private Map<String, Object> memory = new HashMap<String, Object>();
	
	/**
	 * Default value for the non-defined variables
	 */
	public static final double DEFAULT_VALUE = 0.0;
	
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
	 * @see DEFAULT_VALUE
	 */
	public Double getValueOrDefault( String address ) {
		if( !this.memory.containsKey(address) )
			return Memory.DEFAULT_VALUE;
		
		Double tmp = this.getValue( address );
		
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
	public Double getValue( String address ) {
		if( !this.memory.containsKey(address) )
			return null;
		
		if( this.memory.get(address).getClass() != Double.class )
			return null;
		
		return (Double)this.memory.get( address );
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
	public Double setValue( String address, Double value ) {
		if( value != null )
			this.memory.put( address, value );
		
		else if ( address.equals(Memory.ANSWER_VARIABLE) )
			this.memory.put( Memory.ANSWER_VARIABLE, Memory.DEFAULT_VALUE );
		
		else
			this.memory.remove( address );
		
		return value;
	}
	
	/**
	 * Gets the raw value of a memory address
	 * 
	 * @param address The name of the variable to get
	 * @return The value of the memory address corresponding to the variable or <code>null</code>
	 */
	public Object getRaw( String address ) {
		if( !this.memory.containsKey(address) )
			return null;
		
		return this.memory.get( address );
	}
	
	/**
	 * Sets a raw value of a memory address
	 * 
	 * <p>If the value that is going to be set is <code>null</code> the corresponding memory address
	 * will be removed from the memory</p>
	 * 
	 * @param address The name of the variable to set
	 * @param value The value to be set.
	 * @return The value that has been assigned
	 */
	public Object setRaw( String address, Object value ) {
		if( value != null )
			this.memory.put( address, value );
		
		else
			this.memory.remove( address );
		
		return value;
	}
}
