package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for menubar view
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarController implements ActionListener {

	private Application application;

	public MenuBarController(Application application) {
		this.application = application;
	}

	private void stop() {
		application.stop();
	}

	private void readGraphFile() {
		application.makeGraph();
	}

	private void readNWKFile() {
		application.makePhyloTree();
	}

	private void zoomIn() {
		application.getGraphController().zoomLevelUp();
	}

	private void zoomOut() {
		application.getGraphController().zoomLevelDown();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Open node and edge file":
				readGraphFile();
				break;
			case "Open .nwk file":
				readNWKFile();
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
				// reset view here
				break;
			default:
				break;
		}
	}
}
