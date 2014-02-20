package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class MinusOperator extends Operator {

	public MinusOperator() throws ConfigException {
		super();
		this.setBaseName( "-" );
		this.setPriority( (short)0 );
		this.setOperandsLimit( 1, this.getOperandsMax() );
	}

	@Override
	public Operand executeOperation( Stack<Operand> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 2 )
			throw new ExpressionException(); 

		double value1 = operands.get(1).getNumericValue();
		double value2 = operands.get(0).getNumericValue();
		
    	return new Operand( value1 - value2 );
	}
}
