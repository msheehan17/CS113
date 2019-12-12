/**
 * Edge.java - A class to represent an edge between vertices.
 *
 * INSTANCE VARIABLES:
 *      - id (String): The id corresponding to the graph.
 *      - source (Vertex): The source vertex of the edge (starting point).
 *      - destination (Vertex): The ending vertex of the edge (the ending point).
 *      - weight (int): The weight of the edge between the two vertices.
 *
 * METHODS:
 *      - Full constructor to initialize all variables contained within the class.
 *      - getId: Returns the id of the edge.
 *      - getSource: Returns the source vertex of the edge.
 *      - getDestination: Returns the destination vertex of the edge.
 *      - getWeight: Returns the weight of the edge.
 *      - toString: Returns a string representation of the edge.
 *
 * @author Matt Sheehan, Licol Zeinfeld, Paul Krupski
 */
public class Edge  {

    private String id;
    private Vertex source, destination;
    private int weight;

    /**
     * Full constructor.
     * @param id The id corresponding to the graph.
     * @param source The source vertex of the edge (starting point).
     * @param destination The ending vertex of the edge (the ending point).
     * @param weight The weight of the edge between the two vertices.
     */
    public Edge ( String id, Vertex source, Vertex destination, int weight ) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Returns the id of the edge.
     * @return The id of the edge.
     */
    public String getId ( ) {
        return id;
    }

    /**
     * Returns the source vertex of the edge.
     * @return The source vertex of the edge.
     */
    public Vertex getSource ( ) {
        return source;
    }

    /**
     * Returns the destination vertex of the edge.
     * @return The destination vertex of the edge.
     */
    public Vertex getDestination ( ) {
        return destination;
    }

    /**
     * Returns the weight of the edge.
     * @return The weight of the edge.
     */
    public int getWeight ( ) {
        return weight;
    }

    @Override
    public String toString ( ) {
        return ( source + " " + destination );
    }
}