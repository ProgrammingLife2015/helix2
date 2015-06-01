package tudelft.ti2806.pl3;

import newick.NewickParser;
import newick.ParseException;
import tudelft.ti2806.pl3.controls.KeyController;
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
 * <p/>
 * <p/>
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
	private FindgenesController findgenesController;

	/**
	 * Construct the main application view.
	 */
	public Application() {
		super("DNA Bazen v0.5");
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

		this.setFocusable(true);
		this.setVisible(true);
	}

	/**
	 * Parses the graph files and makes a graphview.
	 */
	public void makeGraph() {
		try {
			File nodeFile = FileSelector.selectFile("Select node file", this, ".node.graph");
			File edgeFile = new File(nodeFile.getAbsolutePath().replace(".node", ".edge"));
			GeneData geneData = GeneData.parseGenes("data/geneAnnotationsRef");

			final long startTime = System.currentTimeMillis();

			GraphDataRepository gd = new GraphDataRepository();
			gd.addLoadingObserversList(loadingObservers);
			gd.parseGraph(nodeFile, edgeFile, geneData);

			graphView = new GraphView(gd, loadingObservers);
			zoomBarView = new ZoomBarView(getGraphController());
			findgenesController = new FindgenesController(gd, getGraphController());
			findgenesController.setFrame(this);

			setZoomBarView(zoomBarView.getPanel());
			setGraphView(graphView.getPanel());
			graphView.getPanel().requestFocus();

			graphView.getController().init();

			graphView.getPanel().addKeyListener(keys);

			this.setFocusable(true);

			long loadTime = System.currentTimeMillis() - startTime;
			System.out.println("Loadtime: " + loadTime);
		} catch (FileNotFoundException | FileSelectorException exception) {
			if (confirm("Error!", "Your file was not found. Want to try again?")) {
				makeGraph();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses the phylogentic tree and makes a sidebarview.
	 */
	public void makePhyloTree() {
		try {
			File treeFile = FileSelector.selectFile("Select phylogenetic tree file", this, ".nwk");

			sideBarView = new SideBarView();
			sideBarView.addLoadingObserversList(loadingObservers);
			NewickParser.TreeNode tree = TreeParser.parseTreeFile(treeFile);
			PhyloView phyloView = new PhyloView(tree, getGraphController());
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
