/**
 * EdgeTest.java : Tester for Edge methods
 *
 * @author Paul Krupski, Licol Zeinfeld, Matt Sheehan
 */
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestEdge {

    //List used to test Vertices
    public List<Edge> testEdges = new ArrayList<>();

    @Before
    public void setUp() {

        //Build Edges
        for (int i = 0; i < 6; i++) {

            Vertex v1 = new Vertex(i + "", "Location number " + i);
            Vertex v2 = new Vertex((i+1) + "", "Location number " + (i+1));

            Edge temp = new Edge(i + "", v1,v2, (i*10));
            testEdges.add(temp);

        }
    }

    @Test
    public void getId() {
        for (int i = 0; i < 6; i++) {

            //TestID
            assertEquals("Expected ID is not present", i + "", testEdges.get(i).getId());

        }
    }

    @Test
    public void getSource() {
        for (int i = 0; i < 6; i++) {

            Vertex v1 = new Vertex(i + "", "Location number " + i);
            //TestID
            assertTrue( "Source vertices do not match",v1.equals(testEdges.get(i).getSource()));

        }
    }

    @Test
    public void getDestination() {
        for (int i = 0; i < 6; i++) {

            Vertex v2 = new Vertex((i+1) + "", "Location number " + (i+1));
            //TestID
            assertTrue( "Destination vertices do not match",v2.equals(testEdges.get(i).getDestination()));

        }
    }

    @Test
    public void getWeight() {
        for (int i = 0; i < 6; i++) {
            //TestName
            assertEquals("Expected weight doesn't match", (i*10), testEdges.get(i).getWeight());
        }
    }
}