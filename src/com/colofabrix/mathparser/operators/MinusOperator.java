package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class MinusOperator extends Operator {

	public MinusOperator() throws ConfigException {
		super();
		this.setBaseName( "-" );
		this.setPriority( (short)0 );
		this.setOperandsLimit( 1, 2 );
	}

	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		double value1, value2;
		
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException(); 

		value1 = Operand.extractNumber( operands.pop() );

		// Fetch the second operand only if needed
		if( this.getCurrentOperands() == 1 ) {
			return new Operand( -value1 );
		}
		else {
			value2 = Operand.extractNumber( operands.pop() );
			return new Operand( value1 - value2 );
		}
	}
}
