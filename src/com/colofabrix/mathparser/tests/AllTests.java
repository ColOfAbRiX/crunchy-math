package com.colofabrix.mathparser.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( {
	MathParserTest.class,
	MemoryTest.class,
	OperatorsTest.class,
	TrigonometricOperatorTest.class })
public class AllTests {
	public static double PRECISION_ERROR_ALLOWED = 1E-6;
}
