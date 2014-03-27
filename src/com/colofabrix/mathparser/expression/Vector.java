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
package com.colofabrix.mathparser.expression;

import java.util.Stack;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.builders.ExpressionWorker;

/**
 * Represent the basic type to implement the operators to work with Vectors
 * <p>
 * A vector type will create a vector in memory named {@link Vector#OUTPUT_NAME} which will contain all the operands
 * specified in the vector. The vector type will also refer all the included indexes to the preceding operator.<br/>
 * What's inside a vector will be minimised if possible, otherwise it will be kept as a {@link CmplxExpression}
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public abstract class Vector extends Grouping {

    /**
     * Option to allow minimisation of the vector while parsing it
     */
    public static final Option MINIMIZE = new Option( "$vector_minimise" );

    /**
     * Name of the created variable in memory
     */
    public static final String OUTPUT_NAME = "vector";

    /**
     * Name of the variable that is used for internal works
     */
    public static final String STACK_NAME = ".vector_internal";

    /**
     * Moves the operands and the operators between stacks
     * <p>
     * The vector type uses an internal representation of a vector (a Stack<Expression> stored in memory) which is
     * different than the usual postfix/opstack stacks. This function will fetch the operands from postfix and the
     * operators from opstack and it will put everything in the local stack.<br/>
     * The method will fetch everything was put in the stack until a previous OpenVector or PushVector is found.
     * <p>
     * 
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed before the call to this method
     * @return An Expression found since the last OpeningVector or PushVector
     * @throws ExpressionException If no parameters are given to the function
     */
    protected Expression prepareOperands( CmplxExpression postfix, Stack<Operator> opstack ) throws ExpressionException {
        CmplxExpression local = new CmplxExpression();

        // Call basic constructor. It ensures that the postfix stack is filled with all the operands and operators
        super.executeParsingClosing( postfix, opstack, this.getContext() );

        // Move all the values pushed previously to the saved stack until the previous OpeningFunction is found.

        // First I fetch the values from the last to the one before the opening vector and I put them in a new
        // CmplxExpression
        while( postfix.size() > 0 && !(postfix.lastElement() instanceof Vector && ((Vector)postfix.lastElement()).isOpening()) ) {
            local.add( 0, postfix.pop() );
        }

        // Minimisation of the vector
        if( this.getOption( Vector.MINIMIZE, 0 ) != 0 ) {
            local = ExpressionWorker.packExpression( new MathParser( this.getContext() ).minimise( local ) );
        }

        return ExpressionWorker.unpackExpression( local );
    }
}
