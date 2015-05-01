package tudelft.ti2806.pl3.graph;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.Viewer;

import javax.swing.*;
import java.awt.*;

/**
 * Render the sequence graph
 *
 * Created by Boris Mattijssen on 30-04-15.
 */
public class GraphView extends JPanel {

    private Graph graph;

    /**
     * Initialize graph view, give it a BorderLayout
     */
    public GraphView() {
        this.setLayout(new BorderLayout());
    }

    /**
     * Set the graph and render it into the view
     * @param graph the graph from the graphstream library
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
        renderGraph();
    }

    /**
     * Render the sequence graph and add it to this panel
     */
    public void renderGraph() {
        removeAll();
        graph.addAttribute("ui.antialias");

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();

        this.add(viewer.addDefaultView(false));
    }

    /**
     * When the graph file was not found, show this message instead of the graph
     */
    public void showFileNotFound() {
        removeAll();
        add(new JLabel("The graph file could not be found"));
    }
}
