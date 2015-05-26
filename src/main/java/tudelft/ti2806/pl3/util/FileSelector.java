package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.exception.FileSelectorException;

import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFrame;

/**
 * Let the user select the correct node and egdes files.
 * Created by Kasper on 7-5-15.
 */
public class FileSelector {
	private FileSelector(){
	}

	/**
	 * Opens a file select window.
	 *
	 * @param title
	 * 		of the file chooser
	 * @param frame
	 * 		in which file chooser must be shown
	 * @param filter
	 * 		of the files
	 * @return
	 *      File that is chosen
	 * @throws FileSelectorException
	 *      When more then one file is selected
	 */
	public static File selectFile(String title, JFrame frame, String filter) throws FileSelectorException {
		FileDialog fileDialog = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
		fileDialog.setDirectory(System.getProperty("user.dir"));
		fileDialog.setFile("*" + filter);
		fileDialog.setFilenameFilter((dir, name) -> name.endsWith(filter));
		fileDialog.setVisible(true);
		File[] files = fileDialog.getFiles();
		if (files.length == 1) {
			return files[0];
		}
		throw new FileSelectorException();
	}
}
