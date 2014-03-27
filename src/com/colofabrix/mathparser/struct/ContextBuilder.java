package com.colofabrix.mathparser.struct;

public interface ContextBuilder {

    /**
     * Create a new context
     * <p>
     * A context is the pair <Operators, Memory> that identifies a working area for the operators<br/>
     * The new context will be applied every time {@link #create()} is called
     * </p>
     * 
     * @return <code>true</code> if the context is renewed, <code>false</code> otherwise.
     */
    public abstract Context create();

    /**
     * @return the context
     */
    public abstract Context getContext();

}
