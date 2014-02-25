package com.colofabrix.mathparser.operators;

import java.util.Stack;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class IntegralOperator extends Operator {

	public IntegralOperator() throws ConfigException {
		super();
		this.setBaseName( "Int" );
		this.setPriority( (short)0 );
		this.setOperandsLimit( 5, 5 );
		this.setCurrentOperands( 5 );
	}
	
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 5 )	// Start, End, Function, Variable, Step
			throw new ExpressionException( "Wrong number of given parameters" );

		// Interval start
		double start = Operand.extractNumber( operands.pop() );
		// Interval end
		double end = Operand.extractNumber( operands.pop() );
		// Expression to evaluate
		ExpressionEntry expression = operands.pop();
		// Integration variable
		Operand variable = (Operand)operands.pop();
		// Calculation increment
		double step = Operand.extractNumber( operands.pop() );
		Double result = 0.0;

		// Interval ends in correct order
		if( start > end ) {
			Double tmp = start; start = end; end = tmp;
		}
		// Default step
		step = Math.max( step, 1 / 1000000 );
		
		// Save the value of a possible old variable
		ExpressionEntry oldMemory = memory.getValue( variable.getVariableName() );
		
		try {
			// Creation of the parser
			MathParser mp = new MathParser( new Operators(), memory );
		
			// Sum of the function over the interval
			for( double current = start; current <= end; current += step ) {
				memory.setValue( variable.getVariableName(), new Operand(current) );
				result += mp.ExecutePostfix( expression ) * step;
			}
		}
		catch( ConfigException e ) {
			throw new ExpressionException( "Error evaluating integral expressions" );
		}
		
		// Restore the old variable in memory
		memory.setValue( variable.getVariableName(), oldMemory );
		
    	return new Operand( result );
	}
}
