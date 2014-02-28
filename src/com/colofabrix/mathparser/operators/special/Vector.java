/*
Crunchy Math, Version 1.0, February 2014
Copyright (C) 2014 Fabrizio Colonna <colofabrix@gmail.com>

This file is part of Crunchy Math.

Crunchy Math is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

Crunchy Math is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with Crunchy Math; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/
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

/**
 * Represent the basic type to implement the operators to work with Vectors
 * 
 * <p>A vector type will create a vector in memory named {@link Vector#OUTPUT_NAME} which will contain
 * all the operands specified in the vector. The vector type will also refer all the included indexes
 * to the preceding opreator.<br/>
 * What's inside a vector will be minimized if possible, otherwise it will be kept as a {@link CmplxExpression}</p>
 * 
 * @author Fabrizio Colonna
 */
public abstract class Vector extends GroupingOperator {

	/**
	 * Name of the created variable in memory
	 */
	public static final String OUTPUT_NAME = "vector";
	
	/**
	 * Name of the variable that is used for internal works
	 */
	public static final String STACK_NAME = ".vector_internal";

	/**
	 * Moves the operands and the operators between stacks
	 * 
	 * <p>The vector type uses an internal representation of a vector (a Stack<ExpressionEntry> stored
	 * in memory) which is different than the usual postfix/opstack stacks. This function will fetch
	 * the operands from postfix and the operators from opstack and it will put everything in the local
	 * stack.<br/>
	 * The method will fetch everything was put in the stack until a previous OpenVector or PushVector
	 * is found.<p>
	 * 
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed befor the call to this method
     * @param operators A reference to the operators manager
     * @param memory A reference to the main math memory
	 * @return An ExpressionEntry found since the last OpeningVector or PushVector 
	 * @throws ExpressionException If no parameters are given to the function
	 */
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
			throw new ExpressionException( "There must be at least one parameter" );
	}
	
}
