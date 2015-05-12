package tudelft.ti2806.pl3.controls;

import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.util.ShortcutManager;
import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controls the keys that are used in the application Created by Kasper on
 * 9-5-2015.
 */
public class KeyController implements KeyListener,ShortcutManager {
	/**
	 * Percentage of the screen that is moved
	 */
	private static final double MOVE_FACTOR = 10.0;

	private Application app;
	private GraphController graphController;



	
	/**
	 * Constructor removes the old keylisteners and adds our own
	 * 
	 * @param app
	 *            that is controlled
	 */
	public KeyController(Application app) {
		// remove the default keylistener
		KeyListener[] keyListeners = app.getGraphController().getPanel().getKeyListeners();
		ShortcutManager graphkeys = (ShortcutManager)keyListeners[0];
		graphkeys.release();
		// add our keylistener
		this.app = app;
		graphController = app.getGraphController();
	}

	@Override
	public void init(GraphicGraph graph, View view) {

	}

	@Override
	public void release() {
		app.removeKeyListener(this);
	}
	
	/**
	 * KeyTyped is triggered when the unicode character represented by this key
	 * is sent by the keyboard to system input.
	 * 
	 * @param event
	 *            key that is typed
	 */
	
	@Override
	public void keyTyped(KeyEvent event) {
		
	}
	
	/**
	 * KeyPressed is triggered when the key goes down.
	 * 
	 * @param event
	 *            key that is pressed
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE && app.confirm()) {
			app.stop();
		}
		
		if (event.getKeyCode() == KeyEvent.VK_SPACE) {
			app.getSideBarController().toggleSideBar();
		}

		if(event.getKeyCode() == KeyEvent.VK_EQUALS) {
			double oldzoom = graphController.getCurrentZoomLevel();
			double newzoom = oldzoom *2;
			graphController.changeZoom(newzoom);
			System.out.println("Zoom in - was: " + oldzoom + "| now: " + newzoom);
		}

		if(event.getKeyCode() == KeyEvent.VK_MINUS){
			double oldzoom = app.getGraphController().getCurrentZoomLevel();
			double newzoom = oldzoom / 2;
			app.getGraphController().changeZoom(newzoom);
			System.out.println("Zoom out - was: " + oldzoom + "| now: " + newzoom);
		}

		if(event.getKeyCode() == KeyEvent.VK_RIGHT){
			long oldViewCenter = graphController.getCurrentZoomCenter();
			double move = (graphController.getPanel().getWidth() / MOVE_FACTOR)
					* graphController.getCurrentZoomLevel();
			long newViewCenter = (long)(oldViewCenter + move);
			graphController.moveView(newViewCenter);
			System.out.println("Moving right - oldcenter: " + oldViewCenter + "| newcenter: " + newViewCenter);
			System.out.println("Move factor: " + move);
		}

		if(event.getKeyCode() == KeyEvent.VK_LEFT){
			long oldViewCenter = graphController.getCurrentZoomCenter();
			double move = (graphController.getPanel().getWidth() / MOVE_FACTOR)
					* graphController.getCurrentZoomLevel();
			long newViewCenter = (long)(oldViewCenter - move);
			graphController.moveView(newViewCenter);
			System.out.println("Moving left - oldcenter: " + oldViewCenter + "| newcenter: " + newViewCenter);
			System.out.println("Move factor: " + move);
		}


	}
	
	/**
	 * KeyReleased is triggered when the key comes up.
	 * 
	 * @param event
	 *            key that is releases
	 */
	@Override
	public void keyReleased(KeyEvent event) {
		
	}
}
