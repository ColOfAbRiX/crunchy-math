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

import java.util.Arrays;
import java.util.Collection;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatRuntimeException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.builders.DefaultContextBuilder;
import com.colofabrix.mathparser.struct.builders.DefaultOperators;

@RunWith( Parameterized.class )
public class MathParserTest {

    /**
     * Contains an entry to be checked
     * 
     * @author Fabrizio Colonna
     */
    private class TestEntry {
        public String message = null;   // Description of the test
        public String infix = null;     // Infix expression
        public String postfix = null;   // Postfix translation of Infix
        public Double result;           // Numeric result
        public boolean isMinimizable;   // True if Postfix is minimizable
        public String minimised = null; // Infix minimisation of Infix

        public TestEntry( String message, String infix, String postfix, boolean minimizable, String minimised, Double result ) {
            this.message = message;
            this.infix = infix;
            this.postfix = postfix;
            this.result = result;
            this.minimised = minimised;
            this.isMinimizable = minimizable;
        }
    }

    public static MathParser parser;

    /**
     * Constructs the elements to be tested
     */
    @Parameters
    public static Collection<Object[]> expressionToTest() {

        Apfloat PI = new Apfloat( Math.PI );

        Object[][] tests = new Object[][] { {
            //
            // SIMPLE EXPRESSION TESTING
            //
            "Basic expression with brackets",
            "3 * (2 + 1)",
            "3 2 1 + *",
            true, "9",
            9.0
        }, {
            "Assignment",
            "x = 3",
            "x 3 =",
            false, "x 3 =",
            3.0
        }, {
            "Variable reference",
            "y = x ^ 2",
            "y x 2 ^ =",
            false, "y x 2 ^ =",
            Math.pow( 2, 2 )
        }, {
            //
            // BRACKETS TESTING
            //
            "Brackets at the beginning",
            "(2 + 1)",
            "2 1 +",
            true, "3",
            3.0
        }, {
            "Double operator after closing bracket",
            "(2 + 1) / 1",
            "2 1 + 1 /",
            true, "3",
            3.0
        }, {
            "Unary operator after opening bracket",
            "3 * (-2 + 1)",
            "3 2 #- 1 + *",
            true, "-3",
            -3.0
        }, {
            "Unary operator before opening bracket",
            "-(2 + 1)",
            "2 1 + #-",
            true, "-3",
            -3.0
        }, {
            "Unary operator between operator and opening bracket",
            "3 * -(2 + 1)",
            "3 2 1 + #- *",
            true, "-9",
            -9.0
        }, {
            //
            // VECTOR TESTING
            //
            "Vector creation",
            "[1, 2, 3]",
            "1 2 3",
            false, "1 2 3",
            null
        }, {
            "Vector inner expression",
            "[1 + 1, 2, 3]",
            "1 1 + 2 3",
            true, "2 2 3",
            null
        }, {
            "Vector inner complex expression",
            "[1, 2 * Sin 4 - 1, 3]",
            "1 2 4 #Sin * 1 - 3",
            true, "1 -2.513604990615856502745278189023658188271825774672945142970833546802620987238358832847145621124845496 3",
            null
        }, {
            //
            // UNARY FUNCTIONS TESTING
            //
            "Function with an operation before",
            "y = Sin 5",
            "y 5 #Sin =",
            true, "y -9.5892427466313846889315440615599397335246154396460177813167245423510255808655960307699595542953286632e-1 =",
            -0.9589242746631385
        }, {
            "Function with an operatio after",
            "Sin 5 / 5",
            "5 #Sin 5 /",
            true, "-1.917848549326276937786308812311987946704923087929203556263344908470205116173119206153991910859065732e-1",
            -0.1917848549326277
        }, {
            "Function between operations",
            "2 * Sin 5 / 3",
            "2 5 #Sin 3 / *",
            true, "-6.392828497754256459287696041039959822349743626430678520877816361567350387243730687179973036196885775e-1",
            -0.6392828497754256
        }, {
            "Function inside brackets",
            "2 * (5 + Sin 6)",
            "2 5 6 #Sin + *",
            true, "9.441169003602148254376889106776210480744010271363591363033297260693377691882711330290662123094315508",
            9.441169003602148
        }, {
            "Function with operand inside brackets",
            "5 + Sin (6)",
            "5 6 #Sin +",
            true, "4.720584501801074127188444553388105240372005135681795681516648630346688845941355665145331061547157754",
            4.720584501801074
        }, {
            "Function inside brackets between operations",
            "2 * (5 + Sin 6) / 7",
            "2 5 6 #Sin + 7 / *",
            true, "1.348738429086021179196698443825172925820572895909084480433328180099053955983244475755808874727759358",
            1.3487384290860212
        }, {
            "Complex expression",
            "2 * (5 + Sin 6) / (7 + 1)",
            "2 5 6 #Sin + 7 1 + / *",
            true, "1.180146125450268531797111138347026310093001283920448920379162157586672211485338916286332765386789438",
            1.1801461254502685
        }, {
            //
            // VARIABLES
            //
            "Variable assignment",
            "x = 2",
            "x 2 =",
            false, "x 2 =",
            2.0
        }, {
            "Variable usage",
            "x * 3",
            "x 3 *",
            false, "x 3 *",
            6.0
        }, {
            "Variable in complex expression",
            "- x * (x - 5) + x",
            "x #- x 5 - * x +",
            false, "x #- x 5 - * x +",
            8.0
        }, {
            "Variable inside a vector",
            "[1, x, 3]",
            "1 x 3",
            false, "1 x 3",
            null
        }, {
            "Variable inside a vector with expression",
            "[1, 2 * Sin x, 3]",
            "1 2 x #Sin * 3",
            false, "1 2 x #Sin * 3",
            null
        }, {
            //
            // CUSTOM FUNCTIONS
            //
            "Simple integration of a variable",
            "Int[0, 1, x, x]",
            "0 1 x x 4#Int",
            false, "0 1 x x 4#Int",
            0.5
        }, {
            "Integration over a negative interval",
            "Int[-1, 1, x, x]",
            "1 #- 1 x x 4#Int",
            true, "-1 1 x x 4#Int",
            0.0
        }, {
            "Integration with decimal numbers",
            "Int[0, " + Math.PI + ", x, x]",
            "0 " + Math.PI + " x x 4#Int",
            false, "0 " + Math.PI + " x x 4#Int",
            Math.pow( PI.doubleValue(), 2 ) / 2
        }, {
            "Integration over a function with decimal number",
            "Int[0, " + PI + ", Sin x, x]",
            "0 " + PI + " x #Sin x 4#Int",
            false, "0 " + PI + " x #Sin x 4#Int",
            2.0
        }, {
            "Integration over a function with expressions as upper limit",
            "Int[0, 2 * " + PI + ", Sin x, x]",
            "0 2 3.141592653589793 * x #Sin x 4#Int",
            true, "0 6.283185307179586 x #Sin x 4#Int",
            0.0
        }, {
            "Integration over a function with decimal and negative number",
            "Int[-" + PI + ", " + PI + ", Sin x, x]",
            PI + " #- " + PI + " x #Sin x 4#Int",
            true, "-" + PI + " " + PI + " x #Sin x 4#Int",
            0.0
        }, {
            "Operation before integration",
            "2 + Int[0, 1, x, x]",
            "2 0 1 x x 4#Int +",
            false, "2 0 1 x x 4#Int +",
            2.5
        }, {
            "Operation after integration",
            "Int[0, 1, x, x] * 2",
            "0 1 x x 4#Int 2 *",
            false, "0 1 x x 4#Int 2 *",
            1.0
        }, {
            "Integration inside brackets",
            "2 - (Int[0, 1, x, x] * 3)",
            "2 0 1 x x 4#Int 3 * -",
            false, "2 0 1 x x 4#Int 3 * -",
            0.5
        }, {
            "Integration of a complex function",
            "Int[-1, 1, x * Sin x, x]",
            "1 #- 1 x x #Sin * x 4#Int",
            true, "-1 1 x x #Sin * x 4#Int",
            0.6023373578795135
        }, {
            "Integrals and variables",
            "Int[-x, x, Sin y, y]",
            "x #- x y #Sin y 4#Int",
            false, "x #- x y #Sin y 4#Int",
            0.0
        }, {
            //
            // UNARY OPERATORS SEQUENCES
            //
            "Sequence of unary operators 1",
            "Round ! 5",
            "5 #! #Round",
            true, "1.2e2",
            120.0
        }, {
            "Sequence of unary operators 2",
            "Round 5 !",
            "5 #! #Round",
            true, "1.2e2",
            120.0
        }, {
            "Sequence of unary operators 3",
            "- - 2",
            "2 #- #-",
            true, "2",
            2.0
        }, {
            "Sequence of unary operators 4",
            "Sin Cos 4",
            "4 #Cos #Sin",
            true, "-6.0808300964076556864956118150988855393789235295118254591792978604502190945548080655941462830350080765e-1",
            -0.6080830096407655
        }, {
            "Multiple sequences of unary operators",
            "Sin Cos 4 - - - 2 * Round 5 !",
            "4 #Cos #Sin 2 #- #- 5 #! #Round * -",
            true, "-2.406080830096407655686495611815098885539378923529511825459179297860450219094554808065594146283035008e2",
            -240.60808300964076
        }, {
            //
            // COMPLEX EXPRESSIONS
            //
            "Complex expression 1",
            "-1 + Int[-2, 1, (x + 3) ^ 2 * Sin x, x]",
            "1 #- 2 #- 1 x 3 + 2 ^ x #Sin * x 4#Int +",
            true, "-1 -2 1 x 3 + 2 ^ x #Sin * x 4#Int +",
            0.40227728650772178
        }, {
            //
            // MINIMIZATION TESTS
            //
            "Plain vector with variable",
            "[1, x, 3]",
            "1 x 3",
            false, "1 x 3",
            null
        }, {
            "Minimizable expression with variable 1",
            "x + 2 * 3",
            "x 2 3 * +",
            true, "x 6 +",
            8.0
        }, {
            "Minimizable expression with variable 2",
            "2 * 3 + x",
            "2 3 * x +",
            true, "6 x +",
            8.0
        }, {
            "Fully minimisable expression",
            "2 * (5 + Floor Sin 6) / (7 + 1)",
            "2 5 6 #Sin #Floor + 7 1 + / *",
            true, "1",
            1.0
        }, {
            "Vector with minimimizable elements",
            "[1 + 2, Floor(2 ^ 3), Floor(3 * Sin 4)]",
            "1 2 + 2 3 ^ #Floor 3 4 #Sin * #Floor",
            true, "3 8 -3",
            null
        } };

        return Arrays.asList( tests );
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Creates a parser
        MathParserTest.parser = new MathParser(
                new DefaultContextBuilder( new DefaultOperators(), null ) );
    }

