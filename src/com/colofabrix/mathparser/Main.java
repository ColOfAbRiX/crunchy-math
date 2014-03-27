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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.colofabrix.mathparser.expression.Expression;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;

public class Main {
    public static void main( String[] args ) {
        MathParser mp = new MathParser();

        while( true ) {
            try {
                System.out.print( "Type the expression you want to evaluate: " );

                BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
                String input = in.readLine();

                if( input.isEmpty() ) {
                    break;
                }

                Expression ce = mp.toPostfix( input );
                Expression result = mp.executePostfix( ce );

                System.out.println( "    Convertex expression: " + ce.toString() );
                if( result.getEntryType() == Operand.OPERAND_CODE ) {
                    System.out.println( "    The result is: " + Operand.extractNumber( result ) );
                }
                else {
                    System.out.println( "    The result is: " + mp.minimise( result ) );
                }

                System.out.println();
            }
            catch( ExpressionException | ConfigException | IOException e ) {
                System.out.println( "Exception during the evaluation: " + e.getMessage() );
            }
        }
    }
}
