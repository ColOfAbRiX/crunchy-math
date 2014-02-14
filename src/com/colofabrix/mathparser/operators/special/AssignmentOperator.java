package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operator;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class AssignmentOperator extends Operator {

	public AssignmentOperator() throws ConfigException {
		super();
		this.setBaseName( "=" );
		this.setPriority( (short)-1 );
	}

	public Double executeOperation( Stack<String> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 2 )
			throw new ExpressionException(); 
		
		String operand = operands.get(1);
		
		// Numeric operand
    	if( operand.matches(Operators.NUMBER_REGEX) )
    		return Double.valueOf( operand );
    	
    	// Variable from memory
    	else if( operand.matches(Operators.VARIABLE_REGEX) )
    		return memory.setValue( operand, Double.valueOf(operands.get(0)) );
    	
    	return null;
	}
}
