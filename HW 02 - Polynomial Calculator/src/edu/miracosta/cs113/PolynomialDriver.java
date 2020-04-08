package edu.miracosta.cs113;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * PolynomialDriver.java - Allows the user to test out the polynomial calculator.
 *
 * 1. The program will prompt the user if they would like to work with the 1) first polynomial, 2) second polynomial,
 * 3) add both polynomials, or 4) exit the program.
 *
 * 2. If the user selects to work with either the first or second polynomial, then they will have the option to 1) create
 * a new polynomial, 2) clear the polynomial, 3) edit the polynomial, or 4) add a term.
 *
 *  - Selecting to create the polynomial will create the polynomial.
 *  - Clearing the polynomial will remove all terms.
 *  - Editing the polynomial will allow the user to change the coefficient/exponent of the selected term.
 *  - Adding a term will allow the user to add a term.
 *
 * 3. If the user selects to add two polynomials, the program will ensure neither polynomial is null first, then add them
 * and print the result.
 *
 * 4. If the user selects to end the program, the program will terminate.
 *
 * 5. Both the main and polynomial menus will continue until the user chooses otherwise.
 *
 * @author Matthew Sheehan
 *
 * @version 1.0
 *
 */
public class PolynomialDriver {

    // The polynomials the user will be editing/adding.
    static Polynomial polyOne = null;
    static Polynomial polyTwo = null;

    // The Scanner object that will be used throughout the program.
    private static Scanner sc = new Scanner ( System.in );

    public static void main ( String[] args ) {
        programIntroduction ();
        mainMenuPrompt ();
    }

    /**
     * Gives an introduction to the program.
     */
    private static void programIntroduction ( ) {
        System.out.println ( "\nHello, and welcome to the Polynomial Calculator!" );
        System.out.println ( "I can show you what the sum of two polynomials would look like.\n" );
    }

    /**
     * Lists the main menu options.
     */
    private static void listMainMenuOptions ( ) {
        System.out.println ( "Please select from one of the following options:\n" );
        System.out.println ( "1. Create / edit the first polynomial." );
        System.out.println ( "2. Create / edit the second polynomial." );
        System.out.println ( "3. Add the two polynomials." );
        System.out.println ( "4. Quit the program.\n" );
    }

    /**
     * Prompts the user for their main menu selection.
     */
    private static void mainMenuPrompt ( ) {
        boolean continueProgram; // Controls the outer do while loop. True as long as the user wants to continue.

        do {
            listMainMenuOptions ();

            int userSelection = ensureInputIsInteger ();

            userSelection = ensureNumberIsWithinRange ( userSelection, 1, 4 );

            mainMenuSelection ( userSelection );

            System.out.println ( "Would you like to continue using the program?\n");

            continueProgram = yesOrNo ();

        } while ( continueProgram );
    }

    /**
     * Based on the user's selection, activates the main menu option.
     *
     * @param userSelection
     *      The selection made by the user.
     */
    private static void mainMenuSelection ( int userSelection ) {
            if ( userSelection == 1 )
                polynomialMenuPrompt ( "first" );
            else if ( userSelection == 2 )
                polynomialMenuPrompt ( "second" );
            else if ( userSelection == 3 )
                addPolynomials ( );
            else
                exitProgram ( );
    }

    /**
     * Options for the polynomial menu.
     */
    private static void listPolynomialMenuOptions ( ) {
        System.out.println ( "Please select from one of the following options:\n" );
        System.out.println ( "1. Create a polynomial." );
        System.out.println ( "2. Clear the polynomial." );
        System.out.println ( "3. Edit the polynomial." );
        System.out.println ( "4. Add a term to the polynomial.\n" );
    }

    /**
     * Prompts the user for their polynomial menu selection.
     *
     * @param polynomial
     *      The String indicating if the polynomial to work with is the first or second.
     */
    private static void polynomialMenuPrompt ( String polynomial ) {
        System.out.println ( "You have selected to work with the " + polynomial + " polynomial. What would you like to do?\n");

        int userSelection; // Selection made by user.
        boolean continueProgram; // Controls the outer do while loop. True as long as the user wants to continue.

        do {

            listPolynomialMenuOptions ();

            userSelection = ensureInputIsInteger ();

            userSelection = ensureNumberIsWithinRange ( userSelection, 1, 4 );

            polynomialMenuSelection ( userSelection, polynomial );

            System.out.println ( "Would you like to continue working with the " + polynomial + " polynomial?\n");

            continueProgram = yesOrNo ();

        } while (  continueProgram );
    }


