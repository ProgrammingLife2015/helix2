package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.controls.KeyController;
import tudelft.ti2806.pl3.controls.WindowController;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.sidebar.SideBarController;
import tudelft.ti2806.pl3.visualization.GraphController;
import tudelft.ti2806.pl3.visualization.GraphModel;
import tudelft.ti2806.pl3.visualization.GraphView;
import tudelft.ti2806.pl3.zoomBar.ZoomBarController;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

/**
 * The main application view.
 * 
 * <p>
 * Created by Boris Mattijssen on 07-05-15.
 */
public class Application extends JFrame {
	/**
	 * The value of the layers used in the view.
	 */
	private static final Integer MIDDEL_LAYER = 50;
	private static final Integer HIGHEST_LAYER = 100;
	
	private JLayeredPane main;
	private ScreenSize size;
	
	/**
	 * The controllers of the application.
	 */
	private GraphController graphController;
	private SideBarController sideBarController;
	private ZoomBarController zoomBarController;
	
	/**
	 * Construct the main application view.
	 */
	public Application() {
		super("DNA Bazen");
		// set the size and save it in the singleton
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		main = getLayeredPane();
		this.make();
	}
	
	/**
	 * Make the window visible and set the sizes.
	 */
	public void make() {
		size = ScreenSize.getInstance();
		size.calculate();
	}
	
	/**
	 * Get the views and set them to the application Set the key and window
	 * controllers.
	 */
	public void start() {
		File nodeFile = FileSelector.selectFile("Select node file", this, ".node.graph");
		File edgeFile = new File(nodeFile.getAbsolutePath().replace(".node", ".edge"));
		try {
			// make the controllers
			GraphDataRepository gd = GraphDataRepository.parseGraph(nodeFile, edgeFile);
			graphController = new GraphController(new GraphView(), new GraphModel(gd));
			zoomBarController = new ZoomBarController(graphController);
			sideBarController = new SideBarController(graphController);

			// set the views
			setSideBarView(sideBarController.getPanel());
			setGraphView(graphController.getPanel());
			setZoomBarView(zoomBarController.getPanel());

			// set default zoom & default view
			graphController.changeZoom(10);
			graphController.moveView(1);

			// set the controls.
			// This is done last so we can remove the default libary keycontroller
			WindowController windowController = new WindowController(this);
			KeyController keys = new KeyController(this);
			addKeyListener(keys);
			addWindowListener(windowController);

			this.setFocusable(true);
			this.setVisible(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.stop();
		}
	}
	
	/**
	 * Stop the application and exit.
	 */
	public void stop() {
		// save data or do something else here
		this.dispose();
		System.exit(0);
	}
	
	/**
	 * Asks the user to confirm his choose.
	 * 
	 * @return true if yes, false otherwise
	 */
	public boolean confirm() {
		int answer = JOptionPane
				.showConfirmDialog(main, "You want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
		return answer == JOptionPane.YES_OPTION;
	}
	
	/**
	 * Add the sidebar view to the layout.
	 * 
	 * @param view
	 *            the sidebar view panel
	 */
	public void setSideBarView(Component view) {
		view.setBounds(0, 0, size.getSidebarWidth(), size.getHeight());
		main.add(view, HIGHEST_LAYER);
		view.setVisible(true);
	}
	
	/**
	 * Add the graph view to the layout.
	 *
	 * @param view
	 *            the graph view panel
	 */
	public void setGraphView(Component view) {
		view.setBounds(0, 0, size.getWidth(),
				size.getHeight() - size.getZoombarHeight());
		main.add(view, MIDDEL_LAYER);
		view.setVisible(true);
	}
	
	/**
	 * Add the zoom bar view to the layout.
	 *
	 * @param view
	 *            the zoom bar view panel
	 */
	public void setZoomBarView(Component view) {
		view.setBounds(0, size.getHeight() - size.getZoombarHeight(),
				size.getWidth(), size.getZoombarHeight());
		main.add(view, MIDDEL_LAYER);
		view.setVisible(true);
	}
	
	public GraphController getGraphController() {
		return graphController;
	}
	
	public SideBarController getSideBarController() {
		return sideBarController;
	}
	
	public ZoomBarController getZoomBarController() {
		return zoomBarController;
	}
}
