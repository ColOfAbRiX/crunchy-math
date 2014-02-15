package com.colofabrix.mathparser;

import java.util.*;
import java.util.regex.*;

import com.colofabrix.mathparser.operators.*;
import com.colofabrix.mathparser.operators.special.*;
import com.colofabrix.mathparser.org.*;

/**
 * This class manage the collection of the supported operators in MathParser
 * 
 * @author Fabrizio Colonna
 */
public class Operators extends Vector<Operator> {
	private static final long serialVersionUID = 9039898627558124444L;

	/**
	 * Regular espression to match allowed numbers
	 */
	public static final String NUMBER_REGEX = "-?[0-9]*\\.[0-9]+|[0-9]+";
	
	/**
	 * Regular expression to match a variable name
	 */
	public static final String VARIABLE_REGEX = "([a-zA-Z_]|\\.[a-zA-Z_])[a-zA-Z0-9_]*";
	
	/**
	 * The constructor initializes the operators that are recognized by the Math Parser
	 * @throws ConfigException 
	 */
	public Operators() throws ConfigException {
		this.add( new SumOperator() );
		this.add( new MinusOperator() );
		this.add( new MultiplyOperator() );
		this.add( new DivideOperator() );
		this.add( new PowerOperator() );
        this.add( new SinOperator() );
        this.add( new CosOperator() );
        this.add( new IntegralOperator() );
        
		this.add( new AssignmentOperator() );
        this.add( new OpeningBracket() );
        this.add( new ClosingBracket() );
        this.add( new VectorOpening() );
        this.add( new VectorPush() );
        this.add( new VectorClosing() );
	}
	
    /**
     * It executes the operation performed by the operator
	 * 
	 * <p>Every concrete implementation of Operator must implement this method with the operation that
	 * it has to do.</p>
     *  
     * @param operands A stack containing the operands in reversed order
     * @param memory A reference to the main math memory
     * @return It returns a number if the operation succeeded or <code>null</code> to express empty-result
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
	public Double executeExpression( String operator, Stack<String> operands, Memory memory ) throws ExpressionException {
		Double value = this.fromName(operator).executeOperation( operands, memory );
		memory.setValue( Memory.ANSWER_VARIABLE, value );
		return value;
	}

	/**
	 * Check if a given string is an operator
	 * 
	 * <p>The check is performed searching for a correponding existing operator</p>
	 * 
	 * @param word The string to check
	 * @return <code>true</code> if the string represents an operator
	 */
	public boolean isOperator( String word ) {
		return this.fromName(word) != null;
	}

	/**
	 * It fetches an operator given its name
	 * 
	 * <p>The functions retrieves the operator corresponding to the name given. The name can be both
	 * a base name or a full name.</p>
	 * 
	 * @param name The name of the operator to fetch
	 * @return An instance corresponding to the operator found or <code>null</code> if no operator is found
	 */
	public Operator fromName( String name ) {
		for( Operator op: this )
			if( op.equals( name ) || op.getName().equals(name) )
				return op;
		
		return null;
	}
	
	/**
	 * Create the regualar expression to match any supported element in MathParser
	 * 
	 * @return A Pattern object for the regular expression
	 */
	public Pattern getParsingRegex() {
 		String regex = "";
		
		for( Operator op: this )
            regex += Pattern.quote( op.getBaseName() ) + "|";

        return Pattern.compile( "(" + regex + NUMBER_REGEX + "|" + VARIABLE_REGEX + ")" );
	}
}
