/**
 * Main.java - A map of MiraCosta to help new students get around in the quickest way possible.
 *
 * 1. The program will contain a map for the user to reference, a directions panel, and two button panels. The two
 * button panels will be the start for our user to use the program. Both button panels will have default buttons
 * selected.
 *
 * 2. Once the user has selected buttons (as long as they are not the same location, otherwise a message will tell
 * them so) and has selected the "get directions" button, an action event will be fired.
 *
 * 3. The action event will first build a graph the map will be based off of, loaded with edges and vertices. Once
 * the graph has been filed, a DijkstrasAlgorithm object will take it as an argument to have paths to work from.
 *
 * 4. An for loop will cycle through an array of the buttons, once the loop has be flagged a button is selected
 * (isSelected() == true), that will be used for the start/end vertex (depending on which loop flagged true).
 *
 * 5. The DijkstrasAlgorithm object will take the start and end vertex and arguments and generate the shortest path
 * by comparing weights.
 *
 * 6. Once the shortest path has been found, actionPerformed will edit the text of the JTextArea to display the
 * directions for the user.
 *
 * @author Matt Sheehan, Paul Krupski, Licol Zeifeld.
 */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main extends JFrame implements ActionListener {

    // The name of the JFrame.
    private static final String FRAME_TITLE = "MiraCosta Map";

    // The height and width of the JFrame.
    private static final int WIDTH  = 2000;
    private static final int HEIGHT = 2000;

    // The border for each panel.
    private static final Border BLACK_LINE_BORDER = BorderFactory.createLineBorder ( Color.BLACK );

    // An array of names of the radio buttons;
    private static final String [ ] BUTTON_NAMES = { "Entry 3", "Gym", "Entry 2", "Financial Aid", "Horticulture",
            "Math Dept.", "Entry 1", "Book Store", "Library", "Computer Science Dept.", "Science Dept." };

    // The start and end radio buttons for the user to select from.
    private static JRadioButton entry3Start = null, gymStart = null, entry2Start = null, finAidStart = null,
            hortStart = null, mathStart = null, entry1Start = null, bookStart = null, libStart = null, csStart = null,
            scienceStart = null, entry3End = null, gymEnd = null, entry2End = null, finAidEnd = null, hortEnd = null,
            mathEnd = null, entry1End = null, bookEnd = null, libEnd = null, csEnd = null, scienceEnd = null ;

    // An array of JRadioButtons that only contains the starting location buttons.
    private static JRadioButton [ ] startButtons = { entry3Start, gymStart, entry2Start, finAidStart, hortStart,
            mathStart, entry1Start, bookStart, libStart, csStart, scienceStart };

    // An array of JRadioButtons that only contains the end location buttons.
    private static JRadioButton [ ] endButtons = { entry3End, gymEnd, entry2End, finAidEnd, hortEnd, mathEnd,
            entry1End, bookEnd, libEnd, csEnd, scienceEnd };

    // The path to the map image.
    private static final String IMAGE_PATH = "images/Graph.PNG";

    // The button the user will press in order to get directions.
    private static JButton getDirectionsButton = new JButton ( "Get Directions" );

    // The text area that will display the directions.
    private static JTextArea directionsText;

    // An array of ids for the edges being entered into the graph.
    private static final String [ ] EDGE_IDS = { "Edge_0", "Edge_1", "Edge_2", "Edge_3", "Edge_1", "Edge_1",
            "Edge_4", "Edge_5", "Edge_6", "Edge_1", "Edge_7", "Edge_8", "Edge_9", "Edge_1", "Edge_1", "Edge_10", "Edge_1",
            "Edge_11", "Edge_1", "Edge_12", "Edge_1", "Edge_1", "Edge_1", "Edge_1", "Edge_1" };

    // An array of the number associated with the starting vertices.
    private static final int [ ] SOURCE_VERTEX_NUMBERS = { 0, 1, 1, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 5, 6, 7, 7, 8, 8,
            9, 9, 9, 10, 10, 10 };

    // An array of the number associated with the starting vertices.
    private static final int [ ] DESTINATION_VERTEX_NUMBERS = { 1, 9, 2, 3, 1, 10, 4, 10, 5, 3, 6, 7, 10, 4, 5, 8,
            5, 7, 9, 10, 8, 1, 3, 5, 9 };

    // An array of the weights for each edge.
    private static final int [ ] WEIGHTS = { 515, 264, 488, 502, 488, 278, 500, 278, 589, 500, 264, 246, 250, 589,
            264, 367, 246, 367, 326, 335, 326, 264, 278, 250, 335 };

    // A list of vertices to be used to build the graph.
    private static List < Vertex > nodes = new ArrayList < > (  );

    // A list of edges to be used to build the graph.
    private static List < Edge > edges = new ArrayList < > (  );

    // The graph the map will be based off of.
    private static Graph myGraph;

    public static void main ( String [ ] args ) {
        Main window = new Main ( );
        window.setVisible ( true );
    } // End main.

    private Main ( ) {
        // Set up the initial settings for the GUI.
        setTitle ( FRAME_TITLE );
        setSize ( WIDTH, HEIGHT );
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        setLocationRelativeTo ( null );
        setLayout ( new BorderLayout (  ) );

        // Add the ActionListener for the "Get Directions" button.
        getDirectionsButton.addActionListener ( this );

        // Button panels.
        add ( createBigButtonPanel ( ), BorderLayout.WEST );

        // Map panel.
        add ( new JScrollPane ( createMapPanel ( ) ), BorderLayout.CENTER );

        // Text panel.
        add ( createDirectionsPanel ( ), BorderLayout.SOUTH );
    }

    /**
     * Combines the start button panel with the destination panel for easy addition to the JFrame.
     * @return The panel containing the start button panel and end button panel.
     */
    private static JPanel createBigButtonPanel ( ) {
        // Create the panel for holding the separate buttons panels.
        JPanel bigButtonPanel = new JPanel ( new GridLayout ( 2, 1 ) );

        // Add start button panel.
        bigButtonPanel.add ( createStartButtonPanel ( ) );

        // Add destination button panel.
        bigButtonPanel.add ( createEndButtonPanel ( ) );

        return bigButtonPanel;
    }

    /**
     *  Creates the panel that will hold the start buttons.
     * @return The panel that will hold that start buttons.
     */
    private static JPanel createStartButtonPanel ( ) {
        // Create the panel for holding the buttons.
        JPanel startButtonPanel  = new JPanel ( );

        // Adjust the layout so that the buttons are ordered from top to bottom.
        startButtonPanel.setLayout ( new BoxLayout ( startButtonPanel, BoxLayout.Y_AXIS ) );

        // Create a border around the panel.
        startButtonPanel.setBorder ( BLACK_LINE_BORDER );

        // Create the button group for grouping the radio buttons.
        ButtonGroup startButtonGroup = new ButtonGroup ( );

        // Initialize all start buttons, then add them to the JPanel/ButtonGroup.
        for ( int i = 0; i < startButtons.length; i++ ) {
            startButtons [ i ] = new JRadioButton ( BUTTON_NAMES [ i ] );
            if ( i == 0 ) // Default selected button.
                startButtons [ i ].setSelected ( true );
            startButtonPanel.add ( startButtons [ i ] );
            startButtonGroup.add ( startButtons [ i ] );
        }
        return startButtonPanel;
    }

    /**
     * Creates the panel that will hold the destination buttons.
     * @return The panel that will hold the destination buttons.
     */
    private static JPanel createEndButtonPanel ( ) {
        // Create the panel for holding the buttons.
        JPanel endButtonPanel = new JPanel ( );

        // Adjust the layout so that the buttons are ordered from top to bottom.
        endButtonPanel.setLayout ( new BoxLayout ( endButtonPanel, BoxLayout.Y_AXIS ) );

        // Create a border around the panel.
        endButtonPanel.setBorder ( BLACK_LINE_BORDER );

        // Create the button group for grouping the radio buttons.
        ButtonGroup endButtonGroup = new ButtonGroup ( );

        // Initialize all end buttons, then add them to the JPanel/ButtonGroup.
        for ( int i = 0; i < endButtons.length; i++ ) {
            endButtons [ i ] = new JRadioButton ( BUTTON_NAMES [ i ] );
            if ( i == 1 )
                endButtons [ i ].setSelected ( true );
            endButtonPanel.add ( endButtons [ i ] );
            endButtonGroup.add ( endButtons [ i ] );
        }

        // Add an empty panel for padding between the destination buttons and the "get directions" button.
        JPanel emptyPanel = new JPanel (  );
        emptyPanel.setBorder ( new EmptyBorder ( 10,10,10,10 ) );
        endButtonPanel.add ( emptyPanel );

        // Set the action command and add the button to the panel.
        getDirectionsButton.setActionCommand ( "get directions" );
        endButtonPanel.add ( getDirectionsButton );

        return endButtonPanel;
    }

    /**
     * Creates the panel that will hold the map image.
     * @return The panel that will hold the map image.
     */
    private static JPanel createMapPanel ( ) {
        // The panel for holding the image of the map.
        JPanel mapPanel = new JPanel (  );

        // Border the panel.
        mapPanel.setBorder ( BLACK_LINE_BORDER );

        // Add an image label to the panel.
        mapPanel.add ( new JLabel ( new ImageIcon ( IMAGE_PATH ) ) );

        return mapPanel;
    }

    /**
     * Creates the directions panel.
     * @return The directions panel.
     */
    private static JPanel createDirectionsPanel ( ) {
        // Create the panel for holding the directions.
        JPanel directionsPanel = new JPanel (  );

        // Set the layout of the panel.
        directionsPanel.setLayout ( new BorderLayout ( ) );

        // Create a border around the panel.
        directionsPanel.setBorder ( BLACK_LINE_BORDER );

        // Create the component for displaying the directions. Created with default message.
        directionsText = new JTextArea ( "Choose your start and stop points..", 5, 1 );

        // Ensure user cannot edit the text.
        directionsText.setEditable ( false );

        // Add the text component to the panel.
        directionsPanel.add ( directionsText, BorderLayout.CENTER );

        return directionsPanel;
    }

    /**
     * Builds the vertices and edges needed in order to build the graph, then builds the graph.
     */
    private static void buildGraph ( ) {
        // Build vertices
        for ( int i = 0; i < 11; i++ )
            nodes.add ( new Vertex ( "Node_" + i, BUTTON_NAMES [ i ] ) );

        // Build edges.
        for ( int i = 0; i < EDGE_IDS.length; i++ )
            addEdge ( EDGE_IDS [ i ], SOURCE_VERTEX_NUMBERS [ i ], DESTINATION_VERTEX_NUMBERS [ i ], WEIGHTS [ i ] );

        // Build graph.
        myGraph = new Graph ( nodes, edges );
    }

    /**
     * Builds an edge and adds it to the list of edges.
     * @param edgeId The edge's id.
     * @param sourceVertexNum The number of the source vertex.
     * @param destinationVertexNum The number of the destination vertex.
     * @param weight The weight of the edge.
     */
    private static void addEdge ( String edgeId, int sourceVertexNum, int destinationVertexNum, int weight ) {
        edges.add ( new Edge ( edgeId, nodes.get ( sourceVertexNum ), nodes.get ( destinationVertexNum ), weight ) );
    }

    /**
     * Controls the actions performed when the user hits the "get directions button".
     * @param e The event fired.
     */
    @Override
    public void actionPerformed ( ActionEvent e ) {
        int start = 0, end = 0; // The respective vertex of the start/destination points selected by the user.

        if ( e.getActionCommand ( ).equals ( "get directions" ) ) {
            // Get the starting and end vertices.
            for ( int i = 0; i < startButtons.length; i++ ) {
                if ( startButtons[ i ].isSelected () )
                    start = ( i );
                if ( endButtons[ i ].isSelected () )
                    end = ( i );
            }

            if ( start == end )
                directionsText.setText ( "You're already at that location." );

            // Build the graph.
            buildGraph ();

            // Build an object for performing Dijkstra's algorithm and find the shortest paths.
            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm ( myGraph );
            dijkstra.execute ( nodes.get ( start ) );
            LinkedList < Vertex > path = dijkstra.getPath ( nodes.get ( end ) );

            // With the aid of the paths provided by Dijkstra's algorithm, generate directions.
            StringBuilder sb = new StringBuilder ( "Go from " + path.get ( 0 ).getName ( ) + " to " + path.get ( 1 ).getName ( ) );

            // A path that must traverse more than one location.
            if ( path.size ( ) > 2 ) {
                for ( int i = 2; i < path.size ( ) ; i++ )
                    sb.append ( " to " + path.get ( i ).getName ( ) );
            }
            directionsText.setText ( sb.toString ( ) );
        }
    }
}
