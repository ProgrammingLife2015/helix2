package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Constants;
import tudelft.ti2806.pl3.util.FileSelector;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * LastOpenedMenu displays the files that were last opened.
 * Created by Kasper on 15-6-2015.
 */
public class LastOpenedMenu extends JMenu {

	public LastOpenedMenu() {
		super(Constants.MENU_FILE_OPEN_RECENTS);
		this.setRecentFiles();
	}

	/**
	 * Set the files in the menu.
	 */
	public void setRecentFiles() {
		for (File file : FileSelector.lastopened) {
			JMenuItem recentfile = new JMenuItem(file.toString());
			this.add(recentfile);
		}
	}

	/**
	 * Add listener for the JMenuItems.
	 *
	 * @param listener
	 * 		actionListener
	 */
	public void addActionListener(ActionListener listener) {
		for (Component component : this.getMenuComponents()) {
			if (component instanceof JMenuItem) {
				((JMenuItem) component).addActionListener(listener);
			}
		}
	}
}
