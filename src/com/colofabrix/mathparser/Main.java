package com.colofabrix.mathparser;

import java.io.*;

import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.org.*;

public class Main {

    public static void main( String[] args ) {
		MathParser mp;
		
		try {
			mp = new MathParser();
		}
		catch (ConfigException e) {
			e.printStackTrace();
			return;
		}

		while( true ) {
			try {
				System.out.print( "Type the expression you want to evaluate: " );

				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String input = in.readLine();
				
				if( input.isEmpty() )
					break;

				ExpressionEntry ce = mp.ConvertToPostfix( input );
				Double result = mp.ExecutePostfix( ce );

				System.out.println( "    Convertex expression: " + ce.toString() );
				System.out.println( "    The result is: " + result );
				
				System.out.println();
			}
			catch (ExpressionException | ConfigException | IOException e) {
				System.out.println( "Exception during the evaluation" );
			}
		}
    }    
}
