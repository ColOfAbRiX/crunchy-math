package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class ClosingBracket extends GroupingOperator {
	
    public ClosingBracket() throws ConfigException {
    	super();
        this.setBaseName( ")" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return false;
	}

	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
        return null;
    }
	
}
