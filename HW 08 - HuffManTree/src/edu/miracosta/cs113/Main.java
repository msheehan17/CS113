package edu.miracosta.cs113;

import java.io.*;
import java.util.Scanner;

/**
 * Main.java - The driver program for our HuffmanTree demonstration.
 *
 * 1. The program will prompt the user for the URL of a website of their choice. The program will then create a file
 * that contains the characters of the website (Note: Only authorized characters are lowercase letters, uppercase
 * letters, numbers 0-9, and ' ', ?, !, ., tabs, and newline characters).
 *
 * 2. The program will then generate a String representation of the file in it's entirety. This will be used to create
 * our HuffmanTree object, and then be used to encode the file.
 *
 * 3. Once the file has been encoded, the program will then decode the file.
 *
 * 4. After creating the decoded file (at this point in the program there should be three files: original, encoded,
 * decoded) the program will then display the number of bits within each file. For the original and decoded file,
 * the number of bits will be the product of the number of characters within the file, and 16. Because the encoded file
 * will be a series of '0's and '1's, the number of bits for the encoded file will be the number of '0's and '1's within
 * the file.
 *
 * 5. After the number of bits within each file has been determined, the compression rate will be calculated and the
 * result will be displayed to the console. This wil demonstrate the way in which the huffman tree works by reducing
 * the number of bits required to generate a file.
 *
 * @author Matthew Sheehan
 * @version 1.0
 *
 */
class Main {

    private static final String ORIGINAL_FILE_NAME = "webFile.txt"; // The name of the original file generated.
    private static final String ENCODED_FILE_NAME  = "encode.txt";  // The name of the encoded file.
    private static final String DECODED_FILE_NAME  = "decode.txt";  // The name of the decoded file.
    private static HuffmanTree huff = null; // The HuffmanTree object that will be used throughout the program.

    public static void main ( String[ ] args ) {
        runProgram ( );
    }

    /**
     * Consolidates the methods within one method for use by main.
     */
    private static void runProgram ( ) {
        // Prompt the user for their web page.
        buildHTMLFile ( );

        // Build the HuffmanTree
        huff = new HuffmanTree ( getFileAsString ( ORIGINAL_FILE_NAME ) );

        // Encode the file to another file.
        createEncodedFile ( );

        // Decode the file to another file.
        createDecodedFile ( );

        // Display the number of bits for each file, and the final compression rate.
        getOriginalFileBits ( );
        getEncodedFileBits ( );
        getDecodedFileBits ( );
        determineCompressionRate ( );
    }

    /**
     * Prompts the user for the URL of the website they would like to render a HTML version of.
     */
    private static void buildHTMLFile ( ) {
        Scanner sc = new Scanner ( System.in ); // Object for taking in user input.
        String userGivenURL; // The URL specified by the user.
        boolean validInput = false; // Controls the exception until the user can enter a valid URL.

        while ( !validInput ) {
            try {
                System.out.print ( "Please enter the URL of the website you would like to take the text from: " );
                userGivenURL = sc.nextLine ( );
                TextFileGenerator.makeCleanFile ( userGivenURL, ORIGINAL_FILE_NAME );
                validInput = true;

            } catch ( IOException e ) {
                System.out.println ( "File could not be created." );
                sc.nextLine ( ); // clear buffer.
            }
        }
    }

    /**
     * Returns a String representation of the file argument.
     *
     * @param fileName The name of the file we want a String representation of.
     * @return The String representation of the file.
     */
    private static String getFileAsString ( String fileName ) {
        Scanner input = null;
        StringBuilder sb = new StringBuilder ( );

        try {
            input = new Scanner ( new FileInputStream ( fileName ) );
        } 
        catch ( FileNotFoundException e ) {
            System.out.println ( fileName + " file not found." );
        }

        while ( input.hasNext ( ) ) {
            sb.append ( input.nextLine () );
        }
        
        input.close ( );
        return sb.toString ( );
    }

    /**
     * Creates an encoded file from the original file.
     */
    private static void createEncodedFile ( ) {
        PrintWriter output = null;

        try {
            output = new PrintWriter ( new FileOutputStream ( ENCODED_FILE_NAME ) );

        } catch ( FileNotFoundException e ) {
            System.out.println ( ENCODED_FILE_NAME + " file not found." );
        }
        output.println ( huff.encode ( getFileAsString ( ORIGINAL_FILE_NAME ) ) );
        output.close ( );
    }

    /**
     * Creates a decoded file from the encoded file.
     */
    private static void createDecodedFile ( ) {
        PrintWriter output = null;

        try {
            output = new PrintWriter ( new FileOutputStream ( DECODED_FILE_NAME ) );
        } 
        catch ( FileNotFoundException e ) {
            System.out.println ( DECODED_FILE_NAME + " file not found." );
        }
        output.println ( huff.decode ( getFileAsString ( ENCODED_FILE_NAME ) ) );
        output.close ( );
    }

    /**
     * Displays and returns the number of bits for the original file created from the URL.
     *
     * @return The number of bits for the original file.
     */
    private static int getOriginalFileBits ( ) {
        return ( ( getFileAsString ( ORIGINAL_FILE_NAME ).length ( ) ) * 16 );
    }

    /**
     * Displays the number of bits for the decoded file (16 bits for each character).
     */
    private static int getDecodedFileBits ( ) {
        return ( ( getFileAsString ( DECODED_FILE_NAME ).length ( ) ) * 16 );
    }

    /**
     * Displays and returns the number of bits for the encoded file (1 bit for each character).
     *
     * @return The number of bits for the encoded file.
     */
    private static int getEncodedFileBits ( ) {
        return ( getFileAsString ( ENCODED_FILE_NAME ).length ( ) );
    }

    /**
     * Displays the rate at which the file was compressed when it became encoded.
     */
    private static void determineCompressionRate ()  {
        System.out.println ( "\nOriginal file bits: " + getOriginalFileBits ( ) );
        System.out.println ( "Encoded file bits: " + getEncodedFileBits ( ) );
        System.out.println ( "Decoded file bits: " + getDecodedFileBits ( ) );
        double compressionRate = ( ( double ) ( getOriginalFileBits ( ) / getEncodedFileBits ( ) ) );
        System.out.printf ( "Compression rate: %.1f%%%n", compressionRate );
    }
}
