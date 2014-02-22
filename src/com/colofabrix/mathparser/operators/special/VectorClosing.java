package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class VectorClosing extends Vector {
	
	public static final String STACK_NAME = VectorOpening.STACK_NAME;
	public static final String OUTPUT_NAME = ".vector";
	
    public VectorClosing() throws ConfigException {
		super();
        this.setBaseName( "]" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return false;
	}
    
    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
    	CmplxExpression stack = (CmplxExpression)memory.getValue( VectorOpening.STACK_NAME );
    	
    	if( stack == null )
    		throw new ExpressionException();

    	// Save the operands to the stack
    	stack.push( this.prepareOperands(postfix, opstack, operators, memory) );
    	    	
    	// Transfer the working variable to another variable
		memory.setValue( VectorOpening.STACK_NAME, null );
		memory.setValue( VectorClosing.OUTPUT_NAME, stack );
		
    	// Add all the fetched operands in the postfix string
		postfix.pop();
		postfix.addAll( stack );
		
        return null;
    }
}
