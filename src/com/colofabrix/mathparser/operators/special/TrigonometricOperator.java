package com.colofabrix.mathparser.operators.special;

import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;

/**
 * Represents a trigonometric operator
 * 
 * <p>This class is used to define common option and methods for all the trigonometri operators</p>
 *  
 * @author Fabrizio Colonna
 */
public abstract class TrigonometricOperator extends Operator {
	
	private DegreesUnits selectedUnit;
	
	/**
	 * Default constructor
	 * 
	 * <p>It initialize the object with RADIANS as measure unit</p>
	 * 
	 * @throws ConfigException
	 */
	public TrigonometricOperator() throws ConfigException {
		this( DegreesUnits.RADIANS );
	}; 

	/**
	 * Constructor with option setting
	 * 
	 * @param unit Select the measure unit for degrees
	 * @throws ConfigException
	 */
	public TrigonometricOperator( DegreesUnits unit ) throws ConfigException {
		this.setSelectedUnit( unit );
		
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}

	/**
	 * Gets the current degrees measure unit
	 * 
	 * @return A code from {@see DegreesUnits} representing the currently selected measure unit
	 */
	public DegreesUnits getSelectedUnit() {
		return selectedUnit;
	}

	/**
	 * Sets the current degrees measure unit
	 * 
	 * @param selectedUnit A code from {@see DegreesUnits} representing the currently selected measure unit
	 */
	public void setSelectedUnit( DegreesUnits selectedUnit ) {
		this.selectedUnit = selectedUnit;
	}
	
	/**
	 * Converts a degrees value from common degrees to radians
	 * 
	 * @param degrees The degrees to convert, expressed in degrees
	 * @return The converted degrees expressed in radians
	 */
	protected double degreesToRadians( double degrees ) {
		return degrees * Math.PI / 180;
	}
	
	/**
	 * Converts a degrees value from common radians to degrees
	 * 
	 * @param radians The degrees to convert, expressed in radians
	 * @return The converted degrees expressed in common degrees
	 */
	protected double radiansToDegrees( double radians ) {
		return radians * 180 / Math.PI;
	}

	/**
	 * Converts a degrees value from gradians to radians
	 * 
	 * @param gradians The degrees to convert, expressed in gradians
	 * @return The converted degrees expressed in radians
	 */
	protected double gradiansToRadians( double gradians ) {
		return gradians * Math.PI / 200;
	}
	
	/**
	 * Converts a degrees value from common radians to gradians
	 * 
	 * @param radians The degrees to convert, expressed in gradians
	 * @return The converted degrees expressed in common radians
	 */
	protected double radiansToGradians( double radians ) {
		return radians * 200 / Math.PI;
	}
	
	/**
	 * Converts a degrees value from the specified value to radians
	 * 
	 * <p>The measure unit of the input value is the one that is set on the object. See
	 * {@link TrigonometricOperator.setSelectedUnit}</p>
	 * 
	 * @param value The value to convert. Its unit of measure is implicit
	 * @return The converted degrees expressed in radians
	 */
	public double getRadians( double value ) {
		
		switch( this.getSelectedUnit() ) {
		default:
		case RADIANS:
			return value; 
			
		case DEGREES:
			return this.degreesToRadians( value );
			
		case GRADIANS:
			return this.gradiansToRadians( value );
		}
	}
	
	/**
	 * Converts a degrees value from radians to the currend unit
	 * 
	 * <p>The measure unit of the output value is the one that is set in the object. See
	 * {@link TrigonometricOperator.setSelectedUnit}</p>
	 * 
	 * @param value The value to convert in radians
	 * @return The converted degrees expressed as the measure unit set in the object
	 */
	public double getCurrent( double radians ) {
		switch( this.getSelectedUnit() ) {
		default:
		case RADIANS:
			return radians; 
			
		case DEGREES:
			return this.radiansToDegrees( radians );
			
		case GRADIANS:
			return this.radiansToGradians( radians );
		}
	}
}