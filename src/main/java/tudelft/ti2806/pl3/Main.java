package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.graph.GraphController;
import tudelft.ti2806.pl3.zoomBar.ZoomBarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main application launcher. The layout skeleton will be rendered and
 * all different views will be injected.
 *
 * Created by borismattijssen on 30-04-15.
 */
public class Main extends JDialog {

    private JPanel contentPane;
    private JPanel graphContainer;
    private JPanel zoombarContainer;

    /**
     * Start up the main application window
     */
    public Main() {
        setContentPane(contentPane);
        contentPane.setPreferredSize(new Dimension(1800, 1000));
        setModal(true);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        GraphController graphController = new GraphController();
        ZoomBarController zoombarController = new ZoomBarController();
        graphContainer.add(graphController.getView());
        zoombarContainer.add(zoombarController.getView());
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Main dialog = new Main();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
