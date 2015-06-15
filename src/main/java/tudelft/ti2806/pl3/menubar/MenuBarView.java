package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.View;

import java.awt.Component;
import java.awt.event.KeyEvent;
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
		super();
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
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem openFolder = new JMenuItem("Open folder");
		openFolder.setMnemonic(KeyEvent.VK_O);
		JMenuItem openNode = new JMenuItem("Open node and edge file");
		openNode.setMnemonic(KeyEvent.VK_N);
		JMenuItem openNwk = new JMenuItem("Open .nwk file");
		openNwk.setMnemonic(KeyEvent.VK_W);
		JMenuItem openMeta = new JMenuItem("Open metadata file");
		openNwk.setMnemonic(KeyEvent.VK_M);
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_X);

		fileMenu.add(openFolder);
		fileMenu.add(openNode);
		fileMenu.add(openNwk);
		fileMenu.add(openMeta);
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
		// ascii value of the shortcuts
		final char plus = '+';
		final char minus = '-';
		final char r = 'R';
		final char right = '→';
		final char left = '←';
		final char g = 'G';

		JMenu viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);

		JMenuItem zoomIn = new JMenuItem("Zoom in");
		zoomIn.setAccelerator(KeyStroke.getKeyStroke(plus));
		zoomIn.setMnemonic(KeyEvent.VK_I);
		JMenuItem zoomOut = new JMenuItem("Zoom out");
		zoomOut.setAccelerator(KeyStroke.getKeyStroke(minus));
		zoomOut.setMnemonic(KeyEvent.VK_U);
		JMenuItem moveLeft = new JMenuItem("Move left");
		moveLeft.setAccelerator(KeyStroke.getKeyStroke(left));
		moveLeft.setMnemonic(KeyEvent.VK_L);
		JMenuItem moveRight = new JMenuItem("Move right");
		moveRight.setAccelerator(KeyStroke.getKeyStroke(right));
		moveRight.setMnemonic(KeyEvent.VK_R);
		JMenuItem reset = new JMenuItem("Reset view");
		reset.setAccelerator(KeyStroke.getKeyStroke(r));
		reset.setMnemonic(KeyEvent.VK_S);
		JMenuItem findGenes = new JMenuItem("Navigate to gene");
		findGenes.setAccelerator(KeyStroke.getKeyStroke(g));
		findGenes.setMnemonic(KeyEvent.VK_G);

		viewMenu.add(zoomIn);
		viewMenu.add(zoomOut);
		viewMenu.add(moveLeft);
		viewMenu.add(moveRight);
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

	private JMenu setUpHelp() {
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem help = new JMenuItem("Controls");
		help.setMnemonic(KeyEvent.VK_C);
		JMenuItem about = new JMenuItem("About Me");
		about.setMnemonic(KeyEvent.VK_A);

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
