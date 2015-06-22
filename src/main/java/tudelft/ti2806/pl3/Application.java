package tudelft.ti2806.pl3;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import newick.ParseException;
import tudelft.ti2806.pl3.controls.KeyController;
import tudelft.ti2806.pl3.controls.ScrollListener;
import tudelft.ti2806.pl3.controls.WindowController;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.exception.FileSelectorException;
import tudelft.ti2806.pl3.findgenes.FindGenesController;
import tudelft.ti2806.pl3.loading.LoadingMouse;
import tudelft.ti2806.pl3.menubar.LastOpenedController;
import tudelft.ti2806.pl3.menubar.MenuBarController;
import tudelft.ti2806.pl3.metafilter.MetaFilterController;
import tudelft.ti2806.pl3.sidebar.SideBarController;
import tudelft.ti2806.pl3.sidebar.phylotree.PhyloController;
import tudelft.ti2806.pl3.ui.util.DialogUtil;
import tudelft.ti2806.pl3.util.FileSelector;
import tudelft.ti2806.pl3.util.LastOpenedStack;
import tudelft.ti2806.pl3.util.ParserLastOpened;
import tudelft.ti2806.pl3.util.Resources;
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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;

/**
 * The main application view.
 * Created by Boris Mattijssen on 07-05-15.
 */
@SuppressWarnings("serial")
public class Application extends JFrame implements ControllerContainer {
	/**
	 * The value of the layers used in the view.
	 */
	private static final Integer MIDDLE_LAYER = 50;
	private static final Integer HIGHEST_LAYER = 100;

	private final GraphDataRepository graphDataRepository;
	private final JLayeredPane main;
	private final ArrayList<LoadingObserver> loadingObservers = new ArrayList<>();
	private final KeyController keys;

	private ScreenSize size;


	/**
	 * The controllers of the application.
	 */
	private GraphController graphController;
	private SideBarController sideBarController;
	private ZoomBarController zoomBarController;
	private FindGenesController findGenesController;
	private MetaFilterController metaFilterController;

	/**
	 * Construct the main application view.
	 */
	public Application() {
		super(Constants.APP_NAME);
		setDefaultLookAndFeelDecorated(true);
		this.setIconImage(new ImageIcon(Resources.getResource("helix2.png")).getImage());
		// read the last opened files
		try {
			LastOpenedStack<File> files = ParserLastOpened.readLastOpened();
			FileSelector.setLastOpened(files);
		} catch (IOException e) {
			// the file is missing so there are no last opened.
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
		findGenesController = new FindGenesController(this, graphDataRepository);
		metaFilterController = new MetaFilterController(this, graphDataRepository);
	}

	/**
	 * Make the window visible and set the sizes.
	 */
	private void setUpFrame() {
		// set the size and save it in the singleton
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		size = ScreenSize.getInstance();
		size.calculate();

		// set menu bar
		MenuBarController menuBarController = new MenuBarController(this);
		LastOpenedController lastOpenedController = new LastOpenedController(this);
		menuBarController.setLastOpenedMenu(lastOpenedController.getLastOpenedMenu());
		FileSelector.addLastOpenedObserver(lastOpenedController);
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
			File folder = FileSelector.selectFolder(Constants.DIALOG_SELECT_DATA_FOLDER, this);
			File[] files = FileSelector.getFilesFromFolder(folder, Constants.EXTENSION_NODE,
					Constants.EXTENSION_EDGE, Constants.EXTENSION_PHYLOTREE, Constants.EXTENSION_TEXT);
			makeGraph(files[0], files[1], files[2], files[3]);
		} catch (ArrayIndexOutOfBoundsException exception) {
			if (DialogUtil.confirm(Constants.DIALOG_TITLE_ERROR, "Some necessary files were not found. Want to select a new folder?")) {
				makeGraphFromFolder();
			}
		} catch (FileSelectorException exception) {
			if (DialogUtil.confirm(Constants.DIALOG_TITLE_ERROR, "You have not selected a folder, want to try again?")) {
				makeGraphFromFolder();
			}
		}
	}

	/**
	 * Select only the node and edge files.
	 */
	public void makeGraphFromFiles() {
		try {
			File nodeFile = FileSelector.selectFile(Constants.DIALOG_SELECT_NODE_FILE, this, Constants.EXTENSION_NODE);
			File edgeFile = FileSelector.getOtherExtension(nodeFile,
					Constants.EXTENSION_EDGE, Constants.EXTENSION_NODE);
			makeGraph(nodeFile, edgeFile, null, null);
		} catch (FileSelectorException exception) {
			if (DialogUtil.confirm(Constants.DIALOG_TITLE_ERROR, Constants.DIALOG_FILE_NOT_FOUND)) {
				makeGraphFromFiles();
			}
		}
	}

