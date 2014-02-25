package com.colofabrix.mathparser.expression;

/**
 * It represents an option that is configurable in the system
 * 
 * @author Fabrizio Colonna
 */
public class Option extends ExpressionEntry {

	private String name;
	private ExpressionEntry value;
	
	/**
	 * Code to identify the object type
	 */
	public static final int OPTION_CODE = 4;
	
	/**
	 * Marker to identify an option
	 */
	public static final String OPTION_MARK = "$";

	/**
	 * Regular expression to match an option name
	 */
	public static final String OPTION_REGEX = "\\" + Option.OPTION_MARK + "([a-zA-Z_]|\\.[a-zA-Z_])[a-zA-Z0-9_]*";

	/**
	 * Constructor
	 * 
	 * @param name Name of the option
	 */
	public Option( String name ) {
		this.name = name.replaceAll("^\\" + Option.OPTION_MARK, "" );
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return Return the value of the option
	 */
	public ExpressionEntry getValue() {
		return this.value;
	}

	/**
	 * @param value The value to set
	 */
	public void setValue( ExpressionEntry value ) {
		this.value = value;
	}
	
	/**
	 * Returns a code to identify the type of ExprEntry that this class represents
	 * 
	 * @return An integer indicating the ExprEntry type
	 */
	@Override
	public int getEntryType() {
		return Option.OPTION_CODE;
	}
	
	public String toString() {
		return Option.OPTION_MARK + this.getName();
	}

	@Override
	public boolean isMinimizable() {
		return false;
	}

}
