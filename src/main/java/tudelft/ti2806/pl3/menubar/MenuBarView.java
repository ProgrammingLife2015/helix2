package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Constants;
import tudelft.ti2806.pl3.View;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Menubarview is the view for the menubar.
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarView extends JMenuBar implements View {

	private final List<JMenu> menus;

	/**
	 * Makes the view of the menubar.
	 */
	public MenuBarView() {
		super();
		menus = new ArrayList<>(3);
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
		JMenu fileMenu = new JMenu(Constants.MENU_FILE);
		menus.add(fileMenu);
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem openFolder = new JMenuItem(Constants.MENU_FILE_OPEN_FOLDER);
		openFolder.setMnemonic(KeyEvent.VK_O);
		JMenuItem openNode = new JMenuItem(Constants.MENU_FILE_OPEN_GRAPH_FILES);
		openNode.setMnemonic(KeyEvent.VK_N);
		JMenuItem openNwk = new JMenuItem(Constants.MENU_FILE_OPEN_NWK_FILE);
		openNwk.setMnemonic(KeyEvent.VK_W);
		JMenuItem exit = new JMenuItem(Constants.MENU_FILE_EXIT);
		exit.setMnemonic(KeyEvent.VK_X);

		fileMenu.add(openFolder);
		fileMenu.add(openNode);
		fileMenu.add(openNwk);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		return fileMenu;
	}

	/**
	 * Sets up the item under the header View.
	 *
	 * @return JMenu view
	 */
	private JMenu setUpView() {

		JMenu viewMenu = new JMenu(Constants.MENU_VIEW);
		menus.add(viewMenu);
		viewMenu.setMnemonic(KeyEvent.VK_V);

		JMenuItem zoomIn = new JMenuItem(Constants.MENU_VIEW_ZOOM_IN);
		zoomIn.setAccelerator(KeyStroke.getKeyStroke(Constants.PLUS));
		zoomIn.setMnemonic(KeyEvent.VK_I);
		JMenuItem zoomOut = new JMenuItem(Constants.MENU_VIEW_ZOOM_OUT);
		zoomOut.setAccelerator(KeyStroke.getKeyStroke(Constants.MINUS));
		zoomOut.setMnemonic(KeyEvent.VK_U);
		JMenuItem moveLeft = new JMenuItem(Constants.MENU_VIEW_MOVE_LEFT);
		moveLeft.setAccelerator(KeyStroke.getKeyStroke(Constants.ARROW_LEFT));
		moveLeft.setMnemonic(KeyEvent.VK_L);
		JMenuItem moveRight = new JMenuItem(Constants.MENU_VIEW_MOVE_RIGHT);
		moveRight.setAccelerator(KeyStroke.getKeyStroke(Constants.ARROW_RIGHT));
		moveRight.setMnemonic(KeyEvent.VK_R);
		JMenuItem reset = new JMenuItem(Constants.MENU_VIEW_RESET);
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,0));
		reset.setMnemonic(KeyEvent.VK_S);
		JMenuItem findGenes = new JMenuItem(Constants.MENU_VIEW_NAVIGATE_TO_GENE);
		findGenes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,0));
		findGenes.setMnemonic(KeyEvent.VK_G);

		viewMenu.add(zoomIn);
		viewMenu.add(zoomOut);
		viewMenu.add(moveLeft);
		viewMenu.add(moveRight);
		viewMenu.add(reset);
		viewMenu.add(findGenes);

		return viewMenu;
	}

	private JMenu setUpHelp() {
		JMenu helpMenu = new JMenu(Constants.MENU_HELP);
		menus.add(helpMenu);
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem help = new JMenuItem(Constants.MENU_HELP_CONTROLS);
		help.setMnemonic(KeyEvent.VK_C);
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		JMenuItem about = new JMenuItem(Constants.MENU_HELP_ABOUT);
		about.setMnemonic(KeyEvent.VK_A);

		helpMenu.add(help);
		helpMenu.add(about);

		return helpMenu;
	}

	public void setLastOpenedMenu(Component lastOpenedMenu) {
		JMenu file = menus.get(0);
		file.add(lastOpenedMenu, 3);
	}

	@Override
	public JMenuBar getPanel() {
		return this;
	}

	/**
	 * Adds an action listener to all menu items.
	 *
	 * @param listener
	 * 		the listener
	 */
	public void addActionListener(ActionListener listener) {
		for (JMenu menu : menus) {
			for (Component component : menu.getMenuComponents()) {
				if (component instanceof JMenuItem) {
					((JMenuItem) component).addActionListener(listener);
				}
			}
		}
	}
}
