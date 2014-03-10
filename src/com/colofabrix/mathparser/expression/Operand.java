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
package com.colofabrix.mathparser.expression;

import org.apfloat.Apfloat;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * It represents an operand in an expression
 * 
 * <p>
 * An operand can be a number or a variable name
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public class Operand extends ExpressionEntry {

    /**
     * Regular espression to match allowed numbers
     */
    public static final String NUMBER_REGEX = "-?[0-9]*\\.[0-9]+|[0-9]+";

    /**
     * Code to identify the object type
     */
    public static final int OPERAND_CODE = 1;
    /**
     * Regular expression to match a variable name
     */
    public static final String VARIABLE_REGEX = "([a-zA-Z_]|[^0-9\\s][a-zA-Z_])[a-zA-Z0-9_]*";

    /**
     * Extract a number from an entry, if present
     * 
     * @param entry The entry to where extract the number
     * @return The number contained in the entry
     * @throws ExpressionException When the entry cannot be converted in a number
     * @throws ConfigException
     */
    public static Apfloat extractNumber( ExpressionEntry entry ) throws ExpressionException {
        if( entry.getEntryType() != Operand.OPERAND_CODE )
            throw new ExpressionException( "The entry cannot be converted in a number" );

        return ((Operand)entry).getNumericValue();
    }

    private Memory memory;

    // private Double value = null;
    private Apfloat value;

    private String varName = null;

    /**
     * This constructor creates an operand which contains a number
     * 
     * @param number The number to store
     */
    public Operand( Apfloat number ) {
        this( null, null, false, number, null );
    }

    /**
     * This constructor creates an operand which contains a variable
     * 
     * @param varName Variable name
     * @param varValue Variable value
     * @param memory Reference to the memory
     */
    public Operand( String varName, ExpressionEntry varValue, Memory memory ) {
        this( varName, varValue, true, null, memory );
    }

    /**
     * This constructor creates an operand which contains a variable
     * 
     * @param varName Variable name
     * @param memory Reference to the memory
     */
    public Operand( String varName, Memory memory ) {
        this( varName, null, false, null, memory );
    }

    /**
     * Generic constructor
     * 
     * @param varName Variable name
     * @param varValue Variable value
     * @param number The number to store
     * @param memory Reference to the memory
     */
    protected Operand( String varName, ExpressionEntry varValue, boolean setVarValue, Apfloat number, Memory memory ) {
        this.value = number;
        this.varName = varName;
        this.memory = memory;

        if( this.isVariable() && setVarValue )
            this.memory.setValue( this.varName, varValue );
    }

    /**
     * Checks if the given object is the same as the current one
     * 
     * <p>
     * Equality is implemented checking interal field values
     * </p>
     * <p>
     * This version of the method accept an object of type Object and then make use of the other implemented version of
     * this method.
     * </p>
     * 
     * @param obj An Object object to be compared against the current instance.
     * @return The value <b>zero</b> if the given object is equal to the current one
     */
    @Override
    public boolean equals( Object obj ) {
        if( !this.getClass().equals( obj.getClass() ) )
            return false;

        return this.equals( (Operand)obj );
    }

    /**
     * Checks if the given object is the same as the current one
     * 
     * <p>
     * Equality is implemented checking interal field values<br/>
     * 
     * @param obj An Object object to be compared against the current instance.
     * @return The value <b>zero</b> if the given object is equal to the current one
     */
    public boolean equals( Operand obj ) {
        return this.memory == obj.memory &&
                (this.value == null && this.value == obj.value || this.value.equals( obj.value )) &&
                (this.varName == null && this.varName == obj.varName || this.varName.equals( obj.varName ));
    }

    /**
     * Returns a code to identify the type of ExprEntry that this class represents
     * 
     * @return An integer indicating the ExprEntry type
     */
    @Override
    public int getEntryType() {
        return Operand.OPERAND_CODE;
    }

    /**
     * Gets the numeric value that this object containts
     * 
     * <p>
     * If the object contains a variable, this will be resolved and its content returned. If the objects contains a
     * number, this will be returned. If the referenced variable is empty, the function will return <code>null</code><br/>
     * A referenced memory address can contain another Operand, which means that the value will be dereferenced again
     * until a numeric value is encountered
     * </p>
     * 
     * @return The number stored or <code>null</code>
     * @throws ExpressionException When there is a mismatch between the variable, the memory or between internal fields
     */
    public Apfloat getNumericValue() throws ExpressionException {
        if( this.isVariable() ) {
            ExpressionEntry memoryValue = this.memory.getValue( this.varName );
            return Operand.extractNumber( memoryValue );
        }

        if( this.value != null )
            return this.value;

        throw new ExpressionException( "Cannot convert this entry in a number" );
    }

    /**
     * Gets the name of the variable stored.
     * 
     * @return A string containing the name of the variable or <code>null</code> if the object contains a number
     */
    public String getVariableName() {
        return this.varName;
    }

    /**
     * An operand is minimizable only if it is not a variable
     */
    @Override
    public boolean isMinimizable() {
        return !this.isVariable();
    }

    /**
     * Checks if the objects contains a variable
     * 
     * @return <code>true</code> if the object contain a variable and a valid memory reference
     */
    public boolean isVariable() {
        return (this.memory != null) && (this.varName != null) && (this.value == null);
    }

    /**
     * Get a string representation of the entry
     * 
     * <p>
     * The string representation is commonly used to create output expressions
     * </p>
     * 
     * @return A string containing a representation of the object.
     */
    @Override
    public String toString() {
        if( this.isVariable() )
            return this.getVariableName();

        try {
            return this.getNumericValue().toString();
        }
        catch( ExpressionException e ) {
            return "";
        }
    }
}
