package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * An opening vector operator
 * 
 * <p>The opening vector operator is used to mark the beginning of a vector and to initialize the
 * require data structures</p>
 * 
 * @author Fabrizio Colonna
 */
public class VectorOpening extends Vector {
		
    public VectorOpening() throws ConfigException {
		super();
        this.setBaseName( "[" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return true;
	}

	/**
	 * This method will inizialize the internal memory variable used to store some data
	 */
    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		memory.setValue( Vector.STACK_NAME, new CmplxExpression() );
		postfix.push( this );
        return this;
    }
}
