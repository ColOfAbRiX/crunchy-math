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
import org.junit.Assert;
import org.junit.Test;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.operators.CosOperator;
import com.colofabrix.mathparser.operators.DivideOperator;
import com.colofabrix.mathparser.operators.IntegralOperator;
import com.colofabrix.mathparser.operators.MinusOperator;
import com.colofabrix.mathparser.operators.SinOperator;
import com.colofabrix.mathparser.operators.SumOperator;
import com.colofabrix.mathparser.operators.special.VectorPush;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.Context;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.builders.DefaultContextBuilder;

/**
 * @author Fabrizio Colonna
 */
public class OperatorTest extends Operator {

    @Override
    public Operand executeOperation( Stack<Expression> operands ) throws ExpressionException {
        return null;
    }

    /**
     * Test method for {@link com.colofabrix.mathparser.expression.Operator#clone()}.
     */
    @Test
    public void testClone() {
        Context context = DefaultContextBuilder.createDefault();

        Operator sinop = (Operator)context.getOperators().fromName( "Sin" );
        Operator sinop2 = (Operator)sinop.clone();

        Assert.assertNotSame( "Cloned objects must be different objects", sinop, sinop2 );
        Assert.assertEquals( "Cloned objects must be equals", sinop, sinop2 );
    }

    /**
     * Test method for
     * {@link com.colofabrix.mathparser.expression.Operator#compareTo(com.colofabrix.mathparser.expression.Operator)}.
     */
    @Test
    public void testCompareTo() {

        try {
            // Basic priority check
            Operator op1 = new MinusOperator();
            Operator op2 = new DivideOperator();
            Assert.assertEquals( "Less-than check", op1.compareTo( op2 ) < 0, true );

            op1 = new DivideOperator();
            op2 = new MinusOperator();
            Assert.assertEquals( "Greater-than check", op1.compareTo( op2 ) > 0, true );

            op1 = new MinusOperator();
            op2 = new SumOperator();
            Assert.assertEquals( "Same priority check", op1.compareTo( op2 ) == 0, true );

            // Unary operator check
            op1 = new MinusOperator();
            op1.setCurrentOperands( 1 );
            op2 = new SumOperator();
            Assert.assertEquals( "Unary always greater-than check", op1.compareTo( op2 ) > 0, true );
            op2 = new DivideOperator();
            Assert.assertEquals( "Unary always greater-than check", op1.compareTo( op2 ) > 0, true );
            op2 = new VectorPush();
            Assert.assertEquals( "Unary always greater-than check", op1.compareTo( op2 ) > 0, true );

            // Double unary operator check
            op1 = new SinOperator();
            op2 = new SinOperator();
            Assert.assertEquals( "Current unary always less-than the other unary", op1.compareTo( op2 ) < 0, true );
            Assert.assertEquals( "Current unary always less-than the other unary", op2.compareTo( op1 ) < 0, true );
        }
        catch( ConfigException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    /**
     * Test method for {@link com.colofabrix.mathparser.expression.Operator#getBaseName()}.
     */
    @Test
    public void testGetBaseName() {
        try {
            Operator op1 = new CosOperator();
            Operator op2 = new SumOperator();
            Operator op4 = new IntegralOperator();

            Assert.assertEquals( "Unary operator", "Cos", op1.getBaseName() );
            Assert.assertEquals( "Binary operator", "+", op2.getBaseName() );
            Assert.assertEquals( "4-operands operator", "Int", op4.getBaseName() );
        }
        catch( ConfigException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    /**
     * Test method for {@link com.colofabrix.mathparser.expression.Operator#getName()}.
     */
    @Test
    public void testGetName() {
        try {
            Operator op1 = new CosOperator();
            Operator op2 = new SumOperator();
            Operator op4 = new IntegralOperator();

            Assert.assertEquals( "Unary operator", "#Cos", op1.getName() );
            Assert.assertEquals( "Binary operator", "+", op2.getName() );
            Assert.assertEquals( "4-operands operator", "4#Int", op4.getName() );
        }
        catch( ConfigException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    /**
     * Test method for
     * {@link com.colofabrix.mathparser.expression.Operator#setOption(com.colofabrix.mathparser.expression.Option, int)}
     * .
     */
    @Test
    public void testSetOption() {
        try {
            this.setContext( DefaultContextBuilder.createDefault() );

            int value = 1;
            Option o = new Option( "test" );
            this.setOption( o, value );

            Expression memoryContent = this.getContext().getMemory().getValue( o.getFullName() );

            Assert.assertEquals( "Check type of option-value in memory", true, memoryContent.getEntryType() == Operand.OPERAND_CODE );
            Assert.assertEquals( "Check option-value in memory", value, Operand.extractNumber( memoryContent ).intValue() );
        }
        catch( ExpressionException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }
}
