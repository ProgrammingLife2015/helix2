package tudelft.ti2806.pl3.ui.util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Created by Boris Mattijssen on 14-06-15.
 */
public class DialogUtil {

	private DialogUtil() {
	}

	/**
	 * Open a confirm dialog.
	 *
	 * @param title
	 * 		the title
	 * @param message
	 * 		the message to display
	 * @return the dialog
	 */
	public static boolean confirm(String title, String message) {
		int answer = JOptionPane
				.showConfirmDialog(null, message, title,
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
		return answer == JOptionPane.YES_OPTION;
	}

	/**
	 * Displays a message dialog with a given view.
	 *
	 * @param view
	 * 		to display
	 * @param title
	 * 		of the pop-up
	 */
	public static void displayMessageWithView(Component view, String title) {
		JOptionPane.showMessageDialog(null, view, "Select a gene:", JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Display a message dialog with text.
	 *
	 * @param title
	 * 		of the pop-up
	 * @param message
	 * 		to display
	 */
	public static void displayMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
	}

}
