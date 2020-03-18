package edu.miracosta.cs113;

public class BinarySearchTree < E extends Comparable < E > >  extends BinaryTree < E > implements SearchTree < E > {

    protected boolean addReturn; // Return value from recursive add method.
    protected E deleteReturn; // Return value from recursive delete method.

    public BinarySearchTree ( ) {
        super ( );
    }

    @Override // Method implemented with text from the book.
    public boolean add ( E item ) {
        root = add ( root, item );
        return addReturn;
    }

    /**
     * The data field addReturn is set true if the item is added to the tree, false if the item is already in the tree.
     * @param localRoot The local root of the subtree.
     * @param item The object to be inserted.
     * @return The new local root that now contains the inserted item.
     *
     * Method implemented with text from the book.
     */
    private Node < E > add ( Node < E > localRoot, E item ) {
        if ( localRoot == null ) {
            addReturn = true;
            return new Node<> ( item );
        } else if ( item.compareTo ( localRoot.data ) == 0 ) {
            addReturn = false;
            return localRoot;
        } else if ( item.compareTo ( localRoot.data ) < 0 ) {
            localRoot.left = add ( localRoot.left, item );
            return localRoot;
        } else {
            localRoot.right = add ( localRoot.right, item );
            return localRoot;
        }
    }

    @Override
    public boolean contains ( E target ) {
        return ( find ( target ) != null );
    }

    @Override // Method implemented with text from the book.
    public E delete ( E target ) {
        root = delete ( root, target );
        return deleteReturn;
    }

    /**
     * Recursive delete method.
     * @param localRoot The root of the current subtree.
     * @param item The item to be deleted. The modified local root that does not contain the item.
     * @return The modified local root that does not contain the item.
     *
     * Method implemented with text from the book.
     */
    private Node < E > delete ( Node < E > localRoot, E item ) {
        if ( localRoot == null ) {
            deleteReturn = null;
            return localRoot;
        }

        int compResult = item.compareTo ( localRoot.data );
        if ( compResult < 0 ) {
            localRoot.left = delete ( localRoot.left, item );
            return localRoot;
        } else if ( compResult > 0 ) {
            localRoot.right = delete ( localRoot.right, item );
            return localRoot;
        } else {
            deleteReturn = localRoot.data;
            if ( localRoot.left == null )
                return localRoot.right;
            else if ( localRoot.right == null )
                return localRoot.left;
            else {
                if  ( localRoot.left.right == null ) {
                    localRoot.data = localRoot.left.data;
                    localRoot.left = localRoot.left.left;
                    return localRoot;
                } else {
                    localRoot.data = findLargestChild ( localRoot.left );
                    return localRoot;
                }
            }
        }
    }

    @Override // Method implemented with text from the book.
    public E find ( E target ) {
        return find ( root, target );
    }

    /**
     * Recursive find method.
     * @param localRoot The local subtree's root.
     * @param target The object being sought.
     * @return The object, if found, otherwise null.
     *
     * Method implemented with text from the book.
     */
    private E find ( Node < E > localRoot, E target ) {
        if ( localRoot == null )
            return null;

        int compResult = target.compareTo ( localRoot.data );
        if ( compResult == 0 )
            return localRoot.data;
        else if ( compResult < 0 )
            return find ( localRoot.left, target );
        else
            return find ( localRoot.right, target );
    }

    @Override
    public boolean remove ( E target ) {
        return ( delete ( target ) != null );
    }

    /**
     * Find the node that is the inorder predecessor and replace it with its left child (if any).
     * @param parent The parent of possible inorder predecessor (ip).
     * @return The data in the ip.
     *
     * Method implemented with text from the book.
     */
    private E findLargestChild ( Node < E > parent ) {
        if ( parent.right.right == null ) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        } else
            return findLargestChild ( parent.right );
    }
}
