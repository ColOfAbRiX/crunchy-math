package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;


public class VectorClosing extends GroupingOperator {
	
	public static final String STACK_NAME = VectorOpening.STACK_NAME;
	public static final String OUTPUT_NAME = ".vector";
	
    public VectorClosing() throws ConfigException {
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
    public Operator executeParsing( Stack<String> postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		@SuppressWarnings("unchecked")
		Stack<String> stack = (Stack<String>)memory.getRaw( VectorOpening.STACK_NAME );
    	if( stack == null )
    		throw new ExpressionException();

    	// Move all the values until the previous OpeningFunction. OpeningFunction is the only operator
    	// in opstack, because PushOperator is never pushed
    	while( postfix.size() > 0 && !(operators.fromName(postfix.lastElement()) instanceof VectorOpening) )
    		stack.push( postfix.pop() );
    	
    	// Check the presence of an opening bracket
    	while( postfix.size() > 0 && !(operators.fromName(postfix.lastElement()) instanceof VectorOpening || operators.fromName(postfix.lastElement()) instanceof VectorPush) )
    		throw new ExpressionException();
    	    	
    	// Transfer the working variable to another variable
		memory.setRaw( VectorOpening.STACK_NAME, null );
		memory.setRaw( VectorClosing.OUTPUT_NAME, stack );		
		
		// Remove the opening parenthesis from the operator stack
    	opstack.pop();
    	// Add all the fetched operands in the postfix string
		postfix.pop();
		//postfix.addAll( stack )
		postfix.add( stack.toString() );
		
        return null;
    }
}
