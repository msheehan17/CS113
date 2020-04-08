package edu.miracosta.cs113;

/**
 * Interface to provide the behavior for a Binary Search Tree.
 *
 * @author Matthew Sheehan
 * @version 1.0
 *
 */
public interface SearchTree < E > {

    /**
     * Inserts item where is belongs in the tree. Returns true if item is inserted; false if it isn't (already in tree).
     * @param item The item to be added.
     * @return True if the item is added to the tree; false if not.
     */
    boolean add ( E item );

    /**
     * Returns true if target is found in the tree.
     * @param target The target to be found.
     * @return True if the target is found; false if not.
     */
    boolean contains ( E target );

    /**
     * Returns a reference to the data in the node that is equal to targte. If no such node is found, returns null.
     * @param target The target to be found.
     * @return A reference to the found target; null if not found.
     */
    E find ( E target );

    /**
     * Removes target (if found) from tree and returns it; otherwise, returns null;
     * @param target The target to be deleted.
     * @return A reference to the deleted target.
     */
    E delete ( E target );

    /**
     * Removes target (if found) from tree and returns true; otherwise, returns false;
     * @param target The target to be removed.
     * @return True if the target is removed; false if not.
     */
    boolean remove ( E target );
}
