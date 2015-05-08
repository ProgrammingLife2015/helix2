package tudelft.ti2806.pl3.demo;

import org.graphstream.graph.Graph;

import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.visualization.DisplayView;

import java.io.File;
import java.io.FileNotFoundException;

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
		JFrame frame = new JFrame();
		File nodeFile = selectFile("Select node file", frame, ".node.graph");
		File edgeFile = selectFile("Select edge file", frame, ".edge.graph");
		
		GraphData graph = null;
		try {
			graph = GraphData.parseGraph(nodeFile, edgeFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Graph displayGraph = DisplayView.getGraph("", graph.getNodes(),
				graph.getEdges());
		displayGraph.display();
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
