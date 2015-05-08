package tudelft.ti2806.pl3.sidebar;

import javax.swing.JLayeredPane;
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

    /**
     * Constructs the sidebar view with a fixed width
     */
    public SideBarView() {
        this.setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200,-1));
        setBackground(Color.PINK);


    }
}
