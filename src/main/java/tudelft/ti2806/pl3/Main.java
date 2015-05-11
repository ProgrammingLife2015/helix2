package tudelft.ti2806.pl3;

import javax.swing.UIManager;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

/**
 * Main application launcher.
 * Created by Boris Mattijssen on 30-04-15.
 */
public class Main {

	/**
	 * Launch application.
	 * @param args input arguments
	 */
	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("The Nimbus theme is not found, so the standard theme is used");
		}

		// get the size of the screen
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = ge.getMaximumWindowBounds();
		ScreenSize.getInstance().setWidth((int) bounds.getWidth());
		ScreenSize.getInstance().setHeight((int) bounds.getHeight());

		Application app = new Application();
		app.start();
	}
}
