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

import java.util.Collections;
import java.util.Stack;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.builders.ExpressionWorker;

public class EvalOperator extends Operator {

    public EvalOperator() throws ConfigException {
        super();
        this.setBaseName( "Eval" );
        this.setPriority( (short)2 );
        this.setOperandsLimit( 1, 1 );
        this.setCurrentOperands( 1 );
    }

    @Override
    public Operand executeOperation( Stack<Expression> operands ) throws ExpressionException {
        if( operands.size() < this.getCurrentOperands() ) {
            throw new ExpressionException( "Wrong number of given parameters" );
        }

        // Convert and reverse the operands
        CmplxExpression converted = ExpressionWorker.fromExpression( operands, this.getContext() );
        Collections.reverse( converted );

        // Execution
        Expression executed = new MathParser( this.getContext() ).executePostfix( converted );

        return (Operand)executed;
    }
}