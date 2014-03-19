package com.colofabrix.mathparser.org;

import org.apfloat.Apfloat;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.lib.ApfloatConsts;
import com.colofabrix.mathparser.lib.ApfloatMore;

public class CommonConstants implements MathConstant {
    
    private Memory memory;
    
    /* (non-Javadoc)
     * @see com.colofabrix.mathparser.org.MathConstant#init(com.colofabrix.mathparser.Memory)
     */
    @Override
    public void init( Memory memory ) {
        this.memory = memory;
        
        // Mathematical constants
        this.addNew( "PI", ApfloatConsts.PI );              // pi-greek (pure number)
        this.addNew( "E", ApfloatConsts.E );                // e (pure number)
        
        // Physical constants
        Apfloat c = new Apfloat( 299792458, ApfloatConsts.STRING_PRECISION );
        this.addNew( "c", c );                              // Speed of light in vacuum (m/s)
        Apfloat G = new Apfloat( 6.67384E-11, 5 );
        this.addNew( "G", G );                              // Newtonian constant of gravitation (m^3/(kg·s^2))
        Apfloat h = new Apfloat( 6.62606957E-34, 8 );
        this.addNew( "h", h );                              // Planck constant (J·s)
        Apfloat hb = h.divide( ApfloatConsts.PI.multiply( ApfloatConsts.TWO ) );
        this.addNew( "ħ", hb );                              // Reduced Planck constant (J·s)
        Apfloat u0 = ApfloatConsts.PI.multiply( new Apfloat( 4 ) ).multiply( new Apfloat( 10E-7 ) );
        this.addNew( "µ0", u0 );                            // Vacuum permeability (N/A^2)
        Apfloat ε0 = ApfloatConsts.ONE.divide( u0.multiply( ApfloatMore.safePow( c, ApfloatConsts.TWO ) ).precision(ApfloatConsts.DIVIDE_PRECISION) );
        this.addNew( "ε0", ε0 );                            // Vacuum permittivity (F/m)
        Apfloat Z0 = u0.multiply( c );
        this.addNew( "Z0", Z0 );                            // Characteristic impedance of vacuum (Ω)
        Apfloat ke = ApfloatConsts.ONE.divide( ε0.multiply( ApfloatConsts.PI.multiply( new Apfloat( 4 ) ) ) );
        this.addNew( "ke", ke );                            // Coulomb's constant (N·m^2/C^2)
        Apfloat e = new Apfloat( 1.602176565E10-19, 9 );
        this.addNew( "e", e );                              // Elementary charge (C)
        Apfloat KJ = ApfloatConsts.TWO.multiply( e ).divide( h );
        this.addNew( "KJ", KJ );                            // Josephson constant (Hz/V)
        Apfloat alph = ApfloatMore.safePow( e, ApfloatConsts.TWO ).multiply( c.multiply( u0 ) ).divide( ApfloatConsts.TWO.multiply( h ) ); 
        this.addNew( "α", alph );                              // Fine-structure constant
        Apfloat me = new Apfloat( 9.10938291E-31, 8 );
        this.addNew( "me", me );                            // Electron mass (Kg)
        Apfloat mp = new Apfloat( 1.672621777E-27, 9 );
        this.addNew( "mp", mp );                            // Proton mass (Kg)
        Apfloat uB = e.multiply( hb ).divide( me.multiply( ApfloatConsts.TWO ) );
        this.addNew( "µB", uB );                            // Bohr magneton (J/T)
        Apfloat uN = e.multiply( hb ).divide( mp.multiply( ApfloatConsts.TWO ) );
        this.addNew( "µN", uN );                            // Nuclear magneton (J/T)
    }
    
    @SuppressWarnings( "unused" )
    private void addNew( String name, String value ) {
        this.addNew( name, new Apfloat( value ) );
    }
    
    @SuppressWarnings( "unused" )
    private void addNew( String name, double value ) {
        this.addNew( name, new Apfloat( value ) );
    }
    
    private void addNew( String name, Apfloat value ) {
        try {
            memory.setValue( name, new Operand(value), true );
        }
        catch( ExpressionException e ) {
        }
    }
}
