/**
 * Original file: http://users.iit.uni-miskolc.hu/~piller/Distributions/ApfloatStat.java
 * 
 * Unfortunately I was unable to contact the author, as there is not a direct email address, nor a
 * person name and the website is in a language I do not understand. So I was unable to ask for
 * permission to include this file in Crunchy Math. If the author will ever read this, please
 * contact me and let me know if you are happy.
 * 
 * Fabrizio <colofabrix@gmail.com>
 */
package com.colofabrix.mathparser.lib;

import java.util.ArrayList;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

public class ApfloatStat {
    // Lanczos constants
    private static final int LANCZOS_G = 7;
    private static final double[] LANCZOS_P = { 0.99999999999980993, 676.5203681218851, -1259.1392167224028, 771.32342877765313, -176.61502916214059, 12.507343278686905, -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7 };

    // Spouge constants
    private static final int SPOUGE_A = 5;

    public static Apfloat erf( Apfloat z ) {
        // Numerator
        Apfloat numerator = z;
        Apfloat minus_z_square = z.multiply( z ).negate();

        // Denominator
        Apfloat denominator = ApfConsts.ONE;
        Apfloat n_fact = ApfConsts.ONE;
        int m;

        // Calculate sum
        Apfloat sum = new Apfloat( 0 );
        for( int n = 1; n < ApfConsts.ERF_TAYLOR_MEMBER; n++ ) {
            sum = sum.add( numerator.divide( denominator ) );

            numerator = numerator.multiply( minus_z_square );
            n_fact = n_fact.multiply( new Apfloat( n ) );
            m = 2 * n + 1;
            denominator = n_fact.multiply( new Apfloat( m ) );
        }

        sum = sum.multiply( ApfConsts.TWO );
        sum = sum.divide( ApfloatMath.sqrt( ApfConsts.PI ) );

        return sum;
    }

    public static Apfloat erf_inverse( Apfloat z ) {
        // Calc denominator of coefficients -> coeff_div[]
        ArrayList<Apfloat> coeff_div = new ArrayList<Apfloat>();
        Apfloat m = ApfConsts.ZERO;
        for( int i = 0; i < ApfConsts.ERF_INVERSE_TAYLOR_MEMBER; i++ ) {
            coeff_div.add( m.add( ApfConsts.ONE ).multiply( m.add( m ).add( ApfConsts.ONE ) ) );
            m = m.add( ApfConsts.ONE );
        }

        // Calculate coefficients -> coeff[]
        ArrayList<Apfloat> coeff = new ArrayList<Apfloat>();
        int backward_index;
        Apfloat counter;
        Apfloat sum;
        for( int i = 0; i <= ApfConsts.ERF_INVERSE_TAYLOR_MEMBER; i++ ) {
            if( i < 2 ) {
                coeff.add( ApfConsts.ONE );
            }
            else {
                backward_index = i - 1;
                sum = ApfConsts.ZERO;
                for( int j = 0; j < i; j++ ) {
                    counter = coeff.get( j ).multiply( coeff.get( backward_index ) );
                    sum = sum.add( counter.precision( ApfConsts.DIVIDE_PRECISION ).divide( coeff_div.get( j ) ) );
                    backward_index--;
                }
                coeff.add( sum );
            }
        }

        // Calculate erf_inverse(z) -> sum
        sum = ApfConsts.ZERO;

        Apfloat p = ApfloatMath.sqrt( ApfConsts.PI ).divide( ApfConsts.TWO );
        p = p.multiply( z );

        Apfloat p_square = p.multiply( p );
        Apfloat divider = ApfConsts.ONE;

        for( int i = 0; i < ApfConsts.ERF_INVERSE_TAYLOR_MEMBER; i++ ) {
            sum = sum.add( coeff.get( i ).multiply( p ).precision( ApfConsts.DIVIDE_PRECISION ).divide( divider ) );
            p = p.multiply( p_square );
            divider = divider.add( ApfConsts.TWO );
        }

        return sum;
    }

