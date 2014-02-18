package com.colofabrix.mathparser.operators.special;

import java.util.Stack;

import com.colofabrix.mathparser.*;
import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

public class VectorPush extends GroupingOperator {

	public VectorPush() throws ConfigException {
		super();
		this.setBaseName( "," );
		this.setPriority( (short)2 );
	}
	
	public Double executeOperation( Stack<String> operands, Memory memory ) throws ExpressionException {
        return null;
	}
    
    @Override
    public Operator executeParsing( Stack<String> postfix, Stack<Operator> opstack, Operators operators, Memory memory ) throws ExpressionException {
		@SuppressWarnings("unchecked")
		Stack<String> stack = (Stack<String>)memory.getRaw( VectorOpening.STACK_NAME );
    	if( postfix.size() < 1  || stack == null )
    		throw new ExpressionException();
    	
    	// Call basic constructor. It ensures that the postfix stack is filled with all the operands and operators
    	super.executeParsingClosing( postfix, opstack, operators, memory );
    	
    	// Move all the values until the previous OpeningFunction. OpeningFunction is the only operator
    	// in opstack, because PushOperator is never pushed
    	int i = postfix.size() - 1;
    	while( postfix.size() > 0 && !(operators.fromName(postfix.get(i)) instanceof VectorOpening || operators.fromName(postfix.get(i)) instanceof VectorPush) )
    		i--;
    	
    	i++;
    	stack.addAll( postfix.subList(i, postfix.size()) );
    	ExpressionEntry.createFromPostfix( postfix.subList(i, postfix.size()) );

    	for( ; i <= postfix.size(); i++ )
    		postfix.pop();
    	
    	// Save the private stack
		memory.setRaw( VectorOpening.STACK_NAME , stack );
        
        return this;
    }

	@Override
	public boolean isOpening() {
		return true;
	}
}