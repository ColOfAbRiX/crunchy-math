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
package com.colofabrix.mathparser.struct.builders;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.struct.Context;
import com.colofabrix.mathparser.struct.MemorySetter;
import com.colofabrix.mathparser.struct.OperatorsSetter;

/**
 * Creates a context
 * 
 * <p>Creates and initialize a context. Optionally can create a default context with default values</p>
 * 
 * @author Fabrizio Colonna
 */
public class ContextBuilder {
    private Context context;
    
    public static Context createDefault() {
        return new ContextBuilder().create( new DefaultOperators(), new DefaultConstants() );
    }
    
    /**
     * Create a new context
     * 
     * <p>
     * A context is the pair <Operators, Memory> that identifies a working area for the operators<br/>
     * The new context will be applied every time {@link #create(Class)} is called
     * </p>
     * 
     * @return <code>true</code> if the context is renewd, <code>false</code> otherwise.
     */
    public Context create( OperatorsSetter operators, MemorySetter memory ) {
        // FIXME: What happens when I create new Memory and new Operators? I think I should initialize them? Should I update the references?
        Context newContext = new Context( new Operators(), new Memory() );
        
        this.setContext( newContext );
        operators.initOperators( newContext );
        memory.initMemory( newContext );
        
        return newContext;
    }

    /**
     * @return the context
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    private void setContext( Context context ) {
        this.context = context;
    }
}
