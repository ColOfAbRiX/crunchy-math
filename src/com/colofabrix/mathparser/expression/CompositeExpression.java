package com.colofabrix.mathparser.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;

import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.Operators;
import com.colofabrix.mathparser.org.ExpressionException;

/**
 * A composite expression represents an expression that is made by sub entries
 * 
 * <p>An example of composite expression is "1 + 2", which is made by two Operands and one Operator.</p>
 * 
 * @author Fabrizio Colonna
 */
public class CompositeExpression extends ExpressionEntry implements List<ExpressionEntry> {

	/**
	 * Code to identify the object type
	 */
	public static final int COMPOSITE_CODE = 3;
	
	ArrayList<ExpressionEntry> subExpressions = new ArrayList<>();
	
	/**
	 * Creates a CompositeExpression starting from a string
	 * 
	 * <p>The method splits the string in its component and create an appropriate ExpressionEntry for
	 * every token.</p>
	 * 
	 * @param expression The expression to converts
	 * @param operators A reference to the operators in use
	 * @param memory A reference to the memory in use
	 * @return A CompositeExpression object containing the translated string expression
	 * @throws ExpressionException
	 */
	public static CompositeExpression fromExpression( String expression, Operators operators, Memory memory ) throws ExpressionException {
		// The input string is split in its forming components and translated in object-form
		Matcher m = operators.getParsingRegex().matcher( expression );
		CompositeExpression composite = new CompositeExpression();

		// Saves all matches in a list
		while( m.find() )
			composite.add( ExpressionEntry.fromStringEntry(m.group(), operators, memory) );
		
		return composite;
	}
	
	/**
	 * Creates a CompositeExpression starting from a string
	 * 
	 * <p>The method splits the string in its component and create an appropriate ExpressionEntry for
	 * every token.</p>
	 * 
	 * @param stack A stack of ExpressionEntry to add in a new CompositeExpression
	 * @param operators A reference to the operators in use
	 * @param memory A reference to the memory in use
	 * @return A CompositeExpression object containing the added CompositeExpression(s)
	 * @throws ExpressionException
	 */
	public static CompositeExpression fromExpression( Collection<ExpressionEntry> stack, Operators operators, Memory memory ) throws ExpressionException {
		CompositeExpression composite = new CompositeExpression();
		composite.addAll( stack );
		return composite;
	}
	
	public ExpressionEntry lastElement() {
		if( this.size() == 0 )
			return null;
		
		return this.subExpressions.get( this.size() - 1);
	}
	
	public ExpressionEntry firstElement() {
		if( this.subExpressions.size() == 0 )
			return null;
		
		return this.subExpressions.get( 0 );
	}

	public ExpressionEntry pop() {
		ExpressionEntry temp = this.lastElement();
		this.remove( this.size() - 1);
		return temp;
	}

	public void push(ExpressionEntry e) {
		this.add( this.size() - 1, e );
	}

	/**
	 * Find the type of entry the object represents
	 * 
	 * <p>This method is used to identify the type of the object stored in the entry. It must be
	 * overridden by teh derived classes</p>
	 * 
	 * @return An integer wich uniquely identify the entry type
	 */
	@Override
	public int getEntryType() {
		return CompositeExpression.COMPOSITE_CODE;
	}

	/**
	 * Get a string representation of the entry
	 * 
	 * <p>The string representation is commonly used to create output expressions</p>
	 * 
	 * @returns A string containing a representation of the object.
	 */
	@Override
	public String toString() {
		String output = "";
		
		// Recursively calls the method toString() of the subentries
		for( ExpressionEntry entry: this.subExpressions )
			output += entry.toString() + " ";
		
		return output.replaceFirst( "\\s$", "" );
	}

	@Override
	public boolean add(ExpressionEntry e) {
		return this.subExpressions.add( e );
	}

	@Override
	public void add(int index, ExpressionEntry element) {
		this.subExpressions.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends ExpressionEntry> c) {
		return this.subExpressions.addAll( c );
	}

	@Override
	public boolean addAll(int index, Collection<? extends ExpressionEntry> c) {
		return this.subExpressions.addAll( index, c );
	}

	@Override
	public void clear() {
		this.subExpressions.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.subExpressions.contains( o );
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.subExpressions.containsAll( c );
	}

	@Override
	public ExpressionEntry get(int index) {
		return this.subExpressions.get( index );
	}

	@Override
	public int indexOf(Object o) {
		return this.subExpressions.indexOf( o );
	}

	@Override
	public boolean isEmpty() {
		return this.subExpressions.isEmpty();
	}

	@Override
	public Iterator<ExpressionEntry> iterator() {
		return this.subExpressions.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.lastIndexOf( o );
	}

	@Override
	public ListIterator<ExpressionEntry> listIterator() {
		return this.subExpressions.listIterator();
	}

	@Override
	public ListIterator<ExpressionEntry> listIterator(int index) {
		return this.subExpressions.listIterator( index );
	}

	@Override
	public boolean remove(Object o) {
		return this.subExpressions.remove( o );
	}

	@Override
	public ExpressionEntry remove(int index) {
		return this.subExpressions.remove( index );
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.subExpressions.removeAll( c );
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.subExpressions.retainAll( c );
	}

	@Override
	public ExpressionEntry set(int index, ExpressionEntry element) {
		return this.set( index, element );
	}

	@Override
	public int size() {
		return this.subExpressions.size();
	}

	@Override
	public List<ExpressionEntry> subList(int fromIndex, int toIndex) {
		return this.subExpressions.subList( fromIndex, toIndex );
	}

	@Override
	public Object[] toArray() {
		return this.subExpressions.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.subExpressions.toArray( a );
	}
}
