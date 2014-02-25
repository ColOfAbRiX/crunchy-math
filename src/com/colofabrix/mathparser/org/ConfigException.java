package com.colofabrix.mathparser.org;

/**
 * Exception to represent any generic configuration error
 * 
 * @author Fabrizio Colonna
 */
public class ConfigException extends Exception {
	private static final long serialVersionUID = -8014580873208037612L;
	
	public ConfigException() {};
	
	public ConfigException( String message ) {
		super( message );
	}
}
