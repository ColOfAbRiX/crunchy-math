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
package com.colofabrix.mathparser.struct;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;

/**
 * Represents a MathParser Context
 * 
 * <p>
 * A context is a set of information that differentiate a parser from another. Usually is made of an Operators Manager
 * and a Memory Manager as the set of the supported operator and the set of variables define a frame where a MathParser
 * operates
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public class Context {

    private Operators operators;
    private Memory memory;

    public Context( Operators operators, Memory memory ) {
        this.setMemory( memory );
        this.setOperators( operators );
    }

    @Override
    public boolean equals( Object obj ) {
        if( obj instanceof Context ) {
            Context ctx = (Context)obj;
            return this.memory.equals( ctx.memory ) && this.operators.equals( ctx.operators );
        }
        else
            return super.equals( obj );
    }

    /**
     * @return the memory
     */
    public Memory getMemory() {
        return this.memory;
    }

    /**
     * @return the operators
     */
    public Operators getOperators() {
        return this.operators;
    }

    /**
     * @param memory the memory to set
     */
    private void setMemory( Memory memory ) {
        this.memory = memory;
    }

    /**
     * @param operators the operators to set
     */
    private void setOperators( Operators operators ) {
        this.operators = operators;
    }
}
