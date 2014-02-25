package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * Set or gets options values
 * 
 * @author Fabrizio Colonna
 */
public class OptionOperator extends Operator {

	public OptionOperator() throws ConfigException {
		super();
		this.setBaseName( "Option" );
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}

	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < 1 )
			throw new ExpressionException( "Wrong number of given parameters" );

		// Checks the type of operands
		ExpressionEntry entry = operands.pop();
		if( entry.getEntryType() == CmplxExpression.COMPOSITE_CODE ) {
			// A CmplxExpression means we are trying t
		}
		else if( entry.getEntryType() == Option.OPTION_CODE ) {
		}
		else
			throw new ExpressionException( "Wrong type of operands" );

		
		
		Option option = ((Option)entry);
		int action = (int)Operand.extractNumber( operands.pop() );
		ExpressionEntry optionValue = operands.pop();
		
		switch( action ) {
		case 1:
			memory.setValue( option.getName(), optionValue );
			break;
		case 2:
		default:
			System.out.println("Value of " + option.getName() );
		}
		
    	return new Operand( 0.0 );
	}
}
