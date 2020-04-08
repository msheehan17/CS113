package edu.miracosta.cs113;

/**
 * Term.java - The concrete class for creating terms with coefficients and exponents.
 *
 * Instance Variables:
 *
 *  - coefficient (int): The coefficient of the term.
 *  - exponent (int): The exponent of the term (the power the term is being raised to).
 *
 * Methods:
 *
 *  - Constructors:
 *      - Two full constructors, one which takes arguments for instance variables individually (as integers), and one
 *        that takes a String argument (which it uses to find the instance variables).
 *      - One no-argument constructor that sets all instance variables equal to 1.
 *      - One copy constructor.
 *  - Accessor / Mutator methods for all instance variables.
 *  - setAll(int, int): A Mutator method that sets all instance variables at once.
 *  - toString: Returns a String representation of the term.
 *  - compareTo(Object): Returns -1 if the calling term's exponent is smaller that the term argument's, 0 if they are
 *    the same, and 1 if the calling terms's exponent is larger.
 *  - equals(Object): Compares two terms to determine if they share the same instance variables.
 *  - clone(Object): Returns a clone of the argument term.
 *  - determineCoefficient(String): From the String argument determines the term's coefficient.
 *  - determineExponent(String): From the String argument determines the term's exponent.
 *
 * @author Matthew Sheehan
 * @version 1.0
 *
 */
public class Term implements Cloneable, Comparable {

    private int coefficient; // The coefficient of the term.
    private int exponent;    // The exponent of the term (the power the term is being raised to).

    /**
     * Full constructor.
     *
     * @param coefficient The coefficient of the term.
     * @param exponent The term's exponent.
     */
    Term ( int coefficient, int exponent ) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    /**
     * Full constructor that takes in a String argument instead of two integer arguments. The String will be checked to
     * determine if it has any variables. If none are located, the coefficient will be determined and the exponent set
     * to zero. If variables are found, the String argument will be broken up into two Strings containing the Strings on
     * either side of the variable, and those will be used to check the coefficient and exponent.
     *
     * @param stringTerm
     *      The term in String form.
     */
     Term ( String stringTerm ) {

        String coefficientHalf = null; // The portion that will hold the coefficient String.
        String exponentHalf = null;    // The portion that will hold the exponent String.

        if ( stringTerm.isEmpty () ) {
            System.out.println ( "Invalid input. Terminating program.\n" );
            System.exit ( 0 );

        /*
         * String argument contains a variable, but no exponent. If the String length and the index of x (+ 1) are the
         * same, then we know there is no exponent.
         */
        } else if ( stringTerm.contains ( "x" ) && ( stringTerm.length () == stringTerm.indexOf ( "x" ) + 1 ) ) {
            coefficientHalf = stringTerm.substring ( 0, stringTerm.indexOf ( "x" ) );
            setExponent ( 1 );

        // String argument contains a variable, and an exponent.
        } else if ( stringTerm.contains ( "x" ) && ( stringTerm.length () > stringTerm.indexOf ( "x" ) + 1 ) ) {
            coefficientHalf = stringTerm.substring ( 0, stringTerm.indexOf ( "x" ) );
            exponentHalf = stringTerm.substring ( stringTerm.indexOf ( "x" ) + 1 );
            setExponent ( determineExponent ( exponentHalf ) );

        // String argument does not contain any variables.
        } else {
            coefficientHalf = stringTerm;
            setExponent ( 0 );
        }
        setCoefficient ( determineCoefficient ( coefficientHalf ) );
    }

    /**
     * No-argument constructor. Sets the term's coefficient and exponent to 1.
     */
    Term ( ) {
        this( 1, 1 );
    }


    /**
     * Copy constructor.
     *
     * @param otherTerm The reference variable in which the calling object will copy the instance/reference variables from.
     */
    Term ( Term otherTerm ) {
        this ( otherTerm.getCoefficient ( ), otherTerm.getExponent ( ) );
    }


    /**
     * Accessor method for the term's coefficient.
     *
     * @return The term's coefficient.
     */
    int getCoefficient ( ) {
        return coefficient;
    }


    /**
     * Accessor method for the term's exponent.
     *
     * @return The term's exponent.
     */
    int getExponent ( ) {
        return exponent;
    }


    /**
     * Mutator method for the term's coefficient.
     *
     * @param coefficient The term's coefficient.
     */
    void setCoefficient ( int coefficient ) {
        this.coefficient = coefficient;
    }


    /**
     * Mutator method for the term's exponent.
     *
     * @param exponent The term's exponent.
     */
    void setExponent ( int exponent ) {
        this.exponent = exponent;
    }


