package com.colofabrix.mathparser.operators.special;

import java.util.Map;
import java.util.Stack;

import com.colofabrix.mathparser.Memory;
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
public class MemoryOperator extends Operator {

	public MemoryOperator() throws ConfigException {
		super();
		this.setBaseName( "Memory" );
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}
	
	/**
	 * Select the sub-function to call
	 */
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );

		int value1 = (int)Math.round( Operand.extractNumber( operands.pop() ) );
		
		switch( value1 ) {
		case 1:
			// DISPLAY MEMORY DUMP
			this.displayMemory( memory );
			break;
		}

		
    	return new Operand( 0.0 );
	}
	
	private void displayMemory( Memory mem ) {
		System.out.println();
		System.out.println( "Print memory dump" );
		System.out.println( "--------------------------------------" );
		
		for( Map.Entry<String, ExpressionEntry> entry : mem.getDirectMemoryReference().entrySet() ) {
			System.out.print( entry.getKey() );
			System.out.print( "\t\t" );
			System.out.println( entry.getValue().toString() );
		}
		
		System.out.println( "--------------------------------------" );
	}
}
