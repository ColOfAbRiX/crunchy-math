package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ExpressionException;

public abstract class Vector extends GroupingOperator {

	@Override
	public Operand executeOperation(Stack<ExpressionEntry> operands, Memory memory) throws ExpressionException {
		return null;
	}

	public abstract boolean isOpening();

	protected ExpressionEntry prepareOperands( CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		MathParser mp = new MathParser( operators, memory );
		CmplxExpression local = new CmplxExpression();

    	// Call basic constructor. It ensures that the postfix stack is filled with all the operands and operators
    	super.executeParsingClosing( postfix, opstack, operators, memory );
    	
    	// Move all the values pushed previously to the saved stack until the previous OpeningFunction is found.

    	// First I fetch the values from the last to the one before the opening vector and I put them in a new CmplxExpression
    	while( postfix.size() > 0 && !(postfix.lastElement() instanceof Vector && ((Vector)postfix.lastElement()).isOpening()) )
    		local.add( 0, postfix.pop() );
    	
    	// If the local expression contains only one element I return it raw
    	if( local.size() == 1 )
    		return local.firstElement();
    	
    	// If there are more than one operands I return...
    	else if( local.size() > 1 )
    		// ... the CmplxExpression if it not minimizable....
    		if( !local.isMinimizable() )
    			return local;
    	
    		// ... or a minimized expression
    		else
    			return new Operand( mp.ExecutePostfix(local) );
    	
		// Size of zero means an that no operands are specified and this is not allowed
    	else
    		throw new ExpressionException();
	}
	
}
