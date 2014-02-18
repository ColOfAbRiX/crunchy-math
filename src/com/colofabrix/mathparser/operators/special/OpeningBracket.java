package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.org.ConfigException;


public class OpeningBracket extends GroupingOperator {
	
    public OpeningBracket() throws ConfigException {
    	super();
        this.setBaseName( "(" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return true;
	}

    public Double executeOperation( Stack<String> operands, Memory memory ) {
        return null;
    }
	
}
