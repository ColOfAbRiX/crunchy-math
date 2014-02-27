package com.colofabrix.mathparser.operators.special;

import java.util.Stack;
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

		int value1 = (int)Math.round( Operand.extractNumber( operands.pop() ) );
		
		switch( value1 ) {
		case 1:
			// DISPLAY OPERATORS LIST
			this.displayOperators( this.ops );
			break;
		}

		
    	return new Operand( 0.0 );
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
