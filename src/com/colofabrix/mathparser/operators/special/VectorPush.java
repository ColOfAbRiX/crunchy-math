package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operator;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class VectorPush extends Operator {

	public VectorPush() throws ConfigException {
		super();
		this.setBaseName( "," );
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}
	
	public Double executeOperation( Stack<String> operands, Memory memory ) throws ExpressionException {
        return null;
	}
    
    @Override
    public Operator executeParsing( Stack<String> postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		@SuppressWarnings("unchecked")
		Stack<String> stack = (Stack<String>)memory.getRaw( VectorOpening.STACK_NAME );
    	if( postfix.size() < 1  || stack == null )
    		throw new ExpressionException();

    	// Move all the values until the previous OpeningFunction. OpeningFunction is the only operator
    	// in opstack, because PushOperator is never pushed
    	while( postfix.size() > 0 && !(operators.fromName(postfix.firstElement()) instanceof VectorOpening) )
    		stack.push( postfix.pop() );

    	// Save the private stack
		memory.setRaw( VectorOpening.STACK_NAME , stack );
        
        return null;
    }	
}