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
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.expression.Vector;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * An opening vector operator
 * 
 * <p>
 * The opening vector operator is used to mark the beginning of a vector and to initialize the require data structures
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public class VectorOpening extends Vector {

    public VectorOpening() throws ConfigException {
        super();
        this.setBaseName( "[" );
        this.setPriority( (short)0 );
    }

    /**
     * This method will inizialize the internal memory variable used to store some data
     */
    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack ) throws ExpressionException {
        this.getContext().getMemory().setValue( Vector.STACK_NAME, new CmplxExpression() );
        postfix.push( this );
        return this;
    }

    @Override
    public boolean isOpening() {
        return true;
    }
}
