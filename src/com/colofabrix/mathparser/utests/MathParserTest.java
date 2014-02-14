/**
 * 
 */
package com.colofabrix.mathparser.utests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
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

	private static MathParser mp;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mp = new MathParser(
				new Operators(),
				new Memory() );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		mp = null;
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.MathParser#ConvertToPostfix(java.lang.String)}.
	 */
	@Test
	public void testConvertToPostfix() {
		try {
			assertEquals( "Basic expression with brackets",
					"[3, 2, 1, +, *]",
					mp.ConvertToPostfix("3 * (2 + 1)").toString() );
			
			assertEquals( "Assignment",
					"[x, " + Math.PI + ", =]",
					mp.ConvertToPostfix("x = " + Math.PI ).toString() );

			assertEquals( "Variable reference",
					"[y, x, 2, ^, =]",
					mp.ConvertToPostfix("y = x ^ 2").toString() );
			
			assertEquals( "Assignment with unary function",
					"[x, 2, 5, #Sin, 1, /, *, 6, +, 3, -, =]",
					mp.ConvertToPostfix("x = 2 * Sin 5 / 1 + 6 - 3").toString() );
			
			assertEquals( "Assignment with unary function and brackets",
					"[x, 2, 5, *, 6, #Sin, +, =]",
					mp.ConvertToPostfix("x = (2 * 5) + Sin 6").toString() );
			
			assertEquals( "Assignment with unary function and brackets on unary function",
					"[x, 2, 5, 6, #Sin, +, *, =]",
					mp.ConvertToPostfix("x = 2 * (5 + Sin 6)").toString() );
			
			assertEquals( "Assignment with unary function and brackets for unary function argument",
					"[x, 2, 5, 6, #Sin, +, *, =]",
					mp.ConvertToPostfix("x = 2 * (5 + Sin (6))").toString() );
			
			assertEquals( "Assignment with unary function, brackets and operation after unary function",
					"[x, 2, 5, 6, #Sin, +, 7, /, *, =]",
					mp.ConvertToPostfix("x = 2 * (5 + Sin 6) / 7").toString() );
			
			assertEquals( "Assignment with unary function, brackets and operation with brackets after unary function",
					"[x, 2, 5, 6, #Sin, +, 7, 1, +, /, *, =]",
					mp.ConvertToPostfix("x = 2 * (5 + Sin 6) / (7 + 1)").toString() );
			
			assertEquals( "Simple integration of a variable",
					"[0, 1, x, x, 0.000001, 5#Int]",
					mp.ConvertToPostfix("0 Int 1 x x 0.000001").toString() );
			
			/*
			assertEquals( "Integration over a function",
					"",
					mp.ConvertToPostfix("0 Int 1 Sin(x) x 0.000001").toString() );
			
			assertEquals( "Integration over a function with decimal number",
					"",
					mp.ConvertToPostfix("0 Int " + Math.PI + " x x 0.000001").toString() );
			
			assertEquals( "Integration over a negative interval and simple function",
					"",
					mp.ConvertToPostfix("-1 Int 1 x x 0.000001").toString() );
			*/
		}
		catch (ExpressionException | ConfigException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.MathParser#ExecutePostfix(java.util.Stack)}.
	 */
	@Test
	public void testExecutePostfix() {
		try {
			assertEquals( "Basic expression with brackets",
					9.0,
					mp.ExecutePostfix(mp.ConvertToPostfix("3 * (2 + 1)")),
					0);
			
			
			assertEquals( "Assignment",
					Math.PI,
					mp.ExecutePostfix(mp.ConvertToPostfix("x = " + Math.PI )),
					0 );
			
			assertEquals( "Variable reference",
					Math.pow(Math.PI, 2),
					mp.ExecutePostfix(mp.ConvertToPostfix("y = x ^ 2")),
					0);

			assertEquals( "Assignment with unary function",
					1.0821514506737229,
					mp.ExecutePostfix(mp.ConvertToPostfix("x = 2 * Sin 5 / 1 + 6 - 3")),
					0);
			
			assertEquals( "Assignment with unary function and brackets",
					9.720584501801074,
					mp.ExecutePostfix(mp.ConvertToPostfix("x = (2 * 5) + Sin 6")),
					0);
			
			assertEquals( "Assignment with unary function and brackets on unary function",
					9.441169003602148,
					mp.ExecutePostfix(mp.ConvertToPostfix("x = 2 * (5 + Sin 6)")),
					0);
			
			assertEquals( "Assignment with unary function and brackets for unary function argument",
					9.441169003602148,
					mp.ExecutePostfix(mp.ConvertToPostfix("x = 2 * (5 + Sin (6))")),
					0);
			
			assertEquals( "Assignment with unary function, brackets and operation after unary function",
					1.3487384290860212,
					mp.ExecutePostfix(mp.ConvertToPostfix("x = 2 * (5 + Sin 6) / 7")),
					0);
			
			assertEquals( "Assignment with unary function, brackets and operation with brackets after unary function",
					1.1801461254502685,
					mp.ExecutePostfix(mp.ConvertToPostfix("x = 2 * (5 + Sin 6) / (7 + 1)")),
					0);
			
			assertEquals( "Simple integration of a variable",
					0.5,
					mp.ExecutePostfix(mp.ConvertToPostfix("0 Int 1 x x 0.000001")),
					0.000001);
			
			/*
			assertEquals( "Integration over a function",
					0.5,
					mp.ExecutePostfix(mp.ConvertToPostfix("0 Int 1 Sin(x) x 0.000001")),
					0.0000001);
			
			assertEquals( "Integration over a function with decimal number",
					0.5,
					mp.ExecutePostfix(mp.ConvertToPostfix("0 Int " + Math.PI + " x x 0.000001")),
					0.0000001);
			
			assertEquals( "Integration over a negative interval and simple function",
					0.5,
					mp.ExecutePostfix(mp.ConvertToPostfix("-1 Int 1 x x 0.000001")),
					0.0000001);
			*/
		}
		catch (ExpressionException | ConfigException e) {
			e.printStackTrace();
		}
	}

}
