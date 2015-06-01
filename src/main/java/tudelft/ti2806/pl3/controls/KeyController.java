package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controls the keys that are used in the application.
 * Created by Kasper on 9-5-2015.
 */
public class KeyController implements KeyListener {
	/**
	 * Percentage of the screen that is moved.
	 */
	private static final double MOVE_FACTOR = 10.0;

	private Application app;
	private GraphController graphController;

	/**
	 * Constructor removes the old keylisteners and makes our own.
	 * 
	 * @param app
	 *            that is controlled
	 */
	public KeyController(Application app) {
		// remove the default keylistener

		// add our keylistener
		this.app = app;
	}

	/**
	 * Removes the keylistener from the application.
	 */
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
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			app.stop();
		}

		if (event.getKeyCode() == KeyEvent.VK_SPACE) {
			app.getSideBarController().toggleSideBar();
		}

		if (event.getKeyCode() == KeyEvent.VK_MINUS) {
			app.getGraphController().zoomLevelDown();
		}

		if (event.getKeyCode() == KeyEvent.VK_EQUALS) {
			app.getGraphController().zoomLevelUp();
		}

		if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
			double oldViewCenter = graphController.getCurrentZoomCenter();
			double move = (app.getWidth() / MOVE_FACTOR)
					/ app.getGraphController().getCurrentZoomLevel();
			double newViewCenter = oldViewCenter + move;
			app.getGraphController().moveView(newViewCenter);
		}

		if (event.getKeyCode() == KeyEvent.VK_LEFT) {
			double oldViewCenter = graphController.getCurrentZoomCenter();
			double move = (app.getWidth() / MOVE_FACTOR)
					/ app.getGraphController().getCurrentZoomLevel();
			double newViewCenter = oldViewCenter - move;
			app.getGraphController().moveView(newViewCenter);
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
