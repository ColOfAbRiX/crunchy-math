Crunchy Math - Mathematical Expression Parser
===
Crunchy Math executes arbitrary inline mathematical expressions, it's a calculator!

The projects has two targets: the main one is to learn something about programming and the second one is to build a working calculator that can be embedded in other projects. It was not born, in any way, to replace other libraries that may be better.

Features
---
Its main features are:
 - It can be easily integrated in other projects as a library
 - Basic (+, -, *, /) and advanced (Pow, Sqrt, Log, ...) operators
 - Trigonometric functions and angle units converions
 - Vector values (lists)
 - Custom functions (Rnd, Round, user-defined, ...)
 - Built-in constants (mathematica, physical, ...)
 - Can work with expression and undefined variables
   - Expression minimisation
 - Indirect Eval operator (can execute it's own syntax)
 - Stack memory to access arguments and RAM to access variables
 - Easy extensible model for new operators

Its architecture it's very flexible, providing a simple memory model to store data and can be easily extended with custom functions.

Refer to the documentation for more info: https://github.com/ColOfAbRiX/crunchy-math/tree/master/doc

Learning
---
The primary objective of this project is for me to learn or to become more experienced in few topics of programming. Such part focuses on:
 - Learn how to build a mathematical expression executor
   - infix and postfix notations expressions
   - no formal grammar to define the syntax
   - simple execution VM with an execution unit and a memory
 - Getting introduced to Unit Testing and to JUnti (never used it before)
 - Apply design patterns and OOD considerations to refine my knowledge end experience
 - Deepen the knowledge of the Java Language as before this I did only few small programs
 - Learn some math libraries from Apache Common and Apfloat
 - Getting exposed to numerical analysis to solve some problems
 - Learn a bit about Open Source Licenses

Sample code
---
```
import java.io.*;
import com.colofabrix.mathparser.expression.*;
import com.colofabrix.mathparser.struct.*;

public class Main {
    public static void main( String[] args ) {
        // I first create the parser
        MathParser mp = new MathParser();

        // In this loop the user is requested for an input, the input processed and the result written
        while( true ) {
            try {
                // User input
                System.out.print( "Type the expression you want to evaluate: " );
                BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
                String input = in.readLine();

                // An empty line stops the execution
                if( input.isEmpty() ) {
                    break;
                }

                // The execution is done in two steps to show how it works, but can be done in one
                /// First it converts the input string in postfix notation, ...
                Expression postfix = mp.toPostfix( input );
                // ... then it executes the expression
                Expression result = mp.executePostfix( postfix );

                // Display the infix equivalent
                System.out.println( "    Convertex expression: " + postfix.toString() );
                
                if( result.getEntryType() == Operand.OPERAND_CODE ) {
                    // If the result is a pure number I'll show it
                    System.out.println( "    The result is: " + Operand.extractNumber( result ) );
                }
                else {
                    // Otherwise I'll show a minimized expression
                    System.out.println( "    The result is: " + mp.minimise( result ) );
                }

                System.out.println();
            }
            catch( ExpressionException | ConfigException | IOException e ) {
                System.out.println( "Exception during the evaluation: " + e.getMessage() );
            }
        }
    }
}
```
### Sample Output

Some sample output of the above code

```
Type the expression you want to evaluate: 2 + 2
    Convertex expression: 2 2 +
    The result is: 4

Type the expression you want to evaluate: 2 * Cos(0)
    Convertex expression: 2 0 #Cos *
    The result is: 2

Type the expression you want to evaluate: (2.123 * 7.321) - Cos(PI)
    Convertex expression: 2.123 7.321 * PI #Cos -
    The result is: 1.6542483e1

Type the expression you want to evaluate: Floor(2.123 * 7.321) - Cos(PI)
    Convertex expression: 2.123 7.321 * #Floor PI #Cos -
    The result is: 1.6e1
    
Type the expression you want to evaluate: 2 * Sin(Cos(3))
    Convertex expression: 2 3 #Cos #Sin *
    The result is: -1.6720437230754610681543568610754408326405545925851557145289769825...
    
Type the expression you want to evaluate: e
    Convertex expression: e
    The result is: 1.60217656e10
```

### Notes & Warnings

As the main purpose of this code was to learn, it still contains bugs and undelivered features and work in progress. Please, don't take this as a commercial product available for inclusion in other projects. Nonetheless if it is appreciated I will continue my work.

### Credits
Fabrizio Colonna - <colofabrix@tin.it>
