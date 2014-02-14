package com.colofabrix.mathparser.utests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.colofabrix.mathparser.Memory;

public class MemoryTest {

	private static Memory memory = new Memory();

	@Test
	public void testGetValueOrDefault() {
		memory.setValue( "test", Math.PI );
		assertEquals( "Check value get/set for value", Math.PI, memory.getValueOrDefault("test"), 0 );
		
		memory.setValue( "test", null );
		assertEquals( "Check get/set for default", memory.getValueOrDefault("test"), Memory.DEFAULT_VALUE, 0 );
		
		memory.setValue( "non_existent_test", null );
		assertEquals( "Check get/set for non-existent", memory.getValueOrDefault("non_existent_test"), Memory.DEFAULT_VALUE, 0 );
	}

	@Test
	public void testGetValue() {
		memory.setValue( "test", Math.PI );
		assertEquals( "Check value get/set for value", Math.PI, memory.getValue("test"), 0 );
		
		memory.setValue( "test", null );
		assertNull( "Check get/set for default", memory.getValue("test") );
		
		memory.setValue( "non_existent_test", null );
		assertNull( "Check get/set for non-existent", memory.getValue("non_existent_test") );
	}

	@Test
	public void testGetRaw() {
		Object test = new Object();
		
		memory.setRaw( "test", test );
		assertEquals( "Check value get/set for value", memory.getRaw("test"), test );
		
		memory.setRaw( "test", null );
		assertNull( "Check get/set for default", memory.getRaw("test") );
		
		assertNull( "Check get/set for non-existent", memory.getRaw("non_existent_test") );
	}
}
