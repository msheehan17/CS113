/**
 * DijkstraAlgorithm.java - A class for using Dijkstra's algorithm.
 *
 * INSTANCE VARIABLES:
 *
 *      - nodes (List<Vertex>): A list of vertices Dijkstra's algorithm will be working with.
 *      - edges (List<Edge>): A list of edges between the vertices that Dijkstra's algorithm will traverse.
 *      - settledNodes (Set<Vertex>): A set of vertices traversed.
 *      - unsettledNodes (Set<Vertex>): A set of vertices not yet traversed.
 *      - predecessors(Map<Vertex, Vertex>): A map containing previously traversed vertices.
 *      - distance (Map<Vertex, Integer>): A map containing the weights of the paths traversed.
 *
 * METHODS:
 *
 *      - execute (Vertex): Performs the path traversal to determine the shortest path.
 *      - findMinimalDistance (Vertex): Finds the minimal distance based on the starting vertex.
 *      - getDistance (Vertex, Vertex): Returns the distance between the two vertices (after path traversal).
 *      - getNeighbors (Vertex): Returns a list of neighboring vertices.
 *      - getMinimum (Vertex): Returns the shortest distanced vertex.
 *      - isSettled (Vertex): Determines if a vertex was traversed or not.
 *      - getShortDistance (Vertex): Returns the shortest distance.
 *      - getPath (Vertex): Returns a path between the target vertex and all predecessors.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithm {

    private final List < Vertex > nodes;
    private final List < Edge > edges;
    private Set < Vertex > settledNodes;
    private Set < Vertex > unSettledNodes;
    private Map < Vertex, Vertex > predecessors;
    private Map < Vertex, Integer > distance;

    /**
     * Full constructor.
     * @param graph The graph Dijkstra's will copy the vertices and edges from.
     */
    public DijkstraAlgorithm ( Graph graph ) {
        this.nodes = new ArrayList < > ( graph.getVertices ( ) );
        this.edges = new ArrayList < > ( graph.getEdges ( ) );
    }

    /**
     * Performs the path traversal to determine the shortest path.
     * @param source The starting vertex.
     */
    public void execute ( Vertex source ) {
        settledNodes = new HashSet < > ( );
        unSettledNodes = new HashSet < > ( );
        distance = new HashMap < > ( );
        predecessors = new HashMap < > ( );
        distance.put ( source, 0 );
        unSettledNodes.add ( source );

        while ( unSettledNodes.size ( ) > 0 ) {
            Vertex node = getMinimum ( unSettledNodes );
            settledNodes.add ( node );
            unSettledNodes.remove ( node );
            findMinimalDistances ( node );
        }
    }

    /**
     * Finds the minimal distance based on the starting vertex.
     * @param node The starting vertex.
     */
    private void findMinimalDistances ( Vertex node ) {
        List < Vertex > adjacentNodes = getNeighbors ( node );
        for ( Vertex target : adjacentNodes ) {
            if ( getShortestDistance ( target ) > ( getShortestDistance ( node ) + getDistance ( node, target ) ) ){
                distance.put ( target, ( getShortestDistance ( node ) + getDistance ( node, target ) ) );
                predecessors.put ( target, node );
                unSettledNodes.add ( target );
            }
        }
    }

    /**
     * Returns the distance between the two vertices (after path traversal).
     * @param node The starting vertex.
     * @param target The ending vertex.
     * @return The distance (a sum of the weights/path).
     */
    private int getDistance ( Vertex node, Vertex target ) {
        for ( Edge edge : edges ) {
            if ( edge.getSource ( ).equals ( node ) && edge.getDestination ( ).equals ( target ) )
                return edge.getWeight ( );
        }
        return 0;
    }

    /**
     * Returns a list of neighboring vertices.
     * @param node The vertex to determine neighboring vertices.
     * @return A list of neighboring vertices.
     */
    private List < Vertex > getNeighbors ( Vertex node ) {
        List < Vertex > neighbors = new ArrayList < > ( );
        for ( Edge edge : edges ) {
            if ( edge.getSource ( ).equals ( node ) && ( !isSettled ( edge.getDestination ( ) ) ) )
                neighbors.add( edge.getDestination ( ) );
        }
        return neighbors;
    }

    /**
     * Returns the shortest distanced vertex.
     * @param vertices The starting vertices.
     * @return The shortest distance vertex.
     */
    private Vertex getMinimum ( Set < Vertex > vertices ) {
        Vertex minimum = null;

        for ( Vertex vertex : vertices ) {
            if ( minimum == null )
                minimum = vertex;
            else {
                if ( getShortestDistance ( vertex ) < getShortestDistance ( minimum ) )
                    minimum = vertex;
            }
        }
        return minimum;
    }

    /**
     * Determines if a vertex was traversed or not.
     * @param vertex The vertex in question.
     * @return True if the vertex has been traversed, false if not.
     */
    private boolean isSettled ( Vertex vertex ) {
        return settledNodes.contains ( vertex );
    }

    /**
     * Returns the shortest distance.
     * @param destination The destination vertex.
     * @return The shortest distance.
     */
    private int getShortestDistance ( Vertex destination ) {
        // Should a distance not exist, return infinity.
        if ( distance.get ( destination ) == null )
            return Integer.MAX_VALUE;
            // Else return the distance.
        else
            return distance.get ( destination );
    }

    /**
     * Returns a path between the target vertex and all predecessors.
     * @param target The target vertex.
     * @return A path between the target vertex and all predecessors.
     */
    public LinkedList < Vertex > getPath ( Vertex target ) {
        LinkedList<Vertex> path = new LinkedList < > ( );
        Vertex step = target;

        // check if a path exists
        if ( predecessors.get ( step ) == null )
            return null;

        path.add ( step );

        while ( predecessors.get ( step ) != null ) {
            step = predecessors.get ( step );
            path.add ( step );
        }

        // Put it into the correct order
        Collections.reverse ( path );
        return path;
    }
}