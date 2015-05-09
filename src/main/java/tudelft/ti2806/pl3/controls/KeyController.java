package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controls the keys that are used in the application
 * Created by Kasper on 9-5-2015.
 */
public class KeyController implements KeyListener {
	private Application app;

	/**
	 * Constructor
	 * @param app that is controlled
	 */
	public KeyController(Application app) {
		this.app = app;
	}

	/**
	 * KeyTyped is triggered when the unicode character
	 * represented by this key is sent by the keyboard to system input.
	 * @param e key that is typed
	 */

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * KeyPressed is triggered when the key goes down
	 * @param e key that is pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE && app.confirm()){
			app.stop();
		}
	}

	/**
	 * KeyReleased is triggered when the key comes up
	 * @param e key that is releases
	 */
	@Override
	public void keyReleased(KeyEvent e) {

	}
}
