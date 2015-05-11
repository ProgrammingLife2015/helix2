package tudelft.ti2806.pl3.demo;

import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.graph.FileSelector;
import tudelft.ti2806.pl3.visualization.GraphController;
import tudelft.ti2806.pl3.visualization.GraphModel;
import tudelft.ti2806.pl3.visualization.GraphView;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

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
		JFrame frame = new JFrame();
		File nodeFile = FileSelector.selectFile("Select node file", frame,
				".node.graph");
		File edgeFile = FileSelector.selectFile("Select edge file", frame,
				".edge.graph");
		try {
			GraphDataRepository rep = GraphDataRepository.parseGraph(nodeFile,
					edgeFile);
			GraphController gc = new GraphController(new GraphView(),
					new GraphModel(rep));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(gc.getPanel());
			gc.getPanel().setVisible(true);
			frame.setVisible(true);
			double zoom = 1.0;
			gc.changeZoom(zoom);
			for (int n = 0; n < 100; n++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				gc.changeZoom(zoom *= 2);
				gc.moveView(10000L);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
