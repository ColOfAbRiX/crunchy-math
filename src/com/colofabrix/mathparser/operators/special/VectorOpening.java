package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.GroupingOperator;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operator;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;


public class VectorOpening extends GroupingOperator {
	
	public static final String STACK_NAME = ".stack";
	
    public VectorOpening() throws ConfigException {
		super();
        this.setBaseName( "[" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return true;
	}

    public Double executeOperation( Stack<String> operands, Memory memory ) {
        return null;
    }
    
    @Override
    public Operator executeParsing( Stack<String> postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		memory.setRaw( VectorOpening.STACK_NAME, new Stack<String>() );
		postfix.push( this.getName() );
        return this;
    }
}
