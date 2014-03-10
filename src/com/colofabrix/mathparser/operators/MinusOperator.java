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
package com.colofabrix.mathparser.operators;

import java.util.Stack;
import org.apfloat.Apfloat;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class MinusOperator extends Operator {

    public MinusOperator() throws ConfigException {
        super();
        this.setBaseName( "-" );
        this.setPriority( (short)0 );
        this.setOperandsLimit( 1, 2 );
    }

    @Override
    public Operand executeOperation( Stack<ExpressionEntry> operands ) throws ExpressionException {
        Apfloat value1, value2;

        if( operands.size() < this.getCurrentOperands() )
            throw new ExpressionException( "Wrong number of given parameters" );

        value1 = Operand.extractNumber( operands.pop() );

        // Fetch the second operand only if needed
        if( this.getCurrentOperands() == 1 ) {
            return new Operand( value1.multiply( new Apfloat( -1 ) ) );
        }
        else {
            value2 = Operand.extractNumber( operands.pop() );
            return new Operand( value1.subtract( value2 ) );
        }
    }
}
