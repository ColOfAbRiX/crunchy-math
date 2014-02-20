package com.colofabrix.mathparser.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CompositeExpression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.operators.MinusOperator;
import com.colofabrix.mathparser.operators.MultiplyOperator;
import com.colofabrix.mathparser.operators.special.ClosingBracket;
import com.colofabrix.mathparser.operators.special.OpeningBracket;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * @author Fabrizio Colonna
 */
public class CompositeExpressionTest extends CompositeExpression {

	/**
	 * Test method for {@link com.colofabrix.mathparser.expression.CompositeExpression#fromExpression(java.lang.String, com.colofabrix.mathparser.Operators, com.colofabrix.mathparser.Memory)}.
	 */
	@Test
	public void testFromExpressionStringOperatorsMemory() {
		try {
			// Manual creation of a reference sub-expression
			CompositeExpression reference = new CompositeExpression();
			reference.add( new Operand(3.0) );
			reference.add( new MultiplyOperator() );
			reference.add( new OpeningBracket() );
			reference.add( new Operand(2.0) );
			reference.add( new MinusOperator() );
			reference.add( new Operand(1.0) );
			reference.add( new ClosingBracket() );
			
			// Method testing
			CompositeExpression test =
					CompositeExpression.fromExpression( "3 * (2 - 1)", new Operators(), new Memory() );
			
			assertEquals( "Conversion - Operand count test", reference.size(), test.size() );
			assertEquals( "Conversion - String test", reference.toString(), test.toString() );
		}
		catch (ExpressionException | ConfigException e) {
			fail( e.getMessage() );
		}
	}
	
	/**
	 * Test method for {@link com.colofabrix.mathparser.expression.CompositeExpression#toString()}.
	 */
	@Test
	public void testToString() {
		try {
			// Manual creation of a reference sub-expression
			CompositeExpression test = new CompositeExpression();
			test.add( new Operand(3.0) );
			test.add( new MultiplyOperator() );
			test.add( new OpeningBracket() );
			test.add( new Operand(2.0) );
			test.add( new MinusOperator() );
			test.add( new Operand(1.0) );
			test.add( new ClosingBracket() );
			
			assertEquals( "String comparison test", "3.0 * ( 2.0 - 1.0 )", test.toString() );
		}
		catch (ConfigException e) {
			fail( e.getMessage() );
		}
	}
}
