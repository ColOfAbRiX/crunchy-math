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

import java.util.Collections;
import java.util.Stack;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Grouping;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.Context;
import com.colofabrix.mathparser.struct.ContextBuilder;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.builders.DefaultContextBuilder;
import com.colofabrix.mathparser.struct.builders.ExpressionWorker;

/**
 * Mathematical Expression Parser
 * 
 * @author Fabrizio Colonna
 * @version 0.3
 * @since 2014-01
 */
public class MathParser {

    private Context context;

    /**
     * Creates and initialise MathParser
     * <p>
     * This constructor initialise the object using {@link DefaultContextBuilder#createDefault()} to create a default
     * context
     * </p>
     */
    public MathParser() {
        this( DefaultContextBuilder.createDefault() );
    }

    /**
     * Creates and initialise the MathParser
     * <p>
     * This constructor allow to specify a custom {@link Context}
     * </p>
     * 
     * @param context The context to associate with
     */
    public MathParser( Context context ) {
        this.setContext( context );
    }

    /**
     * Creates and initialise the MathParser
     * <p>
     * This constructor requires a builder to create its context
     * </p>
     * 
     * @param contextBuilder The builder to use to create the context
     */
    public MathParser( ContextBuilder contextBuilder ) {
        this( contextBuilder.create() );
    }

    /**
     * Executes a mathematical expression in infix notation
     * <p>
     * This method uses {@link #toPostfix(Expression)} and {@link #executePostfix(Expression)} to first convert the
     * string to postfix notation and then execute it
     * </p>
     * 
     * @param infix The input stack containing the expression to execute
     * @return An object containing the result of the execution of the expression
     * @throws ExpressionException In case of bad input expression
     * @throws ConfigException In case of misconfiguration of the parser
     */
    public Expression execute( Expression infix ) throws ExpressionException, ConfigException {
        Expression postfix = this.toPostfix( infix );
        return this.executePostfix( postfix );
    }

    /**
     * Executes a mathematical expression in infix notation
     * <p>
     * This method uses {@link #toPostfix(Expression)} and {@link #executePostfix(Expression)} to first convert the
     * string to postfix notation and then execute it
     * </p>
     * 
     * @param strInfix The input stack containing the expression to execute
     * @return An object containing the result of the execution of the expression
     * @throws ExpressionException In case of bad input expression
     * @throws ConfigException In case of misconfiguration of the parser
     */
    public Expression execute( String strInfix ) throws ExpressionException, ConfigException {
        Expression infix = ExpressionWorker.fromExpression( strInfix, this.getContext() );
        Expression postfix = this.toPostfix( infix );
        return this.executePostfix( postfix );
    }

    /**
     * Executes a postfix-notation mathematical expression
     * <p>
     * The method can also minimise an expression which means execute it but without resolving variables
     * <p>
     * 
     * @param postfix The input stack containing the expression to execute
     * @return An object containing the result of the execution of the expression
     * @throws ExpressionException In case of bad input expression
     */
    public Expression executePostfix( Expression postfix ) throws ExpressionException {
        return this.executePostfix( ExpressionWorker.packExpression( postfix ), false );
    }

    /**
     * Gets the current working context
     * 
     * @return The current working context
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * Minimise an expression until only variables and non-minimizable expression are left
     * 
     * @param postfix The input expression to be minimised
     * @return An object containing a mathematically equivalent expression to the input one, but minimised
     * @throws ExpressionException In case of bad input expression
     */
    public Expression minimise( Expression postfix ) throws ExpressionException {
        return this.minimize( ExpressionWorker.packExpression( postfix ) );
    }

    /**
     * Infix-to-postfix converter
     * 
     * @param infix The mathematical expression to convert
     * @return A stack containing the converted object to postfix notation
     * @throws ExpressionException In case of bad input expression
     * @throws ConfigException In case of misconfiguration of MathParser
     */
    public Expression toPostfix( Expression infix ) throws ExpressionException, ConfigException {
        return this.toPostfix( ExpressionWorker.packExpression( infix ) );
    }

    /**
     * Infix-to-postfix converter
     * 
     * @param strInfix The mathematical expression to convert
     * @return A stack containing the converted object to postfix notation
     * @throws ExpressionException In case of bad input expression
     * @throws ConfigException In case of misconfiguration of MathParser
     */
    public Expression toPostfix( String strInfix ) throws ExpressionException, ConfigException {
        Expression infix = ExpressionWorker.fromExpression( strInfix, this.getContext() );
        return this.toPostfix( infix );
    }

    /**
     * Sets the current working context
     * 
     * @param context The context to set
     */
    private void setContext( Context context ) {
        this.context = context;
    }

