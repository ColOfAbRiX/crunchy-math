package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.*;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * A vector push operator
 * 
 * <p>A vector push is a delimiter that splits the last index from a new one. It is used to create
 * subexpressions inside the vector</p>
 * 
 * @author Fabrizio Colonna
 */
public class VectorPush extends Vector {

	public VectorPush() throws ConfigException {
		super();
		this.setBaseName( "," );
		this.setPriority( (short)2 );
	}
	
	/**
	 * It moves the last operands and operators
	 * 
	 * <p>It appends the vector entries to the postfix stack</p> 
	 */
    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
    	CmplxExpression stack = (CmplxExpression)memory.getValue( Vector.STACK_NAME );
    	
    	if( stack == null )
			throw new ExpressionException( "There is no previous vector to push into" );
    	
    	if( postfix.size() < 1 )
			throw new ExpressionException( "Wrong number of given parameters" );
    	
    	// Save the operands to the stack
    	stack.push( this.prepareOperands(postfix, opstack, operators, memory) );

    	// Save the private stack
    	memory.setValue( Vector.STACK_NAME, stack );
        
        return this;
    }

	@Override
	public boolean isOpening() {
		return true;
	}
}