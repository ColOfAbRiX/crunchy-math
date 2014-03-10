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
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.operators.AbsOperator;
import com.colofabrix.mathparser.operators.ArcosOperator;
import com.colofabrix.mathparser.operators.ArsinOperator;
import com.colofabrix.mathparser.operators.ArtanOperator;
import com.colofabrix.mathparser.operators.CeilOperator;
import com.colofabrix.mathparser.operators.CosOperator;
import com.colofabrix.mathparser.operators.CoshOperator;
import com.colofabrix.mathparser.operators.DivideOperator;
import com.colofabrix.mathparser.operators.ExpOperator;
import com.colofabrix.mathparser.operators.FactOperator;
import com.colofabrix.mathparser.operators.FloorOperator;
import com.colofabrix.mathparser.operators.IntegralOperator;
import com.colofabrix.mathparser.operators.LnOperator;
import com.colofabrix.mathparser.operators.LogOperator;
import com.colofabrix.mathparser.operators.MaxOperator;
import com.colofabrix.mathparser.operators.MinOperator;
import com.colofabrix.mathparser.operators.MinusOperator;
import com.colofabrix.mathparser.operators.MultiplyOperator;
import com.colofabrix.mathparser.operators.PowerOperator;
import com.colofabrix.mathparser.operators.RandomOperator;
import com.colofabrix.mathparser.operators.RootOperator;
import com.colofabrix.mathparser.operators.RoundOperator;
import com.colofabrix.mathparser.operators.SinOperator;
import com.colofabrix.mathparser.operators.SinhOperator;
import com.colofabrix.mathparser.operators.SumOperator;
import com.colofabrix.mathparser.operators.TanOperator;
import com.colofabrix.mathparser.operators.TanhOperator;
import com.colofabrix.mathparser.operators.special.AssignmentOperator;
import com.colofabrix.mathparser.operators.special.ClosingBracket;
import com.colofabrix.mathparser.operators.special.GetOperator;
import com.colofabrix.mathparser.operators.special.MemoryOperator;
import com.colofabrix.mathparser.operators.special.OpeningBracket;
import com.colofabrix.mathparser.operators.special.OperatorsOperator;
import com.colofabrix.mathparser.operators.special.SetOperator;
import com.colofabrix.mathparser.operators.special.VectorClosing;
import com.colofabrix.mathparser.operators.special.VectorOpening;
import com.colofabrix.mathparser.operators.special.VectorPush;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;
import com.colofabrix.mathparser.org.OpBuilder;

/**
 * This class manage the collection of the supported operators in MathParser
 * 
 * @author Fabrizio Colonna
 */
public class Operators extends java.util.Vector<Operator> {
    private static final long serialVersionUID = 9039898627558124444L;

    /**
     * The constructor initializes the operators that are recognized by the Math Parser
     * 
     * @throws ConfigException
     */
    public Operators( Memory memory ) throws ConfigException {
        OpBuilder.setOperators( this );
        OpBuilder.setMemory( memory );

        // Algebraic operators
        this.add( OpBuilder.createOperator( SumOperator.class ) );
        this.add( OpBuilder.createOperator( MinusOperator.class ) );
        this.add( OpBuilder.createOperator( MultiplyOperator.class ) );
        this.add( OpBuilder.createOperator( DivideOperator.class ) );
        this.add( OpBuilder.createOperator( PowerOperator.class ) );
        this.add( OpBuilder.createOperator( RootOperator.class ) );
        this.add( OpBuilder.createOperator( FactOperator.class ) );

        // Trigonometric operators
        this.add( OpBuilder.createOperator( SinOperator.class ) );
        this.add( OpBuilder.createOperator( CosOperator.class ) );
        this.add( OpBuilder.createOperator( TanOperator.class ) );
        this.add( OpBuilder.createOperator( ArcosOperator.class ) );
        this.add( OpBuilder.createOperator( ArsinOperator.class ) );
        this.add( OpBuilder.createOperator( ArtanOperator.class ) );
        this.add( OpBuilder.createOperator( SinhOperator.class ) );
        this.add( OpBuilder.createOperator( CoshOperator.class ) );
        this.add( OpBuilder.createOperator( TanhOperator.class ) );

        // Exponentiation operators
        this.add( OpBuilder.createOperator( ExpOperator.class ) );
        this.add( OpBuilder.createOperator( LogOperator.class ) );
        this.add( OpBuilder.createOperator( LnOperator.class ) );

        // Rounding operators
        this.add( OpBuilder.createOperator( CeilOperator.class ) );
        this.add( OpBuilder.createOperator( FloorOperator.class ) );
        this.add( OpBuilder.createOperator( RoundOperator.class ) );
        this.add( OpBuilder.createOperator( MinOperator.class ) );
        this.add( OpBuilder.createOperator( MaxOperator.class ) );

        // Structural operators
        this.add( OpBuilder.createOperator( AssignmentOperator.class ) );
        this.add( OpBuilder.createOperator( OpeningBracket.class ) );
        this.add( OpBuilder.createOperator( ClosingBracket.class ) );
        this.add( OpBuilder.createOperator( VectorOpening.class ) );
        this.add( OpBuilder.createOperator( VectorPush.class ) );
        this.add( OpBuilder.createOperator( VectorClosing.class ) );

        // Mixed operators
        this.add( OpBuilder.createOperator( RandomOperator.class ) );
        this.add( OpBuilder.createOperator( AbsOperator.class ) );
        this.add( OpBuilder.createOperator( IntegralOperator.class ) );

        // Management operators
        this.add( OpBuilder.createOperator( MemoryOperator.class ) );
        this.add( OpBuilder.createOperator( OperatorsOperator.class ) );
        this.add( OpBuilder.createOperator( SetOperator.class ) );
        this.add( OpBuilder.createOperator( GetOperator.class ) );
    }

    /**
     * It executes the operation performed by the operator
     * 
     * <p>
     * Every concrete implementation of Operator must implement this method with the operation that it has to do.
     * </p>
     * <p>
     * This class will also set the variable "Ans" which contains the last result of an operation or {@link Double#NaN}
     * if there is no result.
     * </p>
     * 
     * @param operands A stack containing the operands in reversed order
     * @param memory A reference to the main math memory
     * @return It returns a number if the operation succeeded or <code>null</code> to express empty-result
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
    public Operand executeExpression( Operator operator, Stack<ExpressionEntry> operands, Memory memory ) throws ExpressionException {
        try {
            Operand value = operator.executeOperation( operands );

            if( value != null )
                memory.setValue( Memory.ANSWER_VARIABLE, value );

            else
                memory.setValue( Memory.ANSWER_VARIABLE, null );

            return value;
        }
        catch( Exception e ) {
            throw new ExpressionException( e.getMessage() );
        }
    }

    /**
     * It fetches an operator given its name
     * 
     * <p>
     * The functions retrieves the operator corresponding to the name given. The name can be both a base name or a full
     * name.
     * </p>
     * 
     * @param word The name of the operator to fetch
     * @return An instance corresponding to the operator found or <code>null</code> if no operator is found
     */
    public Operator fromName( String word ) {
        Matcher m = Pattern.compile( Operator.OPNUM_REGEX ).matcher( word );
        if( !m.matches() )
            return null;

        String opname = m.group( 3 );
        if( opname == null )
            return null;

        for( Operator op: this )
            if( op.equals( word ) || op.getName().equals( word ) )
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

        return Pattern.compile( "(" + regex + Operand.NUMBER_REGEX + "|" + Operand.VARIABLE_REGEX + "|"
                + Option.OPTION_REGEX + ")" );
    }

    /**
     * Check if a given string is an operator
     * 
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
