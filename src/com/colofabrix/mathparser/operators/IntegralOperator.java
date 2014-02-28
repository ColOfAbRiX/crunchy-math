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
package com.colofabrix.mathparser.operators;

import java.util.Stack;

import org.apache.commons.math3.analysis.*;
import org.apache.commons.math3.analysis.integration.*;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * 
 * @author Fabrizio Colonna
 */
public class IntegralOperator extends Operator {

	/**
	 * This class is used to interface with the Apache Math Library
	 * 
	 * @author Fabrizio Colonna
	 */
	protected class WorkingExpression implements UnivariateFunction {

		ExpressionEntry oldMemory;
		ExpressionEntry expression;
		Operand variable;
		private MathParser mp;
		
		/**
		 * The constructor saves internally the expression to evaluate
		 * 
		 * @param mp A math parser to execute the expression
		 * @param expression The expression to evaluate
		 * @param variable The integration variable
		 */
		protected WorkingExpression( MathParser mp, ExpressionEntry expression, Operand variable ) {
			this.mp = mp;
			this.expression = expression;
			this.variable = variable;
			
			// Save the value of a possible old variable
			// NOTE: Here the main memory is used and not a new one because there may be memory references in the variables inside the expression to evaluate
			this.oldMemory = this.mp.getMemory().getValue( variable.getVariableName() );
		}
		
		/**
		 * Calculate the value of the function in a point
		 */
		@Override
		public double value( double arg0 ) {
			this.mp.getMemory().setValue(
					this.variable.getVariableName(),
					new Operand( arg0 ) );
			
			try {
				return mp.ExecutePostfix( this.expression );
			}
			catch( ExpressionException e ) {
				throw new IllegalArgumentException();
			}
		}
		
		public void finalize() {
			// Restore the old variable in memory
			this.mp.getMemory().setValue( variable.getVariableName(), this.oldMemory );
		}
	}

	private MathParser parser;
	
	public IntegralOperator() throws ConfigException {
		super();
		this.setBaseName( "Int" );
		this.setPriority( (short)0 );
		this.setOperandsLimit( 4, 4 );
		this.setCurrentOperands( 4 );
	}
	
	/**
	 * Saves some information about from the caller
	 */
	@Override
	public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		this.parser = new MathParser( operators, memory );
		return super.executeParsing(postfix, opstack, operators, memory);
	}
	
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )	// Start, End, Function, Variable, Precision
			throw new ExpressionException( "Wrong number of given parameters" );

		// Interval start
		double lower = Operand.extractNumber( operands.pop() );
		// Interval end
		double upper = Operand.extractNumber( operands.pop() );
		// Expression to evaluate
		ExpressionEntry expression = operands.pop();
		// Integration variable
		Operand variable = (Operand)operands.pop();

		WorkingExpression function = new WorkingExpression( this.parser, expression, variable );
		BaseAbstractUnivariateIntegrator integrator = new SimpsonIntegrator();
		Double result = integrator.integrate( 1000, function, lower, upper );
		
    	return new Operand( result );
	}
}
