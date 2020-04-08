package edu.miracosta.cs113;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * DoubleLinkedList.java - My implementation of a doubly-linked list. As suggested by the HW prompt, some methods
 * implemented in this class come from the textbook/classroom power points.
 *
 * Note: No JavaDoc is given for methods as they are all overrode.
 *
 * @author: Matthew Sheehan
 * @version: 1.0
 *
 */
class DoubleLinkedList < E > implements List < E > {

    private Node < E > head, tail;
    private int size = 0;

    private static class Node < E > {
        private E data;
        private Node<E> next, prev;

        private Node ( E data ){
            this.data = data;
        }
    }

    private class DoubleListIterator implements ListIterator < E > {

        private Node < E > nextItem, lastItemReturned;
        private int index = 0;

        /**
         * Full constructor.
         *
         * @param i The index to begin the iterator.
         */
        DoubleListIterator ( int i ) throws IndexOutOfBoundsException {
            if ( i < 0 || i > size )
                throw new IndexOutOfBoundsException ( "Cannot access index  " + i +"\n" );

            if ( i == size ) {
                index = size;
                nextItem = null;
            } else {
                nextItem = head;
                for ( index = 0; index < i; index++ )
                    nextItem = nextItem.next;
            }
        }

        @Override
        public void add ( E element ) throws NullPointerException {
            if ( element == null )
                throw new NullPointerException ( "Null Element.\n" );

            if ( head == null ) { // List is empty (no head).
                head = new Node < > ( element );
                tail = head;
            } else if ( nextItem == head ) { // List iterator is at the beginning of the list.
                Node < E > newNode = new Node < > ( element );
                newNode.next = nextItem;
                nextItem.prev = newNode;
                head = newNode;
            } else if ( nextItem == null ) { // List iterator is at the end of the list.
                Node < E > newNode = new Node < > ( element );
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            } else { // List iterator is not at the beginning, or the end.
                Node<E> newNode = new Node < > ( element );
                newNode.prev = nextItem.prev;
                nextItem.prev.next = newNode;
                newNode.next = nextItem;
                nextItem.prev = newNode;
            }
            index++;
            size++;
        }

        @Override
        public boolean hasNext () {
            return ( nextItem != null );
        }

        @Override
        public boolean hasPrevious () {
            return ( head != null && nextItem.prev != null );
            //return ( ( nextItem == null && size != 0 ) ||  );
        }

