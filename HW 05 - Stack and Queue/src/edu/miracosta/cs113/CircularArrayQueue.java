package edu.miracosta.cs113;

/**
 * CircularArrayQueue.java - My implementation of a CircularArrayQueue. Note: Some methods are implemented from the book.
 *
 * Instance Variables:
 *
 *      - theData (E[]): The underlying array that will hold each element added to the queue.
 *      - capacity (int): The amount of elements the array can hold.
 *      - front (int): The index of the first element in the array.
 *      - rear (int): The index of the last element in the array.
 *      - size (int): The number of elements in the array.
 *
 * Methods:
 *
 *      - Constructors:
 *          - One full constructor, takes an integer argument for setting the initial capacity of the array.
 *      - reallocate: Creates a new array with twice the capacity of the original array and copies information from the old array into
 *        a new one.
 *      - Overridden methods will use the same documentation from the super interface.
 *
 * @author Matthew Sheehan
 * @version 1.0
 *
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CircularArrayQueue<E> implements Queue<E> {

    private E[] theData; // The underlying array that will hold the information.
    private int capacity, front, rear, size; // The characteristics of the CircularArrayQueue.

    public CircularArrayQueue ( int initialCapacity ) {

        if ( initialCapacity < 0 )
            throw new IllegalArgumentException (  );

        capacity = initialCapacity;

        theData = ( E[] ) new Object [ capacity ];

        front = 0;

        rear = capacity - 1;

        size = 0;
    }

    @Override
    public boolean add ( E element ) throws NullPointerException {

        if ( element == null )
            throw new NullPointerException ( "Null element.\n" );

        if ( size == capacity )
            reallocate ();

        size++;
        rear = ( rear + 1 ) % capacity;
        theData [ rear ] = element;
        return true;
    }


    @Override
    public E element () throws NoSuchElementException {

        if ( size == 0 )
            throw new NoSuchElementException ();

        return theData [ front ];
    }


    @Override // Method implemented with syntax from book, with some modifications.
    public boolean offer ( E element ) throws NullPointerException {

        if ( element == null )
            throw new NullPointerException ( "Null element.\n" );

        if ( size == capacity )
            reallocate ();

        size++;
        rear = ( rear + 1 ) % capacity;
        theData [ rear ] = element;
        return true;
    }


    @Override
    public E peek () {

        if ( size == 0 )
            return null;

        return theData [ front ];
    }


    @Override // Method implemented with syntax from book.
    public E poll () {

        if ( size == 0 )
            return null;

        E result = theData [ front ];
        front = ( front + 1 ) % capacity;
        size--;
        return result;
    }


    @Override
    public E remove () throws NoSuchElementException {

        if ( size == 0 )
            throw new NoSuchElementException ();

        E result = theData [ front ];
        front = ( front + 1 ) % capacity;
        size--;
        return result;
    }


    /**
     * Creates a new array with twice the capacity of the original array and copies information from the old array into
     * a new one. Method implemented with syntax from the book.
     */
    private void reallocate () {

        int newCapacity = 2 * capacity;
        E [] newData = ( E[] ) new Object [ newCapacity ];
        int j = front;

        for ( int i = 0; i < size; i++ ) {

            newData [ i ] = theData [ j ];
            j = ( j + 1 ) % capacity;
        }

        front = 0;
        rear = size - 1;
        capacity = newCapacity;
        theData = newData;
    }


    // The following methods are required from Collections, but will not be implemented.

    @Override
    public boolean addAll ( Collection<? extends E> c ) {

        throw new UnsupportedOperationException ();
    }

    @Override
    public void clear () {

        throw new UnsupportedOperationException ();
    }

    @Override
    public boolean contains ( Object o ) {

        throw new UnsupportedOperationException ();
    }

    @Override
    public boolean containsAll ( Collection<?> c ) {

        throw new UnsupportedOperationException ();
    }

    @Override
    public boolean equals ( Object obj ) {

        throw new UnsupportedOperationException ();
    }

    @Override
    public int hashCode () {

        throw new UnsupportedOperationException ();
    }

    @Override
    public boolean isEmpty () {

        throw new UnsupportedOperationException ();
    }

    @Override
    public Iterator<E> iterator () {

        throw new UnsupportedOperationException ();
    }

    @Override
    public boolean remove ( Object o ) {

        throw new UnsupportedOperationException ();
    }

    @Override
    public boolean removeAll ( Collection<?> c ) {

        throw new UnsupportedOperationException ();
    }

    @Override
    public boolean retainAll ( Collection<?> c ) {

        throw new UnsupportedOperationException ();
    }

    @Override
    public int size () {

        throw new UnsupportedOperationException ();
    }

    @Override
    public Object[] toArray () {

        throw new UnsupportedOperationException ();
    }

    @Override
    public <T> T[] toArray ( T[] a ) {

        throw new UnsupportedOperationException ();
    }
}
