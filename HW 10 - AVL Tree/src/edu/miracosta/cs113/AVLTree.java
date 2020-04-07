package edu.miracosta.cs113;

public class AVLTree < E extends Comparable < E > > extends BinarySearchTreeWithRotate < E > {

    /**
     * Class to represent an AVL Node. It extends the BinaryTree.Node by adding the balance field.
     *
     * Class implemented with text from the book.
     */
    private static class AVLNode < E > extends Node < E > {

        private static final int LEFT_HEAVY = -1; // Constant to indicate left heavy.
        private static final int BALANCED = 0;    // Constant to indicate balanced.
        private static final int RIGHT_HEAVY = 1; // Constant to indicate right heavy.
        private int balance; // Right subtree height - left subtree height.

        /**
         * Constructs a node with the given item as the data field.
         * @param item The data field.
         *
         * Constructor implemented with text from the book.
         */
        AVLNode ( E item ) {
            super ( item );
            balance = BALANCED;
        }

        @Override
        public String toString ( ) {
            return balance + ": " + super.toString ();
        }
    }

    private boolean increase; // Indicates height of tree has increased.

    /**
     * Calls the recursive add method for insertion into the AVL tree.
     * @param item The item being inserted.
     * @return True if the object is inserted; false if the object already exists in the tree.
     *
     * Method implemented with text from the book.
     */
    public boolean add ( E item ) {
        increase = false;
        root = add ( ( AVLNode < E > ) root, item );
        return addReturn;
    }

    /**
     * Inserts the given argument into the tree.
     * @param localRoot The local root of the subtree.
     * @param item The object to be inserted.
     * @return The new local root of the subtree with the item inserted.
     *
     * Method implemented with text from the book, with modifications.
     */
    private AVLNode < E > add ( AVLNode < E > localRoot, E item ) {
       // Nothing in the tree, create a new tree with the item as the root.
        if ( localRoot == null ) {
           addReturn = true;
           increase = true;
           return new AVLNode < > ( item );
       }

       // Item already exists in the ree.
       if ( item.compareTo ( localRoot.data ) == 0 ) {
           increase = false;
           addReturn = false;
           return localRoot;
       // Item is less than the root, recursively check the left subtree.
       } else if ( item.compareTo ( localRoot.data ) < 0 ) {
           localRoot.left = add ( ( AVLNode < E > ) localRoot.left, item );
           // If an item was added, check the balance.
           if ( increase ) {
               decrementBalance ( localRoot );
               // If the tree is left heavy, rebalance left.
               if ( localRoot.balance < AVLNode.LEFT_HEAVY ) {
                   increase = false;
                   return rebalanceLeft ( localRoot );
               }
           }
       // Item is greater than the root, recursively check the right subtree.
       } else if ( item.compareTo ( localRoot.data ) > 0 ) {
           localRoot.right = add ( (AVLNode < E > ) localRoot.right, item );
           // If an item was added, check the balance.
           if ( increase ) {
               incrementBalance ( localRoot );
               // If the tree is right heavy, rebalance right.
               if ( localRoot.balance > AVLNode.RIGHT_HEAVY ) {
                   increase = false;
                   return rebalanceRight ( localRoot );
               }
           }
       }
       return localRoot;
    }

    /**
     * Method to rebalance left.
     * @param localRoot THe root of an AVL subtree that is critically left heavy.
     * @return A new localRoot.
     *
     * Method implemented with text from the book, with modifications.
     */
    private AVLNode < E > rebalanceLeft ( AVLNode < E > localRoot  ) {
        AVLNode < E > leftChild = ( AVLNode < E > ) localRoot.left;
        // Left-right imbalance ( Parent -2, Child +1 ).
        if ( leftChild.balance > AVLNode.BALANCED ) {
            AVLNode < E > leftRightChild = ( AVLNode < E > ) leftChild.right;

            if ( leftRightChild.balance < AVLNode.BALANCED ) {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } else {
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            localRoot.left = rotateLeft ( leftChild );

        } else {
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        return ( AVLNode < E > ) rotateRight ( localRoot );
    }

    /**
     * As we return from an insertion into a node's left subtree, we need to decrement the balance of the node.
     * @param node The node's who's balance needs to be decremented.
     *
     * Method implemented with text from the book.
     */
    private void decrementBalance ( AVLNode < E > node ) {
        node.balance--;
        if ( node.balance == AVLNode.BALANCED )
            increase = false;
    }

    /**
     * Method to rebalance left.
     * @param localRoot THe root of an AVL subtree that is critically left heavy.
     * @return A new localRoot.
     */
    private AVLNode < E > rebalanceRight ( AVLNode < E > localRoot ) {
        AVLNode < E > rightChild = ( AVLNode < E > ) localRoot.right;
        if ( rightChild.balance > AVLNode.BALANCED ) {
            AVLNode < E > rightLeftChild = ( AVLNode < E > ) rightChild.right;
            if ( rightLeftChild.balance < AVLNode.BALANCED ) {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            } else {
                rightChild.balance = AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            localRoot.right = rotateRight ( rightChild );

        } else {
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        return ( AVLNode < E > ) rotateLeft ( localRoot );
    }

    /**
     * As we return from an insertion into a node's right subtree, we need to increment the balance of the node.
     * @param node The node who's balance needs to be incremented.
     */
    private void incrementBalance ( AVLNode < E > node ) {
        node.balance++;
        if ( node.balance == AVLNode.BALANCED )
            increase = false;
    }
}
