package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for menubar view
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarController implements ActionListener, Controller {

	private Application application;

	/**
	 * Constructs a new controller for {@link MenuBarView}.
	 *
	 * @param application
	 * 		The main application which the action is performed in
	 */
	public MenuBarController(Application application) {
		this.application = application;
	}

	private void stop() {
		application.stop();
	}

	private void readGraphFile() {
		application.makeGraph();
	}

	private void readNwkFile() {
		application.makePhyloTree();
	}

	private void zoomIn() {
		application.getGraphController().zoomLevelUp();
	}

	private void zoomOut() {
		application.getGraphController().zoomLevelDown();
	}

	private void resetView() {
		application.getGraphController().resetZoom();
	}


	/**
	 * Controls the Buttons Events from the {@link MenuBarView}.
	 * It reads the button and then starts the correct task.
	 *
	 * @param e
	 * 		is fired when a {@link javax.swing.JMenuItem} is clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Open node and edge file":
				readGraphFile();
				break;
			case "Open .nwk file":
				readNwkFile();
				break;
			case "Exit":
				stop();
				break;
			case "Zoom in ( + )":
				zoomIn();
				break;
			case "Zoom out ( - )":
				zoomOut();
				break;
			case "Reset view":
				resetView();
				break;
			default:
				break;
		}
	}
}
