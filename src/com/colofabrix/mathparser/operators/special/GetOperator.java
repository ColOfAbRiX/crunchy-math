package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * Set an option value
 * 
 * <p>Options are configuration parameters that the various parts of the parser can use as they prefer.
 * There are no rules on options, options are simply memory variable with a name that starts with a
 * marker {@link Option#OPTION_MARK} and they contain a generic {@link ExpressionEntry} value.</p>
 * 
 * @author Fabrizio Colonna
 */
public class GetOperator extends Operator {

	public GetOperator() throws ConfigException {
		super();
		this.setBaseName( "Get" );
		this.setPriority( (short)2 );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}

	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );
		
		try {
			Option option = (Option)operands.pop();
			ExpressionEntry value = memory.getValue( option.getFullName() );
			
			if( value.getEntryType() == Operand.OPERAND_CODE )
				return ((Operand)value);
		}
		catch( ClassCastException e ) {
			throw new ExpressionException( "Wrong type of given parameters" );
		}
		
		return null;
	}
}
