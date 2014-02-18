package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.org.ConfigException;


public class ClosingBracket extends GroupingOperator {
	
    public ClosingBracket() throws ConfigException {
    	super();
        this.setBaseName( ")" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return false;
	}

    public Double executeOperation( Stack<String> operands, Memory memory ) {
        return null;
    }
	
}
