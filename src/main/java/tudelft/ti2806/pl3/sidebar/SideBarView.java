package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.View;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * The view for the sidebar. The user can select options in the sidebar. Created
 * by Kasper on 7-5-15.
 */

@SuppressWarnings("serial")
public class SideBarView extends JPanel implements View {
	public static final double SIDEBAR_FACTOR = 0.40;
	private SideBarController sideBarController;

	/**
	 * Constructs the sidebar view with a fixed width.
	 *
	 *
	 */
	public SideBarView() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(layout);
		setPreferredSize(new Dimension(ScreenSize.getInstance()
				.getSidebarWidth(), ScreenSize.getInstance().getHeight()));
		setMinimumSize(new Dimension(ScreenSize.getInstance()
				.getSidebarWidth(), ScreenSize.getInstance().getHeight()));
		sideBarController = new SideBarController(this);
	}

	public void addToSideBarView(Component view) {
		this.add(view);
	}

	@Override
	public Component getPanel() {
		return this;
	}

	@Override
	public SideBarController getController() {
		return sideBarController;
	}
}
