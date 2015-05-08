package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.zoomBar.ZoomBarView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * The main application view.
 *
 * <p>
 * Created by Boris Mattijssen on 07-05-15.
 */
public class Application extends JPanel {

    private int width = 1800;
    private int height = 1000;
	private JPanel bottom = new JPanel();
    private JLayeredPane main = new JLayeredPane();

	/**
	 * Construct the main application view.
	 */
	public Application() {
		setLayout(new GridLayout());
		setPreferredSize(new Dimension(width, height));
        main.setPreferredSize(new Dimension(width, height - 200));
        bottom.setPreferredSize(new Dimension(width,200));
        add(main);
        add(bottom);
//        add(main);
//        add(right);

//		right.setLayout(new BorderLayout());
//        main.setLayout(new BorderLayout());

//		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
//				main, right);
//		splitPane.setDividerLocation(200);
//
//		add(splitPane);


	}

    /**
     *
     */
	public void setSideBarView(Component view) {
        main.add(view, JLayeredPane.PALETTE_LAYER);
        view.setBounds(0,0,200,1000);
    }

    /**
	 * Add the graph view to the layout.
	 *
	 * @param view
	 *            the graph view panel
	 */
	public void setGraphView(Component view) {
		main.add(view, JLayeredPane.DEFAULT_LAYER);
        view.setBounds(200,0,1600,1000);
	}

	/**
	 * Add the zoom bar view to the layout.
	 *
	 * @param view
	 *            the zoom bar view panel
	 */
	public void setZoomBarView(Component view) {
		bottom.add(view, BorderLayout.SOUTH);
//        view.setBounds(200,1600,1600,200);
	}
}
