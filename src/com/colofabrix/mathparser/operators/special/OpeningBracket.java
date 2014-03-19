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

import com.colofabrix.mathparser.expression.GroupingOperator;
import com.colofabrix.mathparser.struct.ConfigException;

/**
 * An opening bracket operator
 * 
 * <p>
 * Brackets are used to give priority to some section of expressions.<br/>
 * A opening bracket serves to mark the beginning of a subexpression that will be later processed as a differen entity.
 * The push is performed by the parent class {@link GroupingOperator}, this class is only used to define a name and a
 * priorit
 * 
 * @author Fabrizio Colonna
 */
public class OpeningBracket extends GroupingOperator {

    public OpeningBracket() throws ConfigException {
        super();
        this.setBaseName( "(" );
        this.setPriority( (short)0 );
    }

    @Override
    public boolean isOpening() {
        return true;
    }
}
