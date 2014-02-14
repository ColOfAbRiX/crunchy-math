package com.colofabrix.mathparser;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.colofabrix.mathparser.org.ConfigException;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * This class represent a mathematical operator
 * 
 * @author Fabrizio Colonna
 */
public abstract class Operator implements Comparable<Operator>, Cloneable {
	
	/**
	 * Marker for the operand number field
	 */
	public static final String OPNUM_MARK = "#";
	
	/**
	 * Regular expression to match the operand number field (<num>#<opname>)
	 */
	public static final String OPNUM_REGEX = "^([1-9][0-9]*)?(" + OPNUM_MARK + ").*";

	private boolean grouping = false;
	private int minOperands = 2;
	private int maxOperands = 2;
	private int priority = 0;
	private int opCount = 2;
    private String name;
	
    /**
     * Executes the operation performed by the operator
	 * 
	 * <p>Every concrete implementation of Operator must implement this method with the operation that
	 * it has to do.</p>
     *  
     * @param operands A stack containing the operands in reversed order
     * @param memory A reference to the main math memory
     * @return It returns a number if the operation succeeded or <code>null</code> to express empty-result
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
    public abstract Double executeOperation( Stack<String> operands, Memory memory ) throws ExpressionException;
    
    /**
     * Execute the parsing operation that the operator may require
     * 
     * <p>The parsing operation is an operation that is performed when the operator is fetched from
     * the input string</br>
     * The function is called and can fully operate on the output and operand stacks, as well as the
     * parser memory. It must take care of the correct placing of operators and operands on the two
     * stacks in relation to its behaviour.<br/>
     * This default implementation pops out the operators stack all the operators with less priority
     * that the current one and returns the current operator to be pushed on the operators stack;</p>
     * 
     * @param postfix The full postfix stack, as it is build before the call to this method
     * @param opstack The full operator stack, as it is constructed befor the call to this method
     * @param memory A reference to the main math memory
     * @return An instance of the operator to be pushed at the end of the operators stack, 
     * @throws ExpressionException The exception is thrown when there is an evaluation problem
     */
    public Operator executeParsing( Stack<String> postfix, Stack<Operator> opstack, Memory memory ) throws ExpressionException
    {
        // Extract all the operators that precede the current one
        while( opstack.size() > 0 && opstack.lastElement().compareTo( this ) >= 0  )
            postfix.push( opstack.pop().getName() );
        
        return this;
    }

    /**
     * Gets the number of operands currently used
     * 
     * <p>This is the number of operands this instance is using. For example, if 1 it indicates that
     * this is a unary operator</p>
     * 
     * @return A number indicating how many operands this operator needs 
     */
    public int getCurrentOperands() {
    	return this.opCount;
    }
    
    /**
     * Sets the number of operands currently used
     * 
     * <p>This is the number of operands this instance is using. For example, if 1 it indicates that
     * this is a unary operator<br/>
     * The number of operands must be between a minimun and a maximum which is set with setOperandsLimit</p>
     * 
     * @param value A number indicating how many operands this operator needs
     * @throws Exception This exception is thrown when a wrong number of current operands is specified
     */
    public void setCurrentOperands( int value ) throws ConfigException {
    	if( value < this.minOperands || value > this.maxOperands )
    		throw new ConfigException();
    	
    	this.opCount = value;
    }
    
    /**
     * Sets the limits of the number of operands allowed for the operator
     * 
     * <p>For example, the operator <b>minus</b> is a dual operator but, in some cases, can be put in
     * front of a bracket and it becomes a unary operator. In this case the limits are 1 and 2<br/>
     * When the limits are changed the current operator count is set between these limits.</p>
     * 
     * @param min Minimum number of operand allowed
     * @param max Maximum number of operand allowed
     */
    public void setOperandsLimit( int min, int max ) {
    	this.minOperands = min;
    	this.maxOperands = max;
    	
    	this.opCount = Math.min( this.opCount, this.maxOperands );
    	this.opCount = Math.max( this.opCount, this.minOperands );
    }
    
    /**
     * Gets the minimum number of operands allowed for the operator
     * 
     * @return A number that indicates the how many minimum operands are allowed
     */
    public int getOperandsMin() {
    	return this.minOperands;
    }
    
