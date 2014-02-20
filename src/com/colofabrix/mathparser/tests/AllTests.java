package com.colofabrix.mathparser.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( {
	MathParserTest.class,
	MemoryTest.class,
	OperatorsTest.class })
public class AllTests { }
