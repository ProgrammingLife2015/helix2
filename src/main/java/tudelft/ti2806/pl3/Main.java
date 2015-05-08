package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.graph.GraphController;
import tudelft.ti2806.pl3.zoomBar.ZoomBarController;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

/**
 * Main application launcher.
 * The layout skeleton will be rendered and all different views will be injected.
 * Created by Boris Mattijssen on 30-04-15.
 */
public class Main extends JDialog {

	/**
	 * Start up the main application window.
	 * Adds the graph and zoom bar view to the main application view.
	 */
	public Main() {
		GraphController graphController = new GraphController();
		ZoomBarController zoomBarController = new ZoomBarController(graphController);
		Application application = new Application();
		application.setGraphView(graphController.getView());
		application.setZoomBarView(zoomBarController.getView());

		setContentPane(application);
		setModal(true);

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		application.registerKeyboardAction(
				event -> onCancel(),
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	/**
	 * Closes the application.
	 */
	private void onCancel() {
		// add your code here if necessary
		dispose();
	}

	/**
	 * Launch application.
	 * @param args input arguments
	 */
	public static void main(String[] args) {
		Main dialog = new Main();
		dialog.pack();
		dialog.setVisible(true);
		System.exit(0);
	}
}
