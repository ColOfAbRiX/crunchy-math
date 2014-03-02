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
package com.colofabrix.mathparser.lib;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public final class ApfloatConsts {
	// Precision settings
	public static final int DIVIDE_PREC = 50;
	public static final int PI_PREC = 50;
	public static final int SQRT_PREC = 50;
	public static final int EXP_PREC = 50;
	public static final int POW_PRECISION = 50;
	public static final int STRING_PRECISION = 50;
	
	public static final int ERF_TAYLOR_MEMBER = 200;
	public static final int ERF_INVERSE_TAYLOR_MEMBER = 40;
	
	public static final int PHI_PREC = 40;
	public static final int CONST_PREC = 200;
	
	// Constants
	public static final Apfloat ZERO = new Apfloat(0);
	public static final Apfloat HALF = new Apfloat("0.5", Apfloat.INFINITE);
	public static final Apfloat ONE = new Apfloat(1);
	public static final Apfloat TWO = new Apfloat(2);

	public static final Apfloat PI = ApfloatMath.pi(PI_PREC);
	public static final Apfloat E = ApfloatMath.exp(ONE.precision(EXP_PREC));
}
