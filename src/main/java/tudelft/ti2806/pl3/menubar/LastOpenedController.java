package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.util.FileSelector;
import tudelft.ti2806.pl3.util.observers.Observer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JMenu;

/**
 * LastOpenedController is a ActionListener for the recent files submenu.
 * Created by Kasper on 11-6-2015.
 */
public class LastOpenedController implements ActionListener, Controller, Observer {

	private final Application application;
	private final LastOpenedMenu lastOpenedMenu;

	/**
	 * A controller for the LastOpenedMenu.
	 * @param application
	 *      The application class.
	 */
	public LastOpenedController(Application application) {
		super();
		this.application = application;
		this.lastOpenedMenu = new LastOpenedMenu();
		lastOpenedMenu.setMnemonic(KeyEvent.VK_R);
		lastOpenedMenu.addActionListener(this);
	}

	/**
	 * Is called when the user clicks on a file in the submenu.
	 *
	 * @param e
	 * 		button on which the user clicks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String chosenfile = e.getActionCommand();
		File nodeFile = new File(chosenfile);

		if (chosenfile.endsWith(".nwk")) {
			// tree nodeFile
			System.out.println("phylo tree");
			FileSelector.lastopened.add(nodeFile);
			application.makePhyloTree(nodeFile);
		} else if (chosenfile.endsWith(".node.graph")) {
			FileSelector.lastopened.add(nodeFile);
			// node and edge nodeFile
			File edgeFile = FileSelector.getOtherExtension(nodeFile, ".node.graph", ".edge.graph");
			application.makeGraph(nodeFile, edgeFile, null);

		} else {
			FileSelector.lastopened.add(nodeFile);
			// must be folder
			File[] files = FileSelector.getFilesFromFolder(nodeFile, ".node.graph", ".edge.graph", ".nwk");
			application.makeGraph(files[0], files[1], files[2]);
		}

	}

	public JMenu getLastOpenedMenu() {
		return lastOpenedMenu;
	}

	/**
	 * Update the menu with new last opened when change is notified.
	 */
	@Override
	public void update() {
		lastOpenedMenu.removeAll();
		lastOpenedMenu.setRecentFiles();
		lastOpenedMenu.addActionListener(this);
	}
}
