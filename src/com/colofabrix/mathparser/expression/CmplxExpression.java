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
package com.colofabrix.mathparser.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A composite expression represents an expression that is made by sub entries
 * <p>
 * An example of composite expression is "1 + 2", which is made by two Operands and one Operator.
 * </p>
 * 
 * @author Fabrizio Colonna
 */
public class CmplxExpression implements Expression, List<Expression> {

    /**
     * Code to identify the object type
     */
    public static final int COMPOSITE_CODE = 4;

    ArrayList<Expression> subExpressions = new ArrayList<>();

    @Override
    public boolean add( Expression e ) {
        return this.subExpressions.add( e );
    }

    @Override
    public void add( int index, Expression element ) {
        this.subExpressions.add( index, element );
    }

    @Override
    public boolean addAll( Collection<? extends Expression> c ) {
        return this.subExpressions.addAll( c );
    }

    @Override
    public boolean addAll( int index, Collection<? extends Expression> c ) {
        return this.subExpressions.addAll( index, c );
    }

    @Override
    public void clear() {
        this.subExpressions.clear();
    }

    @Override
    public boolean contains( Object o ) {
        return this.subExpressions.contains( o );
    }

    @Override
    public boolean containsAll( Collection<?> c ) {
        return this.subExpressions.containsAll( c );
    }