    /**
     * Gets the maximum number of operands allowed for the operator
     * 
     * @return A number that indicates the how many maximum operands are allowed
     */
    public int getOperandsMax() {
    	return this.maxOperands;
    }
    
    /**
     * Sets the absolute priority of the operand
     * 
     * <p>Priority is used to evaluate operators in the right order.<br/>
     * Priorities have got levels to make sure certain categories of operators have always more or
     * less priority than others. A level spans from Short.MIN_VALUE to Short.MAX_VALUE.</p>
     * <ul>
     *   <li>Common operators have got priorities in level 0</li>
     *   <li>Unary operators have got priorities in level 1</li>
     *   <li>Grouping operators have got priorities in level -1</li>
     * </ul>
     * 
     * @return A number indicating the priority of the operator
     */
    public int getPriority() {
        int base = this.priority;
        
        // Grouping operators have priority over unary operators
        //if( this.isGrouping() )
        //	return base -Short.MAX_VALUE;
        
        // Unary operators have priority over math operators
        if( this.opCount == 1 )
            return base + Short.MAX_VALUE;
        
        return base;
    }

    /**
     * Gets the absolute priority of the operand
     * 
     * <p>The priority of an operator is expressed using an Integer, while a priority can only be
     * set using a Short because priorities use levels.</p>
     * 
     * @see getPriority
     * @param priority A number that establish the priority of the operator.
     */
    protected void setPriority( short priority ) {
        this.priority = (int)priority;
    }

    /**
     * Indicates if the operator is a grouping one
     * 
     * <p>A grouping operator is and operator like a bracket, with an opening ad a closing</p>
     * 
     * @return <code>true</code> if the operator is grouping, <code>false</code> otherwise
     */
	public boolean isGrouping() {
		return this.grouping;
	}

	/**
     * Sets a value that says if the operator is a grouping one
     * 
	 * @param value <code>true</code> if the operator is grouping, <code>false</code> otherwise 
	 */
	protected void setGrouping( boolean value ) {
		this.grouping = value;
	}

	/**
	 * Gets the base name of the operator 
	 * 
	 * The base name of an operator is the name withous operands specifier
	 * 
	 * @return The name of the operator
	 */
	public String getBaseName() {
        return this.name;
	}
	
	/**
	 * Gets the full name of the operator.
	 * 
	 * <p>This name include the operand specifier field, if needed.<br/>
	 * An operator name is composed of 2 distinct fields: the name and the operands counter. Usually
	 * a full operator name is in the form <code><i>op_count</i>#<i>name</i></code><br/>
	 * There are three name formats:</p>
	 * <ul>
	 *   <li><i>name</i> Only the name means 2 operands</li>
	 *   <li><i><b>#</b>name</i> Means 1 operand</li>
	 *   <li><i>N<b>#</b>name</i> Means N operands</li>
	 * </ul>
	 * 
	 * @return  The full name of the operator
	 */
	public String getName() {
		if( this.opCount == 1 )
	        return Operator.OPNUM_MARK + this.name;
		
		else if( this.opCount > 2 )
	        return this.opCount + Operator.OPNUM_MARK + this.name;
		
		else
	        return this.name;
	}

	/**
	 * Sets the base name of the operator
	 * 
	 * <p>The base name of an operator can be set only once, then the operator mantains that name for
	 * all its existence</p>
	 * 
	 * @param name
	 * @throws ConfigException 
	 */
    public void setBaseName( String name ) throws ConfigException {
    	if( this.name != null && !this.name.isEmpty()  )
    		throw new ConfigException();
    		
        this.name = name;
    }
 

