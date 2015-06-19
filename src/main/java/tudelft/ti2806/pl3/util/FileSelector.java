package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.exception.FileSelectorException;
import tudelft.ti2806.pl3.util.observers.Observer;

import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Let the user select the correct node and edges files.
 * Created by Kasper on 7-5-15.
 */
public class FileSelector {
	private static final String APPLE_AWT_FILE_DIALOG_FOR_DIRECTORIES = "apple.awt.fileDialogForDirectories";

	private FileSelector() {
	}

	private static LastOpenedStack<File> lastopened;

	public static LastOpenedStack<File> getLastopened() {
		return lastopened;
	}

	public static void setLastOpened(LastOpenedStack<File> lastOpenedStack) {
		lastopened = lastOpenedStack;
	}

	public static void addLastOpened(File file) {
		lastopened.add(file);
	}

	public static void addLastOpenedObserver(Observer observer) {
		lastopened.addObserver(observer);
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
		if (os.contains("mac")) { // OS X
			System.setProperty(APPLE_AWT_FILE_DIALOG_FOR_DIRECTORIES, "true");
			FileDialog fileDialog = new FileDialog(frame, title, FileDialog.LOAD);
			fileDialog.setDirectory(System.getProperty("user.dir"));
			fileDialog.setVisible(true);
			File[] files = fileDialog.getFiles();
			System.setProperty(APPLE_AWT_FILE_DIALOG_FOR_DIRECTORIES, "false");
			if (files.length == 1) {
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
	 * Filters the files from the folder on extension1, extension2 and extension3.
	 *
	 * @param folder
	 * 		with the files in it
	 * @param extensions
	 * 		to filter on.
	 * @return Array of files
	 */
	public static File[] getFilesFromFolder(File folder, String... extensions) {
		File[] files = new File[extensions.length];

		File[][] fileExtensions = new File[extensions.length][];
		for (int i = 0; i < extensions.length; i++) {
			String extension = extensions[i];
			fileExtensions[i] = folder.listFiles((dir, name) -> { return name.endsWith(extension); });
		}

		for (int i = 0; i < files.length; i++) {
			files[i] = fileExtensions[i][0];
		}

		return files;
	}

	/**
	 * Get a file with a other extension, but with the same name as @param file.
	 *
	 * @param file
	 * 		to replace extension of
	 * @param extension1
	 * 		of old file
	 * @param extension2
	 * 		of new file
	 * @return File with the same name as file but other extension
	 */
	public static File getOtherExtension(File file, String extension1, String extension2) {
		return new File(file.getAbsolutePath().replace(extension1, extension2));
	}
}
