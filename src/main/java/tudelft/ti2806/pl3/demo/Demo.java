package tudelft.ti2806.pl3.demo;

import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.visualization.GraphController;

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
		System.setProperty("org.graphstream.ui.renderer",
				"org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		JFrame frame = new JFrame();
		File nodeFile = Demo.selectFile("Select node file", frame,
				".node.graph");
		File edgeFile = Demo.selectFile("Select edge file", frame,
				".edge.graph");
		try {
			GraphController gc = new GraphController(
					GraphDataRepository.parseGraph(nodeFile, edgeFile));
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
	
	public static File selectFile(String title, JFrame frame, String filter) {
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
