package com.colofabrix.mathparser.tests;

import static org.junit.Assert.*;
import org.apfloat.Apfloat;
import org.junit.Test;
import com.colofabrix.mathparser.MathParser;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.operators.special.Vector;

public class VectorTest {

	@Test
	public void testExecuteOperation() {
        try {
            MathParser mp = new MathParser();

            Apfloat result = mp.ExecutePostfix( mp.ConvertToPostfix( "[1, 1 - 2, Sin x]" ) );
            
            assertNull( "Output must be null", result );
        }
        catch( Exception e ) {
            e.printStackTrace();
            fail( e.getMessage().getClass().toString() );
        }
	}

	@Test
	public void testExecuteParsing() {
        try {
            MathParser mp = new MathParser();

            // Reference
            CmplxExpression reference = new CmplxExpression();
            reference.add( new Operand( new Apfloat( 1 ) ) );
            reference.add( new Operand( new Apfloat( -1 ) ) );
            reference.add( CmplxExpression.fromExpression( "x #Sin", mp.getOperators(), mp.getMemory() ) );

            CmplxExpression parsed = mp.ConvertToPostfix( "[1, 1 - 2, Sin x]" );
            ExpressionEntry result = mp.getMemory().getValue( Vector.OUTPUT_NAME );

            // Check of the output postfix string
            assertEquals( "Parsed vector output", parsed.toString(), reference.toString() );
            
            // Check if the memory output is present
            assertNotNull( "Memory output present", result );

            // Check if the memory output is in the correct object
            if( !(result instanceof CmplxExpression) )
                fail( "The result is not of the expected type" );
            
            // Check the actual content of the memory output
            CmplxExpression result2 = (CmplxExpression)result;
            
            for( int i = 0; i < reference.size(); i++ )
                if( !reference.get(i).equals( result2.get(i) ) )
                    fail( "One vector element is not of the expected type" );
        }
        catch( Exception e ) {
            e.printStackTrace();
            fail( e.getMessage().getClass().toString() );
        }
	}
}
