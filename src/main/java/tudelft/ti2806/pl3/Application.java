package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.graph.GraphController;
import tudelft.ti2806.pl3.sidebar.SideBarController;
import tudelft.ti2806.pl3.zoomBar.ZoomBarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main application view.
 * <p>
 * <p>
 * Created by Boris Mattijssen on 07-05-15.
 */
public class Application extends JFrame {
	static final Integer LOWEST_LAYER = 1;
	static final Integer MIDDEL_LAYER = 50;
	static final Integer HIGHEST_LAYER = 100;

	private JLayeredPane main;
	private ScreenSize size;

	/**
	 * Construct the main application view.
	 */
	public Application() {
		super("DNA Bazen");
		// set the size and save it in the singleton
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		main = getLayeredPane();
		closeBind();
		keyBinds();
	}

	public void make() {
		this.setVisible(true);
		size = ScreenSize.getInstance();
		size.setHeight(this.getHeight());
		size.setWidth(this.getWidth());
		size.calculate();
	}

	public void start() {
		GraphController graphController = new GraphController(this);
		ZoomBarController zoomBarController = new ZoomBarController(graphController);
		SideBarController sideBarController = new SideBarController(graphController);

		setSideBarView(sideBarController.getView());
		setGraphView(graphController.getView());
		setZoomBarView(zoomBarController.getView());

	}

	public void stop() {
		// save data or do something else here
		this.dispose();
		System.exit(0);
	}

	public boolean confirm() {
		int answer = JOptionPane.showConfirmDialog(main, "You want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return answer == JOptionPane.YES_OPTION;
	}

	public void closeBind() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirm()) stop();
			}
		});
	}

	public void keyBinds() {
		// add key binds for the app here
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (confirm()) stop();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
	}

	public void setSideBarView(Component view) {
		view.setBounds(0, 0, size.getSidebarWidth(), size.getHeight());
		main.add(view, HIGHEST_LAYER);
	}

	/**
	 * Add the graph view to the layout.
	 *
	 * @param view the graph view panel
	 */
	public void setGraphView(Component view) {
		view.setBounds(0, 0, size.getWidth(), size.getHeight() - size.getZoombarHeight());
		main.add(view, MIDDEL_LAYER);
	}

	/**
	 * Add the zoom bar view to the layout.
	 *
	 * @param view the zoom bar view panel
	 */
	public void setZoomBarView(Component view) {
		view.setBounds(0, size.getHeight() - size.getZoombarHeight(), size.getWidth(), size.getZoombarHeight());
		main.add(view, MIDDEL_LAYER);
	}
}
