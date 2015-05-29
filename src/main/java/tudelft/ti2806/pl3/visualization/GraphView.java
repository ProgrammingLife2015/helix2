package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;

import java.awt.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * The GraphView is responsible for adding the nodes and edges to the graph,
 * keeping the nodes and edges on the right positions and applying the right
 * style to the graph.
 * 
 * @author Sam Smulders
 *
 */
public class GraphView implements Observer, ViewInterface {
	/**
	 * The zoomLevel used to draw the graph.<br>
	 * A zoom level of 1.0 shows the graph 1:1, so that every base pair should
	 * be readable, each with {@link #basePairDisplayWidth} pixels to draw its
	 * value as text. A zoom level of 2.0 shows the graph with each base pair
	 * using the half this size.
	 */
	private double zoomLevel = 1.0;
	/**
	 * The center position of the view.<br>
	 * The position on the x axis.
	 */
	private long zoomCenter = 1;
	
	/**
	 * The css style sheet used drawing the graph.<br>
	 * Generate a new view to have the changes take effect.
	 */
	
	private List<WrapperClone> graphData;
	private Graph graph = new GraphicGraph("Graph");
	private Viewer viewer;
	private View panel;
	
	private ZoomedGraphModel zoomedGraphModel;
	
	public GraphView(ZoomedGraphModel zoomedGraphModel) {
		this.zoomedGraphModel = zoomedGraphModel;
		graphData = new ArrayList<>();
	}
	
	public void init() {
		generateViewer();
	}
	
	/**
	 * Generates a {@link Viewer} for the graph with the given {@code zoomLevel}
	 * . A new Viewer should be constructed every time the graphData or
	 * zoomLevel updates.
	 */
	private void generateViewer() {
		viewer = new Viewer(graph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		panel = viewer.addDefaultView(false);
	}
	
	/**
	 * Sets the graph its drawing properties.
	 */
	private void setGraphPropertys() {
		String url = "resources/stylesheet.css";
		try {
			List<String> lines = Files.readAllLines(Paths.get(url));
			
			StringBuffer stylesheet = new StringBuffer();
			for (String line : lines) {
				stylesheet.append(line + " ");
			}
			
			graph.addAttribute("ui.stylesheet", stylesheet.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
	}
	
	/**
	 * Generates a Graph from the current graphData.
	 * 
	 * @return a graph with all nodes from the given graphData
	 */
	public Graph generateGraph() {
		graph.clear();
		setGraphPropertys();
		final double someSize = panel.getBounds().height
				/ ((double) panel.getBounds().width * zoomLevel / zoomedGraphModel
						.getWrappedCollapsedNode().getWidth())
				/ zoomedGraphModel.getWrappedCollapsedNode().getGenome().size();
		graphData.forEach(node -> {
				if (!"[FIX]".equals(node.getIdString())) {
					Node graphNode = graph.addNode(Integer.toString(node.getId()));
					double y = node.getY() * someSize;
					graphNode.setAttribute("xy", node.getX(), y);
					graphNode.addAttribute("ui.class",
							node.getOriginalNode().getClass().getSimpleName());
					graphNode.addAttribute("ui.label", node.getOriginalNode().getWidth());
				}
			});
		
		for (Wrapper node : graphData) {
			for (Wrapper to : node.getOutgoing()) {
				if (!"[FIX]".equals(node.getIdString()) && !"[FIX]".equals(to.getIdString())) {
					addNormalEdge(graph, node, to);
				}
			}
		}
		return graph;
	}
	
	/**
	 * Adds an edge between two nodes.
	 * 
	 * @param graph
	 *            the graph to add the edge to
	 * @param from
	 *            the node where the edge begins
	 * @param to
	 *            the node where the edge ends
	 */
	private static void addNormalEdge(Graph graph, Wrapper from, Wrapper to) {
		graph.addEdge(from.getId() + "-" + to.getId(),
				Integer.toString(from.getId()), Integer.toString(to.getId()), true);
	}
	
	@Override
	public Component getPanel() {
		return panel;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == zoomedGraphModel) {
			graphData = zoomedGraphModel.getDataNodeWrapperList();
			zoomLevel = zoomedGraphModel.getZoomLevel();
			// TODO: draw graph with the newly retrieved graphData
			generateGraph();
			zoom();
		}
	}
	
	private void zoom() {
		viewer.getDefaultView().getCamera().setViewPercent(1 / zoomLevel);
	}
	
	public long getZoomCenter() {
		return zoomCenter;
	}
	
	/**
	 * Moves the view to the given position on the x axis.
	 *
	 * @param zoomCenter
	 *            the new center of view
	 */
	public void setZoomCenter(long zoomCenter) {
		this.zoomCenter = zoomCenter;
		viewer.getDefaultView().getCamera().setViewCenter(zoomCenter, 0, 0);
	}
}