    // Gamma function with Lanczos approximation
    // http://en.wikipedia.org/wiki/Lanczos_approximation
    public static Apfloat gamma_Lanczos( Apfloat z ) {
        if( z.compareTo( ApfConsts.HALF ) == -1 ) {
            Apfloat divider = ApfloatMath.sin( ApfConsts.PI.multiply( z ) ).multiply( ApfConsts.ONE.subtract( z ) );
            return ApfConsts.PI.divide( divider );
        }
        else {
            z = z.subtract( ApfConsts.ONE );
            Apfloat x = new Apfloat( ApfloatStat.LANCZOS_P[0] );
            for( int i = 1; i < ApfloatStat.LANCZOS_G + 2; i++ ) {
                x = x.add( new Apfloat( ApfloatStat.LANCZOS_P[i] ).divide( z.add( new Apfloat( i ) ) ) );
            }
            Apfloat t = z.add( new Apfloat( ApfloatStat.LANCZOS_G ) ).add( ApfConsts.HALF );
            Apfloat a = ApfloatMath.sqrt( ApfConsts.TWO.multiply( ApfConsts.PI ) );
            Apfloat b = ApfloatMath.pow( t, z.add( ApfConsts.HALF ) );
            Apfloat c = ApfloatMath.exp( t.negate() ).multiply( x );
            return a.multiply( b ).multiply( c );
        }
    }

    // Gamma function with Spouge approximation
    // http://en.wikipedia.org/wiki/Spouge%27s_approximation
    // http://en.literateprograms.org/Gamma_function_with_Spouge%27s_formula_%28Mathematica%29
    public static Apfloat gamma_Spouge( Apfloat z ) {
        Apfloat k_power = ApfConsts.HALF;
        Apfloat a_minus_k = new Apfloat( ApfloatStat.SPOUGE_A - 1 );
        Apfloat divider_fact = ApfConsts.ONE;
        Apfloat c_k;

        Apfloat sum = ApfConsts.ZERO;

        for( int k = 1; k < ApfloatStat.SPOUGE_A; k++ ) {
            // Calculate c_k
            c_k = ApfloatMath.pow( a_minus_k.precision( ApfConsts.POW_PRECISION ), k_power.precision( ApfConsts.POW_PRECISION ) ).multiply( ApfloatMath.exp( a_minus_k.precision( ApfConsts.EXP_PRECISION ) ) );
            c_k = c_k.divide( divider_fact );

            sum = sum.add( c_k.divide( z.add( new Apfloat( k ) ) ) );

            c_k = c_k.negate();
            k_power = k_power.add( ApfConsts.ONE );
            a_minus_k = a_minus_k.subtract( ApfConsts.ONE );
            divider_fact = divider_fact.multiply( new Apfloat( k, ApfConsts.DIVIDE_PRECISION ) );
        }

        sum = sum.add( ApfloatMath.sqrt( ApfConsts.TWO.multiply( ApfConsts.PI ) ) );
        // System.out.println(">> "+ApfloatMath.sqrt(ApfConsts.TWO.multiply(ApfConsts.PI)));

        Apfloat alpha = ApfloatMath.pow( z.add( new Apfloat( ApfloatStat.SPOUGE_A ) ).precision( ApfConsts.POW_PRECISION ), z.add( ApfConsts.HALF ).precision( ApfConsts.POW_PRECISION ) );
        // System.err.println("alpha = "+alpha);

        Apfloat beta = ApfloatMath.exp( z.add( new Apfloat( ApfloatStat.SPOUGE_A ) ).negate().precision( ApfConsts.EXP_PRECISION ) );
        // System.err.println("beta = "+beta);

        return alpha.multiply( beta ).multiply( sum );
    }

    // Gamma function with Stirling approximation
    // http://en.wikipedia.org/wiki/Stirling%27s_approximation
    public static Apfloat gamma_Stirling( Apfloat z ) {
        Apfloat a = ApfloatMath.sqrt( ApfConsts.TWO.multiply( ApfConsts.PI ).divide( z ) );
        Apfloat b = ApfloatMath.sinh( ApfConsts.ONE.precision( ApfConsts.DIVIDE_PRECISION ).divide( z ) );
        Apfloat c = b.multiply( z );
        Apfloat d = ApfConsts.ONE.precision( ApfConsts.DIVIDE_PRECISION ).divide( ApfloatMath.pow( z, 6 ) ).divide( new Apfloat( 810 ) );
        Apfloat e = z.divide( ApfConsts.E );
        Apfloat f = ApfloatMath.sqrt( c.add( d ) );
        Apfloat g = ApfloatMath.pow( e.multiply( f ), z ).multiply( a );

        return g;
    }

    // Normal probability cumulative distribution function
    public static Apfloat normalCDF( Apfloat z ) {
        Apfloat x = z.divide( ApfloatMath.sqrt( new Apfloat( 2, 1000 ) ) );
        x = ApfloatStat.erf( x );
        x = x.add( ApfConsts.ONE );
        x = x.divide( ApfConsts.TWO );
        return x;
    }

