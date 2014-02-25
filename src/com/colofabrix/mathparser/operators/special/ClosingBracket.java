package com.colofabrix.mathparser.operators.special;

import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.org.ConfigException;

/**
 * A closing bracket operator
 * 
 * <p>Brackets are used to give priority to some section of expressions.<br/>
 * A closing bracket serves to say that a subexpression is finished and to push all the operands and
 * operators to the correct stack. The push is performed by the parent class {@link GroupingOperator},
 * this class is only used to define a name and a priority</p>
 * 
 * @author Fabrizio Colonna
 */
public class ClosingBracket extends GroupingOperator {
    public ClosingBracket() throws ConfigException {
    	super();
        this.setBaseName( ")" );
        this.setPriority( (short)0 );
    }
    
	public boolean isOpening() {
	    return false;
	}
}
