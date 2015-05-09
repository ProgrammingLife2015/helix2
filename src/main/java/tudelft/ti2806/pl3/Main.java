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
			System.out.println("The Nimbus theme is not found, so the standard theme is used");
		}
		Application app = new Application();
		app.make();
		app.start();
	}
}
