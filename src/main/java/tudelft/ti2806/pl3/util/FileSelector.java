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

	public static LastOpenedQueue<File> lastopened;

	public static void setLastOpened(LastOpenedQueue<File> lastOpenedQueue) {
		lastopened = lastOpenedQueue;
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

	/**
	 * Filters the files from the folder on extension1 and extension2
	 * @param folder with the files in it
	 * @param extension1 to filter on, will be placed on index 0
	 * @param extension2 to filter on, will be placed on index 1
	 * @param extension3 to filter on, will be placed on index 2
	 * @return Array of files
	 */
	public static File[] getFilesFromFolder(File folder, String extension1, String extension2,String extension3) {
		File[] files = new File[3];

		File[] extension1Files = folder.listFiles((dir, name) -> {
					return name.endsWith(extension1);
				}
		);
		File[] extension2Files = folder.listFiles((dir, name) -> {
					return name.endsWith(extension2);
				}
		);
		File[] extension3Files = folder.listFiles((dir, name) -> {
					return name.endsWith(extension3);
				}
		);
		files[0] = extension1Files[0];
		files[1] = extension2Files[0];
		files[2] = extension3Files[0];

		return files;
	}
}
