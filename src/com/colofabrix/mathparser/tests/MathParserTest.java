/*
Crunchy Math, Version 1, February 2014
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

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import org.apfloat.Apfloat;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.OpBuilder;

/**
 * @author Fabrizio Colonna
 */
public class MathParserTest extends MathParser {

    private static class TestEntry {
        public String infixString = null;
        public String message = null;
        public String postfixString = null;
        public Double result;

        public TestEntry( String message, String infix, String postfix, double result ) {
            this( message, infix, postfix, (Double)result );
        }

        public TestEntry( String message, String infix, String postfix, Double result ) {
            this.message = message;
            this.infixString = infix;
            this.postfixString = postfix;
            this.result = result;
        }
    }

    private static MathParser mp;

    private static ArrayList<TestEntry> tests;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        OpBuilder.newContext();
        MathParserTest.tests = new ArrayList<MathParserTest.TestEntry>();
        MathParserTest.mp = new MathParser( OpBuilder.getOperators(), OpBuilder.getMemory() );

        // SIMPLE EXPRESSION TESTING
        MathParserTest.tests.add( new TestEntry( "Basic expression with brackets",
                "3 * (2 + 1)",				// Input infix string
                "3 2 1 + *",			// Output postfix string
                9 ) );					// Numeric result

        MathParserTest.tests.add( new TestEntry( "Assignment",
                "x = " + Math.PI,
                "x " + Math.PI + " =",
                Math.PI ) );

        MathParserTest.tests.add( new TestEntry( "Variable reference",
                "y = x ^ 2",
                "y x 2 ^ =",
                Math.pow( Math.PI, 2 ) ) );

        // BRACKETS TESTING
        MathParserTest.tests.add( new TestEntry( "Brackets at the beginning",
                "(2 + 1)",
                "2 1 +",
                3 ) );

        MathParserTest.tests.add( new TestEntry( "Double operator after closing bracket",
                "(2 + 1) / 1",
                "2 1 + 1 /",
                3 ) );

        MathParserTest.tests.add( new TestEntry( "Unary operator after opening bracket",
                "3 * (-2 + 1)",
                "3 2 #- 1 + *",
                -3 ) );

        MathParserTest.tests.add( new TestEntry( "Unary operator before opening bracket",
                "-(2 + 1)",
                "2 1 + #-",
                -3 ) );

        MathParserTest.tests.add( new TestEntry( "Unary operator between operator and opening bracket",
                "3 * -(2 + 1)",
                "3 2 1 + #- *",
                -9 ) );

        // VECTOR TESTING
        MathParserTest.tests.add( new TestEntry( "Vector creation",
                "[1, 2, 3]",
                "1 2 3",
                null ) );

        MathParserTest.tests.add( new TestEntry( "Vector inner expression",
                "[-1, 2, 3]",
                "-1 2 3",
                null ) );

        MathParserTest.tests
                .add( new TestEntry(
                        "Vector inner complex expression",
                        "[1, 2 * Sin 4 - 1, 3]",
                        "1 -2.513604990615856502745278189023658188271825774672945142970833546802620987238358832847145621124845496 3",
                        null ) );

        // UNARY FUNCTIONS TESTING
        MathParserTest.tests.add( new TestEntry( "Function with an operation before",
                "x = Sin 5",
                "x 5 #Sin =",
                -0.9589242746631385 ) );

        MathParserTest.tests.add( new TestEntry( "Function with an operatio after",
                "Sin 5 / 5",
                "5 #Sin 5 /",
                -0.1917848549326277 ) );

        MathParserTest.tests.add( new TestEntry( "Function between operations",
                "2 * Sin 5 / 3",
                "2 5 #Sin 3 / *",
                -0.6392828497754256 ) );

        MathParserTest.tests.add( new TestEntry( "Function inside brackets",
                "2 * (5 + Sin 6)",
                "2 5 6 #Sin + *",
                9.441169003602148 ) );

        MathParserTest.tests.add( new TestEntry( "Function with operand inside brackets",
                "5 + Sin (6)",
                "5 6 #Sin +",
                4.720584501801074 ) );

        MathParserTest.tests.add( new TestEntry( "Function inside brackets between operations",
                "2 * (5 + Sin 6) / 7",
                "2 5 6 #Sin + 7 / *",
                1.3487384290860212 ) );

        MathParserTest.tests.add( new TestEntry( "Complex expression",
                "2 * (5 + Sin 6) / (7 + 1)",
                "2 5 6 #Sin + 7 1 + / *",
                1.1801461254502685 ) );

        // CUSTOM FUNCTIONS
        MathParserTest.tests.add( new TestEntry( "Simple integration of a variable",
                "Int[0, 1, x, x]",
                "0 1 x x 4#Int",
                0.5 ) );

        MathParserTest.tests.add( new TestEntry( "Integration over a negative interval",
                "Int[-1, 1, x, x]",
                "-1 1 x x 4#Int",
                0 ) );

