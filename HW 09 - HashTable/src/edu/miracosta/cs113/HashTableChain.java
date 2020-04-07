package edu.miracosta.cs113;

import java.util.*;

/**
 * HashTableChain.java - My implementation of a chaining hash table.
 *
 * Instance Variables:
 *      - table (LinkedList<Entry<K, V>> []): An array of LinkedList, where each LinkedList contains Entry pairs.
 *      - numKeys (int): The number of mappings in the table (pairs).
 *      - CAPACITY (int): The size of the array.
 *      - LOAD_THRESHOLD (double): To ensure efficiency, LinkedList will be limited to three elements (LOAD_THRESHOLD).
 *
 * Methods:
 *      - rehash: When any LinkedList within the table has reached it's load threshold, another array of LinkedLists
 *        will be created with twice the size (plus 1) of the original table.
 *      - All other methods are overridden and inherit documentation.
 *
 * @author Matthew Sheehan
 * @version 1.0
 *
 */
public class HashTableChain < K, V >  implements Map < K, V > {

    private LinkedList < Entry < K, V > >[ ] table; // The table.
    private int numKeys; // The number of keys (entries) in the table.
    private static final int CAPACITY = 101; // The size of the table.
    private static final double LOAD_THRESHOLD = 3.0; // The max load factor.

    /**
     * Inner class that creates the key-value objects.
     * @param <K> The key.
     * @param <V> The value.
     */
    private static class Entry < K, V >  implements Map.Entry < K, V > {

        private K key;   // The key.
        private V value; // The value.

        /**
         * Creates a new key-value pair.
         * @param key The key.
         * @param value The value.
         */
        Entry ( K key, V value ) {
            this.key = key;
            this.value = value;
        }

        /**
         * Retrieves the key.
         * @return The key.
         */
        public K getKey ( ) {
            return key;
        }

        /**
         * Retrieves the value.
         * @return The value.
         */
        public V getValue ( ) {
            return value;
        }

        /**
         * Sets the new value.
         * @param val The new value.
         * @return The old value.
         */
        public V setValue ( V val ) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

        /**
         * Returns a String representation of the Entry.
         * @return A String representation of the Entry.
         */
        public String toString ( ) {
            return key + "=" + value;
        }
    }

    /**
     * Provides a set view of the underlying hash map.
     */
    private class EntrySet extends AbstractSet < Map.Entry < K , V > > {

        @Override
        public int size  ( ) {
            return numKeys;
        }

        @Override
        public Iterator < Map.Entry < K, V > > iterator ( ) {
            return new SetIterator ( );
        }
    }

    /**
     * The iterator for traversing the hash table.
     */
    private class SetIterator implements Iterator < Map.Entry < K, V > > {

        private int index ;
        private Entry < K, V > lastItemReturned ;
        private Iterator <Entry < K, V > > myIterator ;

        @Override
        public boolean hasNext ( ) {
            if ( myIterator != null ) {
                if ( myIterator.hasNext () )
                    return true;
                else {
                    lastItemReturned = null;
                    index++;
                }
            }

            while ( index < table.length && table [ index ] == null )
                index++;

            if ( index == table.length )
                return false;

            myIterator = table [ index ].iterator ( );
            return myIterator.hasNext ( );
        }

        @Override
        public Map.Entry < K, V > next( ) throws NoSuchElementException {
           if ( ! myIterator.hasNext ( ) )
               throw new NoSuchElementException (  );

           return lastItemReturned = myIterator.next ( );
        }

        @Override
        public void remove( ) throws IllegalStateException {
            if ( lastItemReturned == null )
                throw new IllegalStateException (  );

            myIterator.remove ();
            lastItemReturned = null;
        }
    }

    @SuppressWarnings ( "unchecked" )
    public HashTableChain ( ) {
        table = new LinkedList [ CAPACITY ];
    }

    @Override
    public void clear ( ) {
        for ( int i = 0; i < table.length; i++ )
            table [ i ] = null;

        numKeys = 0;
    }

    @Override
    public boolean containsKey ( Object key ) {
        int index = ( key.hashCode () % table.length );
        if ( index < 0 )
            index += table.length;
        if ( table [ index ] == null )
            return false;

        for ( Entry < K, V > mapping : table [ index ] ) {
            if ( mapping.key.equals ( key ) )
                return true;
        }
        return false;
    }

