package edu.miracosta.cs113;

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


import java.util.*;
import java.util.function.UnaryOperator;

public class DoubleLinkedList<E> implements List<E> {

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;


    private static class Node<E> {

        private E data;
        private Node<E> next;
        private Node<E> prev;

        private Node ( E data ){

            this.data = data;
        }
    }


    private class DoubleListIterator implements ListIterator<E> {

        private Node<E> nextItem;
        private Node<E> lastItemReturned;
        private int index = 0;


        /**
         * Full constructor.
         *
         * @param i The index to begin the iterator.
         */
        public DoubleListIterator ( int i ) {

            if ( i < 0 || i > size )
                throw new IndexOutOfBoundsException ( "Cannot access index  " + i +"\n" );

            lastItemReturned = null;

            if ( i == size ) {

                index = size;
                nextItem = null;

            } else {

                nextItem = head;

                for ( index = 0; index < i; index++ )
                    nextItem = nextItem.next;
            }
        }


        @Override // Note: Method implemented using syntax from book.
        public void add ( E element ) {

            if ( element == null )
                throw new NullPointerException ( "Null Element.\n" );

            // List is empty (no head).
            if ( head == null ) {

                head = new Node<>( element );
                tail = head;

            // List iterator is at the beginning of the list.
            } else if ( nextItem == head ) {

                Node<E> newNode = new Node<>( element );
                newNode.next = nextItem;
                nextItem.prev = newNode;
                head = newNode;

            // List iterator is at the end of the list.
            } else if ( nextItem == null ) {

                Node<E> newNode = new Node<>( element );
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;

            // List iterator is not at the beginning, or the end.
            } else {

                Node<E> newNode = new Node<> ( element );
                newNode.prev = nextItem.prev;
                nextItem.prev.next = newNode;
                newNode.next = nextItem;
                nextItem.prev = newNode;
            }

            index++;
            size++;
        }


        @Override // Note: Method implemented using syntax from book.
        public boolean hasNext () {

            return ( nextItem != null );
        }


        @Override // Note: Method implemented using syntax from book (with slight modifications to pass JUnit).
        public boolean hasPrevious () {

            if ( head == null )
                return false;

            return ( ( nextItem == null && size != 0 ) || nextItem.prev != null );
        }


        @Override // Note: Method implemented using syntax from book.
        public E next () {

            if ( ! hasNext () )
                throw new NoSuchElementException ( "Element does not have a value.\n" );

            lastItemReturned = nextItem;
            nextItem = nextItem.next;

            index++;

            return lastItemReturned.data;
        }


        @Override
        public int nextIndex () {

            return index;
        }


        @Override // Note: Method implemented using syntax from book.
        public E previous () {

            if ( ! hasPrevious () )
                throw new NoSuchElementException ( "Element does not have a value.\n" );

            if ( nextItem == null ) {

                nextItem = tail;

            } else {

                nextItem = nextItem.prev;
            }

            lastItemReturned = nextItem;

            index--;

            return lastItemReturned.data;
        }


        @Override
        public int previousIndex () {

            return ( index - 1 );
        }


