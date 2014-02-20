package com.colofabrix.mathparser.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.operators.CosOperator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class MemoryTest {

	private static Memory memory = new Memory();

	@Test
	public void testGetValueOrDefault() {
		try {
			// Operator saving and retrival
			Operator a = new CosOperator();
			memory.setValue( "test", a );
			assertSame( "Check value get/set for object",
					a,
					memory.getValue("test") );
			
			// Test for null
			memory.setValue( "test", null );
			assertEquals( "Check get/set for default",
					Memory.DEFAULT_VALUE.getNumericValue(),
					((Operand)memory.getValueOrDefault("test")).getNumericValue(), 0 );
			
			// Test for non-existent value
			memory.setValue( "non_existent_test", null );
			assertEquals( "Check get/set for non-existent",
					Memory.DEFAULT_VALUE.getNumericValue(),
					((Operand)memory.getValueOrDefault("non_existent_test")).getNumericValue(), 0 );
		}
		catch( ExpressionException | ConfigException e ) {
			fail( e.getMessage() );
		}
	}
}
