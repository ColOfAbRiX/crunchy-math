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
 * @author fcolonna
 *
 */
public class MathParserTest {

	private static class TestEntry {
		public String message = null;
		public String infixString = null;
		public String postfixString = null;
		public double result = 0.0;
		
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
		
		tests.add( new TestEntry("Basic expression with brackets",
				"3 * (2 + 1)",
				"[3, 2, 1, +, *]",
				9.0 ) );
		
		tests.add( new TestEntry("Assignment",
				"x = " + Math.PI,
				"[x, " + Math.PI + ", =]",
				Math.PI ) );
		tests.add( new TestEntry("Variable reference",
				"y = x ^ 2",
				"[y, x, 2, ^, =]",
				Math.pow(Math.PI, 2) ) );
		
		tests.add( new TestEntry("Unary operator after bracket",
				"3 * (-2 + 1)",
				"[3, 2, #-, 1, +, *]",
				 -3.0) );
		
		tests.add( new TestEntry("Unary operator before bracket",
				"-(2 + 1)",
				"[2, 1, +, #-]",
				-3.0) );
		
		tests.add( new TestEntry("Unary operator between operator and bracket",
				"3 * -(2 + 1)",
				"[3, 2, 1, +, #-, *]",
				-9.0) );
		
		tests.add( new TestEntry("Assignment with unary function",
				"x = 2 * Sin 5 / 1 + 6 - 3",
				"[x, 2, 5, #Sin, 1, /, *, 6, +, 3, -, =]",
				1.0821514506737229 ) );
		
		tests.add( new TestEntry("Basic expression with brackets",
				"3 * (2 + 1)",
				"[3, 2, 1, +, *]",
				9 ) );
		
		tests.add( new TestEntry("Assignment with unary function and brackets",
				"x = (2 * 5) + Sin 6",
				"[x, 2, 5, *, 6, #Sin, +, =]",
				9.720584501801074 ) );
		
		tests.add( new TestEntry("Assignment with unary function and brackets on unary function",
				"x = 2 * (5 + Sin 6)",
				"[x, 2, 5, 6, #Sin, +, *, =]",
				9.441169003602148 ) );
		
		tests.add( new TestEntry("Assignment with unary function and brackets for unary function argument",
				"x = 2 * (5 + Sin (6))",
				"[x, 2, 5, 6, #Sin, +, *, =]",
				9.441169003602148 ) );
		
		tests.add( new TestEntry("Assignment with unary function, brackets and operation after unary function",
				"x = 2 * (5 + Sin 6) / 7",
				"[x, 2, 5, 6, #Sin, +, 7, /, *, =]",
				1.3487384290860212 ) );
		
		tests.add( new TestEntry("Assignment with unary function, brackets and operation with brackets after unary function",
				"x = 2 * (5 + Sin 6) / (7 + 1)",
				"[x, 2, 5, 6, #Sin, +, 7, 1, +, /, *, =]",
				1.1801461254502685) );
		
		tests.add( new TestEntry("Simple integration of a variable",
				"Int[0, 1, x, x, 0.00001]",
				"[0, 1, x, x, 0.00001, 5#Int]",
				 0.5 ) );
		
		tests.add( new TestEntry("Integration over a negative interval",
				"Int[-1, 1, x, x, 0.00001]",
				"[0, [1, #-], x, x, 0.00001, 5#Int]",
				0 ) );
		
		tests.add( new TestEntry("Integration over a function with decimal numbers",
				"Int[0, " + Math.PI + ", x, x, 0.00001]",
				"[0, " + Math.PI + ", x, x, 0.00001, 5#Int]",
				 Math.pow(Math.PI, 2) / 2) );
		
		tests.add( new TestEntry("Integration over a function with decimal number",
				"Int[0, " + Math.PI + ", Sin x, x, 0.00001]",
				"[0, " + Math.PI + ", [x, #Sin], x, 0.00001, 5#Int]",
				2 ) );
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.MathParser#ConvertToPostfix(java.lang.String)}.
	 */
	@Test
	public void testConvertToPostfix() {
		try {
			for( TestEntry test: tests )
				assertEquals( test.message,
						test.postfixString,
						mp.ConvertToPostfix(test.infixString).toString() );
		}
		catch (ExpressionException | ConfigException e) {
			fail( e.getMessage() );
		}
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.MathParser#ExecutePostfix(java.util.Stack)}.
	 */
	@Test
	public void testExecutePostfix() {
		try {
			for( TestEntry test: tests )
				assertEquals( test.message,
						test.result,
						mp.ExecutePostfix(mp.ConvertToPostfix(test.infixString)),
						0.000001 );
		}
		catch (ExpressionException | ConfigException e) {
			fail( e.getMessage() );
		}
	}

}
