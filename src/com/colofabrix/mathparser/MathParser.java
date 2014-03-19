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
import org.apfloat.Apfloat;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;
import com.colofabrix.mathparser.org.MathConstant;
import com.colofabrix.mathparser.org.OpBuilder;

/**
 * Mathemathical Expression Parser
 * 
 * @author Fabrizio Colonna
 * @version 0.3
 * @since 2014-01
 */
public class MathParser {

    private Memory memory;
    private Operators operators;

    /**
     * Creates and initialize MathParser
     * 
     * <p>It uses {@link OpBuilder} to create a default context</p>
     */
    public MathParser() {
        OpBuilder.newContext();
        this.setMemory( OpBuilder.getMemory() );
        this.setOperators( OpBuilder.getOperators() );
    }

    /**
     * Creates and initialize the MathParser
     * 
     * <p>
     * This constructor allow to specify a custom operators manager and memory manager
     * </p>
     * 
     * @param manager The choosen Operators Manager, which contains a collection of supported operators.
     * @param memory The object to use as memory
     */
    public MathParser( Operators manager, Memory memory ) {
        this.setOperators( manager );
        this.setMemory( memory );
    }
    
    /**
     * Creates and initialize the MathParser
     * 
     * <p>
     * This constructor allow to specify a custom operators manager and memory manager
     * </p>
     * 
     * @param manager The choosen Operators Manager, which contains a collection of supported operators.
     * @param memory The object to use as memory
     * @param consts The object used to initialize in memory the mathematical constants
     */
    public MathParser( Operators manager, Memory memory, MathConstant consts ) {
        this( manager, memory );
        consts.init(  memory );
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

        CmplxExpression infix = CmplxExpression.fromExpression( input, this.getOperators(), this.getMemory() );
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

                // Two consecutive operators means the the latter is unary, except in case of the first one is a closing
                // grouping
                if( lastEntry != null && lastEntry.getEntryType() == Operator.OPERATOR_CODE
                        && !(((Operator)lastEntry).isGrouping() && !((GroupingOperator)lastEntry).isOpening()) )
                    if( currentOp.getOperandsMin() <= 2 )
                        currentOp.setCurrentOperands( 1 );

                // Execution of the custom parsing performed by the operators themselves
                currentOp = currentOp.executeParsing( postfix, opstack );
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

        return CmplxExpression.fromExpression( postfix, this.getOperators(), this.getMemory() );
    }

    /**
     * Executes an postfix-notation mathematical expression
     * 
     * @param input The input stack containing the expression
     * @return A number indicating the result of the expression
     * @throws ExpressionException In case of bad input expression
     */
    public Apfloat ExecutePostfix( CmplxExpression input ) throws ExpressionException {
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
                Operand result = this.getOperators().executeExpression( currentOp, localOperands, this.getMemory() );
                if( result != null )
                    localStack.push( result );
            }
            else
                localStack.push( entry );
        }

        // FIXME: It returns null...
        // Check for correct execution - the result must be 1 number or variable
        if( localStack.size() != 1 || localStack.lastElement().getEntryType() != Operand.OPERAND_CODE )
            return null;

        // TODO: Here the code to manage the output precision of the calculations
        return ((Operand)localStack.pop()).getNumericValue();
    }

    /**
     * Executes an postfix-notation mathematical expression
     * 
     * @param input The input stack containing the expression
     * @return A number indicating the result of the expression
     * @throws ExpressionException In case of bad input expression
     */
    public Apfloat ExecutePostfix( ExpressionEntry input ) throws ExpressionException {

        if( input.getEntryType() == Operand.OPERAND_CODE )
            // TODO: Here the code to manage the output precision of the calculations
            return ((Operand)input).getNumericValue();

        else if( input.getEntryType() == CmplxExpression.COMPOSITE_CODE )
            return this.ExecutePostfix( (CmplxExpression)input );

        return null;
    }

    /**
     * @return the memory
     */
    public Memory getMemory() {
        return this.memory;
    }

    /**
     * @return the operators
     */
    public Operators getOperators() {
        return this.operators;
    }

    /**
     * @param memory the memory to set
     */
    protected void setMemory( Memory memory ) {
        this.memory = memory;
    }

    /**
     * @param operators the operators to set
     */
    protected void setOperators( Operators operators ) {
        this.operators = operators;
    }
}
