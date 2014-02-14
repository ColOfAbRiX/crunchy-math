package com.colofabrix.mathparser.operators.special;

import com.colofabrix.mathparser.Operator;
import com.colofabrix.mathparser.org.ConfigException;

public abstract class GroupingOperator extends Operator {
	
	public GroupingOperator() {
		
		this.setGrouping( true );
		this.setOperandsLimit( 1, 1 );
		this.setCurrentOperands( 1 );
	}
	
	@Override
    public void setCurrentOperands( int value ) {
		try {
			super.setCurrentOperands( 1 );
		}
		catch (ConfigException e) {}
	}

	public abstract boolean isOpening();
	
    public boolean isClosing() {
        return !this.isOpening();
    }
}
