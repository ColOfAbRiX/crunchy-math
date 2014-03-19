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

import java.util.HashMap;
import java.util.Map;
import org.apfloat.Apfloat;
import com.colofabrix.mathparser.expression.ExpressionEntry;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * It represents the memory containing the variables that the parser uses
 * 
 * @author Fabrizio Colonna
 */
public class Memory {

    public class MemoryCell {
        
        private ExpressionEntry value;
        private boolean readonly;
        
        /**
         * 
         * @param value Value to insert in the cell
         * @param isReadOnly Read-only attribute
         */
        public MemoryCell( ExpressionEntry value, boolean isReadOnly ) {
            this.setValue( value );
            this.setReadonly( isReadOnly );
        }
        
        /**
         * Constructor
         * 
         * @param value Value to insert in the cell
         */
        public MemoryCell( ExpressionEntry value ) {
            this( value, false );
        }
        
        /**
         * Gets the value of the cell
         * 
         * @return the value
         */
        public ExpressionEntry getValue() {
            return value;
        }
        
        /**
         * Sets the value of the cell
         * 
         * @param value the value to set
         */
        private void setValue( ExpressionEntry value ) {
            this.value = value;
        }
        
        /**
         * Indicates if the cell is read-only
         * @return the readonly
         */
        public boolean isReadonly() {
            return readonly;
        }
        
        /**
         * Sets the read-only attribute of the cell
         * 
         * @param readonly the readonly to set
         */
        private void setReadonly( boolean readonly ) {
            this.readonly = readonly;
        }
        
    }
    
    /**
     * Name of the variable containing the value of the previous calculation
     */
    public static final String ANSWER_VARIABLE = "Ans";

    /**
     * Default value for the non-defined variables
     */
    public static final Operand DEFAULT_VALUE = new Operand( new Apfloat( 0 ) );

    private final Map<String, MemoryCell> memory = new HashMap<>();

    /**
     * The constructor initializes the memory with only the ANSWER_VARIABLE variable with the value DEFAULT_VALUE
     */
    public Memory() {
        try {
            // The answer variable never raise an exception
            this.setValue( Memory.ANSWER_VARIABLE, Memory.DEFAULT_VALUE );
        }
        catch( ExpressionException e ) {}
    }

    /**
     * Gets a reference to the hashmap which stores the memory
     * 
     * @return A Map object containing the memory of the parser
     */
    public Map<String, MemoryCell> getDirectMemoryReference() {
        return this.memory;
    }

    /**
     * Gets the value of a memory address
     * 
     * @param address The name of the variable to get
     * @return The value of the memory address corresponding to the variable or <code>null</code>
     */
    public ExpressionEntry getValue( String address ) {
        if( !this.memory.containsKey( address ) )
            return null;

        return this.memory.get( address ).getValue();
    }

    /**
     * Sets a value of a memory address
     * 
     * <p>
     * If the value that is going to be set is <code>null</code> the corresponding memory address will be removed from
     * the memory
     * </p>
     * 
     * @param address The name of the variable to set
     * @param value The value to be set. If <code>null</code> the memory address will be deleted
     * @return The value that has been assigned
     * @throws ExpressionException When a read-only cell is about to be written 
     */
    public ExpressionEntry setValue( String address, ExpressionEntry value ) throws ExpressionException {
        return this.setValue( address, value, false );
    }
    
    /**
     * Sets a value of a memory address
     * 
     * <p>
     * If the value that is going to be set is <code>null</code> the corresponding memory address will be removed from
     * the memory
     * </p>
     * 
     * @param address The name of the variable to set
     * @param value The value to be set. If <code>null</code> the memory address will be deleted
     * @return The value that has been assigned
     * @throws ExpressionException When a read-only cell is about to be written 
     */
    public ExpressionEntry setValue( String address, ExpressionEntry value, boolean isReadOnly ) throws ExpressionException {
        // Check if we are trying to write over a read-only variable
        if( this.memory.containsKey( address ) )
            if( this.memory.get( address ).isReadonly() )
                throw new ExpressionException( "Attemp to write a read-only variable" );
        
        if( value != null ) {
            // Assign a non-null value
            this.memory.put( address, new MemoryCell( value, isReadOnly ) );
        }
        else {
            if( address.equals( Memory.ANSWER_VARIABLE ) )
                // Assign the default for Answer
                this.memory.put( Memory.ANSWER_VARIABLE, new MemoryCell( Memory.DEFAULT_VALUE, false ) );

            else
                // Remove any other variable
                this.memory.remove( address );
        }

        return value;
    }
}
