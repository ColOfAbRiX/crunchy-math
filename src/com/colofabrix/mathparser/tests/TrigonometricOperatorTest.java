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

import java.util.Stack;
import org.apfloat.Apfloat;
import org.junit.Assert;
import org.junit.Test;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.lib.ApfloatConsts;
import com.colofabrix.mathparser.operators.ArsinOperator;
import com.colofabrix.mathparser.operators.SinOperator;
import com.colofabrix.mathparser.operators.special.AngleUnit;
import com.colofabrix.mathparser.operators.special.TrigonometricOperator;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.builders.ContextBuilder;
import com.colofabrix.mathparser.struct.builders.OpBuilder;

public class TrigonometricOperatorTest {

    @Test
    public void testGetCurrent() {
        Stack<ExpressionEntry> operand = new Stack<ExpressionEntry>();
        Apfloat result;

        try {
            OpBuilder builder = new OpBuilder( ContextBuilder.createDefault() );
            TrigonometricOperator test = (TrigonometricOperator)builder.create( ArsinOperator.class );
            test.executeParsing( new CmplxExpression(), new Stack<Operator>() );

            // Radians test
            test.setSelectedUnit( AngleUnit.RADIANS );
            operand.push( new Operand( new Apfloat( 0.5 ) ) );
            result = test.executeOperation( operand ).getNumericValue();
            Assert.assertEquals( "Using radians",
                    ApfloatConsts.Angular.RAD_PI6.doubleValue(),
                    result.doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED
                    );

            // Degrees test
            test.setSelectedUnit( AngleUnit.DEGREES );
            operand.push( new Operand( new Apfloat( 0.5 ) ) );
            result = test.executeOperation( operand ).getNumericValue();
            Assert.assertEquals( "Using degrees",
                    ApfloatConsts.Angular.DEG_30.doubleValue(),
                    result.doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );

            // Gradians test
            test.setSelectedUnit( AngleUnit.GRADIANS );
            operand.push( new Operand( new Apfloat( 0.5 ) ) );
            result = test.executeOperation( operand ).getNumericValue();
            Assert.assertEquals( "Using gradians",
                    ApfloatConsts.Angular.GRAD_33.doubleValue(),
                    result.doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );
        }
        catch( ExpressionException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }

    @Test
    public void testGetRadians() {
        Stack<ExpressionEntry> operand = new Stack<ExpressionEntry>();
        Operand result;

        try {
            OpBuilder builder = new OpBuilder( ContextBuilder.createDefault() );
            TrigonometricOperator test = (TrigonometricOperator)builder.create( SinOperator.class );
            test.executeParsing( new CmplxExpression(), new Stack<Operator>() );

            // Radians test
            test.setSelectedUnit( AngleUnit.RADIANS );
            operand.push( new Operand( ApfloatConsts.Angular.RAD_PI6 ) );
            result = test.executeOperation( operand );
            Assert.assertEquals( "Using radians",
                    0.5,
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );

            // Degrees test
            test.setSelectedUnit( AngleUnit.DEGREES );
            operand.push( new Operand( ApfloatConsts.Angular.DEG_30 ) );
            result = test.executeOperation( operand );
            Assert.assertEquals( "Using degrees",
                    0.5,
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );

            // Gradians test
            test.setSelectedUnit( AngleUnit.GRADIANS );
            operand.push( new Operand( ApfloatConsts.Angular.GRAD_33 ) );
            result = test.executeOperation( operand );
            Assert.assertEquals( "Using gradians",
                    0.5,
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );
        }
        catch( ExpressionException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }

}
