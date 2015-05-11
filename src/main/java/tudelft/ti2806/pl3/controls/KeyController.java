package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controls the keys that are used in the application Created by Kasper on
 * 9-5-2015.
 */
public class KeyController implements KeyListener {
	private Application app;
	
	/**
	 * Constructor.
	 * 
	 * @param app
	 *            that is controlled
	 */
	public KeyController(Application app) {
		this.app = app;
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
			double oldzoom = app.getGraphController().getCurrentZoomLevel();
			double newzoom = oldzoom *2;
			app.getGraphController().changeZoom(newzoom);
			app.getGraphController().moveView(10000L);
			System.out.println("Zoom in - was: " + oldzoom + "| now: " + newzoom);
		}

		if(event.getKeyCode() == KeyEvent.VK_MINUS){
			double oldzoom = app.getGraphController().getCurrentZoomLevel();
			double newzoom = oldzoom / 2;
			app.getGraphController().changeZoom(newzoom);
			app.getGraphController().moveView(10000L);
			System.out.println("Zoom out - was: " + oldzoom + "| now: " + newzoom);
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
