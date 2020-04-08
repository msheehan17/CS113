package edu.miracosta.cs113;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * HuffmanTree.java - My implementation of Huffman Tree.
 *
 * INSTANCE VARIABLES:
 *      - huffTree (BinaryTree<Huffdata>): The built HuffmanTree.
 *      - symbols (char[]): Holds all characters with frequency (weight) within the HuffmanTree.
 *      - code (String[]): Stores the path for each symbol within the HuffmanTree.
 *      - VALID_CHARACTERS (char[]): The only characters allowed in the HuffmanTree.
 *
 * METHODS:
 *      - Full constuctor: Using the given String argument constructs a HuffmanTree with non-zero frequency (weight) symbols.
 *      - buildTree (HuffData[]): Constructs the calling tree using the array of HuffData.
 *      - getCode(StringBuilder, String, BinaryTree<HuffData>): Traverses the tree recrueivsely, appending the StringBuilder
 *        with the path of the leaf node.
 *
 * @author Matthews Sheehan
 * @version 1.0
 *
 */
class HuffmanTree implements Serializable, HuffmanInterface {

    private static class HuffData implements Serializable {

        private int weight; // The weight (or frequency) assigned to the symbol.
        private Character symbol; // The symbol.

        private HuffData ( int weight, Character symbol ) {
            this.weight = weight;
            this.symbol = symbol;
        }
    }

    private static class CompareHuffmanTrees implements Comparator < BinaryTree < HuffData > > {

        public int compare ( BinaryTree < HuffData > treeLeft, BinaryTree < HuffData > treeRight ) {
            return Double.compare ( treeLeft.getData ( ).weight, treeRight.getData ( ).weight );
        }
    }

    private BinaryTree < HuffData > huffTree;

    private static char [ ] symbols; // Stores the characters in the HuffmanTree from top to bottom.
    private static String [ ] codes; // Stores the code for each character in the HuffmanTree.

