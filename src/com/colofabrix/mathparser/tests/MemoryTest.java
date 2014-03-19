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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Test;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.operators.SumOperator;
import com.colofabrix.mathparser.struct.ConfigException;
import com.colofabrix.mathparser.struct.ExpressionException;

public class MemoryTest {

    private static Memory memory = new Memory();

    @Test
    public void testGetValueOrDefault() {
        try {
            // Operator saving and retrival
            Operator a = new SumOperator();
            MemoryTest.memory.setValue( "test", a );
            assertSame( "Check value get/set for object",
                    a,
                    MemoryTest.memory.getValue( "test" ) );

            // Test for null
            MemoryTest.memory.setValue( "test", null );
            assertNull( "Check get/set for default",
                    MemoryTest.memory.getValue( "test" ) );

            // Test for read-only variable
            try {
                MemoryTest.memory.setValue( "this_is_readonly", a, true );
                MemoryTest.memory.setValue( "this_is_readonly", a, true );
                fail( "Read-only test: the variable was overwritten" );
            }
            catch( ExpressionException e ) {
            }

            // Test for read-only variable
            try {
                MemoryTest.memory.setValue( "this_is_readonly_but_null", null, true );
                MemoryTest.memory.setValue( "this_is_readonly_but_null", null, true );
            }
            catch( ExpressionException e ) {
                fail( "Read-only test with null: the variable was not overwritten" );
            }
        }
        catch( ExpressionException | ConfigException e ) {
            e.printStackTrace();
            fail( e.getMessage().getClass().toString() );
        }
    }
}
