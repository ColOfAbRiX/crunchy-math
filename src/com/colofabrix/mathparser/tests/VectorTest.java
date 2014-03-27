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
package com.colofabrix.mathparser.tests;

import org.apfloat.Apfloat;
import org.junit.Assert;
import org.junit.Test;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Vector;
import com.colofabrix.mathparser.lib.ApfConsts;
import com.colofabrix.mathparser.operators.SinOperator;
import com.colofabrix.mathparser.operators.SumOperator;

public class VectorTest {

    /**
     * Test method for {@link Vector#executeParsing(CmplxExpression, java.util.Stack)}
     */
    @Test
    public void testExecuteOperation() {
        try {
            MathParser mp = new MathParser();
            mp.getContext().getMemory().setValue( "x", new Operand( ApfConsts.PI ) );

            // Reference expression
            CmplxExpression reference = new CmplxExpression();
            reference.add( new Operand( new Apfloat( 1 ) ) );
            reference.add( new CmplxExpression() );
            ((CmplxExpression)reference.lastElement()).add( new Operand( new Apfloat( 1 ) ) );
            ((CmplxExpression)reference.lastElement()).add( new Operand( new Apfloat( 2 ) ) );
            ((CmplxExpression)reference.lastElement()).add( new SumOperator() );
            reference.add( new CmplxExpression() );
            ((CmplxExpression)reference.lastElement()).add( new Operand( "x", mp.getContext().getMemory() ) );
            ((CmplxExpression)reference.lastElement()).add( new SinOperator() );

            // Expression to test
            Expression result = mp.execute( "[1, 1 + 2, Sin x]" );

            // Testing the result
            Assert.assertEquals( "Parsed output vector", reference, result );

            // Testing the memory
            result = mp.getContext().getMemory().getValue( Vector.OUTPUT_NAME );
            Assert.assertNotNull( "Memory output presence", result );
            Assert.assertEquals( "Memory output content", reference, result );
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }
}
