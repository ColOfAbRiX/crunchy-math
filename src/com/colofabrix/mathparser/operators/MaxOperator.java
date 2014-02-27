package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class MaxOperator extends Operator {

	public MaxOperator() throws ConfigException {
		super();
		this.setBaseName( "Min" );
		this.setPriority( (short)0 );
	}
	
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );

		double value1 = Operand.extractNumber( operands.pop() );
		double value2 = Operand.extractNumber( operands.pop() );
		
    	return new Operand( Math.max(value1, value2) );
	}
}
