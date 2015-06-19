package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Constants;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.ui.util.DialogUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

/**
 * Controller for MenuBarView
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarController implements ActionListener, Controller {

	private final MenuBarView menuBarView;
	private final Application application;

	/**
	 * Constructs a new controller for {@link MenuBarView}.
	 *
	 * @param application
	 * 		The main application which the action is performed in
	 */
	public MenuBarController(Application application) {
		this.application = application;
		this.menuBarView = new MenuBarView();
		menuBarView.addActionListener(this);
	}

	public JMenuBar getMenuBar() {
		return menuBarView;
	}

	private void stop() {
		application.stop();
	}

	private void readFolder() {
		application.makeGraphFromFolder();
	}

	private void readGraphFile() {
		application.makeGraphFromFiles();
	}

	private void readNwkFile() {
		application.makePhyloTree();
	}

	private void readMetaFile() {
		application.loadMetaData();
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

	private void showFindGenes() {
		application.getFindGenesController().openDialog();
	}

	private void toggleSideBar() {
		application.getSideBarController().toggleSideBar();
	}

	private void filterMetadata() {
		application.getMetaFilterController().openDialog();
	}

	public void setLastOpenedMenu(Component lastOpenedMenu){
		menuBarView.setLastOpenedMenu(lastOpenedMenu);
	}

	/**
	 * Displays the controls text in a {@link DialogUtil}.
	 */
	private void displayControls() {
		DialogUtil.displayMessageWithView(new JScrollPane(makeControls()), "Controls");
	}

	/**
	 * Make a {@link JTextPane} with controls text.
	 *
	 * @return JTextpane with control text.
	 */
	public JTextPane makeControls() {
		JTextPane textPane = new JTextPane();
		TabStop[] tabs = new TabStop[1];
		tabs[0] = new TabStop(300, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
		TabSet tabSet = new TabSet(tabs);

		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabSet);
		textPane.setParagraphAttributes(aset, false);

		textPane.setText(Constants.INFO_CONTROLS);
		textPane.setEditable(false);
		textPane.setBackground(new Color(240, 240, 240));
		textPane.setPreferredSize(new Dimension(500, 200));

		return textPane;
	}

	/**
	 * Displays the about me text in {@link DialogUtil}.
	 */
	private void displayAbout() {
		DialogUtil.displayMessageWithView(new JScrollPane(makeAbout()), "About me");
	}

	/**
	 * Make a {@link JTextPane} with about me text.
	 *
	 * @return JTextpane with about me text.
	 */
	public JTextPane makeAbout() {
		StyleContext styleContext = new StyleContext();
		DefaultStyledDocument doc = new DefaultStyledDocument(styleContext);
		JTextPane textPane = new JTextPane(doc);

		Style webstyle = doc.addStyle("WebStyle", null);
		StyleConstants.setComponent(webstyle, website());
		try {
			doc.insertString(0, Constants.INFO_ABOUT, null);
			doc.insertString(doc.getLength(), "githublink", webstyle);
		} catch (BadLocationException e) {
			// this will not occur since the text is set on correct locations
			e.printStackTrace();
		}
		textPane.setEditable(false);
		textPane.setBackground(new Color(240, 240, 240));
		textPane.setPreferredSize(new Dimension(500, 200));

		return textPane;
	}

	/**
	 * Makes a clickable JLabel with the github link of our project.
	 * When the user clicks the label, the browser is opened on our github project.
	 * If there is a error the user will get a message of this.
	 *
	 * @return clickable JLabel with URL
	 */
	private JLabel website() {
		JLabel website = new JLabel(Constants.INFO_GITHUB_URL);
		website.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		website.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					URI github = new URI(Constants.INFO_GITHUB_URL);
					Desktop.getDesktop().browse(github);
				} catch (IOException | URISyntaxException exception) {
					String message = "An error has occurred!"
							+ " We are unable to display the GitHub link in your browser.";
					DialogUtil.displayError(message, "Error!");
				}
			}
		});
		website.setForeground(new Color(0, 0, 248));
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
			case Constants.MENU_FILE_OPEN_FOLDER:
				readFolder();
				break;
			case Constants.MENU_FILE_OPEN_GRAPH_FILES:
				readGraphFile();
				break;
			case Constants.MENU_FILE_OPEN_NWK_FILE:
				readNwkFile();
				break;
			case Constants.MENU_FILE_OPEN_META_FILE:
				readMetaFile();
				break;
			case Constants.MENU_FILE_EXIT:
				stop();
				break;
			case Constants.MENU_VIEW_ZOOM_IN:
				zoomIn();
				break;
			case Constants.MENU_VIEW_ZOOM_OUT:
				zoomOut();
				break;
			case Constants.MENU_VIEW_MOVE_LEFT:
				moveLeft();
				break;
			case Constants.MENU_VIEW_MOVE_RIGHT:
				moveRight();
				break;
			case Constants.MENU_VIEW_RESET:
				resetView();
				break;
			case Constants.MENU_VIEW_NAVIGATE_TO_GENE:
				showFindGenes();
				break;
			case Constants.MENU_VIEW_METADATA:
				filterMetadata();
				break;
			case Constants.MENU_HELP_CONTROLS:
				displayControls();
				break;
			case Constants.MENU_HELP_ABOUT:
				displayAbout();
				break;
			case Constants.MENU_VIEW_PYLO:
				toggleSideBar();
				break;
			default:
				break;
		}
	}
}
