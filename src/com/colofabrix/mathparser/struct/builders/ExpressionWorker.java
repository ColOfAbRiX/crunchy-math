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
package com.colofabrix.mathparser.struct.builders;

import java.util.List;
import java.util.regex.Matcher;
import org.apfloat.Apfloat;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.expression.Option;
import com.colofabrix.mathparser.lib.ApfConsts;
import com.colofabrix.mathparser.struct.Context;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * Collection of various methods to work with Expressions.
 * <p>
 * This class contains various method to do common tasks with expressions, like conversion from strings and packing from
 * one type to another
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public final class ExpressionWorker {

    /**
     * Creates a CmplxExpression starting from a list of Expression
     * <p>
     * The method splits the string in its component and create an appropriate Expression for every token.
     * </p>
     * 
     * @param stack A stack of Expression to add in a new CmplxExpression
     * @param context A reference to the used context
     * @return A CmplxExpression object containing the added CmplxExpression(s)
     * @throws ExpressionException
     */
    public static CmplxExpression fromExpression( List<Expression> stack, Context context ) throws ExpressionException {
        CmplxExpression composite = new CmplxExpression();
        composite.addAll( stack );
        return composite;
    }

    /**
     * Creates a CmplxExpression starting from a string
     * <p>
     * The method splits the string in its component and create an appropriate Expression for every token.
     * </p>
     * 
     * @param expression The expression to converts
     * @param context A reference to the used context
     * @return A CmplxExpression object containing the translated string expression
     * @throws ExpressionException
     */
    public static CmplxExpression fromExpression( String expression, Context context ) throws ExpressionException {
        // The input string is split in its forming components and translated in object-form
        Matcher m = context.getOperators().getParsingRegex().matcher( expression );
        CmplxExpression composite = new CmplxExpression();

        // Saves all matches in a list
        while( m.find() ) {
            composite.add( fromWord( m.group(), context ) );
        }

        return composite;
    }

    /**
     * Creates an instance of Expression to hold an entry
     * <p>
     * This builder starts from a single string entry, a single token like a number or an operator, and creates the
     * right Expression object
     * </p>
     * 
     * @param word The name of the variable or the number to store in the object
     * @return A new Expression to hold the specified variable or number
     * @throws ExpressionException
     */
    public static Expression fromWord( String word, Context context ) throws ExpressionException {
        Operators operators = context.getOperators();
        Memory memory = context.getMemory();

        // Creates an operator
        if( operators != null && operators.isOperator( word ) ) {
            return (Expression)((Operator)operators.fromName( word )).clone();
        }
        else if( word.matches( Operand.NUMBER_REGEX ) ) {
            return new Operand( new Apfloat( word, ApfConsts.STRING_PRECISION ) );
        }
        else if( word.matches( Option.OPTION_REGEX ) ) {
            return new Option( word );
        }
        else if( memory != null && word.matches( Operand.VARIABLE_REGEX ) ) {
            return new Operand( word, memory );
        }
        else {
            return null;
        }
    }

    /**
     * It packs the given Expression in the appropriate form inside a CmplxExpression
     * 
     * @param input The Expression entry to pack
     * @return A CmplExpression object containing an equivalent form of input
     */
    public static CmplxExpression packExpression( Expression input ) {
        if( input.getEntryType() == CmplxExpression.COMPOSITE_CODE ) {
            return (CmplxExpression)input;
        }

        CmplxExpression tmp = new CmplxExpression();
        tmp.add( input );
        return tmp;
    }

    /**
     * Clean a CmplxExpression
     * 
     * @param input A complex expression to be cleaned
     * @return An object of type Expression containing a cleaned CmplxExpression
     */
    public static Expression unpackExpression( CmplxExpression input ) {
        if( input.size() == 1 ) {
            return input.firstElement();
        }

        return input;
    }
}