        @Override
        public void remove () {

            if ( head == null || lastItemReturned == null )
                throw new IllegalStateException ( "No Elements in the List.\n" );

            // First element in the list.
            if ( lastItemReturned.prev == null ) {

                // First, and only element in the list.
                if ( lastItemReturned.next == null ) {

                    clear ();
                    size++; // Counter the size decrement operator after the loop.

                } else {

                    lastItemReturned.next.prev = head;
                    head = lastItemReturned.next;
                }

            // Last element in the list.
            } else if ( lastItemReturned.next == null ) {

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
        public void set ( E element ) {

            if ( element == null )
                throw new NullPointerException ( "Null Element.\n" );

            if ( lastItemReturned == null )
                throw new IllegalStateException ();

            lastItemReturned.data = element;
        }

    } // End DoubleListIterator class.


    @Override
    public Iterator<E> iterator () {

        return new DoubleListIterator ( 0 );
    }


    @Override
    public ListIterator<E> listIterator () {

        return new DoubleListIterator ( 0 );
    }


    @Override
    public ListIterator<E> listIterator ( int i ) {

        return new DoubleListIterator ( i );
    }


    @Override
    public boolean add ( E element ) {

        if ( element == null )
            throw new NullPointerException ( "Null Element.\n" );

        listIterator ( size ).add ( element );
        return true;
    }


    @Override // Note: Method implemented using syntax from book.
    public void add ( int i, E element ) {

        if ( element == null )
            throw new NullPointerException ( "Null Element.\n" );

        listIterator ( i ).add ( element );
    }


    @Override
    public void clear () {

        head = null;
        tail = null;
        size = 0;
    }


    @Override
    public boolean equals ( Object o ) {

        DoubleLinkedList otherList = null;

        // Null check.
        if ( o == null ) {

            return false;

        // Class check.
        } else if ( this.getClass () != o.getClass () ) {

            return false;

        // Memory check.
        } else if ( this == o ) {

            return true;

        // Size, then variable check.
        } else {

            otherList = ( DoubleLinkedList<E> ) o;

            // Check that lists are the same size.
            if ( this.size () != otherList.size () ) {

                return false;

            // Check that the list contains the same elements.
            } else {

                DoubleListIterator myIterator = new DoubleListIterator ( 0 );

                while ( myIterator.hasNext () ) {

                    if ( ! otherList.contains ( myIterator.next () ) )
                        return false;
                }

                return true;
            }
        }
    }


    @Override
    public boolean contains ( Object o ) {

        if ( o == null )
            throw new NullPointerException ( "Null Object.\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( 0 );

        while ( myIterator.hasNext () ) {

            if ( o.equals ( myIterator.next () ) )
                return true;
        }

        return false;
    }


    @Override // Note: Method implemented using syntax from book (with slight modifications to pass JUnit).
    public E get ( int index ) {

        DoubleListIterator myIterator = new DoubleListIterator ( index );

        if ( ! myIterator.hasNext () || index >= size )
            throw new IndexOutOfBoundsException ("Cannot access index " + index + ".\n");

        return listIterator ( index ).next ();
    }


    @Override
    public int indexOf ( Object o ) {

        if ( o == null )
            throw new NullPointerException ( "Null Object.\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( 0 );

        while ( myIterator.hasNext () ) {

            if ( o.equals ( myIterator.next () ) )
                return myIterator.previousIndex ();
        }

        return -1;
    }


    @Override
    public int lastIndexOf ( Object o ) {

        if ( o == null )
            throw new NullPointerException ( "Null Object.\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( size );

        while ( myIterator.hasPrevious () ) {

            if ( o.equals ( myIterator.previous () ) )
                return myIterator.nextIndex ();
        }

        return -1;
    }


    @Override
    public boolean isEmpty () {

        return ( size == 0 );
    }


    @Override
    public E remove ( int i ) {

        if ( i < 0 || i >= size )
            throw new IndexOutOfBoundsException ( "Cannot access index " + i + "\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( i );

        E deletedNode = myIterator.next ();

        myIterator.remove ();

        return deletedNode;
    }


    @Override
    public boolean remove ( Object o ) {

        boolean found = false; // Determines if the object was found in the list.

        DoubleListIterator myIterator = new DoubleListIterator ( 0 );

        while ( myIterator.hasNext () ) {

            if ( o.equals ( myIterator.next () ) ) {
                remove ( myIterator.previousIndex () );
                found = true;
            }
        }

        return found;
    }


    @Override
    public E set ( int i, E element ) {

        if ( element == null )
            throw new NullPointerException ( "Null Element.\n" );

        DoubleListIterator myIterator = new DoubleListIterator ( i );

        E deletedNode = get ( i );

        myIterator.next ();
        myIterator.set ( element );

        return deletedNode;
    }


    @Override
    public int size () {

        return size;
    }


    @Override
    public String toString() {

        if ( head == null )
            return "[]";

        String result = "[";

        DoubleListIterator myIterator = new DoubleListIterator ( 0 );

        while ( myIterator.hasNext () ) {

            result += myIterator.next ();

            if ( myIterator.hasNext () )
                result += ", ";
        }

        result += "]";

        return result;
    }


    /* The methods below were implemented as they are part of the List interface,
     * however, these methods will not be used and instead kept as stub code.
     */


    @Override
    public List subList ( int fromIndex, int toIndex ) {

        return null;
    }

    @Override
    public Object[] toArray () {

        return null;
    }

    @Override
    public boolean addAll ( Collection c ) {
        return false;
    }

    @Override
    public boolean addAll ( int index, Collection c ) {
        return false;
    }

    @Override
    public boolean containsAll ( Collection c ) {
        return false;
    }

    @Override
    public boolean removeAll ( Collection c ) {
        return false;
    }

    @Override
    public void replaceAll ( UnaryOperator operator ) {
    }

    @Override
    public boolean retainAll ( Collection c ) {
        return false;
    }

    @Override
    public Object[] toArray ( Object[] a ) {
        return null;
    }

    @Override
    public void sort ( Comparator c ) {
    }

    @Override
    public Spliterator spliterator () {
        return null;
    }
}