    /**
     * A mutator methods that allows all instance variables to be set at the same time.
     *
     * @param coefficient The term's coefficient.
     * @param exponent The term's exponent.
     */
    void setAll ( int coefficient, int exponent ) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }


    /**
     * Overrides the toString method from Object.
     *
     * @return
     *      Returns a String representation of the term.
     */
    @Override
    public String toString () {

        String term = null;
        int coef = getCoefficient ();
        int expo = getExponent ();

        // Coefficient is zero, so there is nothing to return.
        if ( coef == 0 )
            term = "";

        // Exponent is zero, so there is no variable. Either a positive or negative integer will be returned.
        else if ( expo == 0 ) {

            if ( coef > 0 )
                term = ( "+" + getCoefficient () );
            else
                term = ( "" + getCoefficient () );

        // Exponent is one, so x isn't raised to any power. The product of x and a positive/negative integer will be returned.
        } else if ( expo == 1 ){

            if ( coef == 1 )
                term = ( "+x" );
            else if ( coef == -1 )
                term = ( "-x" );
            else if ( coef > 0 )
                term = ( "+" + getCoefficient () + "x" );
            else
                term = ( getCoefficient () + "x" );

        /*
         * The exponent is larger than 1, so x will be returned alongside a positive/negative integer ("+x/-x" if the
         * coefficient is 1/-1, respectively), and x will be raised to a power.
         */
        } else {

            if ( coef == 1 )
                term = ( "+x^" + getExponent () );
            else if ( coef == -1 )
                term = ( "-x^" + getExponent () );
            else if ( coef > 0 )
                term = ( "+" + getCoefficient () + "x^" + getExponent () );
            else
                term = ( getCoefficient () + "x^" + getExponent () );
        }
        return term;
    }


    /**
     * Compares the calling object with the argument object.
     *
     * @param o
     *      The object that will compare against the calling object.
     * @return
     *      Returns true if:
     *          - The calling object and object argument share the same memory location.
     *          - The instance variables of the calling object and object argument are the same.
     *      Returns false if:
     *          - The argument object is null.
     *          - The calling object and the argument object do not come from the same class.
     *          - The instance variables of the calling object and object argument are different.
     *          - None of the conditions above have been met.
     */
    @Override
    public boolean equals ( Object o ) {
        Term other;

        if ( this == o ) // Memory check.
            return true;

        if ( o == null ) // Null check.
            return false;

        // Class check / Compare instance variables should the object derive from the same class.
        if ( this.getClass ( ) == o.getClass ( ) ) {
            other = ( Term ) o;
            return ( this.getCoefficient ( ) == other.getCoefficient ( ) &&
                     this.getExponent ( ) == other.getExponent ( ) );
        } else
            return false;
    }


    /**
     * Compares the exponents of two term objects.
     *
     * @param o
     *      The other term that the calling term will compare against.
     *
     * @return
     *      Return -1:
     *          - If the argument term's exponent is larger than the calling terms's.
     *      Return 0:
     *          - If the argument term's exponent is equal to the calling term's.
     *      Return 1:
     *          - If the argument term's exponent is less than the calling term's.
     */
    public int compareTo ( Object o ) {

        Term t = null;

        // Checks that the comparing object is not null and shares the same class as Term.
        if ( this.getClass () != o.getClass () || o == null ) {

            System.out.println( "Not a comparable object.\n" );
            System.exit( 0 );

        } else {

            t = ( Term ) o;
        }

        if ( this.getExponent() < t.getExponent() )
            return -1;

        else if ( this.getExponent() == t.getExponent() )
            return 0;

        else if ( this.getExponent() > t.getExponent() )
            return 1;

        return -1;
    }


    /**
     * Overrides the method from Object to provide a clone. Because all instance variables are immutable, this
     * method will call the clone method from Object.
     *
     * @return
     *      A clone of the object that contains the same instance variables as the calling object.
     */
    @Override
    public Object clone ( ) throws CloneNotSupportedException{
        return super.clone ( );
    }


    /**
     * Determines the coefficient based on the String input. If the String only contains a "+" or "-", the coefficient
     * will be 1 or -1, respectively. Otherwise, the coefficient will be the parsed number following the sign (numbers
     * following a negative sign will be multiplied by -1).
     *
     * @param coefficientString
     *      The String on the left of the variable.
     * @return
     *      If the String contains a positive sign:
     *          - The number following the positive sign will be parsed as an integer and returned.
     *      If the String contains a negative sign:
     *          - The number following the negative sign will parsed as an integer and returned.
     *      If the String contains neither a positive or negative sign:
     *          - The String (after being parsed as an integer) will be returned.
     */
    private static int determineCoefficient ( String coefficientString ) {

        int coefficient = 0;

        if ( coefficientString.contains ( "+" ) ) {

            if ( coefficientString.length () == ( coefficientString.indexOf ( "+" ) + 1 ) ) // Coefficient String only contains a "+"
                coefficient = 1;

            else if ( coefficientString.length () > ( coefficientString.indexOf ( "+" ) + 1 ) )
                coefficient = Integer.parseInt ( coefficientString.substring ( coefficientString.indexOf ( "+" ) + 1 ) );

        } else if ( coefficientString.contains ( "-" ) ) {

            if ( coefficientString.length () == ( coefficientString.indexOf ( "-" ) + 1 ) ) // Coefficient String only contains a "-"
                coefficient = -1;

            else if ( coefficientString.length () > ( coefficientString.indexOf ( "-" ) + 1 ) )
                coefficient = ( Integer.parseInt ( coefficientString.substring ( coefficientString.indexOf ( "-" ) + 1 ) ) * -1 );

        } else
            coefficient = Integer.parseInt ( coefficientString );

        return coefficient;
    }

    /**
     * Determines the exponent based on the String input. The String will remove the "^" and determine if the String
     * contains a negative sign. If so, the number following the negative sign will be parsed and multiplied by -1.
     *
     * @param exponentString
     * @return
     *      If the String contains a negative sign:
     *          The
     */
    private static int determineExponent ( String exponentString ) {
        int exponent;

        exponentString = exponentString.substring ( exponentString.indexOf ( "^" ) + 1 );

        if ( exponentString.contains ( "-" ))
            exponent = (Integer.parseInt ( exponentString.substring ( exponentString.indexOf ( "-" )  + 1 ) )  * -1 );
        else
            exponent = Integer.parseInt ( exponentString ); // String does not contain negative sign.

        return exponent;
    }
}
