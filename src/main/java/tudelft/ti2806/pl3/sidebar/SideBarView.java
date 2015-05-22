package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.visualization.ViewInterface;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * The view for the sidebar. The user can select options in the sidebar. Created
 * by Kasper on 7-5-15.
 */

@SuppressWarnings("serial")
public class SideBarView extends JPanel implements ViewInterface {
	public static final double SIDEBAR_FACTOR = 0.40;
	
	/**
	 * Constructs the sidebar view with a fixed width.
	 * @param viewList
	 *          the list of all views
	 */
	public SideBarView(ArrayList<Component> viewList) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(layout);
		setPreferredSize(new Dimension(ScreenSize.getInstance()
				.getSidebarWidth(), ScreenSize.getInstance().getHeight()));
		setMinimumSize(new Dimension(ScreenSize.getInstance()
				.getSidebarWidth(), ScreenSize.getInstance().getHeight()));
		viewList.forEach(this::add);
	}

	@Override
	public Component getPanel() {
		return this;
	}
}
