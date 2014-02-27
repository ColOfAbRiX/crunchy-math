package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class RootOperator extends Operator {

	public RootOperator() throws ConfigException {
		super();
		this.setBaseName( "%" );
		this.setPriority( (short)3 );
	}

	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );

		double value1 = Operand.extractNumber( operands.pop() );
		double value2 = Operand.extractNumber( operands.pop() );
		
    	return new Operand( Math.pow(value1, 1 / value2) );
	}
}
