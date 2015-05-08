package tudelft.ti2806.pl3.zoomBar;

import java.awt.*;
import javax.swing.JPanel;


/**
 * The view for the zoom bar.
 * The zoom bar is used to navigate through and zoom in on the graph.
 * Created by Boris Mattijssen on 06-05-15.
 */
public class ZoomBarView extends JPanel {

    private int width = 1600;
    private int height = 200;

	/**
	 * Construct a zoom bar view with a fixed height.
	 */
	public ZoomBarView() {
		this.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(width,height));
        setBackground(Color.black);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
