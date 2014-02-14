package com.colofabrix.mathparser;

import java.util.*;
import java.util.regex.*;
import com.colofabrix.mathparser.operators.special.GroupingOperator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * Mathemathical Expression Parser
 * 
 * @author Fabrizio Colonna
 * @version 0.3
 * @since 2014-01
 */
public final class MathParser {
	
	private Operators operators;
	private Memory memory = new Memory();

	/**
	 * Creates and initialize the MathParser
	 * 
	 * @param manager The choosen Operators Manager, which contains a collection of supported operators.
	 */
	public MathParser( Operators manager, Memory memory ) {
		this.operators = manager;
		this.memory = memory;
	}
	
	public MathParser() throws ConfigException {
		this.operators = new Operators();
		this.memory = new Memory();
	}
	
	/**
	 * Infix-to-postfix converter
	 * 
	 * @param infix The mathematica expression to convert
	 * @return A Stack containing the converted string
	 * @throws ExpressionException In case of bad input expression
	 * @throws ConfigException In case of misconfiguration of MathParser
	 */
    public Stack<String> ConvertToPostfix( String infix ) throws ExpressionException, ConfigException {
    	
        // The working stacks and variables
    	Stack<Operator> opstack = new Stack<>();
        Stack<String> postfix = new Stack<>();
        String lastWord = "";
        int groupingMatching = 0;

        // Splits
        Matcher m = this.operators.getParsingRegex().matcher( infix );

        // Scan all the words inside the string
        while( m.find() ) {
            String word = m.group();
            
            // Add an operator
            if( this.operators.isOperator( word ) ) {
                Operator cOpr = (Operator)this.operators.fromName( word ).clone();
                Operator lastOp = this.operators.fromName( lastWord );

                // Checking for known unary operators or context unary operators
            	if( cOpr.getOperandsMax() == 1 ||
            		lastOp != null && !(lastOp.isGrouping() || cOpr.isGrouping()) ) {
            		
            		if(  cOpr.getOperandsMin() == 1 )
            			cOpr.setCurrentOperands( 1 );
            	
            		// Checking for not allowed consecutive operators
            		else if( cOpr.getOperandsMin() > 1 )
            			throw new ExpressionException();
            	}
            	
                // Opening and closing parenthesis
            	if( cOpr.isGrouping() ) {
            		
	            	if( ((GroupingOperator)cOpr).isOpening() ) {
	                	groupingMatching++;
	                    opstack.push( cOpr );
	                }
	                else if( ((GroupingOperator)cOpr).isClosing() ) {
	                    
	                    // Pop all the previous operators from the stack and push them in the postfix string
	                    while( opstack.size() >= 0 ) {
	                        Operator tmp = opstack.pop();
	                        
	                        if( tmp.isGrouping() && ((GroupingOperator)tmp).isOpening() )
	                            break;
	                        
	                        // Check parentheses match
	                        if( opstack.size() == 0 )
	                            throw new ExpressionException();
	                        
	                        postfix.push( tmp.getName() );
	                    }
	                    
	                    groupingMatching--;
	                }
	            	
	            	postfix.push( word );
            	}
                else {
                	
                    // Extract all the operators that precede the current one
                    while( opstack.size() > 0 && opstack.lastElement().compareTo( cOpr ) >= 0  )
                        postfix.push( opstack.pop().getName() );
    
                    opstack.push( cOpr );
                }
            }
            else
                // Add an operand
                postfix.push( word );
            
            // Remember the last word
            lastWord = word;
        }

        // Unmatched parenthesis
        if( groupingMatching != 0 )
        	throw new ExpressionException();

        // Add the remaining operators
        while( opstack.size() > 0 )
            postfix.push( opstack.pop().getName() );

        return postfix;
    }
    
    /**
     * Executes an postfix-notation mathematical expression
     * 
     * @param input The input stack containing the expression
     * @return A number indicating the result of the expression
	 * @throws ExpressionException In case of bad input expression
     */
    public Double ExecutePostfix( Stack<String> input ) throws ExpressionException {
        ArrayList<String> postfix = new ArrayList<String>( input );
        Stack<String> stack = new Stack<>();

        // Scan all the items in the postfix list
        for( String word: postfix ) {
            
            if( this.operators.isOperator( word ) ) {
            	Stack<String> localOperands = new Stack<String>();

            	// Counting the number of operands needed
            	int o = Operator.discoverOperandsCount( word );
                	
            	// Operand feching
            	for( ; o > 0; o-- )
            		localOperands.push( stack.pop() );
            	
            	// Operator execution
            	Double result = this.operators.executeExpression( word, localOperands, this.memory );
            	if( result != null )
            		stack.push( result.toString() );
                
            }
            else
                stack.push( word );
        }
        
        return Operator.translateOperand( stack.pop(), memory );
    }
}