        MathParserTest.tests.add( new TestEntry( "Integration with decimal numbers",
                "Int[0, " + Math.PI + ", x, x]",
                "0 " + Math.PI + " x x 4#Int",
                Math.pow( Math.PI, 2 ) / 2 ) );

        MathParserTest.tests.add( new TestEntry( "Integration over a function with decimal number",
                "Int[0, " + Math.PI + ", Sin x, x]",
                "0 " + Math.PI + " x #Sin x 4#Int",
                2 ) );

        MathParserTest.tests.add( new TestEntry( "Integration over a function with expressions as upper limit",
                "Int[0, 2 * " + Math.PI + ", Sin x, x]",
                "0 6.283185307179586 x #Sin x 4#Int",
                0 ) );

        MathParserTest.tests.add( new TestEntry( "Integration over a function with decimal and negative number",
                "Int[-" + Math.PI + ", " + Math.PI + ", Sin x, x]",
                "-" + Math.PI + " " + Math.PI + " x #Sin x 4#Int",
                0 ) );

        MathParserTest.tests.add( new TestEntry( "Operation before integration",
                "2 + Int[0, 1, x, x]",
                "2 0 1 x x 4#Int +",
                2.5 ) );

        MathParserTest.tests.add( new TestEntry( "Operation after integration",
                "Int[0, 1, x, x] * 2",
                "0 1 x x 4#Int 2 *",
                1 ) );

        MathParserTest.tests.add( new TestEntry( "Integration inside brackets",
                "2 - (Int[0, 1, x, x] * 3)",
                "2 0 1 x x 4#Int 3 * -",
                0.5 ) );

        MathParserTest.tests.add( new TestEntry( "Integration of a complex function",
                "Int[-1, 1, x * Sin x, x]",
                "-1 1 x x #Sin * x 4#Int",
                0.6023373578795135 ) );

        // UNARY OPERATORS SEQUENCES
        MathParserTest.tests.add( new TestEntry( "Sequence of unary operators 1",
                "Round ! 5",
                "5 #! #Round",
                120 ) );

        MathParserTest.tests.add( new TestEntry( "Sequence of unary operators 2",
                "Round 5 !",
                "5 #! #Round",
                120 ) );

        MathParserTest.tests.add( new TestEntry( "Sequence of unary operators 3",
                "- - 2",
                "2 #- #-",
                2 ) );

        MathParserTest.tests.add( new TestEntry( "Sequence of unary operators 4",
                "Sin Cos 4",
                "4 #Cos #Sin",
                -0.6080830096407655 ) );

        MathParserTest.tests.add( new TestEntry( "Multiple sequences of unary operators",
                "Sin Cos 4 - - - 2 * Round 5 !",
                "4 #Cos #Sin 2 #- #- 5 #! #Round * -",
                -240.60808300964076 ) );

        // COMPLEX EXPRESSIONS
        MathParserTest.tests.add( new TestEntry( "Complex expression 1",
                "-1 + Int[-2, 1, (x + 3) ^ 2 * Sin x, x]",
                "1 #- -2 1 x 3 + 2 ^ x #Sin * x 4#Int +",
                0.40227728650772178 ) );
    }

    public MathParserTest() throws ConfigException {
        super();
    }

    @Test
    public void testConvertToPostfix() {
        try {
            for( TestEntry test: MathParserTest.tests ) {
                String result = MathParserTest.mp.ConvertToPostfix( test.infixString ).toString();

                System.out.printf( "Conversion: %s: %s ---> ", test.message, test.infixString );
                System.out.println( result );

                Assert.assertEquals( test.message, test.postfixString, result );
            }
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
        finally {
            System.out.println();
        }
    }

    @Test
    public void testExecutePostfix() {
        try {
            for( TestEntry test: MathParserTest.tests ) {
                System.out.printf( "Execution: %s: %s ---> ", test.message, test.infixString );

                Apfloat output = MathParserTest.mp.ExecutePostfix( MathParserTest.mp
                        .ConvertToPostfix( test.infixString ) );
                if( output != null ) {
                    double result = output.doubleValue();
                    double error = 1E+6 * Math.abs( (test.result - result) / test.result );

                    if( Double.compare( error, Double.NaN ) != 0 && error != 0 ) {
                        BigDecimal bd = new BigDecimal( error );
                        bd = bd.round( new MathContext( 4 ) );
                        error = bd.doubleValue();
                    }

                    System.out.printf( "%s (Error %sppm)\n", Double.toString( result ), Double.toString( error ) );

                    Assert.assertEquals( test.message, test.result, result, AllTests.PRECISION_ERROR_ALLOWED );
                }
                else {
                    System.out.printf( "null value" );
                    Assert.assertNull( test.message, output );
                }
            }
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
        finally {
            System.out.println();
        }
    }
}
