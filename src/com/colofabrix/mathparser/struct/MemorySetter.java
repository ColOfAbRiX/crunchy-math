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
 * Represents a Memory Worker
 * 
 * <p>
 * Its purpose is to add some constants to memory
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public interface MemorySetter {

    /**
     * Dispose the constants that were initialised previously
     * 
     * @param context The context associated, where the memory resides
     */
    public abstract void disposeMemory( Context context );

    /**
     * Initialise the memory with constants
     * 
     * @param context The context associated, where the memory resides
     */
    public abstract void initMemory( Context context );
}
