package tudelft.ti2806.pl3.ui.util;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * This class gives the user different kinds of pop-ups.
 * Created by Boris Mattijssen on 14-06-15.
 */
public class DialogUtil {
	private static ConfirmOptionPane confirmOptionPane = new DefaultConfirmOptionPane();

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
		return confirmOptionPane.showConfirmDialog(message, title);
	}

	/**
	 * Displays a question message dialog with a given view.
	 *
	 * @param view
	 * 		to display
	 * @param title
	 * 		of the pop-up
	 */
	public static void displayQuestionMessageWithView(Component view, String title) {
		JOptionPane.showMessageDialog(null, view, title, JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Displays a plain message dialog with a given view.
	 *
	 * @param view
	 * 		to display
	 * @param title
	 * 		of the pop-up
	 */
	public static void displayMessageWithView(Component view, String title) {
		JOptionPane.showMessageDialog(null, view, title, JOptionPane.PLAIN_MESSAGE);
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

	/**
	 * Displays the user a error message.
	 *
	 * @param message
	 * 		to displayed on the popup
	 */
	public static void displayError(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public static void setConfirmOptionPane(ConfirmOptionPane pane) {
		confirmOptionPane = pane;
	}

}
