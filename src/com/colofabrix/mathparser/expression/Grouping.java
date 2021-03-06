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
package com.colofabrix.mathparser.expression;

import java.util.Stack;
import com.colofabrix.mathparser.struct.Context;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * Represent a grouping operators
 * <p>
 * A grouping operator is an operator with a beginning and an end, within the infix expression, wich encloses an
 * expression. Usually the enclosed expression has priority over the outer expression<br/>
 * This is a default implementation that models the behaviour of the common brackets
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public abstract class Grouping extends Operator {

    /**
     * The constructor initialise the object setting the number of operands to 1 and the variable
     * grouping to true
     */
    public Grouping() {
        super();
        this.init();
    }

    /**
     * The constructor initialise the object setting the number of operands to 1 and the variable
     * grouping to true
     */
    public Grouping( Context context ) {
        super( context );
        this.init();
    }

    /**
     * A grouping operator executes no mathematical operations
     */
    @Override
    public Operand executeOperation( Stack<Expression> operands ) throws ExpressionException {
        return null;
    }

    /**
     * Execute the parsing operation that the operator may require
     * <p>
     * The parsing operation is an operation that is performed when the operator is fetched from the input string</br>
     * The default implementation for a grouping operator divides the operation for the opening groping and the closing
     * groping, calling the respective functions
     * </p>
     * 
     * @see Operator#executeParsing
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed before the call to this method
     * @return An instance of the operator to be pushed at the end of the operators stack,
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
    @Override
    public Operator executeParsing( CmplxExpression postfix, Stack<Operator> opstack ) throws ExpressionException {
        Operator result;

        // Executes different parsing between opening and closing grouping
        if( this.isOpening() ) {
            result = this.executeParsingOpening( postfix, opstack, this.getContext() );
        }
        else {
            result = this.executeParsingClosing( postfix, opstack, this.getContext() );
        }

        return result;
    }

    /**
     * Gets the absolute priority of the operand
     * <p>
     * The priority of an operator is expressed using an Integer, while a priority can only be set using a Short because
     * priorities use levels. For more information see {@link Operator#getPriority()}
     * </p>
     */
    @Override
    public int getPriority() {
        // Short.MAX_VALUE is doubled because this object has getCurrentOperands() == 1
        return super.getPriority() - 2 * Short.MAX_VALUE;
    };

    /**
     * It indicates if the class represents an opening grouping
     * 
     * @return <code>true</code> if the object is an opening grouping
     */
    public abstract boolean isOpening();

    /**
     * Sets the number of operands currently used
     * <p>
     * A grouping operator is always a unary operator, so whatever value is given this function will set the
     * currentOperands always to 1.
     * </p>
     * 
     * @param value A number indicating how many operands this operator needs
     * @throws Exception This exception is thrown when a wrong number of current operands is specified
     */
    @Override
    public final void setCurrentOperands( int value ) {
        return;
    };

    /**
     * Sets the limits of the number of operands allowed for the operator
     * <p>
     * A grouping operator is always a unary operator, so whatever value is given this function will set the maximum and
     * minimum are always 1.
     * </p>
     * 
     * @param min Minimum number of operand allowed
     * @param max Maximum number of operand allowed
     */
    @Override
    public final void setOperandsLimit( int min, int max ) {
        return;
    }

    private void init() {
        this.setGrouping( true );
        this.setOperandsLimit( 1, 1 );
        this.setCurrentOperands( 1 );
    };

    /**
     * Execute the parsing operation that the operator may require
     * <p>
     * This function is called when an closing grouping is fetched from the input string
     * </p>
     * 
     * @see Operator#executeParsing
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed before the call to this method
     * @param context A reference to the context in use
     * @return An instance of the operator to be pushed at the end of the operators stack,
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
    protected Operator executeParsingClosing( CmplxExpression postfix, Stack<Operator> opstack, Context context ) throws ExpressionException {
        // Pop all the previous operators from the stack and push them in the postfix string until I find an opening
        // grouping
        while( opstack.size() > 0 ) {
            Operator tmp = opstack.pop();

            if( tmp.isGrouping() && ((Grouping)tmp).isOpening() ) {
                break;
            }

            /*
             * NOTE: For normal brackets this is important, but for generic operators this could not
             * be good and, in fact, it creates problems with the Vector type
             * // Check parentheses match
             * if( opstack.size() == 0 )
             * throw new ExpressionException();
             */
            postfix.add( tmp );
        }

        return null;
    }

    /**
     * Execute the parsing operation that the operator may require
     * <p>
     * This function is called when an opening grouping is fetched from the input string
     * </p>
     * 
     * @see Operator#executeParsing
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed befor the call to this method
     * @param context A reference to the context in use
     * @return An instance of the operator to be pushed at the end of the operators stack,
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
    protected Operator executeParsingOpening( Expression postfix, Stack<Operator> opstack, Context context ) throws ExpressionException {
        return this;
    }
}
