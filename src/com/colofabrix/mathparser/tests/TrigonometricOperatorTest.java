package com.colofabrix.mathparser.tests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.operators.ArsinOperator;
import com.colofabrix.mathparser.operators.SinOperator;
import com.colofabrix.mathparser.operators.special.DegreesUnits;
import com.colofabrix.mathparser.operators.special.TrigonometricOperator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class TrigonometricOperatorTest {

	@Test
	public void testGetRadians() {
		Stack<ExpressionEntry> operand = new Stack<ExpressionEntry>();
		Operand result;
		
		try {
			TrigonometricOperator test = new SinOperator();
			test.executeParsing( new CmplxExpression(), new Stack<Operator>(), new Operators(), new Memory() );
			
			// Radians test
			test.setSelectedUnit( DegreesUnits.RADIANS );
			operand.push( new Operand( Math.PI / 6 ) );			// Pi/6 rads = 30 deg
			result = test.executeOperation( operand, null );
			assertEquals( "Using radians", 0.5, result.getNumericValue(), AllTests.PRECISION_ERROR_ALLOWED );
			
			// Degrees test
			test.setSelectedUnit( DegreesUnits.DEGREES );
			operand.push( new Operand( 30.0 ) );				// 30 deg
			result = test.executeOperation( operand, null );
			assertEquals( "Using degrees", 0.5, result.getNumericValue(), AllTests.PRECISION_ERROR_ALLOWED );
			
			// Gradians test
			test.setSelectedUnit( DegreesUnits.GRADIANS );
			operand.push( new Operand( 100/3.0 ) );				// 33.(3) grad = 30 deg
			result = test.executeOperation( operand, null );
			assertEquals( "Using gradians", 0.5, result.getNumericValue(), AllTests.PRECISION_ERROR_ALLOWED );
		}
		catch( ConfigException | ExpressionException e ) {
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
	}

	@Test
	public void testGetCurrent() {
		Stack<ExpressionEntry> operand = new Stack<ExpressionEntry>();
		Operand result;
		
		try {
			TrigonometricOperator test = new ArsinOperator();
			test.executeParsing( new CmplxExpression(), new Stack<Operator>(), new Operators(), new Memory() );
			
			// Radians test
			test.setSelectedUnit( DegreesUnits.RADIANS );
			operand.push( new Operand( 0.5 ) );
			result = test.executeOperation( operand, new Memory() );
			assertEquals( "Using radians", Math.PI / 6, result.getNumericValue(), AllTests.PRECISION_ERROR_ALLOWED );
			
			// Degrees test
			test.setSelectedUnit( DegreesUnits.DEGREES );
			operand.push( new Operand( 0.5 ) );
			result = test.executeOperation( operand, new Memory() );
			assertEquals( "Using degrees", 30, result.getNumericValue(), AllTests.PRECISION_ERROR_ALLOWED );
			
			// Gradians test
			test.setSelectedUnit( DegreesUnits.GRADIANS );
			operand.push( new Operand( 0.5 ) );
			result = test.executeOperation( operand, new Memory() );
			assertEquals( "Using gradians", 33.333333333333333, result.getNumericValue(), AllTests.PRECISION_ERROR_ALLOWED );
		}
		catch( ConfigException | ExpressionException e ) {
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
	}

}
