package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.util.FileSelector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * LastOpenedController is a ActionListener for the recent files submenu.
 * Created by Kasper on 11-6-2015.
 */
public class LastOpenedController implements ActionListener,Controller {

	private Application application;
	public LastOpenedController(Application application) {
		super();
		this.application = application;
	}

	/**
	 * Is called when the user clicks on a file in the submenu.
	 * @param e button on which the user clicks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String chosenfile = e.getActionCommand();
		File file = new File(chosenfile);
		if (chosenfile.endsWith(".nwk")) {
			// tree file
			application.makePhyloTree(file);
		}else if (chosenfile.endsWith(".node.graph")) {
			// node and edge file
			File nodeFile = file;
			File edgeFile = FileSelector.getOtherExtension(nodeFile, ".node.graph", ".edge.graph");
			application.makeGraph(nodeFile, edgeFile, null);

		}else{
			// must be folder
			File[] files = FileSelector.getFilesFromFolder(file, ".node.graph", ".edge.graph",".nwk");
			application.makeGraph(files[0], files[1], files[2]);
		}

	}
}
