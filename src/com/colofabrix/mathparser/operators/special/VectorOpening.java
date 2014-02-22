package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class VectorOpening extends Vector {
	
	public static final String STACK_NAME = ".stack";
	
    public VectorOpening() throws ConfigException {
		super();
        this.setBaseName( "[" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return true;
	}

    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		memory.setValue( VectorOpening.STACK_NAME, new CmplxExpression() );
		postfix.push( this );
        return this;
    }
}
