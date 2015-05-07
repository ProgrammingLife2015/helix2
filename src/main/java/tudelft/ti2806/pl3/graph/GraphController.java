package tudelft.ti2806.pl3.graph;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.data.graph.GraphData;

import javax.swing.JFrame;
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
        JFrame frame = new JFrame();
        File nodeFile = FileSelector.selectFile("Select node file", frame, ".node.graph");
        File edgeFile = FileSelector.selectFile("Select edge file", frame, ".edge.graph");

		try {
			GraphData graphData = GraphData.parseGraph(nodeFile,edgeFile);
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