    public static String normalCDF( String x ) {
        return ApfloatStat.normalCDF( new Apfloat( x, ApfConsts.PHI_PREC ) ).toString();
    }

    // Normal probability cumulative distribution function inverse
    public static Apfloat normalCDF_inverse( Apfloat y ) {
        return ApfloatMath.sqrt( ApfConsts.TWO.precision( ApfConsts.SQRT_PRECISION ) ).multiply( ApfloatStat.erf_inverse( y.add( y ).subtract( ApfConsts.ONE ) ) );
    }

    public static String normalCDF_inverse( String x ) {
        return ApfloatStat.normalCDF_inverse( new Apfloat( x, ApfConsts.PHI_PREC ) ).toString();
    }

    // Normal probability density function
    public static Apfloat normalPDF( Apfloat x ) {
        Apfloat y = ApfConsts.ONE.precision( ApfConsts.DIVIDE_PRECISION ).divide( ApfloatMath.sqrt( ApfConsts.TWO.multiply( ApfConsts.PI ) ) );
        y = y.multiply( ApfloatMath.exp( x.multiply( x ).divide( ApfConsts.TWO ).negate() ) );
        return y;
    }

    public static double normalPDF( double x ) {
        return 1 / Math.sqrt( 2 * Math.PI ) * Math.exp( -(x * x) / 2 );
    }

    // Phi function
    public static double Phi( double x ) {

        // Get sign of x value
        boolean isNegative = false;
        if( x < 0 ) {
            isNegative = true;
            x = -x;
        }

        // Get index of interval
        int intervalIndex = 0;
        if( x > 1.5 )
            intervalIndex++;
        if( x > 3 )
            intervalIndex++;
        if( x > 6 ) {
            if( isNegative )
                return 0;
            return 1;
        }

        // Coefficients of approximation polynoms
        double[][] coeff = { { 0.4999999853197, 0.5300774546729, -0.1621966195471 }, { 0.3989437251038, 0.2799241265723, 1.8137844596010 }, { -0.0000232473822, 0.2005701987176, -1.2430841874817 }, { -0.0663495262607, -0.2504062323459, 0.4883401215203 }, { -0.0004071645564, 0.0949343858651, -0.1201986229749 }, { 0.0105643510048, -0.0131657278224, 0.0189705569006 }, { -0.0003504976933, -0.0009270280158, -0.0018738388405 }, { -0.0012947802876, 0.0004671302299, 0.0001058586660 }, { 0.0002619054865, -0.0000383458376, -0.0000026175074 } };

        // Evaulate polynom
        double px = coeff[0][intervalIndex];
        for( int i = 1; i <= 8; i++ ) {
            px += coeff[i][intervalIndex] * Math.pow( x, i );
        }

        // Return
        if( isNegative )
            return 1 - px;
        return px;
    }

    // Normal cumulative distribution function
    public static double Phi_inverse( double x ) {
        // Check x value
        if( x < 0 || x > 1 ) {
            System.err.println( "Incorrect x value ! " + x );
            return 0;
        }

        double ui = x;
        if( ui >= 0.5 )
            ui = 1 - ui;

        double epsilon = 2 * 1e-15;
        if( ui < epsilon )
            ui = epsilon;

        ui = Math.sqrt( -2 * Math.log( ui ) ) - Math.sqrt( Math.log( 4 ) );

        // Get index of interval
        int intervalIndex = 0;
        if( ui >= 0.01 )
            intervalIndex++;

        // szi constans
        double[][] sziConst = { { 0.2385099062881218351e-3, 0.1389671822546715525e-4 }, { 0.7748014532123519149e-2, 0.9933095513250211212e-3 }, { 0.9433047102597236601e-1, 0.2132223881469308687e-1 }, { 0.5906175347671242813e0, 0.1971184884114817024e0 }, { 0.2052429201482605360e1, 0.9208235553699620741e0 }, { 0.3926527220876257871e1, 0.2302486886454418763e1 }, { 0.3827787912267809326e1, 0.2934913383940946604e1 }, { 0.1475664626635605795e1, 0.1475663066897793476e1 }, { 0.0, 0.2236640681757362082e-6 } };

        // ni constans
        double[][] niConst = { { 0.2384667219100680462e-3, 0.1389640654034188922e-4 }, { 0.7472090148248391360e-2, 0.9770522217813339426e-3 }, { 0.8675117268832776800e-1, 0.2025571989491669521e-1 }, { 0.5155497927835685221e0, 0.1775558927085441912e0 }, { 0.1685386828574926342e1, 0.7785719242838022205e0 }, { 0.3019304632408607270e1, 0.1819506588454068626e1 }, { 0.2757985744718406918e1, 0.2152916059924272000e1 }, { 1, 1.0 } };

        // Calculate szi
        double szi = sziConst[0][intervalIndex];
        for( int i = 1; i <= 8; i++ ) {
            szi *= ui;
            szi += sziConst[i][intervalIndex];
        }

        // Calculate ni
        double ni = niConst[0][intervalIndex];
        for( int i = 1; i <= 7; i++ ) {
            ni *= ui;
            ni += niConst[i][intervalIndex];
        }

        if( x < 0.5 )
            szi = -szi;

        return szi / ni;
    }

