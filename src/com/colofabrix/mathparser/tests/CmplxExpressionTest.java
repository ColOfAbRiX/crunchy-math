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

import static org.junit.Assert.*;

import org.junit.Test;

import com.colofabrix.mathparser.*;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
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
public class CmplxExpressionTest extends CmplxExpression {

	/**
	 * Test method for {@link com.colofabrix.mathparser.expression.CmplxExpression#fromExpression(java.lang.String, com.colofabrix.mathparser.Operators, com.colofabrix.mathparser.Memory)}.
	 */
	@Test
	public void testFromExpressionStringOperatorsMemory() {
		try {
			// Manual creation of a reference sub-expression
			CmplxExpression reference = new CmplxExpression();
			reference.add( new Operand(3.0) );
			reference.add( new MultiplyOperator() );
			reference.add( new OpeningBracket() );
			reference.add( new Operand(2.0) );
			reference.add( new MinusOperator() );
			reference.add( new Operand(1.0) );
			reference.add( new ClosingBracket() );
			
			// Method testing
			CmplxExpression test =
					CmplxExpression.fromExpression( "3 * (2 - 1)", new Operators(), new Memory() );
			
			assertEquals( "Conversion - Operand count test", reference.size(), test.size() );
			assertEquals( "Conversion - String test", reference.toString(), test.toString() );
		}
		catch (ExpressionException | ConfigException e) {
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
	}
	
	/**
	 * Test method for {@link com.colofabrix.mathparser.expression.CmplxExpression#toString()}.
	 */
	@Test
	public void testToString() {
		try {
			// Manual creation of a reference sub-expression
			CmplxExpression test = new CmplxExpression();

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
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
	}
	
	@Test
	public void testStackMethods() {
		try {
			CmplxExpression test = new CmplxExpression();
			ExpressionEntry item1, item2;
			int support;
			
			test.add( item1 = new Operand(3.0) );
			test.add( new MultiplyOperator() );
			test.add( new OpeningBracket() );
			test.add( new Operand(2.0) );
			test.add( new MinusOperator() );
			test.add( new Operand(1.0) );
			test.add( item2 = new ClosingBracket() );
			
			assertSame( "Test .firstElement()", item1, test.firstElement() );
			assertSame( "Test .lastElement()", item2, test.lastElement() );

			support = test.size();
			test.push( item1 );
			assertSame( "Test .push()", item1, test.lastElement() );
			assertEquals("Test .push() collection size", support + 1, test.size() );
			assertSame( "Test .pop()", item1, test.pop() );
			assertEquals("Test .pop() collection size", support, test.size() );
		}
		catch (ConfigException e) {
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
	}
	
	@Test
	public void testIsMinimizable() {
		try {
			MathParser mp = new MathParser( new Operators(), new Memory() );

			assertEquals("Not minimizable", false, mp.ConvertToPostfix( "x * Sin x" ).isMinimizable() );
			assertEquals("Not minimizable", false, mp.ConvertToPostfix( "x ^ 2 * Sin x" ).isMinimizable() );
			assertEquals("Not minimizable", false, mp.ConvertToPostfix( "-1 + Int[-2, 1, x ^ 2 * Sin x, x, 0.01]" ).isMinimizable() );
			assertEquals("Minimizable", false, mp.ConvertToPostfix( "2 * (5 + Sin 6) / (7 + 1)" ).isMinimizable() );
		}
		catch (ConfigException | ExpressionException e) {
			e.printStackTrace();
			fail( e.getMessage().getClass().toString() );
		}
	}
}
