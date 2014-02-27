package com.colofabrix.mathparser.operators.special;

/**
 * The available unit of measurement to express degrees
 * 
 * @author Fabrizio Colonna
 */
public enum DegreesUnits {
	/**
	 * Degrees {@link <a href="http://en.wikipedia.org/wiki/Degree_(angle)">Degrees</a>}
	 */
	DEGREES(1),
	
	/**
	 * Radians {@link <a href="http://en.wikipedia.org/wiki/Radian">Radians</a>}
	 */
	RADIANS(2),
	
	/**
	 * Gradians {@link <a href="http://en.wikipedia.org/wiki/Gradian">Gradians</a>}
	 */
	GRADIANS(3);

	private int value;
	
	DegreesUnits( int value ) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public static DegreesUnits fromValue( int value ) {
		switch( value ) {
		case 1:
			return DEGREES;
		default:
		case 2:
			return RADIANS;
		case 3:
			return GRADIANS;
		}
	}
}
