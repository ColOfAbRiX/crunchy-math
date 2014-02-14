package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;


public class OpeningFunction extends GroupingOperator {
	
    public OpeningFunction() throws ConfigException {
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
    public String executeParsing( Stack<String> postfix, Stack<Operator> opstack, Memory memory ) throws ExpressionException {
		memory.setRaw( ".function_stack", new Stack<String>() );
		return this.getName();
    }
}
