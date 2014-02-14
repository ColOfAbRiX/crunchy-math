package com.colofabrix.mathparser.operators;

import java.util.Stack;
import java.util.regex.Pattern;

import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operator;
import com.colofabrix.mathparser.Operators;
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
	
	public Double executeOperation( Stack<String> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 5 )	// Start, End, Function, Variable, Step
			throw new ExpressionException(); 

		// Interval start - position 4
		double start = Operator.translateOperand( operands.get(4), memory );
		// Interval end - position 3
		double end = Operator.translateOperand( operands.get(3), memory );
		// Expression to evaluate - position 2
		String expression = operands.get(2);
		// Integration variable
		String variable = operands.get(1);
		// Calculation increment
		double step = Operator.translateOperand( operands.get(0), memory );
		Double result = 0.0;

		// Interval ends in correct order
		if( start > end ) {
			Double tmp = start; start = end; end = tmp;
		}
		// Default step
		step = Math.max( step, 1 / 1000000 );
		// The main variable is substituted with an hidden variable
		expression = expression.replaceAll( Pattern.quote(variable), ".int_" + variable );
		variable = variable.replaceAll( Pattern.quote(variable), ".int_" + variable );
		
		try {
			// Creation of the parser
			MathParser mp = new MathParser( new Operators(), memory );
			// Conversion of the expression
			Stack<String> postfix = mp.ConvertToPostfix( expression );
		
			// Sum of the function over the interval
			for( double current = start; current <= end; current += step ) {
				memory.setValue( variable, current );
				result += mp.ExecutePostfix( postfix ) * step;
			}
		}
		catch (ConfigException e) {
			throw new ExpressionException();
		}
		
    	return result;
	}
}
