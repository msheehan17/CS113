package edu.miracosta.cs113;

import java.io.Serializable;
import java.util.Scanner;

/**
 * BinaryTree : Class for a binary tree that stores objects of type E. Code derived from Koffman & Wolfgang's Data
 * Structures: Abstraction and Design Using Java (2nd).
 *
 * @param <E> Generic to hold any data type
 */
public class BinaryTree < E > implements Serializable {
    /** The root node of this tree. */
    protected Node < E > root;

    /**
     * Default constructor to build an empty BinaryTree.
     */
    BinaryTree ( ) {
        root = null;
    }

    /**
     * Constructor to build a BinaryTree with the given Node shallow copied and stored as the root.
     *
     * @param root the root node of this tree
     */
    protected BinaryTree (Node < E > root) {
        this.root = root;
    }

    /**
     * Full constructor to build a BinaryTree from provided data (for root) and left + right children/subtrees.
     *
     * @param data datum stored for this tree's root node
     * @param leftTree left subtree of root
     * @param rightTree right subtree of root
     */
    BinaryTree( E data, BinaryTree < E > leftTree, BinaryTree < E > rightTree) {
        // Set root to a new Node with the given data
        root = new Node < > ( data );

        root.left = ( leftTree == null ) ? null : leftTree.root;    // Set left subtree
        root.right = ( rightTree == null ) ? null : rightTree.root; // Set left subtree
    }

    /**
     * Returns the left subtree.
     *
     * @return The left subtree, or null if either the root or left subtree is null
     */
    BinaryTree < E > getLeftSubtree ( ) {
        return ( root != null && root.left != null ) ? new BinaryTree < > ( root.left ) : null;
    }

    /**
     * Returns the right subtree.
     *
     * @return The right subtree, or null if either the root or left subtree is null
     */
    BinaryTree<E> getRightSubtree ( ) {
        return ( root != null && root.right != null ) ? new BinaryTree < > ( root.right ) : null;
    }

    /**
     * Determines whether this tree is a leaf.
     *
     * @return true if the root has no children
     */
    boolean isLeaf ( ) {
        return ( root.left == null && root.right == null );
    }

    /**
     * Returns the data associated with the root Node.
     *
     * @return The data stored in the root
     */
    E getData ( ) {
        return root.data;
    }

    /**
     * Constructs a binary tree by reading its data with the given Scanner object.
     *
     * pre: The input consists of a pre-order traversal of the binary tree, with each Node on its own line.
     * The line "null" indicates a null tree.
     *
     * @param scan The Scanner attached to the input file
     * @return The binary tree constructed from the given input
     */
    static BinaryTree < String > readBinaryTree (Scanner scan ) {
        // Read a line and trim leading and trailing spaces.
        if ( ! scan.hasNext ( ) )
            return null;
        else {
            String data = scan.next( );

            if ( data.equals ( "null" ) )
                return null;
            else {
                BinaryTree < String > leftTree  = readBinaryTree ( scan );
                BinaryTree < String > rightTree = readBinaryTree ( scan );
                return new BinaryTree < > (data, leftTree, rightTree);
            }
        }
    }

    /**
     * Performs a pre-order traversal of this tree.
     *
     * @param node The local root
     * @param depth The current level in depth of this tree
     * @param sb The String buffer which accumulates the output
     */
    private void preOrderTraverse( Node < E > node, int depth, StringBuilder sb ) {
        for ( int i = 1; i < depth; i++)
            sb.append ( " " );

        if ( node == null )
            sb.append ( "null\n" );
        else {
            sb.append ( node.toString( ) + "\n" );

            preOrderTraverse ( node.left, depth+1, sb );
            preOrderTraverse ( node.right, depth+1, sb );
        }
    }

    @Override
    public String toString ( ) {
        StringBuilder sb = new StringBuilder ( );
        preOrderTraverse(root, 1, sb);
        return sb.toString ( );
    }

    /**
     * The inner class for the BinaryTree, a specialized node which may hold any data type.
     *
     * @param <E> Generic to hold any data type
     */
    protected static class Node < E > implements Serializable {
        /** The constituent data for this Node. */
        protected E data;
        /** The Node's left/right subtree. */
        protected Node<E> left, right;

        /**
         * Constructor which stores the given data in this Node.
         *
         * @param data The data to hold within the node
         */
        Node(E data) {
            this.data = data;
        }

        @Override
        public String toString ( ) {
            return data.toString ( );
        }
    } // End of class Node
} // End of class BinaryTree