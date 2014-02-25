package com.colofabrix.mathparser;

import java.util.*;

import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * Mathemathical Expression Parser
 * 
 * @author Fabrizio Colonna
 * @version 0.3
 * @since 2014-01
 */
public class MathParser {
	
	private Operators operators;
	private Memory memory = new Memory();

	/**
	 * Creates and initialize the MathParser
	 * 
	 * <p>This constructor allow to specify a custom operators manager and memory manager</p>
	 * 
	 * @param manager The choosen Operators Manager, which contains a collection of supported operators.
	 */
	public MathParser( Operators manager, Memory memory ) {
		this.operators = manager;
		this.memory = memory;
	}
	
	/**
	 * Creates and initialize MathParser
	 * 
	 * @throws ConfigException
	 */
	public MathParser() throws ConfigException {
		this.operators = new Operators();
		this.memory = new Memory();
	}
	
	/**
	 * Infix-to-postfix converter
	 * 
	 * @param input The mathematica expression to convert
	 * @return A Stack containing the converted string
	 * @throws ExpressionException In case of bad input expression
	 * @throws ConfigException In case of misconfiguration of MathParser
	 */
    public CmplxExpression ConvertToPostfix( String input ) throws ExpressionException, ConfigException {

    	CmplxExpression infix = CmplxExpression.fromExpression(input, operators, memory);
        CmplxExpression postfix = new CmplxExpression();
        Stack<Operator> opstack = new Stack<>();
        ExpressionEntry lastEntry = null;

        for( ExpressionEntry entry: infix ) {
        	
            // Add an operator
        	if( entry.getEntryType() == Operator.OPERATOR_CODE ) {
        		Operator currentOp = (Operator)entry;

        		// At the beginning of the expression are unary only the operators with getOperandMin == 1
            	if( (lastEntry == null && currentOp.getOperandsMin() == 1) )
            		currentOp.setCurrentOperands( 1 );
            	
                // Two consecutive operators means the the latter is unary, except in case of the first one is a closing grouping
            	if( lastEntry != null && lastEntry.getEntryType() == Operator.OPERATOR_CODE && !(((Operator)lastEntry).isGrouping() && !((GroupingOperator)lastEntry).isOpening()) )
            		if( currentOp.getOperandsMin() <= 2 )
            			currentOp.setCurrentOperands( 1 );
            	
            	// Execution of the custom parsing performed by the operators themselves
            	currentOp = currentOp.executeParsing( postfix, opstack, this.operators, this.memory );
                if( currentOp != null )
                	opstack.push( currentOp );
        	}
            // Add anything else
        	else
        		postfix.add( entry );
        	
            // Remember the last entry
        	lastEntry = entry;
        }
        
        // Transfer the remaining operators
        while( opstack.size() > 0 )
        	postfix.add( opstack.pop() );

        return CmplxExpression.fromExpression(postfix, operators, memory);
    }
    
    /**
     * Executes an postfix-notation mathematical expression
     * 
     * @param input The input stack containing the expression
     * @return A number indicating the result of the expression
	 * @throws ExpressionException In case of bad input expression
     */
    public Double ExecutePostfix( CmplxExpression input ) throws ExpressionException {    	
        Stack<ExpressionEntry> localStack = new Stack<>();
        
    	for( ExpressionEntry entry: input ) {
    		if( entry.getEntryType() == Operator.OPERATOR_CODE ) {
    			Operator currentOp = (Operator)entry;
            	Stack<ExpressionEntry> localOperands = new Stack<>();

            	// Counting the number of operands needed
            	int o = currentOp.getCurrentOperands();
                
            	// Check the operand count for the operator
            	if( localStack.size() < o )
            		throw new ExpressionException( "Wrong number of operand specified" );
            	
            	// Operand feching
            	for( ; o > 0; o-- )
            		localOperands.push( localStack.pop() );
            	
            	// Operator execution
            	Operand result = this.operators.executeExpression( currentOp, localOperands, this.memory );
            	if( result != null )
            		localStack.push( result );
    		}
    		else
    			localStack.push( entry );
    	}
        
    	// Check for correct execution - the result must be 1 number or variable
    	if( localStack.size() != 1 || localStack.lastElement().getEntryType() != Operand.OPERAND_CODE )
    		return Double.NaN;
    	
    	// TODO: Refactor this, check what type it should return and if it always not allowed to return something different than a number
        return ((Operand)localStack.pop()).getNumericValue();
    }
    
    /**
     * Executes an postfix-notation mathematical expression
     * 
     * @param input The input stack containing the expression
     * @return A number indicating the result of the expression
	 * @throws ExpressionException In case of bad input expression
     */
    public Double ExecutePostfix( ExpressionEntry input ) throws ExpressionException {
    	
    	if( input.getEntryType() == Operand.OPERAND_CODE )
    		return ((Operand)input).getNumericValue();
    	
    	else if( input.getEntryType() == CmplxExpression.COMPOSITE_CODE )
    		return this.ExecutePostfix( (CmplxExpression)input );
    	
    	return null;
    }
}
