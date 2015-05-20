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

		// set the Nimbus Theme
		for (UIManager.LookAndFeelInfo info : UIManager
				.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					System.out.println("Theme is not found, default theme is used");
				} catch (InstantiationException e) {
					System.out.println("Theme is not instantiated, default theme is used");
				} catch (IllegalAccessException e) {
					System.out.println("Theme is not accessible, default theme is used");
				} catch (UnsupportedLookAndFeelException e) {
					System.out.println("Theme is not supported, default theme is used");
				}
				break;
			}
		}

		// get the size of the screen
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle bounds = ge.getMaximumWindowBounds();
		ScreenSize.getInstance().setWidth((int) bounds.getWidth());
		ScreenSize.getInstance().setHeight((int) bounds.getHeight());
		
		Application app = new Application();
		app.start();
	}
}
