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
import org.apache.commons.math3.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apfloat.Apfloat;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.lib.Expression2AMLAdapter;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * @author Fabrizio Colonna
 */
public class IntegralOperator extends Operator {

    private MathParser parser = null;

    public IntegralOperator() throws ConfigException {
        super();
        this.setBaseName( "Int" );
        this.setPriority( (short)0 );
        this.setOperandsLimit( 4, 4 );
        this.setCurrentOperands( 4 );
    }

    @Override
    public Operand executeOperation( Stack<Expression> operands ) throws ExpressionException {
        if( operands.size() < this.getCurrentOperands() ) {
            throw new ExpressionException( "Wrong number of given parameters" );
        }

        this.parser = new MathParser( this.getContext() );

        // Interval start
        Apfloat lower = Operand.extractNumber( this.parser.executePostfix( operands.pop() ) );
        // Interval end
        Apfloat upper = Operand.extractNumber( this.parser.executePostfix( operands.pop() ) );
        // Expression to evaluate
        Expression expression = this.parser.minimise( operands.pop() );
        // Integration variable
        Operand variable = (Operand)operands.pop();

        Expression2AMLAdapter function = new Expression2AMLAdapter( this.parser, expression, variable );
        BaseAbstractUnivariateIntegrator integrator = new SimpsonIntegrator();
        function.finalize();

        Apfloat result = null;
        int maxEval = 10000, eval = 10, exp = 5;

        // It starts with a low number of evaluation and exponentially increase to the set maximum
        while( result == null && eval <= maxEval ) {
            try {
                result = new Apfloat( integrator.integrate( eval, function, lower.doubleValue(), upper.doubleValue() ) );
            }
            catch( org.apache.commons.math3.exception.TooManyEvaluationsException e ) {
                eval *= exp;
            }
        }

        return new Operand( result );
    }
}
