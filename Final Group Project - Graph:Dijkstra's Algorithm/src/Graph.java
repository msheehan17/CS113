/**
 * Graph.java - A representation of a graph containing edges and vertices.
 *
 * INSTANCE VARIABLES:
 *      - vertices (List<Vertex>): The vertices in the graph.
 *      - edges (List<Edge>): The edges in the graph.
 *
 * METHODS:
 *      - Full Constructor: Creates a graph with the list of vertices and edges between those vertices.
 *      - getVertices: Returns a list of vertices contained within the graph.
 *      - getEdges: Returns a list of edges contained within the graph.
 *
 * @author Matt Sheehan, Licol Zeinfeld, Paul Krupski
 */
import java.util.List;

public class Graph {

    private List < Vertex > vertices;
    private List < Edge > edges;

    /**
     * Full constructor.
     * @param vertexes The vertices to be represented in the graph.
     * @param edges The edges to be represented in the graph.
     */
    public Graph ( List < Vertex > vertexes, List < Edge > edges) {
        this.vertices = vertexes;
        this.edges = edges;
    }

    /**
     * Returns a list of vertices contained within the graph.
     * @return A list of vertices contained within the graph.
     */
    public List < Vertex > getVertices ( ) {
        return vertices;
    }

    /**
     * Returns a list of edges contained within the graph.
     * @return A list of edges contained within the graph.
     */
    public List < Edge > getEdges ( ) {
        return edges;
    }
}
