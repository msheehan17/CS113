package cs113.groupProject;

/**
 * Vertex.java - The class for representing the vertex of a graph.
 *
 * INSTANCE VARIABLES:
 *      - id (String): The identification of the vertex.
 *      - name (String): The information corresponding to the vertex.
 *
 * METHODS:
 *      - Full constructor: Sets the vertex with its identification and its corresponding information.
 *      - getId: Returns the vertex's id.
 *      - getName: Returns the vertex's name.
 *      - hashCode: Return the hashcode of the vertex.
 *      - equals (Object): Compares two vertices for equality.
 *      - toString: Returns a String representation of the vertex's id and the information held within it.
 *
 * @author Matt Sheehan, Licol Zeinfeld, Paul Krupski
 */
class Vertex {

    private String id, name;

    /**
     * Full constructor.
     * @param id The id of the vertex.
     * @param name The name of the vertex
     */
    Vertex ( String id, String name ) throws IllegalArgumentException {
        if ( id.isEmpty ( ) )
            throw new IllegalArgumentException ( "Id must be valid." );
        else
            this.id = id;

        if ( name.isEmpty ( ) )
            throw new IllegalArgumentException ( "Name must be valid." );
        else
            this.name = name;
    }

    /**
     * Returns the vertex's id.
     * @return The vertex's id.
     */
    String getId ( ) {
        return id;
    }

    /**
     * Returns the information associated with the vertex (the "name").
     * @return The information associated with the vertex.
     */
    String getName ( ) {
        return name;
    }

    @Override
    public int hashCode ( ) {
        return ( id.hashCode ( ) * 31 );
    }

    @Override
    public boolean equals ( Object obj ) {
        Vertex other;
        // Memory check.
        if ( this == obj )
            return true;
            // Class / null check.
        else if ( obj == null || getClass ( ) != obj.getClass ( ) )
            return false;
        else
            other = ( Vertex ) obj;
        
        return id.equals ( other.id );
    }

    @Override
    public String toString ( ) {
        return ( id + ": " + name );
    }
}
