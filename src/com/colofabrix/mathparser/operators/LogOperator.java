package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class LogOperator extends Operator {

	public LogOperator() throws ConfigException {
		super();
		this.setBaseName( "Log" );
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}
	
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );

		double value1 = Operand.extractNumber( operands.pop() );
		
    	return new Operand( Math.log10(value1) );
	}
}
