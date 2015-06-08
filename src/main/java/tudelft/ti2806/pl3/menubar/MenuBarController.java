package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

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

	private void moveLeft() {
		application.getGraphController().moveLeft();
	}

	private void moveRight() {
		application.getGraphController().moveRight();
	}

	private void resetView() {
		application.getGraphController().resetZoom();
	}

	private void displayControls() {
		JOptionPane.showMessageDialog(application, "Tekst", "Controls", JOptionPane.PLAIN_MESSAGE);
	}

	private void displayAbout() {
		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(1000, 500));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.append("Helix\u00B2 is a interactive DNA sequence viewer. ");
		textArea.append("It uses semantic zooming to only display relative information.");
		textArea.append(
				"\nThis application was created for as part of a assignment for Contextproject on the TU Delft.");
		textArea.append("\n");
		textArea.append("\nHelix\u00B2 was created by");
		textArea.append("\n-Tom Brouws");
		textArea.append("\n-Boris Mattijssen");
		textArea.append("\n-Mathieu Post");
		textArea.append("\n-Sam Smulders");
		textArea.append("\n-Kasper Wendel");
		textArea.append("\n");
		textArea.append("\nThe code of this application is opensource and can be found on");
		textArea.add(website());
		textArea.setBackground(new Color(240, 240, 240));
		textArea.setEditable(false);
		JOptionPane.showMessageDialog(application, textArea, "About Me", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(application, textArea, "About Me", JOptionPane.PLAIN_MESSAGE);
	}

	private JLabel website() {
		JLabel website = new JLabel("https://github.com/ProgrammingLife3/ProgrammingLife3");
		website.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		website.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("hallo");
			}
		});
		return website;
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
			case "Move left ( \u2190 )":
				moveLeft();
				break;
			case "Move right ( \u2192 )":
				moveRight();
				break;
			case "Reset view":
				resetView();
				break;
			case "Controls":
				displayControls();
				break;
			case "About Me":
				displayAbout();
				break;
			default:
				break;
		}
	}
}