    /**
     * Compares the priority of the operators
     * 
     * @param op An Operator object to be compared against the current instance.
     * @return The value <b>zero</b> if the given operand has got the same priority of the current, a value <b>less than zero</b> or <b>greater than zero</b> if the priority of the given operand is less than or higher respectively 
     */
    @Override
    public int compareTo( Operator op ) {
        return (int)Math.signum( this.getPriority() - op.getPriority() );
    }
    
    
    /**
     * Checks if the given operator isis the same as the current one
     * 
     * <p>Equality is implemented checking the base name of the operators</br>
     * Operators with the same name are the same operators. To check if two operators are the same
     * instance use Java equality operator <b>==</b></p>
     * <p>This version of the method accept an object of type Object and then make use of the other
     * implemented version of this method. If the object given is not an Operand nor a String the
     * two object are not equals</p>
     * 
     * @param op An Object object to be compared against the current instance.
     * @return The value <b>zero</b> if the given operand has got the same priority of the current, a value <b>less than zero</b> or <b>greater than zero</b> if the priority of the given operand is less than or higher respectively 
     */
    @Override
    public boolean equals( Object op ) {
        if( op.getClass() == String.class )
                return this.equals( (String)op );

        else if( op.getClass() == Operator.class )
            return this.equals( (Operator)op );

        return false;
    }

    /**
     * Check if the given operator are the same as the current one
     * 
     * <p>Equality is implemented checking the base name of the operators</br>
     * Operators with the same name are the same operators. To check if two operators are the same
     * instance use Java equality operator <b>==</b></p>
     * 
     * @param op An Operator object to be compared against the current instance.
     * @return The value <b>zero</b> if the given operand has got the same priority of the current, a value <b>less than zero</b> or <b>greater than zero</b> if the priority of the given operand is less than or higher respectively 
     */
    public boolean equals( Operator op ) {
        return this.equals( op.getBaseName() );
    }
    
    /**
     * Check if the given operator is the same as the current one
     * 
     * <p>Equality is implemented checking the base name of the operators</br>
     * Operators with the same name are the same operators. To check if two operators are the same
     * instance use Java equality operator <b>==</b></p>
     * <p>This version of the method accept an object of type Object and then make use of the other
     * implemented version of this method. If the object given is not an Operand nor a String the
     * two object are not equals</p>
     * 
     * @param op A String containing the name of the operator to be compared.
     * @return The value <b>zero</b> if the given operand has got the same priority of the current, a value <b>less than zero</b> or <b>greater than zero</b> if the priority of the given operand is less than or higher respectively 
     */
    public boolean equals( String op ) {
        return this.name.compareToIgnoreCase( op ) == 0;
    }
    
    
    /**
     * Clones this instance of the operator
     * 
     * @return A new Operator object identical to the Operator object of the current instance
     */
    @Override
    public Object clone() {
        try {
            Operator newOp = this.getClass().newInstance();

            newOp.grouping = this.grouping;
            newOp.maxOperands = this.maxOperands;
            newOp.name = this.name;
            newOp.opCount = this.opCount;
            newOp.priority = this.priority;
            
            return newOp;
        }
        catch( InstantiationException | IllegalAccessException e ) {
            e.printStackTrace();
		}
        return null;
    }

    /**
     * It gets the number of operands an operator needs
     * 
     * <p>The discovering is performed against the name and using the reqular expression OPNUM_REGEX</p>
     * 
     * @param operator A string containing the full name of the operator
     * @return The number of operands that the specified operator needs
     */
    public static int discoverOperandsCount( String operator ) {
    	Matcher m = Pattern.compile( Operator.OPNUM_REGEX ).matcher(operator);
    	
    	if( !m.matches() )
    		return 2;
    	
    	if( m.group(1) == null )
    		return 1;
    	
    	return Integer.valueOf( m.group(1) );
    }
	
    /**
     * Translate an operand from string to Double
     * 
     * <p>A string can contain a number or a variable name. This function returns the correct value
     * that represent the string.</p>
     * 
     * @param operand A string containing the operand
     * @param memory A reference do the Math Parser memory
     * @return A Double representing the value of the operand
     * @throws ExpressionException When there is a problem recognizing the operand
     */
	public static Double translateOperand( String operand, Memory memory ) throws ExpressionException {
		
		// Numeric operand
    	if( operand.matches(Operators.NUMBER_REGEX) )
    		return Double.valueOf( operand );
    	
    	// Variable from memory
    	else if( operand.matches(Operators.VARIABLE_REGEX) )
    		return memory.getValue( operand );
    	
    	throw new ExpressionException();
	}
}