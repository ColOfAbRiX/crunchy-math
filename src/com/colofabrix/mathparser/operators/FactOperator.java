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

import java.math.RoundingMode;
import java.util.Stack;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class FactOperator extends Operator {

	private static final int EXP_PRECISION = 100;
	private static final Apfloat HALF = new Apfloat("0.5", Apfloat.INFINITE);
	private static final Apfloat ONE = new Apfloat(1);
	private static final Apfloat TWO = new Apfloat(2);
	private static final Apfloat PI = ApfloatMath.pi(EXP_PRECISION);
	
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

		Apfloat value1 = Operand.extractNumber(operands.pop());
		
		// If the number is an integer I use the classical factorial function
		if( value1.compareTo(ApfloatMath.round(value1, 1, RoundingMode.FLOOR)) == 0 )
			return new Operand( this.factorial(value1.precision(Apfloat.INFINITE)) );
		
		// If the number is not an integer I use the gamma function
		return new Operand( com.colofabrix.mathparser.lib.ApfloatStat.gamma_Lanczos(value1.add(new Apfloat(1))) );
	}
	
	/**
	 * Factorial for integer values
	 * 
	 * @param x The number to calculate the factorial
	 * @return The factorial of the given number
	 */
	protected Apfloat factorial( Apfloat x ) {
		int result = 1;
		
		for( int i = x.intValue(); i > 0; i-- )
			result *= i;
		
		return new Apfloat(result, Apfloat.INFINITE);
	}
	
	/**
	 * Lanczos approximation for Gamma function
	 * 
	 * {@link <a href="http://rosettacode.org/wiki/Gamma_function#Java">Gamma Function</a>}
	 * 
	 * @deprecated
	 * @param x The argument of the gamma function
	 * @return The result of Γ(x) using the Lanczos approximation
	 */
	protected Apfloat la_gamma( Apfloat x ){
		// Rounded Lanczos coefficients g=7, n=9
		Apfloat[] p = {
				new Apfloat( "0.99999999999980992" ),
				new Apfloat( "676.5203681218851" ),
				new Apfloat( "-1259.1392167224029" ),
				new Apfloat( "771.32342877765312" ),
				new Apfloat( "-176.6150291621406" ),
				new Apfloat( "12.507343278686905" ),
				new Apfloat( "-0.13857109526572012" ),
				new Apfloat( "9.9843695780195716e-6" ),
				new Apfloat( "1.5056327351493116e-7" )
		};
		
		/*
		// Lanczos coefficients g=7, n=9
		// Coefficient from http://rosettacode.org/wiki/Gamma_function#Java
		Apfloat[] p = {
				new Apfloat( "0.99999999999980993" ),
				new Apfloat( "676.5203681218851" ),
				new Apfloat( "-1259.1392167224028" ),
				new Apfloat( "771.32342877765313" ),
				new Apfloat( "-176.61502916214059" ),
				new Apfloat( "12.507343278686905" ),
				new Apfloat( "-0.13857109526572012" ),
				new Apfloat( "9.9843695780195716e-6" ),
				new Apfloat( "1.5056327351493116e-7" )
		};
		*/
		
		/*
		// Lanczos coefficients g=7, n=9
		// Coefficient from http://mrob.com/pub/ries/lanczos-gamma.html#code
		Apfloat[] p = {
				new Apfloat( "0.99999999999980993227684700473478" ),
				new Apfloat( "676.520368121885098567009190444019" ),
				new Apfloat( "-1259.13921672240287047156078755283" ),
				new Apfloat( "771.3234287776530788486528258894" ),
				new Apfloat( "-176.61502916214059906584551354" ),
				new Apfloat( "12.507343278686904814458936853" ),
				new Apfloat( "-0.13857109526572011689554707" ),
				new Apfloat( "9.984369578019570859563E-6" ),
				new Apfloat( "1.50563273514931155834E-7" )
		};
		*/
		Apfloat g = new Apfloat(7);
		
		if( x.compareTo(new Apfloat(0.5)) < 0 )
			return PI.divide(ApfloatMath.sin(PI.multiply(x)).multiply(
					this.la_gamma( ONE.subtract(x) ) ));		// PI / ( sin(PI * x) * la_gamma(1 - x) )
	
		x = x.subtract(ONE);									// x -= 1
		Apfloat a = p[0];
		Apfloat t = x.add( g.add(HALF) );						// t = x + g + 0.5
		
		for( int i = 1; i < p.length; i++ )
			a = a.add( p[i].divide(x.add(new Apfloat(i))));		// a += p[i] / (x + i)
	
		Apfloat result = ApfloatMath.sqrt( TWO.multiply(PI) )
				.multiply( ApfloatMath.pow(t, x.add(HALF).precision(EXP_PRECISION)) )
				.multiply( ApfloatMath.exp(t.negate().precision(EXP_PRECISION)) )
				.multiply( a );									// sqrt( 2 * PI ) * pow(t, x + 0.5) * exp(-t) * a;
		
		return result.precision(Apfloat.INFINITE);
	}
}