    @Override
    public boolean containsValue ( Object value ) {
        for ( LinkedList < Entry < K, V > > list : table ) {
            if ( list != null ) {
                for ( Entry<K, V> mapping : list ) {
                    if ( mapping.value.equals ( value ) )
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set < Map.Entry < K, V > > entrySet ( ) {
        return new EntrySet ( );
    }

    @Override
    @SuppressWarnings ( "unchecked" )
    public boolean equals ( Object o ) {
        // Null check, class check.
        if ( o == null || this.getClass ( ) != o.getClass ( ) )
            return false;
        // Memory check.
        else if ( this == o )
            return true;

        HashTableChain < K, V > otherTable = ( HashTableChain < K, V > ) o;

        return ( this.keySet ( ).equals ( otherTable.keySet ( ) ) );
    }

    @Override // Method implemented from text in the book.
    public V get ( Object key ) {
        int index = ( key.hashCode ( ) % table.length );
        if ( index < 0 )
            index += table.length;
        if ( table [ index ] == null )
            return null;

        for ( Entry < K, V > nextItem : table [ index ] ) {
            if ( nextItem.key.equals ( key ) )
                return nextItem.value;
        }
        return null;
    }

    @Override
    public int hashCode ( ) {
        int sum = 0; // The sum of the hash codes.

        for ( Map.Entry < K, V > element : entrySet ( ) )
            sum += ( element.getKey ( ).hashCode ( ) + element.getValue ( ).hashCode ( ) );

        return sum;
    }

    @Override
    public boolean isEmpty ( ) {
        return ( numKeys == 0 );
    }

    @Override
    @SuppressWarnings ( "unchecked" )
    public Set keySet ( ) {
        Set < K > keySet = new HashSet < > ( size ( ) );

        for ( LinkedList < Entry < K, V > > list : table ) {
            if ( list != null ) {
                for ( Entry < K, V > entry : list )
                    keySet.add ( entry.key );
            }
        }
        return keySet;
    }

    @Override // Method implemented from text in the book.
    public V put ( K key, V value ) {
        int index = ( key.hashCode ( ) % table.length );
        if ( index < 0 )
            index += table.length;
        if ( table [ index ] == null )
            table [ index ] = new LinkedList < > (  );

        for ( Entry < K, V > nextItem : table [ index ] ) {
            if ( nextItem.key.equals ( key ) ) {
                V oldVal = nextItem.value;
                nextItem.setValue ( value );
                return oldVal;
            }
        }

        table [ index ].addFirst ( new Entry < > ( key, value ) );
        numKeys++;
        if ( numKeys > ( LOAD_THRESHOLD * table.length ) )
            rehash ();
        return null;
    }

    @Override
    public void putAll ( Map m ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public V remove ( Object key ) {
        int index = ( key.hashCode () % table.length );
        if ( index < 0 )
            index += table.length;
        if ( table [ index ] == null )
            return null;

        for ( Entry<K, V> mapping : table [ index ] ) {
            if ( mapping.key.equals ( key ) ) {
                V oldVal = mapping.value;
                table [ index ].remove ( mapping );
                numKeys--;
                // Table no longer has any mappings, set to null.
                if ( table [ index ].isEmpty ( ) )
                    table [ index ] = null;

                return oldVal;
            }
        }
        return null;
    }

    @Override
    public int size ( ) {
        return numKeys;
    }

    @Override
    public Collection < V > values ( ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    /**
     * When a LinkedList in the array has reached load factor, rehash will create a larger hash table and store
     * the entries of each non-null LinkedList into the new table.
     */
    @SuppressWarnings ( "unchecked" )
    private void rehash ( ) {
        LinkedList < Entry < K, V > > [ ] oldTable = table;
        table = new LinkedList [ oldTable.length * 2 + 1 ];

        numKeys = 0; // Reset the keys.

        // Take every LinkedList in the array and if it isn't null add its nodes (entries) to the hash table.
        for ( LinkedList < Entry < K, V > > list : oldTable ) {
            if ( list != null ) {
                for ( Entry<K, V> item : list )
                    put ( item.key, item.value );
            }
        }
    }
}