package tudelft.ti2806.pl3;

import javax.swing.UIManager;

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
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		Application app = new Application();
		app.make();
		app.start();
	}
}
