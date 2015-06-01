package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.View;

import java.awt.Component;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Menubarview is the view for the menubar.
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarView extends JMenuBar implements View {

	private MenuBarController menuBarController;


	/**
	 * Makes the view of the menubar.
	 *
	 * @param application
	 * 		to place the menubar in.
	 */
	public MenuBarView(Application application) {
		super();
		menuBarController = new MenuBarController(application);
		add(setUpFile());
		add(setUpView());
	}

	/**
	 * Set the Items under the header File.
	 *
	 * @return JMenu file
	 */
	private JMenu setUpFile() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem openNode = new JMenuItem("Open node and edge file");
		JMenuItem openNwk = new JMenuItem("Open .nwk file");
		JMenuItem exit = new JMenuItem("Exit");

		fileMenu.add(openNode);
		fileMenu.add(openNwk);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		// add action listener for every item
		for (Component component : fileMenu.getMenuComponents()) {
			if (component instanceof JMenuItem) {
				((JMenuItem) component).addActionListener(menuBarController);
			}
		}

		return fileMenu;
	}

	/**
	 * Sets up the item under the header View.
	 *
	 * @return JMenu view
	 */
	private JMenu setUpView() {
		JMenu viewMenu = new JMenu("View");

		JMenuItem zoomIn = new JMenuItem("Zoom in ( + )");
		JMenuItem zoomOut = new JMenuItem("Zoom out ( - )");
		JMenuItem reset = new JMenuItem("Reset view");
		JMenuItem findGenes = new JMenuItem("Navigate to gene");

		viewMenu.add(zoomIn);
		viewMenu.add(zoomOut);
		viewMenu.add(reset);
		viewMenu.add(findGenes);

		// add action listener for every item
		for (Component component : viewMenu.getMenuComponents()) {
			if (component instanceof JMenuItem) {
				((JMenuItem) component).addActionListener(menuBarController);
			}
		}

		return viewMenu;
	}

	@Override
	public JMenuBar getPanel() {
		return this;
	}

	@Override
	public MenuBarController getController() {
		return menuBarController;
	}
}
