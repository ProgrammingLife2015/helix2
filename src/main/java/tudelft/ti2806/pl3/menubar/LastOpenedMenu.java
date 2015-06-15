package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.util.FileSelector;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Created by Kasper on 15-6-2015.
 */
public class LastOpenedMenu extends JMenu {

	public LastOpenedMenu(String name) {
		super(name);
		this.setRecentFiles();
	}

	private void setRecentFiles(){
		for (File file : FileSelector.lastopened) {
			JMenuItem recentfile = new JMenuItem(file.toString());
			this.add(recentfile);
		}
	}

	public void addActionListener(ActionListener listener) {
		for (Component component : this.getMenuComponents()) {
			if (component instanceof JMenuItem) {
				((JMenuItem) component).addActionListener(listener);
			}
		}
	}
}
