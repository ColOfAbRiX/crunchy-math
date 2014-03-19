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
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * Set an option value
 * 
 * <p>
 * Options are configuration parameters that the various parts of the parser can use as they prefer. There are no rules
 * on options, options are simply memory variable with a name that starts with a marker {@link Option#OPTION_MARK} and
 * they contain a generic {@link ExpressionEntry} value.
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public class SetOperator extends Operator {

    public SetOperator() throws ConfigException {
        super();
        this.setBaseName( "Set" );
        this.setPriority( (short)2 );
        this.setOperandsLimit( 2, 2 );
        this.setCurrentOperands( 2 );
    }

    /**
     * Sets the value of an option using the shared memory
     */
    @Override
    public Operand executeOperation( Stack<ExpressionEntry> operands ) throws ExpressionException {
        if( operands.size() < this.getCurrentOperands() )
            throw new ExpressionException( "Wrong number of given parameters" );

        try {
            Option option = (Option)operands.pop();
            ExpressionEntry value = operands.pop();

            this.getContext().getMemory().setValue( option.getFullName(), value );
        }
        catch( ClassCastException e ) {
            throw new ExpressionException( "Wrong type of given parameters" );
        }

        return null;
    }
}
