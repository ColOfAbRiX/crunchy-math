package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class CosOperator extends Operator {

	public CosOperator() throws ConfigException {
		super();
		this.setBaseName( "Cos" );
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}
	
	@Override
    public void setCurrentOperands( int value ) {
		try {
			super.setCurrentOperands( 1 );
		}
		catch (ConfigException e) {}
	}
	
	@Override
	public Operand executeOperation( Stack<Operand> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 1 )
			throw new ExpressionException(); 

		double value1 = operands.get(0).getNumericValue();
		
    	return new Operand( Math.cos(value1) );
	}
}
