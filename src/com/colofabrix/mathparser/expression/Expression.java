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


/**
 * This is the holder of the expression
 * 
 * @author Fabrizio Colonna
 */
public interface Expression {

    public boolean equals( Object obj );

    /**
     * Find the type of entry the object represents
     * <p>
     * This method is used to identify the type of the object stored in the entry. It must be overridden by teh derived
     * classes
     * </p>
     * 
     * @return An integer which uniquely identify the entry type
     */
    public int getEntryType();

    /**
     * Checks if an expression is minimizable
     * <p>
     * An expression is minimizable if it doesn't contain any variable
     * </p>
     * 
     * @return <code>true</code> if the expression is minimizable</code>
     */
    public boolean isMinimizable();

    /**
     * Get a string representation of the entry
     * <p>
     * The string representation is commonly used to create output expressions
     * </p>
     * 
     * @return A string containing a representation of the object.
     */
    @Override
    public String toString();
}