    /**
     * Checks if the given object is the same as the current one
     * <p>
     * Equality is implemented iterating through the component of this object and check if every one is also equals.
     * <br/>
     * 
     * @param obj An Object object to be compared against the current instance.
     * @return <code>true</code> if the given object is equal to the current one, <code>false</code> otherwise
     */
    public boolean equals( CmplxExpression obj ) {
        if( this.size() != obj.size() ) {
            return false;
        }

        for( int i = this.size() - 1; i >= 0; i-- ) {
            if( !this.get( i ).equals( obj.get( i ) ) ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given object is the same as the current one
     * <p>
     * Equality is implemented iterating through the component of this object and check if every one is also equals.
     * </p>
     * <p>
     * This version of the method accept an object of type Object and then make use of the other implemented version of
     * this method.
     * </p>
     * 
     * @param obj An Object object to be compared against the current instance.
     * @return <code>true</code> if the given object is equal to the current one, <code>false</code> otherwise
     */
    @Override
    public boolean equals( Object obj ) {
        if( obj instanceof CmplxExpression ) {
            return this.equals( (CmplxExpression)obj );
        }

        return false;
    }

    /**
     * Gets the first element of the expression
     * 
     * @return An Expression at the beginning of the expression or <code>null</code> if the list is empty
     */
    public Expression firstElement() {
        if( this.subExpressions.size() == 0 ) {
            return null;
        }

        return this.subExpressions.get( 0 );
    }

    @Override
    public Expression get( int index ) {
        return this.subExpressions.get( index );
    }

    /**
     * Find the type of entry the object represents
     * <p>
     * This method is used to identify the type of the object stored in the entry. It must be overridden by teh derived
     * classes
     * </p>
     * 
     * @return An integer wich uniquely identify the entry type
     */
    @Override
    public int getEntryType() {
        return CmplxExpression.COMPOSITE_CODE;
    }

    @Override
    public int indexOf( Object o ) {
        return this.subExpressions.indexOf( o );
    }

    @Override
    public boolean isEmpty() {
        return this.subExpressions.isEmpty();
    }

    /**
     * Checks if an expression is minimizable
     * <p>
     * An expression is minimizable if any of it subexpression is minimizable. E.g. "[1, 2 * 3, 4]" is minimizable as
     * the middle element is, but "[1, 2, 3]" is not.<br/>
     * The check is performed looking for at least one executable operator, then if its operands are minimizable and,
     * last, recursively checking the subexpressions.
     * </p>
     * 
     * @return <code>true</code> if the expression is minimizable</code>
     */
    @Override
    public boolean isMinimizable() {
        // If there is only one element...
        if( this.size() == 1 ) {
            return this.firstElement().isMinimizable();
        }

        CmplxExpression operands = new CmplxExpression();
        for( Expression entry: this ) {
            // Check if the operator is executable (mean that has all the operands it needs)
            if( entry.getEntryType() == Operator.OPERATOR_CODE ) {

                // If the n. of operands does not match I skip this operator
                if( operands.size() < ((Operator)entry).getCurrentOperands() ) {
                    // I put a placeholder in behalf of the evaluated expression
                    operands = new CmplxExpression();
                    operands.push( null );
                    break;
                }

                boolean opsMinimizables = true;

                // I check if there is a minimizable operand
                for( int i = ((Operator)entry).getCurrentOperands(); i > 0; i-- ) {
                    Expression current = operands.pop();
                    if( current == null || !current.isMinimizable() ) {
                        opsMinimizables = false;
                    }
                }

                // If all the operands are minimizable so it is the operation they belong
                if( opsMinimizables ) {
                    return true;
                }

                // I put a placeholder in behalf of the evaluated expression
                operands.push( null );
            }
            else {
                // If the non-operator is a CmplxExpression I return true if it is itself minimizable
                if( entry.getEntryType() == CmplxExpression.COMPOSITE_CODE && entry.isMinimizable() ) {
                    return true;
                }

                // I memorise the Expression as an operand
                operands.push( entry );
            }
        }

        return false;
    }

    @Override
    public Iterator<Expression> iterator() {
        return this.subExpressions.iterator();
    }

    /**
     * Gets the last element of the expression
     * 
     * @return An Expression at the end of the expression or <code>null</code> if the list is empty
     */
    public Expression lastElement() {
        if( this.size() == 0 ) {
            return null;
        }

        return this.subExpressions.get( this.size() - 1 );
    }

    @Override
    public int lastIndexOf( Object o ) {
        return this.lastIndexOf( o );
    }

    @Override
    public ListIterator<Expression> listIterator() {
        return this.subExpressions.listIterator();
    }

    @Override
    public ListIterator<Expression> listIterator( int index ) {
        return this.subExpressions.listIterator( index );
    }

    /**
     * Gets the last element of the expression and remove it from the list
     * 
     * @return An Expression at the end of the expression or <code>null</code> if the list is empty
     */
    public Expression pop() {
        Expression temp = this.lastElement();
        this.remove( this.size() - 1 );
        return temp;
    }

    /**
     * Add an Expression at the end of the expression
     * 
     * @param e The Expression to add
     */
    public void push( Expression e ) {
        this.add( this.size(), e );
    }

    @Override
    public Expression remove( int index ) {
        return this.subExpressions.remove( index );
    }

    @Override
    public boolean remove( Object o ) {
        return this.subExpressions.remove( o );
    }

    @Override
    public boolean removeAll( Collection<?> c ) {
        return this.subExpressions.removeAll( c );
    }

    @Override
    public boolean retainAll( Collection<?> c ) {
        return this.subExpressions.retainAll( c );
    }

    @Override
    public Expression set( int index, Expression element ) {
        return this.subExpressions.set( index, element );
    }

    @Override
    public int size() {
        return this.subExpressions.size();
    }

    @Override
    public List<Expression> subList( int fromIndex, int toIndex ) {
        return this.subExpressions.subList( fromIndex, toIndex );
    }

    @Override
    public Object[] toArray() {
        return this.subExpressions.toArray();
    }

    @Override
    public <T> T[] toArray( T[] a ) {
        return this.subExpressions.toArray( a );
    }

    /**
     * Get a string representation of the entry
     * <p>
     * The string representation is commonly used to create output expressions
     * </p>
     * 
     * @return A string containing a representation of the object.
     */
    @Override
    public String toString() {
        String output = "";

        // Recursively calls the method toString() of the subentries
        for( Expression entry: this.subExpressions ) {
            output += entry.toString() + " ";
        }

        return output.replaceFirst( "\\s$", "" );
    }

}
