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
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Vector;

public class VectorTest {

    @Test
    public void testExecuteOperation() {
        try {
            MathParser mp = new MathParser();

            Apfloat result = mp.ExecutePostfix( mp.ConvertToPostfix( "[1, 1 - 2, Sin x]" ) );

            Assert.assertNull( "Output must be null", result );
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }

    @Test
    public void testExecuteParsing() {
        try {
            MathParser mp = new MathParser();

            // Reference
            CmplxExpression reference = new CmplxExpression();
            reference.add( new Operand( new Apfloat( 1 ) ) );
            reference.add( new Operand( new Apfloat( -1 ) ) );
            reference.add( CmplxExpression.fromExpression( "x #Sin", mp.getContext() ) );

            CmplxExpression parsed = mp.ConvertToPostfix( "[1, 1 - 2, Sin x]" );
            ExpressionEntry result = mp.getContext().getMemory().getValue( Vector.OUTPUT_NAME );

            // Check of the output postfix string
            Assert.assertEquals( "Parsed vector output", parsed.toString(), reference.toString() );

            // Check if the memory output is present
            Assert.assertNotNull( "Memory output present", result );

            // Check if the memory output is in the correct object
            if( !(result instanceof CmplxExpression) )
                Assert.fail( "The result is not of the expected type" );

            // Check the actual content of the memory output
            CmplxExpression result2 = (CmplxExpression)result;

            for( int i = 0; i < reference.size(); i++ )
                if( !reference.get( i ).equals( result2.get( i ) ) )
                    Assert.fail( "One vector element is not of the expected type" );
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }
}
