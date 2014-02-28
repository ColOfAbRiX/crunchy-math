/*
Crunchy Math, Version 1.0, February 2014
Copyright (C) 2014 Fabrizio Colonna <colofabrix@gmail.com>

This file is part of Crunchy Math.

Crunchy Math is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

Crunchy Math is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with Crunchy Math; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/
package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * Represents a trigonometric operator
 * 
 * <p>This class is used to define common option and methods for all the trigonometri operators</p>
 *  
 * @author Fabrizio Colonna
 */
public abstract class TrigonometricOperator extends Operator {
	
	protected static final String OPTION_UNITS = "$degrees";
	private Memory memory;
	
	/**
	 * Default constructor
	 * 
	 * <p>It initialize the object with RADIANS as unit of measurement</p>
	 * 
	 * @throws ConfigException
	 */
	public TrigonometricOperator() throws ConfigException {
		this( DegreesUnits.RADIANS );
	}; 

	/**
	 * Constructor with option setting
	 * 
	 * @param unit Select the unit of measurement for degrees
	 * @throws ConfigException
	 */
	public TrigonometricOperator( DegreesUnits unit ) throws ConfigException {
		this.setSelectedUnit( unit );
		
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}

	/**
	 * Gets the current degrees unit of measurement
	 * 
	 * @return A code from {@link DegreesUnits} representing the currently selected unit of measurement
	 * @throws ExpressionException 
	 */
	public DegreesUnits getSelectedUnit() throws ExpressionException {
		if( this.memory == null )
			return DegreesUnits.RADIANS;
		
		ExpressionEntry value = this.memory.getValue( TrigonometricOperator.OPTION_UNITS );
		
		if( value == null || value.getEntryType() != Operand.OPERAND_CODE ) {
			return DegreesUnits.RADIANS;
		}
		
		return DegreesUnits.fromValue( ((Operand)value).getNumericValue().intValue() );
	}

	/**
	 * Sets the current degrees unit of measurement
	 * 
	 * @param selectedUnit A code from {@link DegreesUnits} representing the currently selected unit of measurement
	 */
	public void setSelectedUnit( DegreesUnits selectedUnit ) {
		if( this.memory == null )
			return;
		
		this.memory.setValue( TrigonometricOperator.OPTION_UNITS, new Operand(selectedUnit.getValue() + 0.0) );
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
	 * <p>The unit of measurement of the input value is the one that is set on the object. See
	 * {@link TrigonometricOperator#setSelectedUnit}</p>
	 * 
	 * @param value The value to convert. Its unit of measure is implicit
	 * @return The converted degrees expressed in radians
	 * @throws ExpressionException 
	 */
	public double getRadians( double value ) throws ExpressionException {
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
	 * <p>The unit of measurement of the output value is the one that is set in the object. See
	 * {@link TrigonometricOperator#setSelectedUnit}</p>
	 * 
	 * @param radians The value to convert in radians
	 * @return The converted degrees expressed as the unit of measurement set in the object
	 * @throws ExpressionException 
	 */
	public double getCurrent( double radians ) throws ExpressionException {
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
	
	/**
	 * This method is used to save a memory reference locally
	 */
	@Override
	public Operator executeParsing(CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory) throws ExpressionException {
		this.memory = memory;
		return super.executeParsing(postfix, opstack, operators, memory);
	}
}