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

		double start = Operator.translateOperand( operands.get(4), memory );
		double end = Operator.translateOperand( operands.get(3), memory );
		String expression = operands.get(2);
		String variable = operands.get(1);
		double step = Operator.translateOperand( operands.get(0), memory );
		Double result = 0.0;
		
		start = Math.min( start, end );
		end = Math.max( start, end );
		step = Math.max( step, 1 / 1000000 );
		expression = expression.replaceAll( Pattern.quote(variable), ".int_" + variable );
		variable = variable.replaceAll( Pattern.quote(variable), ".int_" + variable );
		
		try {
			MathParser mp = new MathParser( new Operators(), memory );
			Stack<String> postfix = mp.ConvertToPostfix( expression );
		
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
