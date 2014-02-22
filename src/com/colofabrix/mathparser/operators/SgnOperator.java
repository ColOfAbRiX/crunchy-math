package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class SgnOperator extends Operator {

	public SgnOperator() throws ConfigException {
		super();
		this.setBaseName( "Sgn" );
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
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 1 )
			throw new ExpressionException(); 

		double value1 = Operand.extractNumber( operands.pop() );
		
    	return new Operand( Math.signum(value1) );
	}
}
