package com.colofabrix.mathparser.expression;

import java.util.Stack;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * Represent a grouping operators
 * 
 * <p>A grouping operator is an operator with a beginning and an end, within the infix expression, wich
 * encloses an expression. Usually the eclosed expression has priority over the outer expression<br/>
 * This is a default implementation that models the behaviour of the common brackets</p>
 * 
 * @author fcolonna
 */
public abstract class GroupingOperator extends Operator {
	
	/**
	 * The constructor initialize the object setting the number of operands to 1 and the variable
	 * grouping to true 
	 */
	public GroupingOperator() {
		
		this.setGrouping( true );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}
	
	/**
	 * It indicates if the class represents an opening grouping
	 * 
	 * @return <code>true</code> if the object is an opening grouping
	 */
	public abstract boolean isOpening();

    /**
     * Gets the absolute priority of the operand
     * 
     * <p>The priority of an operator is expressed using an Integer, while a priority can only be
     * set using a Short because priorities use levels.</p>
     * 
     * @see com.colofabrix.mathparser.Operator.getPriority
     * @param priority A number that establish the priority of the operator.
     */
    @Override
    public int getPriority() {
    	// Short.MAX_VALUE is doubled because this object has getCurrentOperands() == 1
    	return super.getPriority() - 2 * Short.MAX_VALUE;
    };
    
    /**
     * Sets the number of operands currently used
     * 
     * <p>A grouping operator is always a unary operator, so whatever value is given this function
     * will set the currentOperands always to 1.</p>
     * 
     * @param value A number indicating how many operands this operator needs
     * @throws Exception This exception is thrown when a wrong number of current operands is specified
     */
	@Override
    public final void setCurrentOperands( int value ) {
		return;
	}
	
    /**
     * Sets the limits of the number of operands allowed for the operator
     * 
     * <p>A grouping operator is always a unary operator, so whatever value is given this function
     * will set the maximum and minimum are always 1.</p>
     * 
     * @param min Minimum number of operand allowed
     * @param max Maximum number of operand allowed
     */
	@Override
	public final void setOperandsLimit(int min, int max) {
		return;
	};

    /**
     * Execute the parsing operation that the operator may require
     * 
     * <p>The parsing operation is an operation that is performed when the operator is fetched from
     * the input string</br>
     * The default implementation for a grouping operator divides the operation for the opening groping
     * and the closing groping, calling the respective functions</p>
     *
     * @see Operator.executeParsing
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed befor the call to this method
     * @param memory A reference to the main math memory
     * @return An instance of the operator to be pushed at the end of the operators stack, 
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
	@Override
	public Operator executeParsing( Stack<String> postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		Operator result;
		
		// Executes different parsing between opening and closing grouping
		if( this.isOpening() )
			result = this.executeParsingOpening( postfix, opstack, operators, memory );
		
		else
			result = this.executeParsingClosing( postfix, opstack, operators, memory );
		
		return result;
	};
	
    /**
     * Execute the parsing operation that the operator may require
     * 
     * <p>This function is called when an openining grouping is fetched from the input string</p>
     *
     * @see Operator.executeParsing
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed befor the call to this method
     * @param operators TODO
     * @param memory A reference to the main math memory
     * @return An instance of the operator to be pushed at the end of the operators stack, 
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
	protected Operator executeParsingOpening( Stack<String> postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
        return this;
	}
	
    /**
     * Execute the parsing operation that the operator may require
     * 
     * <p>This function is called when an closing grouping is fetched from the input string</p>
     *
     * @see Operator.executeParsing
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed befor the call to this method
     * @param operators TODO
     * @param memory A reference to the main math memory
     * @return An instance of the operator to be pushed at the end of the operators stack, 
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
	protected Operator executeParsingClosing( Stack<String> postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
        // Pop all the previous operators from the stack and push them in the postfix string until I find an opening grouping
        while( opstack.size() > 0 ) {
            Operator tmp = opstack.pop();
            
            if( tmp.isGrouping() && ((GroupingOperator)tmp).isOpening() )
                break;
            
            /* NOTE: For normal brackets this is important, but for generic operators this could not
             * be good and, in fact, it creates problems with the Vector type
            // Check parentheses match
            if( opstack.size() == 0 )
                throw new ExpressionException();
            */
            postfix.push( tmp.getName() );
        }
        
        return null;
	}
}
