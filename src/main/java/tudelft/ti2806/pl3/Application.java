package tudelft.ti2806.pl3;

import newick.ParseException;
import tudelft.ti2806.pl3.controls.KeyController;
import tudelft.ti2806.pl3.controls.ScrollListener;
import tudelft.ti2806.pl3.controls.WindowController;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.exception.FileSelectorException;
import tudelft.ti2806.pl3.findgenes.FindgenesController;
import tudelft.ti2806.pl3.loading.LoadingMouse;
import tudelft.ti2806.pl3.menubar.LastOpenedController;
import tudelft.ti2806.pl3.menubar.MenuBarController;
import tudelft.ti2806.pl3.sidebar.SideBarController;
import tudelft.ti2806.pl3.sidebar.phylotree.PhyloController;
import tudelft.ti2806.pl3.util.FileSelector;
import tudelft.ti2806.pl3.util.LastOpenedStack;
import tudelft.ti2806.pl3.util.ParserLastOpened;
import tudelft.ti2806.pl3.util.observers.LoadingObserver;
import tudelft.ti2806.pl3.visualization.GraphController;
import tudelft.ti2806.pl3.zoombar.ZoomBarController;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

/**
 * The main application view.
 * Created by Boris Mattijssen on 07-05-15.
 */
@SuppressWarnings("serial")
public class Application extends JFrame implements ControllerContainer {
	/**
	 * The value of the layers used in the view.
	 */
	private static final Integer MIDDEL_LAYER = 50;
	private static final Integer HIGHEST_LAYER = 100;
	private final GraphDataRepository graphDataRepository;

	private JLayeredPane main;
	private ScreenSize size;
	private ArrayList<LoadingObserver> loadingObservers = new ArrayList<>();
	private KeyController keys;


	/**
	 * The controllers of the application.
	 */
	private GraphController graphController;
	private SideBarController sideBarController;
	private ZoomBarController zoomBarController;
	private FindgenesController findgenesController;

	/**
	 * Construct the main application view.
	 */
	public Application() {
		super("Helix" + "\u00B2");
		// read the last opened files
		try {
			LastOpenedStack<File> files = ParserLastOpened.readLastOpened();
			FileSelector.setLastOpened(files);
		} catch (IOException e) {
			// the file is missing so there are no lastopened
			FileSelector.setLastOpened(new LastOpenedStack<>(ParserLastOpened.limit));
		}

		keys = new KeyController(this);
		loadingObservers.add(new LoadingMouse(this));
		main = getLayeredPane();
		graphDataRepository = new GraphDataRepository();

		setUpFrame();
		setUpControllers();
		setUpUi();
	}

	private void setUpControllers() {
		graphController = new GraphController(graphDataRepository);
		graphController.addLoadingObservers(loadingObservers);
		sideBarController = new SideBarController(this);
		sideBarController.addLoadingObserversList(loadingObservers);
		zoomBarController = new ZoomBarController(this);
		findgenesController = new FindgenesController(this, graphDataRepository);
	}

	/**
	 * Make the window visible and set the sizes.
	 */
	public void setUpFrame() {
		// set the size and save it in the singleton
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		size = ScreenSize.getInstance();
		size.calculate();

		// set menu bar
		MenuBarController menuBarController = new MenuBarController(this);
		LastOpenedController lastOpenedController = new LastOpenedController(this);
		menuBarController.setLastOpenedMenu(lastOpenedController.getLastOpenedMenu());
		FileSelector.lastopened.addObserver(lastOpenedController);
		setMenuBar(menuBarController.getMenuBar());

		// set window controller
		WindowController windowController = new WindowController(this);
		addWindowListener(windowController);

		this.setMinimumSize(new Dimension(size.getMinimumWidth(), size.getMinimumHeight()));

		this.setFocusable(true);
		this.setVisible(true);
	}

	private void setUpUi() {
		this.addComponentListener(resizeAdapter());
		setZoomBarView();
		setGraphView();
		setSideBarView();
	}

	/**
	 * Select the graph files on a folder basis.
	 */
	public void makeGraphFromFolder() {
		try {
			File folder = FileSelector.selectFolder("Select data folder", this);
			File[] files = FileSelector.getFilesFromFolder(folder, ".node.graph", ".edge.graph",".nwk");
			makeGraph(files[0], files[1], files[2]);
		} catch (FileSelectorException | NullPointerException exception) {
			if (confirm("Error!", "Your file was not found. Want to try again?")) {
				makeGraphFromFolder();
			}
		}
	}

	/**
	 * Select only the node and edge files.
	 */
	public void makeGraphFromFiles() {
		try {
			File nodeFile = FileSelector.selectFile("Select node file", this, ".node.graph");
			File edgeFile = FileSelector.getOtherExtension(nodeFile, ".node.graph", ".edge.graph");
			makeGraph(nodeFile, edgeFile, null);
		} catch (FileSelectorException exception) {
			if (confirm("Error!", "Your file was not found. Want to try again?")) {
				makeGraphFromFiles();
			}
		}
	}

