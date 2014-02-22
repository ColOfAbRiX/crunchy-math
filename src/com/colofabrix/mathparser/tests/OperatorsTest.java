package com.colofabrix.mathparser.tests;

import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.org.ConfigException;

/**
 * @author Fabrizio Colonna
 */
public class OperatorsTest extends Operators {
	private static final long serialVersionUID = 6371343270148186589L;
	public OperatorsTest() throws ConfigException { super(); }

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.Operators#executeExpression(com.colofabrix.mathparser.expression.Operator, Stack, com.colofabrix.mathparser.Memory)}.
	 */
	@Test
	public void testExecuteExpression() {
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.Operators#isOperator(java.lang.String)}.
	 */
	@Test
	public void testIsOperator() {
	}

	/**
	 * Test method for {@link com.colofabrix.mathparser.Operators#fromName(java.lang.String)}.
	 */
	@Test
	public void testFromName() {
	}

}
