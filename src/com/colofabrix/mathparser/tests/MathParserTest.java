/**
 * 
 */
package com.colofabrix.mathparser.tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import com.colofabrix.mathparser.*;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * @author Fabrizio Colonna
 */
public class MathParserTest extends MathParser {

	public MathParserTest() throws ConfigException { super(); }

	private static class TestEntry {
		public String message = null;
		public String infixString = null;
		public String postfixString = null;
		public double result;
		
		public TestEntry( String message, String infix, String postfix, double result ) {
			this.message = message;
			this.infixString = infix;
			this.postfixString = postfix;
			this.result = result;
		}
	}
	
	private static MathParser mp;
	private static ArrayList<TestEntry> tests;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tests = new ArrayList<MathParserTest.TestEntry>();
		mp = new MathParser( new Operators(), new Memory() );
		
		// SIMPLE EXPRESSION TESTING
		tests.add( new TestEntry("Basic expression with brackets",
				"3 * (2 + 1)",				// Input infix string
				"3.0 2.0 1.0 + *",			// Output postfix string
				9.0 ) );					// Numeric result
		
		tests.add( new TestEntry("Assignment",
				"x = " + Math.PI,
				"x " + Math.PI + " =",
				Math.PI ) );
		
		tests.add( new TestEntry("Variable reference",
				"y = x ^ 2",
				"y x 2.0 ^ =",
				Math.pow(Math.PI, 2) ) );
		
		// BRACKETS TESTING
		tests.add( new TestEntry("Brackets at the beginning",
				"(2 + 1)",
				"2.0 1.0 +",
				 3.0) );

		tests.add( new TestEntry("Double operator after closing bracket",
				"(2 + 1) / 1",
				"2.0 1.0 + 1.0 /",
				 3.0) );
		
		tests.add( new TestEntry("Unary operator after opening bracket",
				"3 * (-2 + 1)",
				"3.0 2.0 #- 1.0 + *",
				 -3.0) );
		
		tests.add( new TestEntry("Unary operator before opening bracket",
				"-(2 + 1)",
				"2.0 1.0 + #-",
				-3.0) );
		
		tests.add( new TestEntry("Unary operator between operator and opening bracket",
				"3 * -(2 + 1)",
				"3.0 2.0 1.0 + #- *",
				-9.0) );

		// UNARY FUNCTIONS TESTING
		tests.add( new TestEntry("Function with an operation before",
				"x = Sin 5",
				"x 5.0 #Sin =",
				-0.9589242746631385 ) );

		tests.add( new TestEntry("Function with an operatio after",
				"Sin 5 / 5",
				"5.0 #Sin 5.0 /",
				-0.1917848549326277 ) );
		
		tests.add( new TestEntry("Function between operations",
				"2 * Sin 5 / 3",
				"2.0 5.0 #Sin 3.0 / *",
				-0.6392828497754256 ) );
		
		tests.add( new TestEntry("Function inside brackets",
				"2 * (5 + Sin 6)",
				"2.0 5.0 6.0 #Sin + *",
				9.441169003602148 ) );
		
		tests.add( new TestEntry("Function with operand inside brackets",
				"5 + Sin (6)",
				"5.0 6.0 #Sin +",
				4.720584501801074 ) );
		
		tests.add( new TestEntry("Function inside brackets between operations",
				"2 * (5 + Sin 6) / 7",
				"2.0 5.0 6.0 #Sin + 7.0 / *",
				1.3487384290860212 ) );
		
		tests.add( new TestEntry("Complex expression",
				"2 * (5 + Sin 6) / (7 + 1)",
				"2.0 5.0 6.0 #Sin + 7.0 1.0 + / *",
				1.1801461254502685) );

		// CUSTOM FUNCTIONS
		tests.add( new TestEntry("Simple integration of a variable",
				"Int[0, 1, x, x, 0.00001]",
				"0.0 1.0 x x 1.0E-5 5#Int",
				 0.5 ) );
		
		tests.add( new TestEntry("Integration over a negative interval",
				"Int[-1, 1, x, x, 0.00001]",
				"-1.0 1.0 x x 1.0E-5 5#Int",
				0 ) );
		
		tests.add( new TestEntry("Integration with decimal numbers",
				"Int[0, " + Math.PI + ", x, x, 0.00001]",
				"0.0 " + Math.PI + " x x 1.0E-5 5#Int",
				 Math.pow(Math.PI, 2) / 2) );
		
		tests.add( new TestEntry("Integration over a function with decimal number",
				"Int[0, " + Math.PI + ", Sin x, x, 0.00001]",
				"0.0 " + Math.PI + " x #Sin x 1.0E-5 5#Int",
				2 ) );
		
		tests.add( new TestEntry("Integration over a function with decimal and negative number",
				"Int[-" + Math.PI + ", " + Math.PI + ", Sin x, x, 0.00001]",
				"-" + Math.PI + " " + Math.PI + " x #Sin x 1.0E-5 5#Int",
				0 ) );
		
		tests.add( new TestEntry("Operation before integration",
				"2 + Int[0, 1, x, x, 0.00001]",
				"2.0 0.0 1.0 x x 1.0E-5 5#Int +",
				 2.5 ) );
		
		tests.add( new TestEntry("Operation after integration",
				"Int[0, 1, x, x, 0.00001] * 2",
				"0.0 1.0 x x 1.0E-5 5#Int 2.0 *",
				 1.0 ) );
		
		tests.add( new TestEntry("Integration inside brackets",
				"2 - (Int[0, 1, x, x, 0.00001] * 3)",
				"2.0 0.0 1.0 x x 1.0E-5 5#Int 3.0 * -",
				 0.5 ) );
		
		tests.add( new TestEntry("Integration of a complex function",
				"Int[-1, 1, x * Sin x, x, 0.00001]",
				"-1.0 1.0 x x #Sin * x 1.0E-5 5#Int",
				0.6023457726114663 ) );
		
		// COMPLEX EXPRESSIONS
		tests.add( new TestEntry("Complex expression 1",
				"-1 + Int[-2, 1, x ^ 2 * Sin x, x, 0.01]",
				"1.0 #- -2.0 1.0 x 2.0 ^ x #Sin * x 0.01 5#Int +",
				-3.268630319821518 ) );
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.MathParser#ConvertToPostfix(java.lang.String)}.
	 */
	@Test
	public void testConvertToPostfix() {
		try {
			for( TestEntry test: tests ) {
				System.out.print( "Conversion: " + test.message + ": " + test.infixString + " ---> " );
				System.out.println( mp.ConvertToPostfix(test.infixString).toString() );
				assertEquals( test.message,
						test.postfixString,
						mp.ConvertToPostfix(test.infixString).toString() );
			}
		}
		catch (ExpressionException | ConfigException e) {
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
		finally {
			System.out.println();
		}
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.MathParser#ExecutePostfix(java.util.Stack)}.
	 */
	@Test
	public void testExecutePostfix() {
		try {
			for( TestEntry test: tests ) {
				System.out.print( "Execution: " + test.message + ": " + test.infixString + " ---> " );
				System.out.println( mp.ExecutePostfix(mp.ConvertToPostfix(test.infixString)) );
				assertEquals( test.message,
						test.result,
						mp.ExecutePostfix(
								mp.ConvertToPostfix(test.infixString)),
						0.00002 );
			}
		}
		catch (ExpressionException | ConfigException e) {
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
		finally {
			System.out.println();
		}
	}
	
	@Test
	public void testConversionWrongExpressions() {
	}
}
