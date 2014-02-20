package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.*;
import com.colofabrix.mathparser.expression.CompositeExpression;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class VectorPush extends GroupingOperator {

	public VectorPush() throws ConfigException {
		super();
		this.setBaseName( "," );
		this.setPriority( (short)2 );
	}
	
	@Override
	public Operand executeOperation( Stack<Operand> operands, Memory memory ) throws ExpressionException {
        return null;
	}
    
    @Override
    public Operator executeParsing( CompositeExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
    	CompositeExpression stack = (CompositeExpression)memory.getValue( VectorOpening.STACK_NAME );
    	
    	if( postfix.size() < 1  || stack == null )
    		throw new ExpressionException();
    	
    	// Call basic constructor. It ensures that the postfix stack is filled with all the operands and operators
    	super.executeParsingClosing( postfix, opstack, operators, memory );
    	
    	// Move all the values pushed previously to the saved stack until the previous OpeningFunction is found.
    	while( postfix.size() > 0 && postfix.lastElement() instanceof VectorOpening || postfix.lastElement() instanceof VectorPush )
    		stack.push( postfix.pop() );
    	
    	// Save the private stack
    	memory.setValue( VectorOpening.STACK_NAME, stack );
        
        return this;
    }

	@Override
	public boolean isOpening() {
		return true;
	}
}