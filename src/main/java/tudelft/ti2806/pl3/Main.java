package tudelft.ti2806.pl3;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main application launcher. Created by Boris Mattijssen on 30-04-15.
 */
public class Main {

	/**
	 * Launch application.
	 * 
	 * @param args
	 *            input arguments
	 */
	public static void main(String[] args) {

		// set native look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			System.out.println("Theme is not found, default theme is used");
		} catch (InstantiationException e) {
			System.out.println("Theme is not instantiated, default theme is used");
		} catch (IllegalAccessException e) {
			System.out.println("Theme is not accessible, default theme is used");
		} catch (UnsupportedLookAndFeelException e) {
			System.out.println("Theme is not supported, default theme is used");
		}

		// get the size of the screen
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle bounds = ge.getMaximumWindowBounds();
		ScreenSize.getInstance().setWidth((int) bounds.getWidth());
		ScreenSize.getInstance().setHeight((int) bounds.getHeight());
		
		new Application();
	}
}