    /**
     * Based on the user's selection, activates the polynomial menu option.
     *
     * @param selection
     *      The selection made by the user from the polynomial menu.
     * @param polynomial
     *      A String indicating if the first or second polynomial is being worked on.
     */
    private static void polynomialMenuSelection ( int selection, String polynomial ) {

        switch ( selection ) {

            // Create a new polynomial.
            case 1:
                createPolynomial ( polynomial );
                break;

            // Clear the polynomial.
            case 2:
                clearPolynomial ( polynomial );
                break;

            // Edit the polynomial.
            case 3:
                editPolynomial ( polynomial );
                break;
            // Add a term to the polynomial.
            case 4:
                addToPolynomial ( polynomial );
                break;
        }
    }


    /**
     * If either or both polynomials are null, the method will print a String indicating so. Otherwise, the method
     * will add both Strings and print a toString of the sum.
     */
    private static void addPolynomials () {
        if ( polyOne == null && polyTwo == null )
            System.out.println ( "Cannot add polynomials, neither polynomial has been set.\n" );
        else if ( polyOne == null )
            System.out.println ( "Cannot add polynomials, the first polynomial has not been set.\n" );
        else if ( polyTwo == null )
            System.out.println ( "Cannot add polynomials, the second polynomial has not been set.\n" );
        else {
            polyOne.add ( polyTwo );
            System.out.println ( "The result of those two polynomials is: " + polyOne.toString () +"\n");
        }
    }

    /**
     * Prompts the user if they would like to exit the program.
     */
    private static void exitProgram () {
        System.out.println ( "Wold you like to exit the program?" );
        boolean exitProgram = yesOrNo ();

        if ( exitProgram ) {
            System.out.println ( "Goodbye." );
            System.exit ( 0 );
        }
    }

    /**
     * Allows the user to create a new polynomial.
     *
     * @param polynomial
     *  The polynomial the user would like to create.
     */
    private static void createPolynomial ( String polynomial ) {

        if ( polynomial.equalsIgnoreCase ( "first" ) ) {

            if ( polyOne != null )
                System.out.println ( "Polynomial already exists.\n" );
            else {
                polyOne = new Polynomial ();
                System.out.println ( "First polynomial created.\n" );
            }

        } else if ( polynomial.equalsIgnoreCase ( "second" ) ) {

            if ( polyTwo != null )
                System.out.println ( "Polynomial already exists.\n" );
            else {
                polyTwo = new Polynomial ();
                System.out.println ( "Second polynomial created.\n" );
            }
        }
    }


    /**
     * Allows the user to clear a polynomial.
     *
     * @param polynomial
     *  The polynomial the user would like to clear.
     */
    private static void clearPolynomial ( String polynomial ) {
        if ( polynomial.equalsIgnoreCase ( "first" ) && polyOne != null ) {
            if ( polyOne.getNumTerms () == 0 )
                System.out.println ( "The polynomial is already empty.\n" );
            else {
                polyOne.clear ();
                System.out.println ( "Polynomial cleared of all terms.\n" );
            }
        } else if ( polynomial.equalsIgnoreCase ( "second" ) && polyTwo != null )
            if ( polyTwo.getNumTerms ( ) == 0)
                System.out.println ( "The polynomial is already empty.\n" );
            else {
                polyTwo.clear ( );
                System.out.println ( "Polynomial cleared of all terms.\n" );
            }
    }

