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

import org.apfloat.Apfloat;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * Displays operators information
 * 
 * @author Fabrizio Colonna
 */
public class OperatorsOperator extends Operator {

	private Operators ops;
	
	public OperatorsOperator() throws ConfigException {
		super();
		this.setBaseName( "Operators" );
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}
	
	/**
	 * This method is used to save the operators information locally
	 */
	@Override
	public Operator executeParsing(CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory) throws ExpressionException {
		this.ops = operators;
		return super.executeParsing( postfix, opstack, operators, memory );
	};
	
	/**
	 * Select the sub-function to call
	 * 1 = Display all operators
	 */
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );

		int value1 = Operand.extractNumber( operands.pop() ).intValue();
		
		switch( value1 ) {
		case 1:
			// DISPLAY OPERATORS LIST
			this.displayOperators( this.ops );
			break;
		}

		
    	return new Operand( new Apfloat(0) );
	}
	
	private void displayOperators( Operators ops ) {
		System.out.println();
		System.out.println( "Print memory dump" );
		System.out.println( "--------------------------------------" );
		
		for( Operator entry : ops ) {
			System.out.print( entry.getBaseName() );
			System.out.print( "\t\t" );
			System.out.print( entry.getCurrentOperands() );
			System.out.print( "\t" );
			System.out.println( entry.getOperandsMin() + " " + entry.getOperandsMax() );
		}
		
		System.out.println( "--------------------------------------" );
	}
}
