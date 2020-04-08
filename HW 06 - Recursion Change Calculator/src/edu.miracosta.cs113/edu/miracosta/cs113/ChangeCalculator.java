package edu.miracosta.cs113;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * ChangeCalculator : Class containing the recursive method calculateChange, which determines and prints all
 * possible coin combinations representing a given monetary value in cents.
 * <p>
 * Problem derived from Koffman & Wolfgang's Data Structures: Abstraction and Design Using Java (2nd ed.):
 * Ch. 5, Programming Project #7, pg. 291.
 *
 * NOTE: An additional method, printCombinationsToFile(int), has been added for the equivalent tester file to
 * verify that all given coin combinations are unique.
 */
class ChangeCalculator {

    private static ArrayList < int[ ] > changeCombinations = new ArrayList < > ( );
    private static final String FILE_NAME = "CoinCombinations.txt";

    /**
     * Wrapper method for determining all possible unique combinations of quarters, dimes, nickels, and pennies that
     * equal the given monetary value in cents.
     *
     * In addition to returning the number of unique combinations, this method will print out each combination to the
     * console. The format of naming each combination is up to the user, as long as they adhere to the expectation
     * that the coins are listed in descending order of their value (quarters, dimes, nickels, then pennies). Examples
     * include "1Q 2D 3N 4P", and "[1, 2, 3, 4]".
     *
     * @param cents a monetary value in cents
     * @return the total number of unique combinations of coins of which the given value is comprised
     */
    static int calculateChange ( int cents ) throws IllegalArgumentException {
        if ( cents < 0 )
            throw new IllegalArgumentException ( "Numbers must be positive." );

        // Clear the list from any previous makeChange method calls.
        changeCombinations.clear ();

        makeChange ( 0,0,0, cents );

        // Reverse the order of the ArrayList, since it starts with penny values first.
        Collections.reverse ( changeCombinations );

        for ( int [ ] array : changeCombinations )
            System.out.println ( "[ " + array [0] + ", " + array [1] + ", " + array [2] + ", " + array [3] + " ]" );

        return changeCombinations.size ( );
    }

    /**
     * This method works by first adding the change in pennies to the changeCombinations ArrayList. Then the method
     * will determine if you can return increments of nickels as change, then increments of dimes, and finally
     * increments of quarters. Example: If we look for the change of 5 cents, we will first find that we can return
     * 5 pennies ( as this is initially the most obvious change we can give ). Then the method will continue to the
     * first recursive case, which will increment the number of nickels by one, and recursively re-try the method with
     * now zero pennies ( which should return to the previous recursive call ).
     *
     * @param quarters The number of quarters.
     * @param dimes The number of dimes.
     * @param nickels The number of nickels.
     * @param pennies The number of pennies.
     */
    private static void makeChange ( int quarters, int dimes, int nickels, int pennies ) {
        // First we determine if this is a new combination, if so, then we can add it to the ArrayList
        for ( int [ ] array : changeCombinations ) {
            if ( array [ 0 ] == quarters && array [ 1 ] == dimes && array [ 2 ] == nickels && array [ 3 ] == pennies )
                return;
        }

        int [ ] newCombo = { quarters, dimes, nickels, pennies };

        changeCombinations.add ( newCombo );

        if ( pennies <= 4 )
            return;

        // The number of cents exceeds 4, so we can return a nickel.
        if ( pennies >= 5 )
            makeChange ( quarters, dimes, nickels + 1, pennies - 5 );

        // The number of cents exceeds 10, so we can return a dime.
        if ( pennies >= 10 )
            makeChange ( quarters, dimes + 1, nickels, pennies - 10 );

        // The number of cents exceeds 25, so we can return a quarter.
        if ( pennies >= 25 )
            makeChange ( quarters + 1, dimes, nickels, pennies - 25 );
    }

    /**
     * Calls upon calculateChange(int) to calculate and print all possible unique combinations of quarters, dimes,
     * nickels, and pennies that equal the given value in cents.
     *
     * Similar to calculateChange's function in printing each combination to the console, this method will also
     * produce a text file named "CoinCombinations.txt", writing each combination to separate lines.
     *
     * @param cents a monetary value in cents
     */
    static void printCombinationsToFile ( int cents ) {
        calculateChange ( cents );
        PrintWriter output = null;

        try {
            output = new PrintWriter ( new FileOutputStream ( FILE_NAME ) );
        } 
        catch ( FileNotFoundException e ) {
            System.out.println ( "CoinCombinations.txt file not found." );
            System.exit ( 0 );
        }

        // Clear the list from any previous makeChange method calls.
        changeCombinations.clear ( );

        // Reverse the order of the ArrayList, since it starts with penny values first.
        Collections.reverse ( changeCombinations );

        for ( int[] array : changeCombinations )
            output.println ( "[ " + array [ 0 ] + ", " + array [ 1 ] + ", " + array [ 2 ] + ", " + array [ 3 ] + " ]" );

        output.close ();
    }
}