	/**
	 * Parses the graph files and makes a graphview.
	 */
	public void makeGraph(File nodeFile, File edgeFile, File treeFile) {
		try {
			final long startTime = System.currentTimeMillis();

			graphController.parseGraph(nodeFile, edgeFile);
			if (treeFile != null) {
				makePhyloTree(treeFile);
			}

			long loadTime = System.currentTimeMillis() - startTime;
			System.out.println("Loadtime: " + loadTime);
		} catch (FileNotFoundException exception) {
			if (confirm("Error!", "Your file was not found. Want to try again?")) {
				makeGraph(nodeFile, edgeFile, treeFile);
			}
		}
	}

	/**
	 * Parses the phylogenetic tree through file selection and makes a sidebarview.
	 */
	public void makePhyloTree() {
		makePhyloTree(null);
	}

	/**
	 * Parses the phylogenetic tree through automatic selection and makes a sidebarview.
	 */
	public void makePhyloTree(File input) {
		try {
			File treeFile;
			if (input == null) {
				treeFile = FileSelector.selectFile("Select phylogenetic tree file", this, ".nwk");
			} else {
				treeFile = input;
			}

			getSideBarController().getPhyloController().parseTree(treeFile);


		} catch (FileSelectorException exception) {
			if (confirm("Error!", "Your file was not found. Want to try again?")) {
				makePhyloTree();
			}
		} catch (ParseException exception) {
			if (confirm("Error!", "Your file was not formatted correctly. Want to try again?")) {
				makePhyloTree();
			}
		}
	}

	/**
	 * Stop the application and exit.
	 */
	public void stop() {
		// save data or do something else here
		if (this.confirm("Exit", "Are you sure you want to exit the application? ")) {
			try {
				ParserLastOpened.saveLastOpened(FileSelector.lastopened);
			} catch (IOException | InterruptedException e) {
				System.out.println("Unable to save the files");
				e.printStackTrace();
			}
			this.dispose();
			System.exit(0);
		}
	}

	/**
	 * Asks the user to confirm his choose with a pop up.
	 *
	 * @param title
	 * 		of the popup
	 * @param message
	 * 		in the popup
	 * @return true if yes, false otherwise
	 */
	public boolean confirm(String title, String message) {
		int answer = JOptionPane
				.showConfirmDialog(main, message, title,
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
		return answer == JOptionPane.YES_OPTION;
	}

	/**
	 * Add the sidebar view to the layout.
	 */
	public void setSideBarView() {
		Component view = getSideBarController().getPanel();
		view.setBounds(0, size.getMenubarHeight(), size.getSidebarWidth(), size.getHeight());
		main.add(view, HIGHEST_LAYER);
		view.setVisible(true);
		view.addKeyListener(keys);
		view.setVisible(false);
	}

	/**
	 * Add the menubar view to the layout.
	 *
	 * @param view
	 * 		the menubar view panel
	 */
	public void setMenuBar(JMenuBar view) {
		view.setBounds(0, 0, size.getWidth(), size.getMenubarHeight());
		main.add(view, HIGHEST_LAYER);
		view.setVisible(true);
	}

	/**
	 * Add the graph view to the layout.
	 */
	public void setGraphView() {
		Component view = getGraphController().getPanel();
		view.setBounds(0, 0, size.getWidth(),
				size.getHeight() - size.getZoombarHeight());
		main.add(view, MIDDEL_LAYER);
		view.setVisible(true);
		view.addKeyListener(keys);
		view.addMouseWheelListener(new ScrollListener(this));
	}

	/**
	 * Add the zoom bar view to the layout.
	 */
	public void setZoomBarView() {
		Component view = getZoomBarController().getPanel();
		view.setBounds(0, size.getHeight() - size.getZoombarHeight(),
				size.getWidth(), size.getZoombarHeight());
		main.add(view, MIDDEL_LAYER);
		view.setVisible(true);
	}

	/**
	 * Creates an adapter that updates screen sizes for the components in the view.
	 *
	 * @return the adapter
	 */
	private ComponentAdapter resizeAdapter() {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Rectangle bounds = new Rectangle(main.getWidth(), main.getHeight());

				size.setWidth((int) bounds.getWidth());
				size.setHeight((int) bounds.getHeight());
				size.calculate();

				getSideBarController().getPanel().setBounds(0, size.getMenubarHeight(),
						size.getSidebarWidth(), size.getHeight());
				getGraphController().getPanel().setBounds(0, 0, size.getWidth(),
						size.getHeight() - size.getZoombarHeight());
				getZoomBarController().getPanel().setBounds(0,
						size.getHeight() - size.getZoombarHeight(),
						size.getWidth(), size.getZoombarHeight());
				getPhyloController().getView().updateSize();

				main.repaint();
			}
		};
	}

	@Override
	public GraphController getGraphController() {
		return graphController;
	}

	@Override
	public PhyloController getPhyloController() {
		return sideBarController.getPhyloController();
	}

	@Override
	public SideBarController getSideBarController() {
		return sideBarController;
	}

	@Override
	public ZoomBarController getZoomBarController() {
		return zoomBarController;
	}

	@Override
	public FindgenesController getFindgenesController() {
		return findgenesController;
	}

}
