package edu.miracosta.cs113;

/**
 * Main.java - Shows the comparison of adding numbers to a binary search tree and an AVL tree.
 *
 * Algorithm:
 *
 * 1. The program will create an array of 20 randomly generated integers.
 *
 * 2. Once the integers have been assigned, they will be added to both a BinarySearchTree object, and an AVLTree
 * object simultaneously.
 *
 * 3. Both tree objects will call toString from the BinarySearchTree class, which will reveal the contents of the
 * tree in a pre-order traversal. This should highlight the balancing ability of the AVL tree.
 *
 * @author Matt Sheehan
 * @version 1.0
 */
class Main {

    private static int [ ] randomNumbers = new int [ 20 ];
    private static BinarySearchTree < Integer > bst = new BinarySearchTree < > ( );
    private static AVLTree < Integer > avl = new AVLTree < > ( );

    public static void main ( String [ ] args ) {
        runProgram ( );
    } // end main.

    /**
     * Condenses all helper methods for use by main.
     */
    private static void runProgram ( ) {
        generateRandomNumbersArray ( );
        assignNumbersToTrees ( );
        printTrees ( );
    }

    /**
     * Assign random numbers to an array.
     */
    private static void generateRandomNumbersArray ( ) {
        for ( int i = 0; i < randomNumbers.length; i++ )
            randomNumbers [ i ] = ( int ) ( ( Math.random ( ) * 100 ) + 1 );
    }

    /**
     * Assign the random numbers from the array to each tree.
     */
    private static void assignNumbersToTrees ( ) {
        for ( int randomNumber : randomNumbers ) {
            bst.add ( randomNumber );
            avl.add ( randomNumber );
        }
    }

    /**
     * Print the contents of the trees.
     */
    private static void printTrees ( ) {
        System.out.println ( bst.toString2 ( ) + "\n" );
        System.out.println ( "Height of the binary tree: " + bst.getHeight ( bst.root ) + "\n\n" );
        System.out.println ( avl.toString2 ( ) + "\n" );
        System.out.println ( "Height of the AVL tree: " + avl.getHeight ( avl.root ) + "\n\n" );
    }
}