    /**
     * Executes a postfix-notation mathematical expression
     * <p>
     * The method can also minimise an expression which means execute it but without resolving variables
     * <p>
     * 
     * @param postfix The input stack containing the expression to execute
     * @param minimize Indicates if the expression should only be minimised.
     * @return An object containing the result of the execution of the expression
     * @throws ExpressionException In case of bad input expression
     */
    protected Expression executePostfix( CmplxExpression postfix, boolean minimize ) throws ExpressionException {
        CmplxExpression localStack = new CmplxExpression();

        for( Expression entry: postfix ) {
            if( entry.getEntryType() == Operator.OPERATOR_CODE ) {
                Operator currentOp = (Operator)entry;
                Stack<Expression> localOperands = new Stack<>();

                // Counting the number of operands needed
                int o = currentOp.getCurrentOperands();

                // Check the operand count for the operator
                if( localStack.size() < o ) {
                    throw new ExpressionException( "Wrong number of operand specified" );
                }

                // Operand fetching
                for( ; o > 0; o-- ) {
                    localOperands.push( localStack.pop() );
                }

                // Minimisation-only section
                if( minimize ) {
                    boolean minimizable = true;

                    // Check if the expression is minimizable
                    for( Expression op: localOperands ) {
                        minimizable = minimizable && op.isMinimizable();
                    }

                    // If it is not minimizable I add it as a nested
                    // CmplxExpression
                    if( !minimizable ) {
                        Collections.reverse( localOperands );
                        localStack.addAll( localOperands );
                        localStack.add( currentOp );
                        continue;
                    }
                }

                // Operator execution
                Operand result = this.getContext().getOperators().executeExpression( currentOp, localOperands );
                if( result != null ) {
                    localStack.push( result );
                }
            }
            else {
                localStack.push( entry );
            }
        }

        // Return only the first element or all the CmplxExpression
        if( localStack.size() == 1 ) {
            return localStack.firstElement();
        }

        return ExpressionWorker.unpackExpression( localStack );
    }

    /**
     * Minimise an expression until only variables and non-minimizable expression are left
     * 
     * @param postfix The input expression to be minimised
     * @return An object containing a mathematically equivalent expression to the input one, but minimised
     * @throws ExpressionException In case of bad input expression
     */
    protected Expression minimize( CmplxExpression postfix ) throws ExpressionException {
        CmplxExpression output = new CmplxExpression();

        // Minimise every single components of the expression
        for( Expression entry: postfix ) {
            if( entry.getEntryType() == CmplxExpression.COMPOSITE_CODE ) {
                output.add( this.minimise( entry ) );
            }
            else {
                output.add( entry );
            }
        }

        // Minimise the whole expression
        if( output.isMinimizable() ) {
            return this.executePostfix( output, true );
        }

        return ExpressionWorker.unpackExpression( output );
    }

    /**
     * Infix-to-postfix converter
     * 
     * @param infix The mathematical expression to convert
     * @return A stack containing the converted object to postfix notation
     * @throws ExpressionException In case of bad input expression
     * @throws ConfigException In case of misconfiguration of MathParser
     */
    protected Expression toPostfix( CmplxExpression infix ) throws ExpressionException, ConfigException {
        CmplxExpression postfix = new CmplxExpression();
        Stack<Operator> opstack = new Stack<>();
        Expression lastEntry = null;

        for( Expression entry: infix ) {

            // Add an operator
            if( entry.getEntryType() == Operator.OPERATOR_CODE ) {
                Operator currentOp = (Operator)entry;

                // At the beginning of the expression are unary only the
                // operators with getOperandMin == 1
                if( lastEntry == null && currentOp.getOperandsMin() == 1 ) {
                    currentOp.setCurrentOperands( 1 );
                }

                // Two consecutive operators means the the latter is unary,
                // except in case of the first one is a closing
                // grouping
                if( lastEntry != null && lastEntry.getEntryType() == Operator.OPERATOR_CODE
                        && !(((Operator)lastEntry).isGrouping() && !((Grouping)lastEntry).isOpening()) ) {
                    if( currentOp.getOperandsMin() <= 2 ) {
                        currentOp.setCurrentOperands( 1 );
                    }
                }

                // Execution of the custom parsing performed by the operators
                // themselves
                currentOp = currentOp.executeParsing( postfix, opstack );
                if( currentOp != null ) {
                    opstack.push( currentOp );
                }
            }
            // Add anything else
            else {
                postfix.add( entry );
            }

            // Remember the last entry
            lastEntry = entry;
        }

        // Transfer the remaining operators
        while( opstack.size() > 0 ) {
            postfix.add( opstack.pop() );
        }

        return ExpressionWorker.unpackExpression( postfix );
    }
}
