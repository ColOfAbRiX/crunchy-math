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

import java.util.List;
import java.util.Vector;
import org.apfloat.Apfloat;
import org.junit.Assert;
import org.junit.Test;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.operators.IntegralOperator;
import com.colofabrix.mathparser.operators.MinusOperator;
import com.colofabrix.mathparser.operators.MultiplyOperator;
import com.colofabrix.mathparser.operators.SumOperator;
import com.colofabrix.mathparser.operators.special.ClosingBracket;
import com.colofabrix.mathparser.operators.special.OpeningBracket;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.Context;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.builders.DefaultContextBuilder;
import com.colofabrix.mathparser.struct.builders.ExpressionWorker;

/**
 * @author Fabrizio Colonna
 */
public class ExpressionTest {

    /**
     * Test method for {@link com.colofabrix.mathparser.expression.Expression#equals(java.lang.Object)}.
     */
    @Test
    public void testEquals() {
        try {
            Context context = DefaultContextBuilder.createDefault();

            List<Expression> test = new Vector<Expression>();
            test.add( new Operand( new Apfloat( 1 ) ) );
            test.add( new SumOperator() );
            test.add( new Option( "$option" ) );
            test.add( ExpressionWorker.fromExpression( "3 * ( 2 - 1 ) + Int[0, 1, x, x]", context ) );

            List<Expression> referenceEquals = new Vector<Expression>();
            referenceEquals.add( new Operand( new Apfloat( 1 ) ) );
            referenceEquals.add( new SumOperator() );
            referenceEquals.add( new Option( "$option" ) );
            referenceEquals.add( ExpressionWorker.fromExpression( "3 * ( 2 - 1 ) + Int[0, 1, x, x]", context ) );

            List<Expression> referenceNotEquals = new Vector<Expression>();
            referenceNotEquals.add( new Operand( new Apfloat( 2 ) ) );
            referenceNotEquals.add( new MinusOperator() );
            referenceNotEquals.add( new Option( "other_option" ) );
            referenceNotEquals.add( ExpressionWorker.fromExpression( "3 * Int[1, 2, x, x]", context ) );

            // Cross checking all the equalities
            for( Expression expr: test ) {
                for( Expression ref: referenceEquals ) {
                    if( expr.getEntryType() == ref.getEntryType() ) {
                        // Check equality between Expression of the same type
                        Assert.assertEquals( "Equality check between " + ref.getClass().toString() + " and " + expr.getClass().toString(), ref, expr );
                    }
                    else {
                        // Check inequality between Expression of the same type
                        Assert.assertNotEquals( "INequality check between " + ref.getClass().toString() + " and " + expr.getClass().toString(), ref, expr );
                    }
                }

                // Check inequality for all of the objects in this list
                for( Expression ref: referenceNotEquals ) {
                    Assert.assertNotEquals( "INequality check between " + ref.getClass().toString() + " and " + expr.getClass().toString(), ref, expr );
                }
            }
        }
        catch( ConfigException | ExpressionException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }

    /**
     * Test method for {@link com.colofabrix.mathparser.expression.Expression#getEntryType()}.
     */
    @Test
    public void testGetEntryType() {
        try {
            Expression test;

            // Operand (number) conversion check
            test = new Operand( new Apfloat( 1 ) );
            Assert.assertEquals( "Operand code", 1, test.getEntryType() );

            // Operand (variable) conversion check
            test = new Operand( "variable", null );
            Assert.assertEquals( "Operand code", 1, test.getEntryType() );

            // Operator conversion check
            test = new SumOperator();
            Assert.assertEquals( "Operator code", 2, test.getEntryType() );

            // Option conversion check
            test = new Option( "option" );
            Assert.assertEquals( "Option code", 3, test.getEntryType() );

            // Option conversion check
            test = new CmplxExpression();
            Assert.assertEquals( "CmplxExpression code", 4, test.getEntryType() );
        }
        catch( ConfigException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }

    /**
     * Test method for {@link com.colofabrix.mathparser.expression.Expression#toString()}.
     */
    @Test
    public void testToString() {
        try {
            Expression test;

            // Operand (number) conversion check
            test = new Operand( new Apfloat( 1 ) );
            Assert.assertEquals( "Number toString", "1", test.toString() );

            // Operand (variable) conversion check
            test = new Operand( "variable", null );
            Assert.assertEquals( "Variable toString", "variable", test.toString() );

            // Operator conversion check
            test = new SumOperator();
            Assert.assertEquals( "Operator toString", "+", test.toString() );

            // Option conversion check
            test = new Option( "option" );
            Assert.assertEquals( "Option toString", Option.OPTION_MARK + "option", test.toString() );

            // Check spacing between Expression
            test = new CmplxExpression();
            ((CmplxExpression)test).add( new SumOperator() );
            ((CmplxExpression)test).add( new SumOperator() );
            Assert.assertEquals( "Spacing check", "+ +", test.toString() );

            // Check of an automated conversion
            test = ExpressionWorker.fromExpression( "1 + variable + $option + [1, 2, 3] + Int[0, 1, x, x]", DefaultContextBuilder.createDefault() );
            Assert.assertEquals( "Comprehensive conversion check", "1 + variable + $option + [ 1 , 2 , 3 ] + 4#Int [ 0 , 1 , x , x ]", test.toString() );

            // Manual creation of a reference sub-expression
            CmplxExpression level0 = new CmplxExpression();

            level0.add( new Operand( new Apfloat( 3 ) ) );
            level0.add( new MultiplyOperator() );
            level0.add( new OpeningBracket() );
            level0.add( new Operand( new Apfloat( 2 ) ) );
            level0.add( new MinusOperator() );
            level0.add( new Operand( new Apfloat( 1 ) ) );
            level0.add( new ClosingBracket() );

            Assert.assertEquals( "CmplxExpression toString", "3 * ( 2 - 1 )", level0.toString() );

            CmplxExpression level01 = new CmplxExpression();
            level01.add( new Operand( new Apfloat( 0 ) ) );
            level01.add( new Operand( new Apfloat( 1 ) ) );
            level01.add( new Operand( "x", null ) );
            level01.add( new Operand( "x", null ) );
            level01.add( new IntegralOperator() );
            level0.add( new SumOperator() );
            level0.add( level01 );

            Assert.assertEquals( "CmplxExpression toString with subexpressions", "3 * ( 2 - 1 ) + 0 1 x x 4#Int", level0.toString() );
        }
        catch( ConfigException | ExpressionException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }
}
