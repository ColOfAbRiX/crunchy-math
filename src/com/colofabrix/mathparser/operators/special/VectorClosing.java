package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CompositeExpression;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.expression.Operand;
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

	@Override
	public Operand executeOperation( Stack<Operand> operands, Memory memory ) throws ExpressionException {
    	return null;
    }
    
    @Override
    public Operator executeParsing( CompositeExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
    	CompositeExpression stack = (CompositeExpression)memory.getValue( VectorOpening.STACK_NAME );
    	
    	if( stack == null )
    		throw new ExpressionException();

    	// Move all the values until the previous OpeningFunction. OpeningFunction is the only operator
    	// in opstack, because PushOperator is never pushed
    	while( postfix.size() > 0 && !(postfix.lastElement() instanceof VectorOpening) )
    		stack.push( postfix.pop() );
    	
    	// Check the presence of an opening bracket
    	while( postfix.size() > 0 && !(postfix.lastElement() instanceof VectorOpening || postfix.lastElement() instanceof VectorPush) )
    		throw new ExpressionException();
    	    	
    	// Transfer the working variable to another variable
		memory.setValue( VectorOpening.STACK_NAME, null );
		memory.setValue( VectorClosing.OUTPUT_NAME, stack );
		
		// Remove the opening parenthesis from the operator stack
    	opstack.pop();
    	
    	// Add all the fetched operands in the postfix string
		postfix.pop();
		postfix.addAll( stack );
		
        return null;
    }
}
