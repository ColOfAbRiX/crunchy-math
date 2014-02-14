package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;


public class ClosingFunction extends GroupingOperator {
	
    public ClosingFunction() throws ConfigException {
		super();
        this.setBaseName( "]" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return true;
	}

    public Double executeOperation( Stack<String> operands, Memory memory ) throws ExpressionException {
    	return null;
    }
    
    @Override
    public String executeParsing( Stack<String> postfix, Stack<Operator> opstack, Memory memory ) throws ExpressionException {
		@SuppressWarnings("unchecked")
		Stack<String> stack = (Stack<String>)memory.getRaw(".function_stack");
    	if( stack == null )
    		throw new ExpressionException();

    	// Save the last element from the postfix to the local stack
    	stack.push( postfix.lastElement() );
    	
    	// Transfer the working variable to another variable
		memory.setRaw( ".function_stack", null );
		memory.setRaw( ".function_argument", stack );
		
		return null;
    }
}
