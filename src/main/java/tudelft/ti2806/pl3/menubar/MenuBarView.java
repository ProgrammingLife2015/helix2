package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.View;

import java.awt.Component;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

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
		menuBarController = new MenuBarController(application);
		add(setUpFile());
		add(setUpView());
		add(setUpHelp());
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
		// cast is safe since we only add JMenuItems
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
		// ascii value of the shortcuts
		final char plus = 43;
		final char minus = 45;
		final char r = 82;
		final char right = "\u2192".charAt(0);
		final char left = "\u2190".charAt(0);

		JMenu viewMenu = new JMenu("View");

		JMenuItem zoomIn = new JMenuItem("Zoom in");
		zoomIn.setAccelerator(KeyStroke.getKeyStroke(plus));
		JMenuItem zoomOut = new JMenuItem("Zoom out");
		zoomOut.setAccelerator(KeyStroke.getKeyStroke(minus));
		JMenuItem moveLeft = new JMenuItem("Move left");
		moveLeft.setAccelerator(KeyStroke.getKeyStroke(left));
		JMenuItem moveRight = new JMenuItem("Move right");
		moveRight.setAccelerator(KeyStroke.getKeyStroke(right));
		JMenuItem reset = new JMenuItem("Reset view");
		reset.setAccelerator(KeyStroke.getKeyStroke(r));

		viewMenu.add(zoomIn);
		viewMenu.add(zoomOut);
		viewMenu.add(moveLeft);
		viewMenu.add(moveRight);
		viewMenu.add(reset);

		// add action listener for every item
		// cast is safe since we only add JMenuItems
		for (Component component : viewMenu.getMenuComponents()) {
			((JMenuItem) component).addActionListener(menuBarController);
		}

		return viewMenu;
	}

	private JMenu setUpHelp() {
		JMenu helpMenu = new JMenu("Help");

		JMenuItem help = new JMenuItem("Controls");
		JMenuItem about = new JMenuItem("About Me");

		helpMenu.add(help);
		helpMenu.add(about);

		// add action listener for every item
		// cast is safe since we only add JMenuItems
		for (Component component : helpMenu.getMenuComponents()) {
			((JMenuItem) component).addActionListener(menuBarController);
		}

		return helpMenu;
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
