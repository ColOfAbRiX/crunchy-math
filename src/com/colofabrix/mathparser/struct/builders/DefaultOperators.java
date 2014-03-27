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

import java.util.ArrayList;
import java.util.List;
import com.colofabrix.mathparser.expression.Operator;
import com.colofabrix.mathparser.operators.AbsOperator;
import com.colofabrix.mathparser.operators.ArcosOperator;
import com.colofabrix.mathparser.operators.ArsinOperator;
import com.colofabrix.mathparser.operators.ArtanOperator;
import com.colofabrix.mathparser.operators.CeilOperator;
import com.colofabrix.mathparser.operators.CosOperator;
import com.colofabrix.mathparser.operators.CoshOperator;
import com.colofabrix.mathparser.operators.DivideOperator;
import com.colofabrix.mathparser.operators.EvalOperator;
import com.colofabrix.mathparser.operators.ExpOperator;
import com.colofabrix.mathparser.operators.FactOperator;
import com.colofabrix.mathparser.operators.FloorOperator;
import com.colofabrix.mathparser.operators.IntegralOperator;
import com.colofabrix.mathparser.operators.LnOperator;
import com.colofabrix.mathparser.operators.LogOperator;
import com.colofabrix.mathparser.operators.MaxOperator;
import com.colofabrix.mathparser.operators.MinOperator;
import com.colofabrix.mathparser.operators.MinusOperator;
import com.colofabrix.mathparser.operators.MultiplyOperator;
import com.colofabrix.mathparser.operators.PowerOperator;
import com.colofabrix.mathparser.operators.RandomOperator;
import com.colofabrix.mathparser.operators.RootOperator;
import com.colofabrix.mathparser.operators.RoundOperator;
import com.colofabrix.mathparser.operators.SinOperator;
import com.colofabrix.mathparser.operators.SinhOperator;
import com.colofabrix.mathparser.operators.SumOperator;
import com.colofabrix.mathparser.operators.TanOperator;
import com.colofabrix.mathparser.operators.TanhOperator;
import com.colofabrix.mathparser.operators.special.AssignmentOperator;
import com.colofabrix.mathparser.operators.special.ClosingBracket;
import com.colofabrix.mathparser.operators.special.GetOperator;
import com.colofabrix.mathparser.operators.special.MemoryOperator;
import com.colofabrix.mathparser.operators.special.OpeningBracket;
import com.colofabrix.mathparser.operators.special.OperatorsOperator;
import com.colofabrix.mathparser.operators.special.SetOperator;
import com.colofabrix.mathparser.operators.special.VectorClosing;
import com.colofabrix.mathparser.operators.special.VectorOpening;
import com.colofabrix.mathparser.operators.special.VectorPush;
import com.colofabrix.mathparser.struct.Context;
import com.colofabrix.mathparser.struct.OperatorsSetter;

public class DefaultOperators implements OperatorsSetter {

    private final List<Operator> localOps = new ArrayList<Operator>();

    public DefaultOperators() {
        OpBuilder builder = new OpBuilder();

        // Algebraic operators
        this.localOps.add( builder.create( SumOperator.class ) );
        this.localOps.add( builder.create( MinusOperator.class ) );
        this.localOps.add( builder.create( MultiplyOperator.class ) );
        this.localOps.add( builder.create( DivideOperator.class ) );
        this.localOps.add( builder.create( PowerOperator.class ) );
        this.localOps.add( builder.create( RootOperator.class ) );
        this.localOps.add( builder.create( FactOperator.class ) );

        // Trigonometric operators
        this.localOps.add( builder.create( SinOperator.class ) );
        this.localOps.add( builder.create( CosOperator.class ) );
        this.localOps.add( builder.create( TanOperator.class ) );
        this.localOps.add( builder.create( ArcosOperator.class ) );
        this.localOps.add( builder.create( ArsinOperator.class ) );
        this.localOps.add( builder.create( ArtanOperator.class ) );
        this.localOps.add( builder.create( SinhOperator.class ) );
        this.localOps.add( builder.create( CoshOperator.class ) );
        this.localOps.add( builder.create( TanhOperator.class ) );

        // Exponentiation operators
        this.localOps.add( builder.create( ExpOperator.class ) );
        this.localOps.add( builder.create( LogOperator.class ) );
        this.localOps.add( builder.create( LnOperator.class ) );

        // Rounding operators
        this.localOps.add( builder.create( CeilOperator.class ) );
        this.localOps.add( builder.create( FloorOperator.class ) );
        this.localOps.add( builder.create( RoundOperator.class ) );
        this.localOps.add( builder.create( MinOperator.class ) );
        this.localOps.add( builder.create( MaxOperator.class ) );

        // Structural operators
        this.localOps.add( builder.create( AssignmentOperator.class ) );
        this.localOps.add( builder.create( OpeningBracket.class ) );
        this.localOps.add( builder.create( ClosingBracket.class ) );
        this.localOps.add( builder.create( VectorOpening.class ) );
        this.localOps.add( builder.create( VectorPush.class ) );
        this.localOps.add( builder.create( VectorClosing.class ) );

        // Mixed operators
        this.localOps.add( builder.create( RandomOperator.class ) );
        this.localOps.add( builder.create( AbsOperator.class ) );
        this.localOps.add( builder.create( IntegralOperator.class ) );
        this.localOps.add( builder.create( EvalOperator.class ) );

        // Management operators
        this.localOps.add( builder.create( MemoryOperator.class ) );
        this.localOps.add( builder.create( OperatorsOperator.class ) );
        this.localOps.add( builder.create( SetOperator.class ) );
        this.localOps.add( builder.create( GetOperator.class ) );
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.colofabrix.mathparser.org.OperatorsSetter#disposeOperators(com.colofabrix.mathparser.org.MathContext)
     */
    @Override
    public void disposeOperators( Context context ) {
        for( Operator op: this.localOps ) {
            context.getOperators().remove( op );
            op.setContext( null );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.colofabrix.mathparser.org.OperatorsSetter#initOperators(com.colofabrix.mathparser.org.MathContext)
     */
    @Override
    public void initOperators( Context context ) {
        for( Operator op: this.localOps ) {
            op.setContext( context );
            context.getOperators().add( op );
        }
    }
}
