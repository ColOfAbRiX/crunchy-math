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
import com.colofabrix.mathparser.*;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * A vector push operator
 * 
 * <p>
 * A vector push is a delimiter that splits the last index from a new one. It is used to create subexpressions inside
 * the vector
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public class VectorPush extends Vector {

    public VectorPush() throws ConfigException {
        super();
        this.setBaseName( "," );
        this.setPriority( (short)2 );
    }

    /**
     * It moves the last operands and operators
     * 
     * <p>
     * It appends the vector entries to the postfix stack
     * </p>
     */
    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
        CmplxExpression stack = (CmplxExpression)memory.getValue( Vector.STACK_NAME );

        if( stack == null )
            throw new ExpressionException( "There is no previous vector to push into" );

        if( postfix.size() < 1 )
            throw new ExpressionException( "Wrong number of given parameters" );

        // Save the operands to the stack
        stack.push( this.prepareOperands( postfix, opstack, operators, memory ) );

        // Save the private stack
        memory.setValue( Vector.STACK_NAME, stack );

        return this;
    }

    @Override
    public boolean isOpening() {
        return true;
    }
}
