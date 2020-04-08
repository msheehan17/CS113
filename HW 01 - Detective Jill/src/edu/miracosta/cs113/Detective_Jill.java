package edu.miracosta.cs113;

import model.AssistantJack;
import model.Theory;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Detective_Jill.java - Driver program. With the aid of AssistantJack, Detective Jill will attempt to confirm theories
 * in 20 guesses or less.
 *
 * ALGORITHM:
 *
 * The Detective_Jill class will use similar code from the RandomClue class, however, Detective_Jill will not
 * use a for loop of random guess but will instead:
 *
 *  - Jill will try guessing "1" for each category (which should possibly rule out Jack's answer set one immediately).
 *
 *  - Should any of the answers not be one, Assistant Jack will return their number (although Assistant Jack has the
 *    potential to return a random number if more than one guess is wrong, at least the random number returned will still
 *    be at least one wrong answer, which is the one we will increment).
 *
 *  - Assistant Jack won't return any wrong answers, so Detective Jill will continue to increment her theory values
 *    until Assistant Jack returns zero.
 *
 *  @author: Matthew Sheehan
 *  @version: 1.0
 */
class Detective_Jill {

    /**
     * Driver method for strategic guessing.
     *
     * @param args not used for driver
     */
    public static void main ( String [ ] args ) {
        int solution; // The number returned by Assistant Jack indicating which part of the solution is wrong (if only one).
        int weapon, location, murder; // The respective numbers for the murderer, weapon used, and the location.
        int setChosen;                // The answer set chosen by the user.

        Theory answer;      // The correct answer.
        AssistantJack jack; // Our assistant for making guesses.

        setChosen = promptUser ( ); // Prompt user for set.

        // Assistant Jack will abide by the answer set given after prompting the user for their choice.
        jack = new AssistantJack ( setChosen );

        // Jill will begin by testing against Jack's answer set one.
        weapon = location = murder = 1;
        solution = jack.checkAnswer(weapon, location, murder);

        // Jill begins guessing her theories.
        while ( solution != 0 ) {
            if ( solution == 3 )
                murder++;
            else if ( solution == 2 )
                location++;
            else if (solution == 1)
                weapon++;

            solution = jack.checkAnswer ( weapon, location, murder );
        }
        answer = new Theory ( weapon, location, murder ); // The correct answer found.

        System.out.println ("Total Checks = " + jack.getTimesAsked ( ) + ", Solution: " + answer + "\n");
        System.out.println ( ( jack.getTimesAsked ( ) > 20 ) ? "FAILED!! You're a horrible Detective...." :
                                                               "WOW! You might as well be called Batman!");
    }

    /**
     * Prompts the user to select which answer set they would like Assistant Jack to abide by.
     *
     * @return Answer set chosen by user.
     */
    static int promptUser ( ) {
        int answerSet = -1;    // Answer set chosen by the user.
        boolean done  = false; // Keeps the loop going until the user enters an integer as their answer.

        Scanner keyboard = new Scanner ( System.in );

        while ( ! done ) {
            try {
                System.out.print ( "Which theory would like you like to test? (1, 2, 3[random]): " );
                answerSet = keyboard.nextInt ( );
                done = true;
            }
            catch ( InputMismatchException e ) {
                System.out.println ( "That is not an acceptable answer. Answers must be integers! Try again.\n" );
            }
        }
        keyboard.close();
        return answerSet;
    }
}