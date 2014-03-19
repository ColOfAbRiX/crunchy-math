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
package com.colofabrix.mathparser.struct;

/**
 * Represents an Operator Worker
 * 
 * <p>Its purpose is to add the default operators</p>
 * 
 * @author Fabrizio Colonna
 */
public interface OperatorsSetter {
    
    /**
     * Initialize the Operators manager with a default set of operators
     * 
     * @param context
     */
    public abstract void initOperators( Context context );

    /**
     * Disposes the operators set during initialization from the Operators manager
     * 
     * @param context
     */
    public abstract void disposeOperators( Context context );
}
