/**
 * GraphTest.java : Tester for Graph methods
 *
 * @author Paul Krupski, Licol Zeinfeld, Matt Sheehan
 */
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class GraphTest {

    //List of Edges and List of Vertices that will be given to the test graph
    private List<Edge> testEdges = new ArrayList<>();
    private List<Vertex> testVertices = new ArrayList<>();

    //Graph to test
    private Graph testGraph = null;

    @Before
    public void setUp() {

        //Build Vertices, Edges, and input into graph

        //Build Vertices and put into ArrayList
        for (int i = 0; i < 6; i++) {

            Vertex tempV1 = new Vertex(i + "", "Location number " + i);

            testVertices.add(tempV1);

        }

        //Build Edges that connect each vertex and put edges into ArrayList
        for (int i = 0; i < 5; i++) {

            Edge tempE = new Edge(i + "",testVertices.get(i),testVertices.get(i+1) ,(i*10));

            testEdges.add(tempE);
        }

        //Graph created with complete ArrayLists of all Edges and Vertices
        testGraph = new Graph(testVertices, testEdges);
    }

    //Test for correct vertex
    @Test
    public void getVertexes() {

        for (int i = 0; i < 6; i++) {

            Vertex temp = new Vertex(i + "", "Location number " + i);
            assertTrue(temp.equals(testGraph.getVertices ().get(i)));

        }
    }

    //Compare ID's to ensure correct edge
    @Test
    public void getEdges() {

        for (int i = 0; i < 5; i++) {

            Edge temp = new Edge(i + "",testVertices.get(i),testVertices.get(i+1) ,(i*10));
            assertEquals(temp.getId(),testGraph.getEdges().get(i).getId());

        }
    }
}