	/**
	 * Parses the graph files and makes a GraphView.
	 */
	public void makeGraph(File nodeFile, File edgeFile, File treeFile, File metaFile) {
		try {
			graphController.parseGraph(nodeFile, edgeFile, metaFile);

			if (treeFile != null) {
				makePhyloTree(treeFile);
			}
		} catch (FileNotFoundException exception) {
			if (DialogUtil.confirm(Constants.DIALOG_TITLE_ERROR, Constants.DIALOG_FILE_NOT_FOUND)) {
				makeGraph(nodeFile, edgeFile, treeFile, metaFile);
			}
		}
	}

	/**
	 * Parses the phylogenetic tree through file selection and makes a SideBarView.
	 */
	public void makePhyloTree() {
		makePhyloTree(null);
	}

	/**
	 * Parses the phylogenetic tree through automatic selection and makes a SideBarView.
	 */
	public void makePhyloTree(File input) {
		try {
			File treeFile;
			if (input == null) {
				treeFile = FileSelector.selectFile(Constants.DIALOG_SELECT_PHYLOTREE_FILE, this,
						Constants.EXTENSION_PHYLOTREE);
			} else {
				treeFile = input;
			}

			getSideBarController().getPhyloController().parseTree(treeFile);

		} catch (FileSelectorException exception) {
			if (DialogUtil.confirm(Constants.DIALOG_TITLE_ERROR, Constants.DIALOG_FILE_NOT_FOUND)) {
				makePhyloTree();
			}
		} catch (ParseException exception) {
			if (DialogUtil.confirm(Constants.DIALOG_TITLE_ERROR, Constants.DIALOG_FILE_FORMATTED_INCORRECTLY)) {
				makePhyloTree();
			}
		}
	}

	/**
	 * Load the metadata from a separate file.
	 */
	public void loadMetaData() {
		try {
			File metaFile = FileSelector.selectFile("Select metadata file", this, ".txt");
			graphDataRepository.loadMetaData(metaFile);
		} catch (FileSelectorException | FileNotFoundException exception) {
			if (DialogUtil.confirm("Error!", "Your file was not found. Want to try again?")) {
				loadMetaData();
			}
		}
	}

	/**
	 * Stop the application and exit.
	 */
	@SuppressFBWarnings({"DM_EXIT"})
	public void stop() {
		// save data or do something else here
		if (DialogUtil.confirm(Constants.DIALOG_TITLE_EXIT, Constants.DIALOG_EXIT)) {
			try {
				ParserLastOpened.saveLastOpened(FileSelector.getLastopened());
			} catch (IOException | InterruptedException e) {
				DialogUtil.displayError(Constants.DIALOG_TITLE_ERROR, Constants.DIALOG_FAIL_SAVE_FILES);
				e.printStackTrace();
			}
			this.dispose();
			System.exit(0);
		}
	}

	/**
	 * Add the sidebar view to the layout.
	 */
	private void setSideBarView() {
		Component view = getSideBarController().getPanel();
		view.setBounds(0, size.getMenubarHeight(), size.getSideBarWidth(), size.getHeight());
		main.add(view, HIGHEST_LAYER);
		view.setVisible(true);
		view.addKeyListener(keys);
		view.setVisible(false);
	}

	/**
	 * Add the JMenuBar view to the layout.
	 *
	 * @param view
	 * 		the JMenuBar to set as menu.
	 */
	private void setMenuBar(JMenuBar view) {
		view.setBounds(0, 0, size.getWidth(), size.getMenubarHeight());
		main.add(view, HIGHEST_LAYER);
		view.setVisible(true);
	}

	/**
	 * Add the graph view to the layout.
	 */
	private void setGraphView() {
		Component view = getGraphController().getPanel();
		view.setBounds(0, 0, size.getWidth(),
				size.getHeight() - size.getZoomBarHeight());
		main.add(view, MIDDLE_LAYER);
		view.setVisible(true);
		view.addKeyListener(keys);
		view.addMouseWheelListener(new ScrollListener(this));
	}

	/**
	 * Add the zoom bar view to the layout.
	 */
	private void setZoomBarView() {
		Component view = getZoomBarController().getPanel();
		view.setBounds(0, size.getHeight() - size.getZoomBarHeight(),
				size.getWidth(), size.getZoomBarHeight());
		main.add(view, MIDDLE_LAYER);
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

				// resize all the views.
				getSideBarController().getPanel().setBounds(0, size.getMenubarHeight(),
						size.getSideBarWidth(), size.getHeight());
				getGraphController().getPanel().setBounds(0, 0, size.getWidth(),
						size.getHeight() - size.getZoomBarHeight());
				getZoomBarController().getPanel().setBounds(0,
						size.getHeight() - size.getZoomBarHeight(),
						size.getWidth(), size.getZoomBarHeight());
				getPhyloController().getView().updateSize();

				// recalculate the zoombar interestingness.
				getGraphController().calculateCollectInterest();
				getZoomBarController().graphLoaded();

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
	public FindGenesController getFindGenesController() {
		return findGenesController;
	}

	@Override
	public MetaFilterController getMetaFilterController() {
		return metaFilterController;
	}
}