        @Override
        public E next ( ) throws NoSuchElementException {
            if ( ! hasNext ( ) )
                throw new NoSuchElementException ( "Element does not have a value.\n" );

            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        @Override
        public int nextIndex ( ) {
            return index;
        }

        @Override // Note: Method implemented using syntax from book.
        public E previous ( ) throws NoSuchElementException {
            if ( ! hasPrevious ( ) )
                throw new NoSuchElementException ( "Element does not have a value.\n" );

            nextItem = ( nextItem == null ) ? tail : nextItem.prev;
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        @Override
        public int previousIndex () {
            return ( index - 1 );
        }

        @Override
        public void remove ( ) throws IllegalStateException {
            if ( head == null || lastItemReturned == null )
                throw new IllegalStateException ( "No Elements in the List.\n" );

            if ( lastItemReturned.prev == null ) { // First element in the list.
                if ( lastItemReturned.next == null ) { // First, and only element in the list.
                    clear ( );
                    size++; // Counter the size decrement operator after the loop.
                } else {
                    lastItemReturned.next.prev = head;
                    head = lastItemReturned.next;
                }
            } else if ( lastItemReturned.next == null ) { // Last element in the list.
                lastItemReturned.prev.next = null;
                tail = lastItemReturned.prev;
            } else {
                lastItemReturned.prev.next = lastItemReturned.next;
                lastItemReturned.next.prev = lastItemReturned.prev;
            }
            index--; // Move the cursor back one after the node has been deleted.
            size--;  // Size has gone down one element.
        }

        @Override
        public void set ( E element ) throws NullPointerException, IllegalStateException {
            if ( element == null )
                throw new NullPointerException ( "Null Element.\n" );

            if ( lastItemReturned == null )
                throw new IllegalStateException ( );

            lastItemReturned.data = element;
        }
    }

    @Override
    public Iterator < E > iterator ( ) {
        return new DoubleListIterator ( 0 );
    }

    @Override
    public ListIterator < E > listIterator ( ) {
        return new DoubleListIterator ( 0 );
    }

    @Override
    public ListIterator < E > listIterator ( int i ) {
        return new DoubleListIterator ( i );
    }

    @Override
    public boolean add ( E element ) throws NullPointerException {
        if ( element == null )
            throw new NullPointerException ( "Null Element.\n" );

        listIterator ( size ).add ( element );
        return true;
    }

    @Override
    public void add ( int i, E element ) throws NullPointerException {
        if ( element == null )
            throw new NullPointerException ( "Null Element.\n" );

        listIterator ( i ).add ( element );
    }

    @Override
    public void clear () {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean equals ( Object o ) {
        DoubleLinkedList otherList = null;

        if ( o == null ) { // Null check.
            return false;
        } else if ( this.getClass ( ) != o.getClass ( ) ) { // Class check.
            return false;
        } else if ( this == o ) { // Memory check.
            return true;
        } else { // Size, then variable check.
            otherList = ( DoubleLinkedList < E > ) o;

            if ( this.size ( ) != otherList.size ( ) ) { // Check that lists are the same size.
                return false;
            } else { // Check that the list contains the same elements.
                DoubleListIterator myIterator = new DoubleListIterator ( 0 );

                while ( myIterator.hasNext ( ) ) {
                    if ( ! otherList.contains ( myIterator.next () ) )
                        return false;
                }
                return true;
            }
        }
    }

    @Override
    public boolean contains ( Object o ) throws NullPointerException {
        if ( o == null )
            throw new NullPointerException ( "Null Object.\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( 0 );

        while ( myIterator.hasNext () ) {
            if ( o.equals ( myIterator.next ( ) ) )
                return true;
        }
        return false;
    }

    @Override
    public E get ( int index ) throws IndexOutOfBoundsException {
        DoubleListIterator myIterator = new DoubleListIterator ( index );

        if ( ! myIterator.hasNext () || index >= size )
            throw new IndexOutOfBoundsException ("Cannot access index " + index + ".\n");

        return listIterator ( index ).next ();
    }

    @Override
    public int indexOf ( Object o ) throws NullPointerException {
        if ( o == null )
            throw new NullPointerException ( "Null Object.\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( 0 );

        while ( myIterator.hasNext ( ) ) {
            if ( o.equals ( myIterator.next () ) )
                return myIterator.previousIndex ();
        }
        return -1;
    }

    @Override
    public int lastIndexOf ( Object o ) throws NullPointerException {
        if ( o == null )
            throw new NullPointerException ( "Null Object.\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( size );

        while ( myIterator.hasPrevious ( ) ) {
            if ( o.equals ( myIterator.previous ( ) ) )
                return myIterator.nextIndex ( );
        }
        return -1;
    }

    @Override
    public boolean isEmpty ( ) {
        return ( size == 0 );
    }

    @Override
    public E remove ( int i ) throws IndexOutOfBoundsException {
        if ( i < 0 || i >= size )
            throw new IndexOutOfBoundsException ( "Cannot access index " + i + "\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( i );

        E deletedNode = myIterator.next ( );

        myIterator.remove ( );

        return deletedNode;
    }

    @Override
    public boolean remove ( Object o ) {
        boolean found = false; // Determines if the object was found in the list.

        DoubleListIterator myIterator = new DoubleListIterator ( 0 );

        while ( myIterator.hasNext ( ) ) {
            if ( o.equals ( myIterator.next ( ) ) ) {
                remove ( myIterator.previousIndex ( ) );
                found = true;
            }
        }
        return found;
    }

    @Override
    public E set ( int i, E element ) throws NullPointerException {
        if ( element == null )
            throw new NullPointerException ( "Null Element.\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( i );

        E deletedNode = get ( i );

        myIterator.next ( );
        myIterator.set ( element );

        return deletedNode;
    }

    @Override
    public int size ( ) {
        return size;
    }

    @Override
    public String toString() {
        if ( head == null )
            return "[]";

        StringBuilder sb = new StringBuilder ( "[" );

        DoubleListIterator myIterator = new DoubleListIterator ( 0 );

        while ( myIterator.hasNext () ) {
            sb.append ( myIterator.next ( ) );
            if ( myIterator.hasNext () )
                sb .append ( ", " );
        }
        return sb.append ( "]" ).toString ( );
    }


    /* The methods below were implemented as they are part of the List interface,
     * however, these methods will not be used and instead kept as stub code.
     */


    @Override
    public List subList ( int fromIndex, int toIndex ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public Object [ ] toArray ( ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public boolean addAll ( Collection c ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public boolean addAll ( int index, Collection c ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public boolean containsAll ( Collection c ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public boolean removeAll ( Collection c ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public void replaceAll ( UnaryOperator operator ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public boolean retainAll ( Collection c ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public Object[] toArray ( Object [ ] a ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public void sort ( Comparator c ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }

    @Override
    public Spliterator spliterator ( ) throws UnsupportedOperationException {
        throw new UnsupportedOperationException (  );
    }
}
