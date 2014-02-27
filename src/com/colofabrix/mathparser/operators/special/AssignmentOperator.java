package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * Assignment operator
 * 
 * <p>This operator set a memory variable with a value.</p>
 * 
 * @author Fabrizio Colonna
 */
public class AssignmentOperator extends Operator {

	public AssignmentOperator() throws ConfigException {
		super();
		this.setBaseName( "=" );
		this.setPriority( (short)-1 );
	}

    /**
     * Set a memory variable with the value of the second operand
     *  
     * @param operands A stack containing the operands in reversed order
     * @param memory A reference to the main math memory
     * @return It returns the value that has just set in memory
     * @throws ExpressionException If the second operand is not an object of type Operand
     * @throws ExpressionException If the first operand is not a variable name
     */
	@Override
	public Operand executeOperation( Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
		Operand variable, operand;
		
		if( operands.size() < this.getCurrentOperands() )
			throw new ExpressionException( "Wrong number of given parameters" );
		
		// The operands must be of type Operand, otherwise Exception
		try {
			variable = (Operand)operands.pop();
			operand = (Operand)operands.pop();
		}
		catch( ClassCastException e ) {
			throw new ExpressionException( "Wrong type of given parameters" );
		}

		// The variable must be... a variable!
		if( !variable.isVariable() )
			throw new ExpressionException( "Cannot assign to a number" );
		
		return (Operand)memory.setValue(
				variable.getVariableName(),
				new Operand(operand.getNumericValue()) );
	}
}