    // An array of valid characters allowed within the HuffmanTree.
    private static final char [ ] VALID_CHARACTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                                                       'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                                       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                                                       'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                                       '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '?', '!',
                                                       ' ', '\n', '\t' };

    /**
     * Full constructor for the HuffmanTree. Takes in a String argument and counts the frequency of each legal
     * character (identified in CHARACTERS array) within the String.
     * @param text The String used to determine symbol frequencies.
     */
    HuffmanTree ( String text ) {
        // Stores the HuffData nodes containing the symbol and symbol weight.
        ArrayList < HuffData > nodes = new ArrayList < > (  );

        /* The outer for loop will cycle through each character in the VALID_CHARACTERS array. The inner for loop
         * will iterate through each character in the text argument. The variable integer called frequency will be
         * incremented by one every time the current character in the inner for loop matches the character in the
         * outer for loop. Once frequency (weight) for the character has been found (and it is not zero), a HuffData
         * object will be initialized with the symbol and weight, and added to the nodes array.
         */
        for ( char validCharacter : VALID_CHARACTERS ) {
            int frequency = 0;
            for ( int j = 0; j < text.length (); j++ ) {
                if ( validCharacter == text.charAt ( j ) )
                    frequency++;
            }
            if ( frequency != 0 )
                nodes.add ( new HuffData ( frequency, validCharacter ) );
        }

        // Trim down the ArrayList.
        nodes.trimToSize ( );

        // Create an array containing the non-null values.
        HuffData [ ] nonNullNodes = new HuffData [ nodes.size ( ) ];
        nodes.toArray ( nonNullNodes );

        // Build the tree.
        buildTree ( nonNullNodes );

        /* Traverse the tree using a StringBuilder object. The StringBuilder will collect each symbol and it's
         * corresponding code. The StringBuilder's String will be split into different Strings. Each character
         * at index 0 (the symbol) will be stored in the symbols array, and the remainder of the String (the code)
         * will be stored in the codes array. (As this will help our encode method).
         */
        StringBuilder sb = new StringBuilder (  );
        getCode ( sb, "", huffTree );
        String [ ] symbolsAndCodes = ( sb.toString () ).split ( "#" );

        symbols = new char [ symbolsAndCodes.length ];
        codes = new String [ symbolsAndCodes.length ];

        for ( int i = 0; i < symbolsAndCodes.length; i++ ) {
            symbols [ i ] = symbolsAndCodes [ i ].charAt ( 0 );
            codes [ i ] = symbolsAndCodes [ i ].substring ( symbolsAndCodes [ i ].indexOf ( ":" ) + 1 );
        }
    }

    /**
     * Builds the Huffman tree using the given alphabet and weights.
     * @param symbols An array of HuffData objects.
     *
     * Note: This method was implemented from text in the book.
     */
    private void buildTree ( HuffData [ ] symbols ) {
        // The priority queue for storing our binary trees. The CompareHuffmanTree object will dictate the natural order.
        Queue < BinaryTree < HuffData > > theQueue = new PriorityQueue < > ( symbols.length, new CompareHuffmanTrees ( ) );

        // Create binary trees only containing one HuffData node.
        for ( HuffData nextSymbol : symbols ) {
            BinaryTree <HuffData > aBinaryTree = new BinaryTree < > ( nextSymbol, null, null );
            theQueue.offer ( aBinaryTree );
        }

        // Build the tree.
        while ( theQueue.size ( ) > 1 ) {
            BinaryTree < HuffData > left = theQueue.poll ( );
            BinaryTree < HuffData > right = theQueue.poll ( );
            HuffData sum = new HuffData ( ( left.getData ( ).weight + right.getData ( ).weight ), null );
            BinaryTree < HuffData > newTree = new BinaryTree < > ( sum, left, right );
            theQueue.offer ( newTree );
        }
        huffTree = theQueue.poll ( );
    }

    /**
     * Outputs a pre order traversal of the Huffman tree.
     * @param sb A StringBuilder object for gathering all symbols and their codes.
     * @param code The code to add to the StringBuilder.
     * @param tree The current node in the tree.
     *
     * Note: This method was implemented from text (from printCode) in the book, with some modifications.
     */
    private void getCode ( StringBuilder sb, String code, BinaryTree < HuffData > tree ) {
        HuffData theData = tree.getData ( );
        if ( theData.symbol != null ) {
            sb.append ( theData.symbol + ":" + code + "#");
        } else {
            getCode ( sb, ( code + "0" ), tree.getLeftSubtree ( ) );
            getCode ( sb, ( code + "1" ), tree.getRightSubtree ( ) );
        }
    }


    //Note: This method was implemented from text in the book.
    @Override
    public String decode ( String codedMessage ) {
        StringBuilder result = new StringBuilder ( );
        BinaryTree < HuffData > currentTree = huffTree;
        for ( int i = 0; i < codedMessage.length ( ); i++ ) {
            if ( codedMessage.charAt ( i ) == '1' )
                currentTree = currentTree.getRightSubtree ( );
            else
                currentTree = currentTree.getLeftSubtree ( );

            if ( currentTree.isLeaf ( ) ) {
                HuffData theData = currentTree.getData ( );
                result.append ( theData.symbol );
                currentTree = huffTree;
            }
        }
        return result.toString ( );
    }

    @Override
    public String encode ( String message ) {
        StringBuilder sb = new StringBuilder (  );
        /* Traverse the characters in the String argument. Should it match any character in the symbols array, then
         * the corresponding code from the code array will be appended to the String builder.
         */
        for ( int i = 0; i < message.length ( ); i++ ){
            for ( int j = 0; j < symbols.length; j++ ) {
                if ( message.charAt ( i ) == symbols [ j ] )
                    sb.append ( codes [ j ] );
            }
        }
        return sb.toString ();
    }
}
