/*
Crunchy Math, Version 1.0, February 2014
Copyright (C) 2014 Fabrizio Colonna <colofabrix@gmail.com>

This file is part of Crunchy Math.

Crunchy Math is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

Crunchy Math is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with Crunchy Math; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.colofabrix.mathparser;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * This class manage the collection of the supported operators in MathParser
 * 
 * @author Fabrizio Colonna
 */
public class Operators extends java.util.Vector<Operator> {
    private static final long serialVersionUID = 9039898627558124444L;
	private Memory memory;

	/**
	 * Constructor
	 * 
	 * @param memory A reference to the memory in use
	 */
    public Operators( Memory memory ) {
    	this.memory = memory;
	}
    
    /**
     * It executes the operation performed by the operator
     * <p>
     * Every concrete implementation of Operator must implement this method with the operation that it has to do.
     * </p>
     * <p>
     * This class will also set the variable "Ans" which contains the last result of an operation or {@link Double#NaN}
     * if there is no result.
     * </p>
     * @param operands A stack containing the operands in reversed order
     * 
     * @return It returns a number if the operation succeeded or <code>null</code> to express empty-result
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
    public Operand executeExpression( Operator operator, Stack<Expression> operands ) throws ExpressionException {
        try {
            Operand value = operator.executeOperation( operands );

            if( value != null ) {
                this.memory.setValue( Memory.ANSWER_VARIABLE, value );
            }
            else {
            	this.memory.setValue( Memory.ANSWER_VARIABLE, null );
            }

            return value;
        }
        catch( Exception e ) {
            throw new ExpressionException( e.getMessage() );
        }
    }

    /**
     * It fetches an operator given its name
     * <p>
     * The functions retrieves the operator corresponding to the name given. The name can be both a base name or a full
     * name.
     * </p>
     * 
     * @param word The name of the operator to fetch
     * @return An instance corresponding to the operator found or <code>null</code> if no operator is found
     */
    public Expression fromName( String word ) {
        Matcher m = Pattern.compile( Operator.OPNUM_REGEX ).matcher( word );
        if( !m.matches() ) {
            return null;
        }

        String opname = m.group( 3 );
        if( opname == null ) {
            return null;
        }

        for( Operator op: this ) {
            if( op.equals( word ) || op.getName().equals( word ) ) {
                return op;
            }
        }

        return null;
    }

    /**
     * Create the regular expression to match any supported element in MathParser
     * 
     * @return A Pattern object for the regular expression
     */
    public Pattern getParsingRegex() {
        String regex = "";

        for( Operator op: this ) {
            regex += Pattern.quote( op.getBaseName() ) + "|";
        }

        return Pattern.compile( "(" + regex + Operand.NUMBER_REGEX + "|" + Operand.VARIABLE_REGEX + "|" + Option.OPTION_REGEX + ")" );
    }

    /**
     * Check if a given string is an operator
     * <p>
     * The check is performed searching for a correponding existing operator
     * </p>
     * 
     * @param word The string to check
     * @return <code>true</code> if the string represents an operator
     */
    public boolean isOperator( String word ) {
        return this.fromName( word ) != null;
    }
}
