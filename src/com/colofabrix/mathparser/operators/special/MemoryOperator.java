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
package com.colofabrix.mathparser.operators.special;

import java.util.Map;
import java.util.Stack;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatRuntimeException;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;

/**
 * Displays operators information
 * 
 * @author Fabrizio Colonna
 */
public class MemoryOperator extends Operator {

    public MemoryOperator() throws ConfigException {
        super();
        this.setBaseName( "Memory" );
        this.setPriority( (short)2 );
        this.setOperandsLimit( 1, 1 );
        this.setCurrentOperands( 1 );
    }

    /**
     * Select the sub-function to call
     */
    @Override
    public Operand executeOperation( Stack<Expression> operands ) throws ExpressionException {
        if( operands.size() < this.getCurrentOperands() )
            throw new ExpressionException( "Wrong number of given parameters" );

        int value1 = Operand.extractNumber( operands.pop() ).intValue();

        switch( value1 ) {
            case 1:
                // DISPLAY MEMORY DUMP
                this.displayMemory( this.getContext().getMemory() );
                break;
        }

        return new Operand( new Apfloat( 0 ) );
    }

    private void displayMemory( Memory mem ) {
        System.out.println();
        System.out.println( "Print memory dump" );
        System.out.println( "----------------------------------------------------------------------------" );
        System.out.println( "Name\t\tRO\t\tPrec\t\tValue" );
        System.out.println( "----------------------------------------------------------------------------" );

        for( Map.Entry<String, Memory.MemoryCell> entry: mem.getSet() ) {
            String name = entry.getKey();
            Memory.MemoryCell cell = entry.getValue();

            System.out.print( name );
            System.out.print( "\t\t" );

            System.out.print( cell.isReadonly() );
            System.out.print( "\t\t" );

            if( cell.getValue().getEntryType() == Operand.OPERAND_CODE ) {
                try {
                    Operand operand = (Operand)cell.getValue();

                    System.out.print( operand.toNumber( ).precision() );
                    System.out.print( "\t\t" );

                    System.out.println( operand.toNumber( ) );
                }
                catch( ApfloatRuntimeException | ExpressionException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else {
                System.out.println( "Not-printable" );
            }
        }

        System.out.println( "----------------------------------------------------------------------------" );
    }
}
