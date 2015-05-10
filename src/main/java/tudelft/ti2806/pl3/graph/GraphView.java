package tudelft.ti2806.pl3.graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.Node;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;


/**
 * Render the sequence graph.
 * Created by Boris Mattijssen on 30-04-15.
 */
public class GraphView extends JPanel {

	private List<Node> nodes;
	private List<Edge> edges;


	/**
	 * Construct empty graph view.
	 */
	public GraphView() {
		setLayout(new BorderLayout());
	}

	/**
	 * Construct a graph view containing nodes and edges.
	 * The constructor will automatically make the graph render.
	 * @param nodes the graph's nodes
	 * @param edges the graph's edges
	 */
	public GraphView(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
		setLayout(new BorderLayout());
		renderGraph();
	}


	/**
	 * Render the sequence graph and add it to this panel.
	 */
	public void renderGraph() {
		removeAll();
		Graph graph = new SingleGraph("");
		for (Node node : nodes) {
			org.graphstream.graph.Node graphNode = graph.addNode(node
					.getNodeId() + "");
			graphNode.addAttribute("Node", node);
		}
		for (Edge edge : edges) {
			graph.addEdge(edge.toString(), edge.getFrom().getNodeId() + "",
					edge.getTo().getNodeId() + "");
		}

		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.enableAutoLayout();

		this.add(viewer.addDefaultView(false));
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	/**
	 * When the graph file was not found, show this message instead of the graph.
	 */
	public void showFileNotFound() {
		removeAll();
		add(new JLabel("The graph file could not be found"));
	}
}
