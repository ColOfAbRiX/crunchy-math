package com.colofabrix.mathparser.operators;

import java.util.Stack;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CompositeExpression;
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
	public Operand executeOperation( Stack<Operand> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 5 )	// Start, End, Function, Variable, Step
			throw new ExpressionException(); 

		// Interval start - position 4
		double start = operands.get(4).getNumericValue();
		// Interval end - position 3
		double end = operands.get(3).getNumericValue();
		// Expression to evaluate - position 2
		ExpressionEntry expression = operands.get(2);
		// Integration variable
		Operand variable = operands.get(1);
		// Calculation increment
		double step = operands.get(0).getNumericValue();
		Double result = 0.0;

		// Interval ends in correct order
		if( start > end ) {
			Double tmp = start; start = end; end = tmp;
		}
		// Default step
		step = Math.max( step, 1 / 1000000 );
		// The main variable is substituted with an hidden variable
		//expression = expression.replaceAll( Pattern.quote(variable.get), ".int_" + variable );
		//variable = variable.replaceAll( Pattern.quote(variable), ".int_" + variable );
		
		try {
			// Creation of the parser
			MathParser mp = new MathParser( new Operators(), memory );
			// Conversion of the expression
			CompositeExpression postfix = mp.ConvertToPostfix( expression.toString() );
		
			// Sum of the function over the interval
			for( double current = start; current <= end; current += step ) {
				memory.setValue( variable.getVariableName(), new Operand(current) );
				result += mp.ExecutePostfix( postfix ) * step;
			}
		}
		catch (ConfigException e) {
			throw new ExpressionException();
		}
		
    	return new Operand( result );
	}
}
