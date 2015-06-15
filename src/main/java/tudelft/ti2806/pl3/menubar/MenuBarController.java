package tudelft.ti2806.pl3.menubar;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Controller;

import java.awt.Color;
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
import javax.swing.JOptionPane;
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
 * Controller for menubar view
 * Created by Kasper on 27-5-2015.
 */
public class MenuBarController implements ActionListener, Controller {

    private Application application;

    /**
     * Text that is displayed in the About Me option in the Help menu.
     */
    final String about = "Helix² is an interactive DNA sequence viewer. "
            + "It uses semantic zooming to only display relative information. \n"
            + "This application was created as part of an assignment"
            + "for the Context Project at TU Delft.\n"
            + "\n"
            + "Helix² was created by: \n"
            + "- Tom Brouws\n"
            + "- Boris Mattijssen\n"
            + "- Mathieu Post\n"
            + "- Sam Smulders\n"
            + "- Kasper Wendel\n"
            + "\n"
            + "The code of this application is open source and can be found on GitHub: \n";

    /**
     * Text that is displayed in the Controls option in the Help menu.
     */
    final String controls = "Helix² uses key shortcuts to make life easier. "
            + "All the controls that can be used are listed below. \n"
            + "\n"
            + "Zooming in     \t+ \n"
            + "Zooming out    \t - \n"
            + "Reset the view \t R \n"
            + "Move the view to the left \t \u2190 \n"
            + "Move the view to the right \t \u2192 \n"
            + "Gene navigation window \t G \n"
            + "Hide/show phylogenetic tree window \t spacebar \n"
            + "\n"
            + "All of the menus can be controlled with the underlined letter, "
            + "hold the ALT key to activate this.";

    /**
     * Constructs a new controller for {@link MenuBarView}.
     *
     * @param application
     *         The main application which the action is performed in
     */
    public MenuBarController(Application application) {
        this.application = application;
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
        application.getFindgenesController().openDialog();
    }

    /**
     * Displays the controls text in a {@link JTextPane}.
     */
    public void displayControls() {
        JTextPane textPane = new JTextPane();
        TabStop[] tabs = new TabStop[1];
        tabs[0] = new TabStop(300, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
        TabSet tabSet = new TabSet(tabs);

        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabSet);
        textPane.setParagraphAttributes(aset, false);

        textPane.setText(controls);
        textPane.setEditable(false);
        textPane.setBackground(new Color(240, 240, 240));
        textPane.setPreferredSize(new Dimension(500, 200));

        JOptionPane.showMessageDialog(application, new JScrollPane(textPane), "Controls",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays the about me text in a {@link JTextPane}.
     */
    private void displayAbout() {
        StyleContext styleContext = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(styleContext);
        JTextPane textPane = new JTextPane(doc);

        Style webstyle = doc.addStyle("WebStyle", null);
        StyleConstants.setComponent(webstyle, website());
        try {
            doc.insertString(0, about, null);
            doc.insertString(doc.getLength(), "githublink", webstyle);
        } catch (BadLocationException e) {
            // this will not occur since the text is set on correct locations
            e.printStackTrace();
        }
        textPane.setEditable(false);
        textPane.setBackground(new Color(240, 240, 240));
        textPane.setPreferredSize(new Dimension(500, 200));

        JOptionPane.showMessageDialog(application, new JScrollPane(textPane), "About Me",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Makes a clickable JLabel with the github link of our project.
     * When the user clicks the label, the browser is opened on our github project.
     * If there is a error the user will get a message of this.
     *
     * @return clickable JLabel with URL
     */
    private JLabel website() {
        JLabel website = new JLabel("https://github.com/ProgrammingLife3/ProgrammingLife3");
        website.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    URI github = new URI("https://github.com/ProgrammingLife3/ProgrammingLife3");
                    Desktop.getDesktop().browse(github);
                } catch (IOException | URISyntaxException exception) {
                    String message = "An error has occurred!"
                            + " We are unable to display the GitHub link in your browser.";
                    displayError(message);
                }
            }
        });
        website.setForeground(new Color(0, 0, 248));
        return website;
    }

    /**
     * Displays the user a error message.
     *
     * @param message
     *         to displayed on the popup
     */
    private void displayError(String message) {
        JOptionPane.showMessageDialog(application, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Controls the Buttons Events from the {@link MenuBarView}.
     * It reads the button and then starts the correct task.
     *
     * @param e
     *         is fired when a {@link javax.swing.JMenuItem} is clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open folder":
                readFolder();
                break;
            case "Open node and edge file":
                readGraphFile();
                break;
            case "Open .nwk file":
                readNwkFile();
                break;
            case "Exit":
                stop();
                break;
            case "Zoom in":
                zoomIn();
                break;
            case "Zoom out":
                zoomOut();
                break;
            case "Move left":
                moveLeft();
                break;
            case "Move right":
                moveRight();
                break;
            case "Reset view":
                resetView();
                break;
            case "Navigate to gene":
                showFindGenes();
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
