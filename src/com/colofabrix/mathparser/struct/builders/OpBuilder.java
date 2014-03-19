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
package com.colofabrix.mathparser.struct.builders;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.struct.Context;

/**
 * Creates operators
 * 
 * <p>
 * This class offer an easy and standard way to create context-operators. A context-operator is an operator with a
 * reference to an operator manager and a memory
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public final class OpBuilder {
    private Context context;

    public OpBuilder( Context context ) {
        this.context = context;
    }
    
    public OpBuilder() { }
    
    /**
     * Creates a new operator with a context
     * 
     * <p>
     * The methd first instantiate a new Operator and the it sets its context<br/>
     * Please note that a call to {@link #newContext()} or to {@link #setMemory(Memory)}/
     * {@link #setOperators(Operators)} must be done to ensure the presence of a context.
     * </p>
     * 
     * @param operator The operator to be created
     * @return A new instance of the specified operator or <code>null<code> if errors during the instantiation
     */
    public Operator create( Class<? extends Operator> operator ) {
        try {
            // Create a new instance of the operator
            Operator newOp = operator.newInstance();

            // Set the context of the operator (Operators and Memory)
            newOp.setContext( this.context );

            return newOp;
        }
        catch( InstantiationException | IllegalAccessException e ) {
            e.printStackTrace();
            return null;
        }
    }
}