    // Normal cumulative distribution function
    public static Apfloat Phi_inverse_ap( Apfloat x ) {
        // Check x value
        if( x.compareTo( ApfConsts.ZERO ) == -1 || x.compareTo( ApfConsts.ONE ) == 1 ) {
            System.err.println( "Incorrect x value ! " + x );
            return null;
        }

        Apfloat ui = x;
        if( ui.compareTo( ApfConsts.HALF ) != -1 )
            ui = ApfConsts.ONE.subtract( ui );

        /*
         * !!!
         * double epsilon = 2 * 1e-15;
         * if(ui < epsilon) ui = epsilon;
         */

        // ui = Math.sqrt(-2 * Math.log(ui)) - Math.sqrt(Math.log(4));
        ui = ApfloatMath.sqrt( ApfConsts.TWO.multiply( ApfloatMath.log( ui.precision( ApfConsts.EXP_PRECISION ) ) ).negate() );
        ui = ui.subtract( ApfloatMath.sqrt( ApfloatMath.log( new Apfloat( 4 ).precision( ApfConsts.EXP_PRECISION ) ) ) );

        // Get index of interval
        int intervalIndex = 0;
        // if(ui >= 0.01) intervalIndex++;
        if( ui.compareTo( new Apfloat( 0.01 ) ) != -1 )
            intervalIndex++;

        // szi constans
        String[][] sziConst = { { "0.2385099062881218351e-3", "0.1389671822546715525e-4" }, { "0.7748014532123519149e-2", "0.9933095513250211212e-3" }, { "0.9433047102597236601e-1", "0.2132223881469308687e-1" }, { "0.5906175347671242813e0", "0.1971184884114817024e0" }, { "0.2052429201482605360e1", "0.9208235553699620741e0" }, { "0.3926527220876257871e1", "0.2302486886454418763e1" }, { "0.3827787912267809326e1", "0.2934913383940946604e1" }, { "0.1475664626635605795e1", "0.1475663066897793476e1" }, { "0.0", "0.2236640681757362082e-6" } };

        // ni constans
        String[][] niConst = { { "0.2384667219100680462e-3", "0.1389640654034188922e-4" }, { "0.7472090148248391360e-2", "0.9770522217813339426e-3" }, { "0.8675117268832776800e-1", "0.2025571989491669521e-1" }, { "0.5155497927835685221e0", "0.1775558927085441912e0" }, { "0.1685386828574926342e1", "0.7785719242838022205e0" }, { "0.3019304632408607270e1", "0.1819506588454068626e1" }, { "0.2757985744718406918e1", "0.2152916059924272000e1" }, { "1", "1.0" } };

        // Calculate szi
        Apfloat szi = new Apfloat( sziConst[0][intervalIndex], ApfConsts.CONST_PRECISION );
        for( int i = 1; i <= 8; i++ ) {
            szi = szi.multiply( ui );
            szi = szi.add( new Apfloat( sziConst[i][intervalIndex], ApfConsts.CONST_PRECISION ) );
        }

        // Calculate ni
        Apfloat ni = new Apfloat( niConst[0][intervalIndex], ApfConsts.CONST_PRECISION );
        for( int i = 1; i <= 7; i++ ) {
            ni = ni.multiply( ui );
            ni = ni.add( new Apfloat( niConst[i][intervalIndex], ApfConsts.CONST_PRECISION ) );
        }

        // if(x < 0.5) szi = -szi;
        if( x.compareTo( ApfConsts.HALF ) == -1 )
            szi = szi.negate();

        return szi.divide( ni );
    }

    public static String Phi_inverse_ap( String x ) {
        return ApfloatStat.Phi_inverse_ap( new Apfloat( x, ApfConsts.PHI_PREC ) ).toString();
    }
}
