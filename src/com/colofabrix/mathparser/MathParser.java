package com.colofabrix.mathparser;

import java.util.*;
import com.colofabrix.mathparser.expression.CompositeExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
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
	
	/**
	 * Regular expression to match a variable name
	 */
	public static final String VARIABLE_REGEX = "([a-zA-Z_]|\\.[a-zA-Z_])[a-zA-Z0-9_]*";
	
	/**
	 * Regular espression to match allowed numbers
	 */
	public static final String NUMBER_REGEX = "-?[0-9]*\\.[0-9]+|[0-9]+";
	
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
    public CompositeExpression ConvertToPostfix( String input ) throws ExpressionException, ConfigException {

        CompositeExpression infix = CompositeExpression.fromExpression(input, operators, memory);
        CompositeExpression postfix = new CompositeExpression();
        Stack<Operator> opstack = new Stack<>();
        ExpressionEntry lastEntry = null;

        for( ExpressionEntry entry: infix ) {
        	
            // Add an operator
        	if( entry.getEntryType() == Operator.OPERATOR_CODE ) {
        		Operator currentOp = (Operator)entry;
        		
                // Two consecutive operators means the last one is unary
            	if( lastEntry == null || lastEntry.getEntryType() == Operator.OPERATOR_CODE )
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

        return CompositeExpression.fromExpression(postfix, operators, memory);
    }
    
    /**
     * Executes an postfix-notation mathematical expression
     * 
     * @param input The input stack containing the expression
     * @return A number indicating the result of the expression
	 * @throws ExpressionException In case of bad input expression
     */
    public Double ExecutePostfix( CompositeExpression input ) throws ExpressionException {
    	
        Stack<Operand> localStack = new Stack<>();
        
    	for( ExpressionEntry entry: input ) {
    		if( entry.getEntryType() == Operator.OPERATOR_CODE ) {
    			Operator currentOp = (Operator)entry;
            	Stack<Operand> localOperands = new Stack<>();

            	// Counting the number of operands needed
            	int o = currentOp.getCurrentOperands();
                
            	// Check the operand count for the operator
            	if( localStack.size() != o )
            		throw new ExpressionException();
            	
            	// Operand feching
            	for( ; o > 0; o-- )
            		localOperands.push( localStack.pop() );
            	
            	// Operator execution
            	Operand result = this.operators.executeExpression( currentOp, localOperands, this.memory );
            	if( result != null )
            		localStack.push( result );
    		}
    		else if( entry.getEntryType() == Operand.OPERAND_CODE )
    			localStack.push( (Operand)entry );
    	}
        
    	// Check for correct execution - the result must be 1 number or variable
    	if( localStack.size() != 1 || localStack.lastElement().getEntryType() != Operand.OPERAND_CODE )
    		throw new ExpressionException();
    	
    	// TODO: Refactor this
        return ((Operand)localStack.pop()).getNumericValue();
    }
}
