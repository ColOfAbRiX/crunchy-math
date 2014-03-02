package com.colofabrix.mathparser.tests;

import static org.junit.Assert.*;

import java.util.Stack;

import org.apfloat.Apfloat;
import org.junit.Test;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.lib.ApfloatConsts;
import com.colofabrix.mathparser.operators.ArsinOperator;
import com.colofabrix.mathparser.operators.SinOperator;
import com.colofabrix.mathparser.operators.special.DegreesUnits;
import com.colofabrix.mathparser.operators.special.TrigonometricOperator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class TrigonometricOperatorTest {

	private static final Apfloat VAL_DEGREES = new Apfloat( 30 );
	private static final Apfloat VAL_RADIANS = ApfloatConsts.PI.divide(new Apfloat(6));
	private static final Apfloat VAL_GRADIANS = new Apfloat(100).divide(new Apfloat(3.0));

	@Test
	public void testGetRadians() {
		Stack<ExpressionEntry> operand = new Stack<ExpressionEntry>();
		Operand result;
		
		try {
			TrigonometricOperator test = new SinOperator();
			test.executeParsing( new CmplxExpression(), new Stack<Operator>(), new Operators(), new Memory() );
						
			// Radians test
			test.setSelectedUnit( DegreesUnits.RADIANS );
			operand.push( new Operand( VAL_RADIANS ) );						// Pi/6 rads = 30 deg
 			result = test.executeOperation( operand, null );
			assertEquals( "Using radians",
					0.5,
					result.getNumericValue().doubleValue(),
					AllTests.PRECISION_ERROR_ALLOWED );
			
			
			// Degrees test
			test.setSelectedUnit( DegreesUnits.DEGREES );
			operand.push( new Operand( VAL_DEGREES ) );						// 30 deg
			result = test.executeOperation( operand, null );
			assertEquals( "Using degrees",
					0.5,
					result.getNumericValue().doubleValue(),
					AllTests.PRECISION_ERROR_ALLOWED );
			
			
			// Gradians test
			test.setSelectedUnit( DegreesUnits.GRADIANS );
			operand.push( new Operand( VAL_GRADIANS ) );					// 33.(3) grad = 30 deg
			result = test.executeOperation( operand, null );
			assertEquals( "Using gradians",
					0.5,
					result.getNumericValue().doubleValue(),
					AllTests.PRECISION_ERROR_ALLOWED );
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
			operand.push( new Operand( new Apfloat(0.5) ) );
			result = test.executeOperation( operand, new Memory() );
			assertEquals( "Using radians",
					VAL_RADIANS.doubleValue(),
					result.getNumericValue().doubleValue(),
					AllTests.PRECISION_ERROR_ALLOWED );
			
			
			// Degrees test
			test.setSelectedUnit( DegreesUnits.DEGREES );
			operand.push( new Operand( new Apfloat(0.5) ) );
			result = test.executeOperation( operand, new Memory() );
			assertEquals( "Using degrees",
					VAL_DEGREES.doubleValue(),
					result.getNumericValue().doubleValue(),
					AllTests.PRECISION_ERROR_ALLOWED );
			
			
			// Gradians test
			test.setSelectedUnit( DegreesUnits.GRADIANS );
			operand.push( new Operand( new Apfloat(0.5) ) );
			result = test.executeOperation( operand, new Memory() );
			assertEquals( "Using gradians",
					VAL_GRADIANS.doubleValue(),
					result.getNumericValue().doubleValue(),
					AllTests.PRECISION_ERROR_ALLOWED );
		}
		catch( ConfigException | ExpressionException e ) {
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
	}

}
