package tudelft.ti2806.pl3.demo;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.data.graph.Node;
import tudelft.ti2806.pl3.data.graph.SingleNode;
import tudelft.ti2806.pl3.visualization.GraphModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class Demo {
	/**
	 * Demo for sprint 1, week 2. Demonstrating the parsing of the edge and node
	 * files and displaying them with the GraphStream library.
	 * 
	 * @param par
	 *            should be empty
	 */
	public static void main(String[] par) {
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		JFrame frame = new JFrame("Demo");
		// Graph parsing
		GraphData graph = null;
		{
			File nodeFile = selectFile("Select node file", frame, ".node.graph");
			File edgeFile = selectFile("Select edge file", frame, ".edge.graph");
			try {
				graph = GraphData.parseGraph(nodeFile, edgeFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		// Positioning
		for (Node node : graph.getNodes()) {
			SingleNode snode = (SingleNode) node;
			snode.calculateStartX();
			snode.calculatePreviousNodesCount();
		}
		// Genome sorting
		{
			int i = 0;
			for (Genome genome : graph.getGenomes()) {
				genome.setYposition(i++);
			}
		}
		// Filtering
		GraphModel gm = new GraphModel(graph);
		{
			List<Filter<Node>> filters = new ArrayList<Filter<Node>>();
			gm.produceGraph(filters);
		}
		// Graphstream
		Graph displayGraph = generateGraph(gm.getGraph());
		{
			displayGraph.addAttribute("ui.stylesheet",
					"edge.normalEdge {shape: freeplane;"
							+ "fill-color: #00000070;}"
							+ "edge.nodeEdge {fill-color: red;"
							+ "stroke-width:3px;}"
							+ "node {stroke-mode: plain;" + "size: 0;"
							+ "shape: freeplane;"
							+ "fill-color: #00000000;}");
			
			displayGraph.addAttribute("ui.quality");
			displayGraph.addAttribute("ui.antialias");
		}
		Viewer dp = displayGraph.display(false);
		View view = dp.getDefaultView();
		new Thread(new Runnable() {
			private double zoom = 1.0;
			private double speed = 0.0;
			private double x = 0.0;
			private double y = 250.0;
			
			@Override
			public void run() {
				try {
					move(1);
					zoomIn(17.0);
					Thread.sleep(1000);
					speed(200);
					move(60);
					slow(1.0);
					Thread.sleep(1000);
					zoomIn(23.0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			private void slow(double slowTill) throws InterruptedException {
				for (; speed >= slowTill;
						speed -= 0.1 + Math.sqrt(speed), x += speed) {
					view.getCamera().setViewCenter(x, y, 0);
					Thread.sleep(100);
				}
			}
			
			private void move(int maxMove) throws InterruptedException {
				for (int i = 0; i < maxMove; i++) {
					Thread.sleep(100);
					x += speed;
					view.getCamera().setViewCenter(x, y, 0);
				}
			}
			
			private void speed(int maxSpeed) throws InterruptedException {
				for (; speed <= maxSpeed;
						speed += 0.1 + Math.sqrt(speed), x += speed) {
					view.getCamera().setViewCenter(x, y, 0);
					Thread.sleep(100);
				}
			}
			
			private void zoomIn(double maxZoom) throws InterruptedException {
				while (zoom < maxZoom) {
					view.getCamera().setViewPercent(1.0 / (zoom * zoom));
					zoom += 0.1;
					Thread.sleep(100);
				}
			}
			
		}).start();
	}
	
	private static Graph generateGraph(GraphData graphData) {
		Graph graph = new SingleGraph("");
		double scale = 0.1;
		for (Node node : graphData.getNodes()) {
			// calculate ylocation
			List<Integer> ylocation = new ArrayList<Integer>();
			for (Genome genome : node.getSource()) {
				ylocation.add(genome.getYposition());
			}
			Collections.sort(ylocation);
			
			int nodeSpace = node.getPreviousNodesCount() * 50;
			
			String nodeName = node.getId() + "";
			org.graphstream.graph.Node leftNode = graph.addNode("[" + nodeName);
			leftNode.setAttribute("xy", node.getXStart() * scale + nodeSpace,
					ylocation.get(ylocation.size() / 2) * 50);
			leftNode.setAttribute("ui.size", 0);
			
			org.graphstream.graph.Node rightNode = graph
					.addNode(nodeName + "]");
			rightNode.setAttribute("xy", node.getXEnd() * scale + nodeSpace,
					ylocation.get(ylocation.size() / 2) * 50);
			org.graphstream.graph.Edge edge = graph.addEdge("[" + nodeName
					+ "]", "[" + nodeName, nodeName + "]");
			edge.addAttribute("ui.class", "nodeEdge");
			
		}
		for (Edge edge : graphData.getEdges()) {
			org.graphstream.graph.Edge gedge = graph.addEdge(edge.toString(),
					edge.getFrom().getId() + "]", "[" + edge.getTo().getId());
			gedge.addAttribute("ui.class", "normalEdge");
		}
		return graph;
	}
	
	private static File selectFile(String title, JFrame frame, String filter) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.setDialogTitle(title);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					String path = file.getAbsolutePath().toLowerCase();
					if (path.endsWith(filter)) {
						return true;
					}
				}
				return false;
			}
			
			@Override
			public String getDescription() {
				return filter;
			}
		});
		int option = chooser.showOpenDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			File[] sf = chooser.getSelectedFiles();
			if (sf.length == 1) {
				return sf[0];
			}
		}
		return null;
	}
}
