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
package com.colofabrix.mathparser.org;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.Operator;

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
    private static MathConstant constants;
    private static Memory memory;
    private static Operators operators;

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
    public static Operator createOperator( Class<? extends Operator> operator ) {
        try {
            // Create a new instance of the operator
            Operator newOp = operator.newInstance();

            // Set the context of the operator (Operators and Memory)
            newOp.setContext( OpBuilder.getOperators(), OpBuilder.getMemory() );

            return newOp;
        }
        catch( InstantiationException | IllegalAccessException e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the current memory
     * 
     * @return A reference to the currently used memory
     */
    public static Memory getMemory() {
        return OpBuilder.memory;
    }

    /**
     * Gets the current operator manager
     * 
     * @return A reference to the currently used operator manager
     */
    public static Operators getOperators() {
        return OpBuilder.operators;
    }

    /**
     * Create a new context
     * 
     * <p>
     * A context is the pair <Operators, Memory> that identifies a working area for the operators<br/>
     * The new context will be applied every time {@link #createOperator(Class)} is called
     * </p>
     * 
     * @return <code>true</code> if the context is renewd, <code>false</code> otherwise.
     */
    public static boolean newContext() {
        Memory oldMemory = OpBuilder.memory;
        Operators oldOperators = OpBuilder.operators;

        try {
            OpBuilder.memory = new Memory();
            OpBuilder.operators = new Operators( OpBuilder.memory );
            OpBuilder.constants = new CommonConstants();
            OpBuilder.constants.init( OpBuilder.memory );
        }
        catch( Exception e ) {
            OpBuilder.memory = oldMemory;
            OpBuilder.operators = oldOperators;

            return false;
        }

        return true;
    }

    /**
     * Sets the current memory
     * 
     * @param memory
     */
    public static void setMemory( Memory memory ) {
        OpBuilder.memory = memory;
    }

    /**
     * Sets the current operator manager
     * 
     * @param operators
     */
    public static void setOperators( Operators operators ) {
        OpBuilder.operators = operators;
    }
    
    public static MathConstant getConstants() {
        return constants;
    }

    public static void setConstants( MathConstant constants ) {
        OpBuilder.constants = constants;
    }
}
