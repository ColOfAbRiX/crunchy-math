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

	@Override
	public Operand executeOperation( Stack<Operand> operands, Memory memory ) throws ExpressionException {
        return null;
    }
    
    @Override
    public Operator executeParsing( CompositeExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		memory.setValue( VectorOpening.STACK_NAME, new CompositeExpression() );
		postfix.push( this );
        return this;
    }
}
