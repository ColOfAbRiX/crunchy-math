package com.colofabrix.mathparser;

import java.util.*;
import java.util.regex.*;

import com.colofabrix.mathparser.expression.ExprBuilder;
import com.colofabrix.mathparser.expression.ExprEntry;
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
    public Stack<ExprEntry> ConvertToPostfix( String input ) throws ExpressionException, ConfigException {

        Stack<ExprEntry> infix = new ExprBuilder(this.operators).buildExpression( input );
        Stack<ExprEntry> postfix = new Stack<>();
        Stack<ExprEntry> opstack = new Stack<>();
        ExprEntry lastEntry = null;

        for( ExprEntry entry: infix ) {
        	
            // Add an operator
        	if( entry.isOperator() ) {
        		Operator currentOp = entry.getOperator();
        		
                // Two consecutive operators means the last one is unary
            	if( lastEntry == null || lastEntry.isOperator() )
            		currentOp.setCurrentOperands( 1 );
            	
            	// Execution of the custom parsing performed by the operators themselves
            	entry = ExprEntry.createExpression( currentOp.executeParsing(postfix, opstack, this.operators, this.memory) );
                if( entry != null )
                	opstack.push( entry );
        	}
            // Add anything else
        	else
        		postfix.push( entry );
        	
            // Remember the last entry
        	lastEntry = entry;
        }
        
        // Transfer the remaining operators
        while( opstack.size() > 0 )
        	postfix.push( opstack.pop() );

        return postfix;
    }
    
    /**
     * Executes an postfix-notation mathematical expression
     * 
     * @param input The input stack containing the expression
     * @return A number indicating the result of the expression
	 * @throws ExpressionException In case of bad input expression
     */
    public Double ExecutePostfix( Stack<ExprEntry> input ) throws ExpressionException {
    	
        Stack<ExprEntry> localStack = new Stack<>();
        
    	for( ExprEntry entry: input ) {
    		if( entry.isOperator() ) {
            	Stack<ExprEntry> localOperands = new Stack<>();

            	// Counting the number of operands needed
            	int o = entry.getOperator().getCurrentOperands();
                
            	// Check the operand count for the operator
            	if( localStack.size() != o )
            		throw new ExpressionException();
            	
            	// Operand feching
            	for( ; o > 0; o-- )
            		localOperands.push( localStack.pop() );
            	
            	// Operator execution
            	ExprEntry result = this.operators.executeExpression( entry, localOperands, this.memory );
            	if( result != null )
            		localStack.push( result );
    		}
    		else
    			localStack.push( entry );
    	}
        
    	// Check for correct execution - the result must be 1 number
    	if( localStack.size() != 1 || !localStack.lastElement().isNumber() || !localStack.lastElement().isVariable() )
    		throw new ExpressionException();
    	
    	// TODO: Refactor this
        return Operator.translateOperand( localStack.pop().toString(), memory );
    }
}
