package tudelft.ti2806.pl3.graph;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.Viewer;
import tudelft.ti2806.pl3.data.GraphParser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Render the sequence graph
 *
 * Created by borismattijssen on 30-04-15.
 */
public class GraphView {

    public GraphView() {

    }

    /**
     * Render the sequence graph
     *
     * @return JPanel containing the graph
     */
    public JPanel render() {
        Graph graph;
        try {
            graph = GraphParser.parseGraph("hello", new File(
                    "data/10_strains_graph/simple_graph.node.graph"), new File(
                    "data/10_strains_graph/simple_graph.edge.graph"));
        } catch (FileNotFoundException e) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel("The graph data file could not be found"));
            return panel;
        }

        graph.addAttribute("ui.antialias");

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();

        return viewer.addDefaultView(false);
    }

}
