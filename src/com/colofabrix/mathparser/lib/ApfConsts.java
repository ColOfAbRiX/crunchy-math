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

import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public final class ApfConsts {
    public static final class Angular {
        public static final Apfloat DEG_180 = new Apfloat( 180 );
        public static final Apfloat DEG_30 = new Apfloat( 30 );
        public static final Apfloat DEG_360 = new Apfloat( 360 );
        public static final Apfloat DEG_45 = new Apfloat( 45 );
        public static final Apfloat DEG_60 = new Apfloat( 60 );
        public static final Apfloat DEG_90 = new Apfloat( 90 );

        public static final Apfloat GRAD_100 = new Apfloat( 100 );
        public static final Apfloat GRAD_200 = new Apfloat( 200 );
        public static final Apfloat GRAD_33 = new Apfloat( 100 ).divide( new Apfloat( 3 ).precision( ApfConsts.DIVIDE_PRECISION ) );
        public static final Apfloat GRAD_400 = new Apfloat( 400 );
        public static final Apfloat GRAD_50 = new Apfloat( 50 );
        public static final Apfloat GRAD_66 = new Apfloat( 200 ).divide( new Apfloat( 3 ).precision( ApfConsts.DIVIDE_PRECISION ) );

        // Angular constants
        public static final Apfloat RAD_2PI = ApfConsts.TWO.multiply( ApfConsts.PI );
        public static final Apfloat RAD_PI = ApfConsts.PI;
        public static final Apfloat RAD_PI2 = ApfConsts.PI.divide( ApfConsts.TWO.precision( ApfConsts.DIVIDE_PRECISION ) );
        public static final Apfloat RAD_PI3 = ApfConsts.PI.divide( new Apfloat( 3 ).precision( ApfConsts.DIVIDE_PRECISION ) );
        public static final Apfloat RAD_PI4 = ApfConsts.PI.divide( new Apfloat( 4 ).precision( ApfConsts.DIVIDE_PRECISION ) );
        public static final Apfloat RAD_PI6 = ApfConsts.PI.divide( new Apfloat( 6 ).precision( ApfConsts.DIVIDE_PRECISION ) );
    }

    // Precision settings
    public static final int SQRT_PRECISION = 100;
    public static final int STRING_PRECISION = 100;
    public static final int CONST_PRECISION = 200;
    public static final int DIVIDE_PRECISION = 100;
    public static final int PI_PRECISION = 100;
    public static final int POW_PRECISION = 100;
    public static final int EXP_PRECISION = 100;
    public static final int PHI_PREC = 40;

    // Constants
    public static final Apfloat ZERO = new Apfloat( 0 );
    public static final Apfloat HALF = new Apfloat( "0.5", Apfloat.INFINITE );
    public static final Apfloat ONE = new Apfloat( 1 );
    public static final Apfloat TWO = new Apfloat( 2 );
    public static final Apcomplex I = new Apcomplex( ApfConsts.ZERO, ApfConsts.ONE );

    // Irrational constants
    public static final Apfloat E = ApfloatMath.exp( ApfConsts.ONE.precision( ApfConsts.EXP_PRECISION ) );
    public static final int ERF_INVERSE_TAYLOR_MEMBER = 40;
    public static final int ERF_TAYLOR_MEMBER = 200;
    public static final Apfloat PI = ApfloatMath.pi( ApfConsts.PI_PRECISION );
}
