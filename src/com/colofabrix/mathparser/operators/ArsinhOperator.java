package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.operators.special.TrigonometricOperator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class ArsinhOperator extends TrigonometricOperator {

	public ArsinhOperator() throws ConfigException {
		super();
		this.setBaseName( "Arsinh" );
	}
	
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );

		double value1 = Operand.extractNumber( operands.pop() );
		double result = Math.log(value1 + Math.sqrt(Math.pow(value1, 2) + 1));
		
    	return new Operand( this.getCurrent(result) );
	}
}
