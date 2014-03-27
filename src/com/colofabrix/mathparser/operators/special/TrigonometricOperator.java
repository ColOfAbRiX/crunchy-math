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
import org.apfloat.Apfloat;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.lib.ApfConsts;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * Represents a trigonometric operator
 * 
 * <p>
 * This class is used to define common option and methods for all the trigonometric operators
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public abstract class TrigonometricOperator extends Operator {

    protected static final Option OPTION_UNITS = new Option( "$degrees" );
    protected static final int PI_PRECISION = 200;

    /**
     * Default constructor
     * 
     * <p>
     * It initialise the object with RADIANS as unit of measurement
     * </p>
     * 
     * @throws ConfigException
     */
    public TrigonometricOperator() throws ConfigException {
        this( AngleUnit.RADIANS );
    };

    /**
     * Constructor with option setting
     * 
     * @param unit Select the unit of measurement for degrees
     * @throws ConfigException
     */
    public TrigonometricOperator( AngleUnit unit ) throws ConfigException {
        this.setSelectedUnit( unit );

        this.setPriority( (short)2 );
        this.setOperandsLimit( 1, 1 );
        this.setCurrentOperands( 1 );
    }

    /**
     * This method is used to save a memory reference locally
     */
    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack ) throws ExpressionException {
        return super.executeParsing( postfix, opstack );
    }

    /**
     * Gets the current degrees unit of measurement
     * 
     * @return A code from {@link AngleUnit} representing the currently selected unit of measurement
     * @throws ExpressionException
     */
    public AngleUnit getSelectedUnit() throws ExpressionException {
        final AngleUnit radians = AngleUnit.RADIANS;
        int selected = this.getOption( TrigonometricOperator.OPTION_UNITS, radians.getValue() );
        return AngleUnit.fromValue( selected );
    }

    /**
     * Sets the current degrees unit of measurement
     * 
     * @param selectedUnit A code from {@link AngleUnit} representing the currently selected unit of measurement
     */
    public void setSelectedUnit( AngleUnit selectedUnit ) {
        this.setOption( TrigonometricOperator.OPTION_UNITS, selectedUnit.getValue() );
    }

    /**
     * Converts a degrees value from radians to the current unit
     * 
     * <p>
     * The unit of measurement of the output value is the one that is set in the object. See
     * {@link TrigonometricOperator#setSelectedUnit}
     * </p>
     * 
     * @param radians The value to convert in radians
     * @return The converted degrees expressed as the unit of measurement set in the object
     * @throws ExpressionException
     */
    public Apfloat toCurrentUnit( Apfloat radians ) throws ExpressionException {
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
     * Converts a degrees value from the specified value to radians
     * 
     * <p>
     * The unit of measurement of the input value is the one that is set on the object. See
     * {@link TrigonometricOperator#setSelectedUnit}
     * </p>
     * 
     * @param value The value to convert. Its unit of measure is implicit
     * @return The converted degrees expressed in radians
     * @throws ExpressionException
     */
    public Apfloat toRadians( Apfloat value ) throws ExpressionException {
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
     * Converts a degrees value from common degrees to radians
     * 
     * @param degrees The degrees to convert, expressed in degrees
     * @return The converted degrees expressed in radians
     */
    protected Apfloat degreesToRadians( Apfloat degrees ) {
        return degrees.multiply( ApfConsts.PI.divide( ApfConsts.Angular.DEG_180 ) );
    }

    /**
     * Converts a degrees value from gradians to radians
     * 
     * @param gradians The degrees to convert, expressed in gradians
     * @return The converted degrees expressed in radians
     */
    protected Apfloat gradiansToRadians( Apfloat gradians ) {
        return gradians.multiply( ApfConsts.PI.divide( ApfConsts.Angular.GRAD_200 ) );
    }

    /**
     * Converts a degrees value from common radians to degrees
     * 
     * @param radians The degrees to convert, expressed in radians
     * @return The converted degrees expressed in common degrees
     */
    protected Apfloat radiansToDegrees( Apfloat radians ) {
        return radians.multiply( ApfConsts.Angular.DEG_180.divide( ApfConsts.PI ) );
    }

    /**
     * Converts a degrees value from common radians to gradians
     * 
     * @param radians The degrees to convert, expressed in gradians
     * @return The converted degrees expressed in common radians
     */
    protected Apfloat radiansToGradians( Apfloat radians ) {
        return radians.multiply( ApfConsts.Angular.GRAD_200.divide( ApfConsts.PI ) );
    }
}
