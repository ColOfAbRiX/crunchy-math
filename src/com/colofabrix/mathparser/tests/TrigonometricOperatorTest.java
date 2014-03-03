package com.colofabrix.mathparser.tests;

import static org.junit.Assert.*;
import java.util.Stack;
import org.apfloat.Apfloat;
import org.junit.Test;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.expression.CmplxExpression;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.lib.ApfloatConsts;
import com.colofabrix.mathparser.operators.ArsinOperator;
import com.colofabrix.mathparser.operators.SinOperator;
import com.colofabrix.mathparser.operators.special.DegreesUnits;
import com.colofabrix.mathparser.operators.special.TrigonometricOperator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class TrigonometricOperatorTest {

    @Test
    public void testGetRadians() {
        Stack<ExpressionEntry> operand = new Stack<ExpressionEntry>();
        Operand result;

        try {
            TrigonometricOperator test = new SinOperator();
            test.executeParsing( new CmplxExpression(), new Stack<Operator>(), new Operators(), new Memory() );

            // Radians test
            test.setSelectedUnit( DegreesUnits.RADIANS );
            operand.push( new Operand( ApfloatConsts.Angular.PI_SIXTHS ) );
            result = test.executeOperation( operand, null );
            assertEquals( "Using radians",
                    0.5,
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );

            // Degrees test
            test.setSelectedUnit( DegreesUnits.DEGREES );
            operand.push( new Operand( ApfloatConsts.Angular.DEG_30 ) );
            result = test.executeOperation( operand, null );
            assertEquals( "Using degrees",
                    0.5,
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );

            // Gradians test
            test.setSelectedUnit( DegreesUnits.GRADIANS );
            operand.push( new Operand( ApfloatConsts.Angular.GRAD_33 ) );
            result = test.executeOperation( operand, null );
            assertEquals( "Using gradians",
                    0.5,
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );
        }
        catch( ConfigException | ExpressionException e ) {
            e.printStackTrace();
            fail( e.getMessage().getClass().toString() );
        }
    }

    @Test
    public void testGetCurrent() {
        Stack<ExpressionEntry> operand = new Stack<ExpressionEntry>();
        Operand result;

        try {
            TrigonometricOperator test = new ArsinOperator();
            test.executeParsing( new CmplxExpression(), new Stack<Operator>(), new Operators(), new Memory() );

            // Radians test
            test.setSelectedUnit( DegreesUnits.RADIANS );
            operand.push( new Operand( new Apfloat( 0.5 ) ) );
            result = test.executeOperation( operand, new Memory() );
            assertEquals( "Using radians",
                    ApfloatConsts.Angular.PI_SIXTHS.doubleValue(),
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );

            // Degrees test
            test.setSelectedUnit( DegreesUnits.DEGREES );
            operand.push( new Operand( new Apfloat( 0.5 ) ) );
            result = test.executeOperation( operand, new Memory() );
            assertEquals( "Using degrees",
                    ApfloatConsts.Angular.DEG_30.doubleValue(),
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );

            // Gradians test
            test.setSelectedUnit( DegreesUnits.GRADIANS );
            operand.push( new Operand( new Apfloat( 0.5 ) ) );
            result = test.executeOperation( operand, new Memory() );
            assertEquals( "Using gradians",
                    ApfloatConsts.Angular.GRAD_33.doubleValue(),
                    result.getNumericValue().doubleValue(),
                    AllTests.PRECISION_ERROR_ALLOWED );
        }
        catch( ConfigException | ExpressionException e ) {
            e.printStackTrace();
            fail( e.getMessage().getClass().toString() );
        }
    }

}
