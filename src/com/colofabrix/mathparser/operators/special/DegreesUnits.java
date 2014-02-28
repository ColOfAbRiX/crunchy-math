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
package com.colofabrix.mathparser.operators.special;

/**
 * The available unit of measurement to express degrees
 * 
 * @author Fabrizio Colonna
 */
public enum DegreesUnits {
	/**
	 * Degrees {@link <a href="http://en.wikipedia.org/wiki/Degree_(angle)">Degrees</a>}
	 */
	DEGREES(1),
	
	/**
	 * Radians {@link <a href="http://en.wikipedia.org/wiki/Radian">Radians</a>}
	 */
	RADIANS(2),
	
	/**
	 * Gradians {@link <a href="http://en.wikipedia.org/wiki/Gradian">Gradians</a>}
	 */
	GRADIANS(3);

	private int value;
	
	DegreesUnits( int value ) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public static DegreesUnits fromValue( int value ) {
		switch( value ) {
		case 1:
			return DEGREES;
		default:
		case 2:
			return RADIANS;
		case 3:
			return GRADIANS;
		}
	}
}
