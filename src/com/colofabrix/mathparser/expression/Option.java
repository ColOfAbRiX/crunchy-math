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
 * It represents an option that is configurable in the system
 * 
 * @author Fabrizio Colonna
 */
public class Option extends ExpressionEntry {

    /**
     * Code to identify the object type
     */
    public static final int OPTION_CODE = 4;

    /**
     * Marker to identify an option
     */
    public static final String OPTION_MARK = "$";

    /**
     * Regular expression to match an option name
     */
    public static final String OPTION_REGEX = "\\" + Option.OPTION_MARK + "([a-zA-Z_]|\\.[a-zA-Z_])[a-zA-Z0-9_]*";

    /**
     * Regular expression to match a string
     */
    public static final String STRING_VALUE = "\"(?:[^\"\\]|\\.)*\"";

    private String name;

    /**
     * Constructor
     * 
     * @param name Name of the option
     */
    public Option( String name ) {
        this.name = name.replaceAll( "^\\" + Option.OPTION_MARK, "" );
    }

    /**
     * Returns a code to identify the type of ExprEntry that this class represents
     * 
     * @return An integer indicating the ExprEntry type
     */
    @Override
    public int getEntryType() {
        return Option.OPTION_CODE;
    }

    /**
     * Gets the name of the Option comprehensive of the marker
     * 
     * @return The name of the option
     */
    public String getFullName() {
        return Option.OPTION_MARK + this.getName();
    }

    /**
     * Gets the base name of the Option
     * 
     * @return The name of the option
     */
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isMinimizable() {
        return false;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }

}
