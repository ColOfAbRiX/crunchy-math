package com.colofabrix.mathparser.operators.special;

import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.org.ConfigException;

/**
 * An opening bracket operator
 * 
 * <p>Brackets are used to give priority to some section of expressions.<br/>
 * A opening bracket serves to mark the beginning of a subexpression that will be later processed as
 * a differen entity. The push is performed by the parent class {@link GroupingOperator},
 * this class is only used to define a name and a priorit
 * 
 * @author Fabrizio Colonna
 */
public class OpeningBracket extends GroupingOperator {
	
    public OpeningBracket() throws ConfigException {
    	super();
        this.setBaseName( "(" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return true;
	}
}
