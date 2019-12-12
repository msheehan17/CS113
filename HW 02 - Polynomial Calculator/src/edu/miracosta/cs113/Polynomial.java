package edu.miracosta.cs113;

/**
 * Polynomial.java - Concrete class that uses a LinkedList of terms to replicate a polynomial in software.
 *
 * Instance Variables:
 *      - polynomialTerms ( LinkedList <Terms>): A LinkedList that holds each term of the polynomial.
 *
 * Methods:
 *      - Constructors:
 *          - Full / No argument constructor that initializes the polynomial.
 *          - Copy constructor.
 *      - toString: Returns a String representation of the polynomial.
 *      - getNumTerms: Returns the number of terms in the polynomial.
 *      - getTerm: Returns the term located in the index requested by the user.
 *      - clear: Clears the polynomial of all terms.
 *      - addTerm: Adds the input term to the polynomial. Adds the coefficients of terms with the same exponent, and
 *        places the term in order of greatest to least exponent.
 *      - add: Combines the calling polynomial with the argument polynomial.
 *
 * @author Matthew Sheehan
 * @version 1.0
 */

import java.util.LinkedList;

public class Polynomial {

    private LinkedList<Term> polynomialTerms;


    /**
     * Full / no-argument constructor.
     */
    public Polynomial () {

        polynomialTerms = new LinkedList<> ();
    }


    /**
     * Copy constructor.
     *
     * @param otherPoly The other polynomial the calling polynomial is copying.
     */
    public Polynomial ( Polynomial otherPoly ) {

        this ();

        if ( otherPoly == null ) {

            System.out.println ( "Null object. Terminating program.\n" );
            System.exit ( 0 );
        }

        for ( int i = 0; i < otherPoly.getNumTerms (); i++ ) {

            this.polynomialTerms.add ( otherPoly.getTerm ( i ) );

        }
    }


    /**
     * Overrides toString method in Object. Returns a String version of the polynomial.
     *
     * @return String version of the polynomial.
     */
    @Override
    public String toString () {

        String thePolynomial = "";

        if ( polynomialTerms.isEmpty () )
            return "0";

        else {

            if ( polynomialTerms.get (0).toString ().charAt ( 0 ) == '+')
                thePolynomial += getTerm ( 0 ).toString ().substring ( 1 );
            else
                thePolynomial += getTerm ( 0 ).toString ();
        }

        for ( int i = 1; i < getNumTerms (); i++ ) {

            thePolynomial += getTerm ( i ).toString ();
        }

        return thePolynomial;
    }


    /**
     * Returns the number of terms in the polynomial.
     *
     * @return The number of terms in the polynomial.
     */
    public int getNumTerms () {

        return polynomialTerms.size ();
    }


    /**
     * Returns a copy of the term at the selected index.
     *
     * @param index
     *      The index of the term you're trying to retrieve.
     * @return
     *      Returns a copy of the term selected.
     */
    public Term getTerm ( int index ) {

        return new Term ( polynomialTerms.get ( index ) );
    }


    /**
     * Clears the LinkedList of all terms.
     */
    public void clear () {

        polynomialTerms.clear ();
    }


    /**
     * Adds a term to the polynomial. If the argument term's exponent matches any exponent of a term already in
     * the polynomial, then the coefficients will be added. If no exponents match, then the method will cycle through
     * all terms to determine if the argument exponent is the largest (by using compareTo). If so, it will take the
     * index of the term with the lesser exponent.
     *
     * @param t
     *      The term being added to the polynomial.
     */
    public void addTerm ( Term t ) {

        int index = -1;
        int sumOfCoefficients = 0;
        Term oldTerm;

        // If the LinkedList is empty, simply add the term.
        if ( polynomialTerms.isEmpty () )

            polynomialTerms.add ( t );

        else {

            // Determine if there are any matching exponents.
            for ( int i = 0; i < polynomialTerms.size (); i++ ) {

                if ( t.getExponent () == polynomialTerms.get ( i ).getExponent () )
                    index = i;
            }

            // if any matching exponents are found a new term with the sum of the old and argument coefficients is made.
            if ( index != -1 ) {

                oldTerm = polynomialTerms.remove ( index );
                sumOfCoefficients = ( oldTerm.getCoefficient () + t.getCoefficient () );

                // If the sum of coefficients doesn't cancel out the terms, then the new term will be added to the polynomial.
                if ( sumOfCoefficients != 0)
                    polynomialTerms.add ( index, new Term ( sumOfCoefficients, t.getExponent () ) );

            } else {

                // No matching exponents were found, see if the argument exponent is larger than any existing terms in the polynomial.
                for ( int i = 0; i < polynomialTerms.size (); i++ ) {

                    if ( t.compareTo ( polynomialTerms.get ( i ) )  > 0 ) {
                        index = i;
                        break;
                    }
                }

                if ( index != -1 )
                    polynomialTerms.add ( index, t );
                // The term argument does not have matching exponents, nor an exponent greater than any existing in the polynomial.
                else
                    polynomialTerms.add ( t );
            }
        }
    }


    /**
     * Copies terms from the argument polynomial to the calling polynomial. This method uses addTerm, so this method
     * will check if any term being added from the argument polynomial has an exponent that matches any exponent in the
     * calling polynomial.
     *
     * @param otherPoly
     *      The argument polynomial being added to the calling polynomial.
     */
    public void add ( Polynomial otherPoly ) {

        for ( int i = 0; i < otherPoly.getNumTerms (); i++ )
            addTerm ( otherPoly.getTerm ( i ) );
    }
}
