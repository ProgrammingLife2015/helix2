package tudelft.ti2806.pl3.ui.util;

/**
 * Always returns YES for a ConfirmOptionPane
 * Created by Kasper on 17-6-2015.
 */
public class YesConfirmOptionPane implements ConfirmOptionPane {

	@Override
	public boolean showConfirmDialog(Object message, String title) {
		return true;
	}
}
