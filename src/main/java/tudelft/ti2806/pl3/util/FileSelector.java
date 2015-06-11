package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.exception.FileSelectorException;

import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Let the user select the correct node and egdes files.
 * Created by Kasper on 7-5-15.
 */
public class FileSelector {
	private FileSelector() {
	}

	public static LastOpenedQueue<File> lastopened = new LastOpenedQueue<>(5);

	/**
	 * Opens a file select window.
	 *
	 * @param title
	 * 		of the file chooser
	 * @param frame
	 * 		in which file chooser must be shown
	 * @param filter
	 * 		of the files
	 * @return File that is chosen
	 * @throws FileSelectorException
	 * 		When more than one file is selected
	 */
	public static File selectFile(String title, JFrame frame, String filter) throws FileSelectorException {
		FileDialog fileDialog = new FileDialog(frame, title, FileDialog.LOAD);
		fileDialog.setDirectory(System.getProperty("user.dir"));
		fileDialog.setFile("*" + filter);
		fileDialog.setFilenameFilter((dir, name) -> name.endsWith(filter));
		fileDialog.setVisible(true);
		File[] files = fileDialog.getFiles();
		if (files.length == 1) {
			lastopened.add(files[0]);
			return files[0];
		}
		throw new FileSelectorException();
	}

	/**
	 * Opens a folder select window.
	 *
	 * @param title
	 * 		of the folder chooser
	 * @param frame
	 * 		in which file chooser must be shown
	 * @return File that is chosen
	 * @throws FileSelectorException
	 * 		When more than one folder is selected
	 */
	public static File selectFolder(String title, JFrame frame) throws FileSelectorException {

		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("mac") >= 0) { // OS X
			System.setProperty("apple.awt.fileDialogForDirectories", "true");
			FileDialog fileDialog = new FileDialog(frame, title, FileDialog.LOAD);
			fileDialog.setDirectory(System.getProperty("user.dir"));
			fileDialog.setVisible(true);
			File[] files = fileDialog.getFiles();
			if (files.length == 1) {
				System.setProperty("apple.awt.fileDialogForDirectories", "false");
				lastopened.add(files[0]);
				return files[0];
			}
		} else { // Other OS
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				lastopened.add(fileChooser.getSelectedFile());
				return fileChooser.getSelectedFile();
			}
		}
		throw new FileSelectorException();
	}
}
