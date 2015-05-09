package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.ScreenSize;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;



/**
 * The view for the sidebar.
 * The user can select options in the sidebar.
 * Created by Kasper on 7-5-15.
 */
public class SideBarView extends JPanel {
	public static final double SIDEBAR_FACTOR = 0.25;

    /**
     * Constructs the sidebar view with a fixed width
     */
    public SideBarView() {
        this.setLayout(new BorderLayout());
        setPreferredSize(new Dimension(
				ScreenSize.getInstance().getSidebarWidth(),
				ScreenSize.getInstance().getHeight()));
        setBackground(Color.PINK);
    }
}
