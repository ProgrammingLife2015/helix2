package tudelft.ti2806.pl3;

import newick.NewickParser;
import newick.ParseException;
import tudelft.ti2806.pl3.controls.KeyController;
import tudelft.ti2806.pl3.controls.ScrollListener;
import tudelft.ti2806.pl3.controls.WindowController;
import tudelft.ti2806.pl3.data.gene.GeneData;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.exception.FileSelectorException;
import tudelft.ti2806.pl3.findgenes.FindgenesController;
import tudelft.ti2806.pl3.loading.LoadingMouse;
import tudelft.ti2806.pl3.menubar.MenuBarView;
import tudelft.ti2806.pl3.sidebar.SideBarController;
import tudelft.ti2806.pl3.sidebar.SideBarView;
import tudelft.ti2806.pl3.sidebar.phylotree.PhyloView;
import tudelft.ti2806.pl3.util.FileSelector;
import tudelft.ti2806.pl3.util.TreeParser;
import tudelft.ti2806.pl3.visualization.GraphController;
import tudelft.ti2806.pl3.visualization.GraphView;
import tudelft.ti2806.pl3.zoomBar.ZoomBarController;
import tudelft.ti2806.pl3.zoomBar.ZoomBarView;

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
public class Application extends JFrame {
	/**
	 * The value of the layers used in the view.
	 */
	private static final Integer MIDDEL_LAYER = 50;
	private static final Integer HIGHEST_LAYER = 100;

	private JLayeredPane main;
	private ScreenSize size;
	private ArrayList<LoadingObserver> loadingObservers = new ArrayList<>();
	private KeyController keys;


	/**
	 * The controllers of the application.
	 */
	private GraphView graphView;
	private SideBarView sideBarView;
	private ZoomBarView zoomBarView;
	private PhyloView phyloView;
	private FindgenesController findgenesController;

	/**
	 * Construct the main application view.
	 */
	public Application() {
		super("Helix²");
		// set the size and save it in the singleton
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		keys = new KeyController(this);

		main = getLayeredPane();
		this.make();
	}

	/**
	 * Make the window visible and set the sizes.
	 */
	public void make() {
		size = ScreenSize.getInstance();
		size.calculate();

		// set menu bar
		MenuBarView menuBarView = new MenuBarView(this);
		setMenuBar(menuBarView.getPanel());
		// set window controller
		WindowController windowController = new WindowController(this);
		addWindowListener(windowController);
		loadingObservers.add(new LoadingMouse(this));

		this.setMinimumSize(new Dimension(size.getMinimumWidth(), size.getMinimumHeight()));

		this.setFocusable(true);
		this.setVisible(true);
	}

	/**
	 * Select the graph files on a folder basis.
	 */
	public void makeGraphFromFolder() {
		try {
			File folder = FileSelector.selectFolder("Select data folder", this);

			File[] nodeFiles = folder.listFiles((dir, name) -> {
					return name.endsWith(".node.graph");
				}
			);
			File[] treeFiles = folder.listFiles((dir, name) -> {
					return name.endsWith("nwk");
				}
			);
			File edgeFile = new File(nodeFiles[0].getAbsolutePath().replace(".node", ".edge"));

			makeGraph(nodeFiles[0], edgeFile, treeFiles[0]);
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
			File edgeFile = new File(nodeFile.getAbsolutePath().replace(".node", ".edge"));
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
	private void makeGraph(File nodeFile, File edgeFile, File treeFile) {
		try {
			GeneData geneData = GeneData.parseGenes("geneAnnotationsRef");

			final long startTime = System.currentTimeMillis();

			GraphDataRepository gd = new GraphDataRepository();
			gd.addLoadingObserversList(loadingObservers);
			gd.parseGraph(nodeFile, edgeFile, geneData);

			graphView = new GraphView(gd, loadingObservers);
			zoomBarView = new ZoomBarView(getGraphController());
			findgenesController = new FindgenesController(gd, getGraphController());
			findgenesController.setFrame(this);
			graphView.addComponentListener(zoomBarView);
			getGraphController().addGraphMovedListener(getZoomBarController());

			this.addComponentListener(resizeAdapter());

			setZoomBarView(zoomBarView.getPanel());
			setGraphView(graphView.getPanel());
			graphView.getPanel().requestFocus();

			graphView.getController().init();

			ScrollListener scrollListener = new ScrollListener(this);
			graphView.getPanel().addKeyListener(keys);
			graphView.getPanel().addMouseWheelListener(scrollListener);

			if (treeFile != null) {
				makePhyloTree(treeFile);
			}

			this.setFocusable(true);

			long loadTime = System.currentTimeMillis() - startTime;
			System.out.println("Loadtime: " + loadTime);
		} catch (FileNotFoundException exception) {
			if (confirm("Error!", "Your file was not found. Want to try again?")) {
				makeGraph(nodeFile, edgeFile, treeFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
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

			sideBarView = new SideBarView();
			sideBarView.addLoadingObserversList(loadingObservers);
			NewickParser.TreeNode tree = TreeParser.parseTreeFile(treeFile);
			phyloView = new PhyloView(tree, getGraphController());
			sideBarView.addToSideBarView(phyloView.getPanel());
			setSideBarView(sideBarView.getPanel());


			sideBarView.getPanel().addKeyListener(keys);

		} catch (FileSelectorException exception) {
			if (confirm("Error!", "Your file was not found. Want to try again?")) {
				makePhyloTree();
			}
		} catch (ParseException | IOException exception) {
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
	 *
	 * @param view
	 * 		the sidebar view panel
	 */
	public void setSideBarView(Component view) {
		view.setBounds(0, size.getMenubarHeight(), size.getSidebarWidth(), size.getHeight());
		main.add(view, HIGHEST_LAYER);
		view.setVisible(true);
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
	 *
	 * @param view
	 * 		the graph view panel
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
	 * 		the zoom bar view panel
	 */
	public void setZoomBarView(Component view) {
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

				sideBarView.getPanel().setBounds(0, size.getMenubarHeight(), size.getSidebarWidth(),
						size.getHeight());
				graphView.getPanel().setBounds(0, 0, size.getWidth(),
						size.getHeight() - size.getZoombarHeight());
				zoomBarView.getPanel().setBounds(0, size.getHeight() - size.getZoombarHeight(),
						size.getWidth(), size.getZoombarHeight());
				phyloView.updateSize();

				main.repaint();
			}
		};
	}

	public GraphController getGraphController() {
		return graphView.getController();
	}

	public SideBarController getSideBarController() {
		return sideBarView.getController();
	}

	public ZoomBarController getZoomBarController() {
		return zoomBarView.getController();
	}

	public FindgenesController getFindgenesController() {
		return findgenesController;
	}

}
