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
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.operators.MinusOperator;
import com.colofabrix.mathparser.operators.MultiplyOperator;
import com.colofabrix.mathparser.operators.special.ClosingBracket;
import com.colofabrix.mathparser.operators.special.OpeningBracket;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.builders.DefaultContextBuilder;
import com.colofabrix.mathparser.struct.builders.ExpressionWorker;

/**
 * @author Fabrizio Colonna
 */
public class CmplxExpressionTest extends CmplxExpression {

    @Test
    @Deprecated
    public void testFromExpressionStringOperatorsMemory() {
        try {
            // Manual creation of a reference sub-expression
            CmplxExpression reference = new CmplxExpression();
            reference.add( new Operand( new Apfloat( 3 ) ) );
            reference.add( new MultiplyOperator() );
            reference.add( new OpeningBracket() );
            reference.add( new Operand( new Apfloat( 2 ) ) );
            reference.add( new MinusOperator() );
            reference.add( new Operand( new Apfloat( 1 ) ) );
            reference.add( new ClosingBracket() );

            // Method testing
            CmplxExpression test = ExpressionWorker.fromExpression( "3 * (2 - 1)", DefaultContextBuilder.createDefault() );

            Assert.assertEquals( "Conversion - Operand count test", reference.size(), test.size() );
            Assert.assertEquals( "Conversion - String test", reference.toString(), test.toString() );
        }
        catch( ExpressionException | ConfigException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }

    /**
     * Test method for {@link CmplxExpression#firstElement()}, {@link CmplxExpression#lastElement()},
     * {@link CmplxExpression#push(Expression)} and {@link CmplxExpression#pop()}
     */
    @Test
    public void testStackMethods() {
        try {
            CmplxExpression test = new CmplxExpression();
            Expression item1, item2;
            int support;

            test.add( item1 = new Operand( new Apfloat( 3 ) ) );
            test.add( new MultiplyOperator() );
            test.add( new OpeningBracket() );
            test.add( new Operand( new Apfloat( 2 ) ) );
            test.add( new MinusOperator() );
            test.add( new Operand( new Apfloat( 1 ) ) );
            test.add( item2 = new ClosingBracket() );

            Assert.assertSame( "Test .firstElement()", item1, test.firstElement() );
            Assert.assertSame( "Test .lastElement()", item2, test.lastElement() );

            support = test.size();
            test.push( item1 );
            Assert.assertSame( "Test .push()", item1, test.lastElement() );
            Assert.assertEquals( "Test .push() collection size", support + 1, test.size() );
            Assert.assertSame( "Test .pop()", item1, test.pop() );
            Assert.assertEquals( "Test .pop() collection size", support, test.size() );
        }
        catch( ConfigException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }
}
