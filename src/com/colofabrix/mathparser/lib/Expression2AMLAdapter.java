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
package com.colofabrix.mathparser.lib;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apfloat.Apfloat;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * This class is used to interface with the Apache Math Library
 * 
 * @author Fabrizio Colonna
 */
public class Expression2AMLAdapter implements UnivariateFunction {

    private final MathParser mp;
    private final Memory mem;
    Expression expression;
    Expression oldMemory;
    Operand variable;

    /**
     * The constructor saves internally the expression to evaluate
     * 
     * @param mp A math parser to execute the expression
     * @param expression The expression to evaluate
     * @param variable The integration variable
     * @throws ExpressionException
     */
    public Expression2AMLAdapter( MathParser mp, Expression expression, Operand variable ) throws ExpressionException {
        this.mp = mp;
        this.mem = this.mp.getContext().getMemory();
        this.expression = expression;
        this.variable = variable;

        // Save the value of a possible old variable
        // NOTE: Here the main memory is used and not a new one because there may be memory references in the
        // variables inside the expression to evaluate
        this.oldMemory = this.mem.getValue( variable.getVariableName() );
        // Check for read-only
        this.mem.setValue( this.variable.getVariableName(), this.oldMemory );
    }

    /**
     * Finalises the wrapper
     */
    @Override
    public void finalize() {
        // I checked in the constructor that the variable is not read-only
        try {
            // Restore the old variable in memory
            this.mem.setValue( this.variable.getVariableName(), this.oldMemory );
        }
        catch( ExpressionException e ) {
        }
    }

    /**
     * Calculate the value of the function in a point
     */
    @Override
    public double value( double arg0 ) {
        // I have already checked in the constructor that the variable is not read-only
        try {
            // Set the variable value in the memory
            this.mem.setValue( this.variable.getVariableName(),
                    new Operand( new Apfloat( arg0 ) ) );

            // Calculate the value of the expression
            Expression result = this.mp.executePostfix( this.expression );
            return Operand.extractNumber( result ).doubleValue();
        }
        catch( ExpressionException e ) {
            throw new IllegalArgumentException();
        }
    }
}
