package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.*;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class VectorPush extends Vector {

	public VectorPush() throws ConfigException {
		super();
		this.setBaseName( "," );
		this.setPriority( (short)2 );
	}
	
    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
    	CmplxExpression stack = (CmplxExpression)memory.getValue( VectorOpening.STACK_NAME );
    	
    	if( postfix.size() < 1  || stack == null )
    		throw new ExpressionException();
    	
    	// Save the operands to the stack
    	stack.push( this.prepareOperands(postfix, opstack, operators, memory) );

    	// Save the private stack
    	memory.setValue( VectorOpening.STACK_NAME, stack );
        
        return this;
    }

	@Override
	public boolean isOpening() {
		return true;
	}
}