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
public class CmplxExpression extends ExpressionEntry implements List<ExpressionEntry> {

	/**
	 * Code to identify the object type
	 */
	public static final int COMPOSITE_CODE = 3;
	
	ArrayList<ExpressionEntry> subExpressions = new ArrayList<>();
	
	/**
	 * Creates a CmplxExpression starting from a string
	 * 
	 * <p>The method splits the string in its component and create an appropriate ExpressionEntry for
	 * every token.</p>
	 * 
	 * @param expression The expression to converts
	 * @param operators A reference to the operators in use
	 * @param memory A reference to the memory in use
	 * @return A CmplxExpression object containing the translated string expression
	 * @throws ExpressionException
	 */
	public static CmplxExpression fromExpression( String expression, Operators operators, Memory memory ) throws ExpressionException {
		// The input string is split in its forming components and translated in object-form
		Matcher m = operators.getParsingRegex().matcher( expression );
		CmplxExpression composite = new CmplxExpression();

		// Saves all matches in a list
		while( m.find() )
			composite.add( ExpressionEntry.fromStringEntry(m.group(), operators, memory) );
		
		return composite;
	}
	
	/**
	 * Creates a CmplxExpression starting from a string
	 * 
	 * <p>The method splits the string in its component and create an appropriate ExpressionEntry for
	 * every token.</p>
	 * 
	 * @param stack A stack of ExpressionEntry to add in a new CmplxExpression
	 * @param operators A reference to the operators in use
	 * @param memory A reference to the memory in use
	 * @return A CmplxExpression object containing the added CmplxExpression(s)
	 * @throws ExpressionException
	 */
	public static CmplxExpression fromExpression( List<ExpressionEntry> stack, Operators operators, Memory memory ) throws ExpressionException {
		CmplxExpression composite = new CmplxExpression();
		composite.addAll( stack );
		return composite;
	}
	
	/**
	 * Checks if an expression is minimizable
	 * 
	 * <p>An expression is minimizable if it doesn't contain any variable</p>
	 * 
	 * @return <code>true</code> if the expression is minimizable</code>
	 */
	public boolean isMinimizable() {
		boolean result = true;
		
		for( ExpressionEntry entry: this.subExpressions ) {
			if( entry.getEntryType() == Operand.OPERAND_CODE && ((Operand)entry).isVariable() )
				return false;
			
			else if( entry.getEntryType() == CmplxExpression.COMPOSITE_CODE )
				result = ((CmplxExpression)entry).isMinimizable();
		}
		
		return result;
	}
	
	/**
	 * Gets the last element of the expression
	 * 
	 * @return An ExpressionEntry at the end of the expression or <code>null</code> if the list is empty
	 */
	public ExpressionEntry lastElement() {
		if( this.size() == 0 )
			return null;
		
		return this.subExpressions.get( this.size() - 1);
	}
	
	/**
	 * Gets the first element of the expression
	 * 
	 * @return An ExpressionEntry at the beginning of the expression or <code>null</code> if the list is empty
	 */
	public ExpressionEntry firstElement() {
		if( this.subExpressions.size() == 0 )
			return null;
		
		return this.subExpressions.get( 0 );
	}

	/**
	 * Gets the last element of the expression and remove it from the list
	 * 
	 * @return An ExpressionEntry at the end of the expression or <code>null</code> if the list is empty
	 */
	public ExpressionEntry pop() {
		ExpressionEntry temp = this.lastElement();
		this.remove( this.size() - 1);
		return temp;
	}

	/**
	 * Add an ExpressionEntry at the end of the expression
	 * 
	 * @param e The ExpressionEntry to add
	 */
	public void push(ExpressionEntry e) {
		this.add( this.size(), e );
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
		return CmplxExpression.COMPOSITE_CODE;
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
