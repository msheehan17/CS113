/**
 * VertexTest.java : Tester for Vertex methods
 *
 * @author Paul Krupski, Licol Zeinfeld, Matt Sheehan
 */
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class VertexTest {

    //List used to test Vertices
    private List<Vertex> testVertices = new ArrayList<>();

    @Before
    public void setUp() {

        //Build Vertices
        for (int i = 0; i < 6; i++) {

            Vertex temp = new Vertex(i + "", "Location number " + i);
            testVertices.add(temp);

        }
    }

    @Test
    public void getId() {
        for (int i = 0; i < 6; i++) {

            //TestID
            assertEquals("Expected vertex was not created", i + "", testVertices.get(i).getId());

        }
    }

    @Test
    public void getName() {
        for (int i = 0; i < 6; i++) {
            //TestName
            assertEquals("Expected vertex was not created", "Location number " + i, testVertices.get(i).getName());
        }
    }
}