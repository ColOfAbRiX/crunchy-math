package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class MultiplyOperator extends Operator {

	public MultiplyOperator() throws ConfigException {
		super();
		this.setBaseName( "*" );
		this.setPriority( (short)1 );
	}

	public Double executeOperation( Stack<String> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 2 )
			throw new ExpressionException(); 

		double value1 = Operator.translateOperand( operands.get(1), memory );
		double value2 = Operator.translateOperand( operands.get(0), memory );
		
    	return value1 * value2;
	}
}
