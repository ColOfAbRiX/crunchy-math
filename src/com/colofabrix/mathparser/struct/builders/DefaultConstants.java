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
package com.colofabrix.mathparser.struct.builders;

import java.util.Map;
import org.apfloat.Apfloat;
import com.colofabrix.mathparser.Memory;
import com.colofabrix.mathparser.expression.Operand;
import com.colofabrix.mathparser.lib.ApfConsts;
import com.colofabrix.mathparser.lib.ApfloatMore;
import com.colofabrix.mathparser.struct.Context;
import com.colofabrix.mathparser.struct.ExpressionException;
import com.colofabrix.mathparser.struct.MemorySetter;

public final class DefaultConstants extends Memory implements MemorySetter {

    /**
     * Constructor
     */
    public DefaultConstants() {
        // Mathematical constants
        this.addNew( "PI", ApfConsts.PI );              // pi-greek (pure number)
        this.addNew( "E", ApfConsts.E );                // e (pure number)

        // Physical constants
        Apfloat c = new Apfloat( 299792458, ApfConsts.STRING_PRECISION );
        this.addNew( "c", c );                              // Speed of light in vacuum (m/s)
        Apfloat G = new Apfloat( 6.67384E-11, 5 );
        this.addNew( "G", G );                              // Newtonian constant of gravitation (m^3/(kg·s^2))
        Apfloat h = new Apfloat( 6.62606957E-34, 8 );
        this.addNew( "h", h );                              // Planck constant (J·s)
        Apfloat hb = h.divide( ApfConsts.PI.multiply( ApfConsts.TWO ) );
        this.addNew( "ħ", hb );                              // Reduced Planck constant (J·s)
        Apfloat u0 = ApfConsts.PI.multiply( new Apfloat( 4 ) ).multiply( new Apfloat( 10E-7 ) );
        this.addNew( "µ0", u0 );                            // Vacuum permeability (N/A^2)
        Apfloat ε0 = ApfConsts.ONE.divide( u0.multiply( ApfloatMore.safePow( c, ApfConsts.TWO ) ).precision( ApfConsts.DIVIDE_PRECISION ) );
        this.addNew( "ε0", ε0 );                            // Vacuum permittivity (F/m)
        Apfloat Z0 = u0.multiply( c );
        this.addNew( "Z0", Z0 );                            // Characteristic impedance of vacuum (Ω)
        Apfloat ke = ApfConsts.ONE.divide( ε0.multiply( ApfConsts.PI.multiply( new Apfloat( 4 ) ) ) );
        this.addNew( "ke", ke );                            // Coulomb's constant (N·m^2/C^2)
        Apfloat e = new Apfloat( 1.602176565E10 - 19, 9 );
        this.addNew( "e", e );                              // Elementary charge (C)
        Apfloat KJ = ApfConsts.TWO.multiply( e ).divide( h );
        this.addNew( "KJ", KJ );                            // Josephson constant (Hz/V)
        Apfloat alph = ApfloatMore.safePow( e, ApfConsts.TWO ).multiply( c.multiply( u0 ) ).divide( ApfConsts.TWO.multiply( h ) );
        this.addNew( "α", alph );                              // Fine-structure constant
        Apfloat me = new Apfloat( 9.10938291E-31, 8 );
        this.addNew( "me", me );                            // Electron mass (Kg)
        Apfloat mp = new Apfloat( 1.672621777E-27, 9 );
        this.addNew( "mp", mp );                            // Proton mass (Kg)
        Apfloat uB = e.multiply( hb ).divide( me.multiply( ApfConsts.TWO ) );
        this.addNew( "µB", uB );                            // Bohr magneton (J/T)
        Apfloat uN = e.multiply( hb ).divide( mp.multiply( ApfConsts.TWO ) );
        this.addNew( "µN", uN );                            // Nuclear magneton (J/T)
    }

    /**
     * Dispose the constants that were initialised previously
     * 
     * @param context The context where the the memory to initialise resides
     */
    @Override
    public void disposeMemory( Context context ) {
        try {
            // Removes all the local constants to the main memory
            for( Map.Entry<String, Memory.MemoryCell> entry: this.getMemory().entrySet() ) {
                context.getMemory().setValue( entry.getKey(), null );
            }
        }
        catch( ExpressionException e ) {
        }
    }

    /**
     * Initialise the memory with constants
     * 
     * @param context The context where the the memory to initialise resides
     */
    @Override
    public void initMemory( Context context ) {
        try {
            // Adds all the local constants to the main memory
            for( Map.Entry<String, Memory.MemoryCell> entry: this.getMemory().entrySet() ) {
                context.getMemory().setValue( entry.getKey(), entry.getValue().getValue(), entry.getValue().isReadonly() );
            }
        }
        catch( ExpressionException e ) {
        }
    }

    private void addNew( String name, Apfloat value ) {
        try {
            this.setValue( name, new Operand( value ), true );
        }
        catch( ExpressionException e ) {
        }
    }

    @SuppressWarnings( "unused" )
    private void addNew( String name, double value ) {
        this.addNew( name, new Apfloat( value ) );
    }

    @SuppressWarnings( "unused" )
    private void addNew( String name, String value ) {
        this.addNew( name, new Apfloat( value ) );
    }
}