    private final TestEntry test;

    /**
     * It initialises the testing entry with the given parameters
     * 
     * @param message Test description
     * @param infix Infix expression to test
     * @param postfix Expected postfix conversion
     * @param minimizable Expected isMinimisable result
     * @param minimised Expected minimised expression
     * @param result Expected numeric result, or <code>null</code>
     */
    public MathParserTest( String message, String infix, String postfix, boolean minimizable, String minimised, Double result ) {
        // Initialise the test reference object
        this.test = new TestEntry( message, infix, postfix, minimizable, minimised, result );
    }

    /**
     * Test method for {@link MathParser#executePostfix(Expression)} .
     */
    @Test
    public void testExecutePostfix() {
        this.setTestVariable();

        try {
            // Executes the method to test
            Expression tmp = MathParserTest.parser.toPostfix( this.test.infix );
            Expression partial = MathParserTest.parser.executePostfix( tmp );

            // If there is a numeric output, calculate some statistics
            if( this.test.result != null ) {
                Apfloat output = Operand.extractNumber( partial );
                double result = output.doubleValue();

                // Assertion
                Assert.assertEquals( this.test.message, this.test.result, result, AllTests.PRECISION_ERROR_ALLOWED );
            }
            else {
                // Not numeric result
                Assert.assertEquals( this.test.message, partial.getEntryType() != Operand.OPERAND_CODE, true );
            }
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    /**
     * Test method for {@link Expression#isMinimizable()} .
     */
    @Test
    public void testIsMinimizable() {
        this.setTestVariable();
        Expression test = null;

        try {
            // Convert the expression to postfix
            test = MathParserTest.parser.toPostfix( this.test.infix );

            // Check the condition
            Assert.assertEquals( this.test.message,
                    this.test.isMinimizable,
                    test.isMinimizable() );
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    /**
     * Test method for {@link MathParser#minimise(Expression)} .
     */
    @Test
    public void testMinimise() {
        this.setTestVariable();
        Expression test = null, minimised;

        try {
            test = MathParserTest.parser.toPostfix( this.test.infix );
            minimised = MathParserTest.parser.minimise( test );

            // Check the condition
            Assert.assertEquals( this.test.message, this.test.minimised, minimised.toString() );
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    /**
     * Test method for {@link MathParser#toPostfix(Expression)} .
     */
    @Test
    public void testToPostfix() {
        this.setTestVariable();

        try {
            String result = MathParserTest.parser.toPostfix( this.test.infix ).toString();
            Assert.assertEquals( this.test.message, this.test.postfix, result );
        }
        catch( Exception e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }

    private void setTestVariable() {
        // Sets a default test variable
        try {
            MathParserTest.parser.getContext().getMemory().setValue( "x", new Operand( new Apfloat( 2.0 ) ) );
        }
        catch( NumberFormatException | ApfloatRuntimeException | ExpressionException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
