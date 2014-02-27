package com.colofabrix.mathparser.operators;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class FactOperator extends Operator {

	public FactOperator() throws ConfigException {
		super();
		this.setBaseName( "!" );
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}
	
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );

		double value1 = Operand.extractNumber( operands.pop() );
		
    	return new Operand( this.la_gamma(value1 + 1) );
	}
	
	/**
	 * Stirling approximation for Gamma function
	 * 
	 * <a href="http://rosettacode.org/wiki/Gamma_function#Java">Gamma Function</a>
	 * 
	 * @param x The argument of the gamma function
	 * @return The result of Γ(x) using the Stirling approximation
	 */
	protected double st_gamma( double x ) {
		return Math.sqrt( 2 * Math.PI / x ) * Math.pow( x / Math.E, x );
	}
 
	/**
	 * Lanczos approximation for Gamma function
	 * 
	 * {@link <a href="http://rosettacode.org/wiki/Gamma_function#Java">Gamma Function</a>}
	 * 
	 * @param x The argument of the gamma function
	 * @return The result of Γ(x) using the Lanczos approximation
	 */
	protected double la_gamma( double x ){
		double[] p = { 0.99999999999980993,  676.5203681218851,    -1259.1392167224028,
			     	   771.32342877765313,  -176.61502916214059,    12.507343278686905,
			     	   -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7 };
		int g = 7;
		if( x < 0.5 )
			return Math.PI / ( Math.sin(Math.PI * x) * la_gamma(1 - x) );
 
		x -= 1;
		double a = p[0];
		double t = x + g + 0.5;
		for( int i = 1; i < p.length; i++ )
			a += p[i] / (x + i);
 
		return Math.sqrt( 2 * Math.PI ) * Math.pow(t, x + 0.5) * Math.exp(-t) * a;
	}
}
