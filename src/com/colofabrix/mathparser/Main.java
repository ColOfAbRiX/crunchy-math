package com.colofabrix.mathparser;

import java.io.*;

import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.org.*;

public class Main {

    public static void main( String[] args ) {

		while( true ) {
			System.out.print( "Type the expression you want to evaluate: " );

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String input = in.readLine();
				
				if( input.isEmpty() )
					break;
			
				MathParser mp = new MathParser();
				CmplxExpression ce = mp.ConvertToPostfix( input );
				Double result = mp.ExecutePostfix( ce );
						
				System.out.println( "    Convertex expression: " + ce.toString() );
				System.out.println( "    The result is: " + result );
			}
			catch (ExpressionException | ConfigException | IOException e) {
				System.out.println( "Exception during the evaluation" );
			}
			System.out.println();
		}
    }    
}
