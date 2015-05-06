package tudelft.ti2806.pl3.graph;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.data.graph.GraphData;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Controls the graph view.
 * Created by Boris Mattijssen on 30-04-15.
 */
public class GraphController implements Controller {

	private GraphView view;

	/**
	 * Initialize view and pass the graph to it.
	 */
	public GraphController() {
		try {
			GraphData graphData = GraphData.parseGraph(new File(
					"data/10_strains_graph/simple_graph.node.graph"), new File(
					"data/10_strains_graph/simple_graph.edge.graph"));
			view = new GraphView(graphData.getNodes(), graphData.getEdges());
		} catch (FileNotFoundException e) {
			view = new GraphView();
			view.showFileNotFound();
		}
	}

	@Override
	public Component getView() {
		return view;
	}
}