    /**
     * Allows the user to edit the polynomial.
     *
     * @param polynomial
     *      The polynomial you would like to edit.
     */
    private static void editPolynomial ( String polynomial ) {
        int userSelection; // The number of the term selected by the user.
        int coefficient;   // The coefficient entered by the user.
        int exponent;      // The exponent entered by the user.

        if ( polynomial.equalsIgnoreCase ( "first" ) ) {
            if ( checkPolynomial ( polyOne ) ) {
                System.out.println ( "The polynomial doesn't have any terms. Creating polynomial now and adding x variable.\n" );
                if ( polyOne == null )
                    polyOne = new Polynomial (  );

                polyOne.addTerm ( new Term (1, 1) );
            }

            System.out.println ( "Which term would you like to edit?");

            userSelection = ensureInputIsInteger ();

            userSelection = ensureNumberIsWithinRange ( userSelection, 1, polyOne.getNumTerms () );

            coefficient = getCoefficient ();

            exponent = getExponent ();

            polyOne.getTerm ( userSelection - 1 ).setAll ( coefficient, exponent );

        } else if ( polynomial.equalsIgnoreCase ( "second" ) ) {

            if ( checkPolynomial ( polyTwo ) ) {

                System.out.println ( "The polynomial doesn't exist. Creating polynomial now and adding x variable.\n" );

                if ( polyTwo == null )
                    polyTwo = new Polynomial (  );

                polyTwo.addTerm ( new Term (1, 1) );
            }

            System.out.println ( "Which term would you like to edit?");

            userSelection = ensureInputIsInteger ();

            userSelection = ensureNumberIsWithinRange ( userSelection, 1, polyTwo.getNumTerms () );

            coefficient = getCoefficient ();

            exponent = getExponent ();

            polyTwo.getTerm ( userSelection - 1 ).setAll ( coefficient, exponent );
        }
    }


    /**
     * Allows the user to add a term to the polynomial.
     *
     * @param polynomial
     *      The polynomial selected by the user.
     */
    private static void addToPolynomial ( String polynomial ) {

        int coefficient = getCoefficient ();
        int exponent = getExponent ();

        if ( polynomial.equalsIgnoreCase ( "first" ) )
            polyOne.addTerm ( new Term ( coefficient, exponent ) );
        else if ( polynomial.equalsIgnoreCase ( "second" ) )
            polyTwo.addTerm ( new Term ( coefficient, exponent ) );
    }

    
    /**
     * Prompts the user for the term's coefficient.
     *
     * @return
     *      Returns the coefficient entered by the user.
     */
    private static int getCoefficient () {
        System.out.println ( "What coefficient would you like for your term?");
        return ensureInputIsInteger ( );
    }


    /**
     * Prompts the user for the term's exponent.
     *
     * @return
     *      Returns the exponent entered by the user.
     */
    private static int getExponent ( ) {
        System.out.println ( "What exponent would you like for your term?");
        return ensureInputIsInteger ( );
    }

    /**
     * Prompts the user for a yes or no input.
     *
     * @return
     *      Returns true if the user enters yes (or its variations) as input. Returns false otherwise.
     */
    private static boolean yesOrNo ( ) {
        System.out.print ( "Your selection ( \"yes\" | \"no\" ): ");
        String userSelection = sc.next ( );
        System.out.println ( " " );

        switch ( userSelection ) {
            case "y": case "Y": case "yes": case "YES":
                return true;
        }
        return false;
    }

    /**
     * Uses exception handling to ensure the user is entering an integer.
     *
     * @return
     *      The number entered by the user.
     */
    private static int ensureInputIsInteger ( ) {
        int theNumber = 0; // The number entered by the user.
        boolean validInput = false; // Controls the exception loop until the exception is resolved.

        System.out.print ( "Your selection: " );

        while ( ! validInput ) {
            try {
                theNumber = sc.nextInt ();
                System.out.println ( " " );
                validInput = true;
            }
            catch ( InputMismatchException e ) {
                sc.nextLine ( );
                System.out.print ( "Input must be a number 1 - 4. Try again: " );
            }
        }
        return theNumber;
    }


    /**
     * Ensures the user is entering an number within the given range.
     *
     * @param numberGiven
     *      The number entered by the user.
     *
     * @param lowerlimit
     *      The lower limit of the range.
     * @param upperlimit
     *      The upper limit of the range.
     * @return
     *      The number given by the user, fixed (if applicable).
     */
    private static int ensureNumberIsWithinRange ( int numberGiven, int lowerlimit, int upperlimit ) {
        if ( numberGiven < lowerlimit || numberGiven > upperlimit ) {
            while ( numberGiven < lowerlimit || numberGiven > upperlimit ) {
                System.out.print ( "Number must be between " + lowerlimit + " and " +  upperlimit + ". Try again: " );
                numberGiven = sc.nextInt ();
                System.out.println ( " " );
            }
        }
        return numberGiven;
    }

    /**
     * Checks if the selected polynomial is empty or null.
     *
     * @param polynomial
     *      The polynomial selected by the user.
     */
    private static boolean checkPolynomial ( Polynomial polynomial ){
        return ( polynomial == null | polynomial.getNumTerms ( ) == 0  );
    }
}
