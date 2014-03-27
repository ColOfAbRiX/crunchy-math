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
import com.colofabrix.mathparser.struct.ContextBuilder;
import com.colofabrix.mathparser.struct.MemorySetter;
import com.colofabrix.mathparser.struct.OperatorsSetter;

/**
 * Creates a context
 * <p>
 * Creates and initialise a context. Optionally can create a default context with default values
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public final class DefaultContextBuilder implements ContextBuilder {
    /**
     * Creates a Context with the default values
     * 
     * @return A new Context initialised with the default values
     */
    public static Context createDefault() {
        return new DefaultContextBuilder(
        		new DefaultOperators(),
        		new DefaultConstants()
        	).create();
    }

    private Context context;

    /**
     * Constructor
     * <p>
     * If one of the parameters is <code>null</code> the corresponding section will not be initialised
     * </p>
     * 
     * @param operators The OperatorSetter used to initialise operators. Can be <code>null</code>
     * @param memory The MemorySetter used to initialise the memory. Can be <code>null</code>
     */
    public DefaultContextBuilder( OperatorsSetter operators, MemorySetter memory ) {
        this.context = this.create( operators, memory );
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.colofabrix.mathparser.struct.builders.IContextBuilder#create(com.colofabrix.mathparser.struct.OperatorsSetter
     * , com.colofabrix.mathparser.struct.MemorySetter)
     */
    @Override
    public Context create() {
        return this.context;
    };

    /*
     * (non-Javadoc)
     * 
     * @see com.colofabrix.mathparser.struct.builders.IContextBuilder#getContext()
     */
    @Override
    public Context getContext() {
        return this.context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.colofabrix.mathparser.struct.builders.IContextBuilder#create(com.colofabrix.mathparser.struct.OperatorsSetter
     * , com.colofabrix.mathparser.struct.MemorySetter)
     */
    private Context create( OperatorsSetter operators, MemorySetter memory ) {
        // FIXME: What happens when I create new Memory and new Operators? I think I should initialise them? Should I
        // update the references?
    	Memory newMemory = new Memory();
    	Operators newOperators = new Operators( newMemory );
        Context newContext = new Context( newOperators, newMemory );

        this.setContext( newContext );

        // Initialize the operators (if possible)
        if( operators != null ) {
            operators.initOperators( newContext );
        }

        // Initialize the memory (if possible)
        if( memory != null ) {
            memory.initMemory( newContext );
        }

        return newContext;
    }

    /**
     * @param context the context to set
     */
    private void setContext( Context context ) {
        this.context = context;
    }
}
