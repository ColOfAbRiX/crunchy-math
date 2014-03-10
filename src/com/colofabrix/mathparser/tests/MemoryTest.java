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
package com.colofabrix.mathparser.tests;

import org.junit.Assert;
import org.junit.Test;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.operators.CosOperator;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class MemoryTest {

    private static Memory memory = new Memory();

    @Test
    public void testGetValueOrDefault() {
        try {
            // Operator saving and retrival
            Operator a = new CosOperator();
            MemoryTest.memory.setValue( "test", a );
            Assert.assertSame( "Check value get/set for object",
                    a,
                    MemoryTest.memory.getValue( "test" ) );

            // Test for null
            MemoryTest.memory.setValue( "test", null );
            Assert.assertEquals( "Check get/set for default",
                    Memory.DEFAULT_VALUE.getNumericValue().doubleValue(),
                    ((Operand)MemoryTest.memory.getValueOrDefault( "test" )).getNumericValue().doubleValue(), 0 );

            // Test for non-existent value
            MemoryTest.memory.setValue( "non_existent_test", null );
            Assert.assertEquals( "Check get/set for non-existent",
                    Memory.DEFAULT_VALUE.getNumericValue().doubleValue(),
                    ((Operand)MemoryTest.memory.getValueOrDefault( "non_existent_test" )).getNumericValue()
                            .doubleValue(), 0 );
        }
        catch( ExpressionException | ConfigException e ) {
            e.printStackTrace();
            Assert.fail( e.getMessage().getClass().toString() );
        }
    }
}
