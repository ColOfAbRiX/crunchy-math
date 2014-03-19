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
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * Assignment operator
 * 
 * <p>
 * This operator set a memory variable with a value.
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public class AssignmentOperator extends Operator {

    public AssignmentOperator() throws ConfigException {
        super();
        this.setBaseName( "=" );
        this.setPriority( (short)-1 );
    }

    /**
     * Set a memory variable with the value of the second operand
     * 
     * @param operands A stack containing the operands in reversed order
     * @return It returns the value that has just set in memory
     * @throws ExpressionException If the second operand is not an object of type Operand
     * @throws ExpressionException If the first operand is not a variable name
     */
    @Override
    public Operand executeOperation( Stack<ExpressionEntry> operands ) throws ExpressionException {
        Operand variable, operand;

        if( operands.size() < this.getCurrentOperands() )
            throw new ExpressionException( "Wrong number of given parameters" );

        // The operands must be of type Operand, otherwise Exception
        try {
            variable = (Operand)operands.pop();
            operand = (Operand)operands.pop();
        }
        catch( ClassCastException e ) {
            throw new ExpressionException( "Wrong type of given parameters" );
        }

        // The variable must be... a variable!
        if( !variable.isVariable() )
            throw new ExpressionException( "Cannot assign to a number" );

        return (Operand)this.getContext().getMemory().setValue(
                variable.getVariableName(),
                new Operand( operand.getNumericValue() ) );
    }
}
