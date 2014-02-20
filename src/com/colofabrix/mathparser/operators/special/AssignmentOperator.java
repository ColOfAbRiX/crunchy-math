package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class AssignmentOperator extends Operator {

	public AssignmentOperator() throws ConfigException {
		super();
		this.setBaseName( "=" );
		this.setPriority( (short)-1 );
	}

	@Override
	public Operand executeOperation( Stack<Operand> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 2 )
			throw new ExpressionException(); 
		
		Operand variable = operands.get(0);
		Operand operand = operands.get(1);

		// The variable must be... a variable!
		if( !variable.isVariable() )
			throw new ExpressionException();
		
		return (Operand)memory.setValue(
				variable.getVariableName(),
				new Operand(operand.getNumericValue()) );
	}
}
