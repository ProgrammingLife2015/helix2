package tudelft.ti2806.pl3.ui.util;

import javax.swing.JOptionPane;

/**
 * Default implementation of the {@link ConfirmOptionPane}.
 * Created by Kasper on 17-6-2015.
 */
public class DefaultConfirmOptionPane implements ConfirmOptionPane {
	@Override
	public boolean showConfirmDialog(Object message, String title) {
		int answer = JOptionPane
				.showConfirmDialog(null, message, title,
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
		return answer == JOptionPane.YES_OPTION;
	}
}
