package tudelft.ti2806.pl3;

import newick.NewickParser;
import newick.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

/**
 * Let the user select the correct node and egdes files.
 * Created by Kasper on 7-5-15.
 */
public class FileSelector {

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
	 */
	public static File selectFile(String title, JFrame frame, String filter) {
		JFileChooser chooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		chooser.setCurrentDirectory(workingDirectory);
		chooser.setMultiSelectionEnabled(true);
		chooser.setDialogTitle(title);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				} else {
					String path = file.getAbsolutePath().toLowerCase();
					if (path.endsWith(filter)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getDescription() {
				return filter;
			}
		});
		int option = chooser.showOpenDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			File[] sf = chooser.getSelectedFiles();
			if (sf.length == 1) {
				return sf[0];
			}
		}
		return null;
	}

	/**
	 * Parse the phylogenetic tree with the Newick Parser.
	 *
	 * @param treeFile
	 * 		phylogenetic tree file (.nwk)
	 * @return Parsed tree
	 * @throws IOException
	 * 		if the file is wrong
	 * @throws ParseException
	 * 		if the file is not formatted correctly
	 */
	public static NewickParser.TreeNode parseTreeFile(File treeFile) throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(treeFile))));
		NewickParser.TreeNode tree = new NewickParser(new ByteArrayInputStream(br.readLine()
				.replaceAll("(\\d)\\.(\\d*)e-05", "0.0000$1$2")
				.replaceAll("-", "_").getBytes()))
				.tree();

		return tree;
	}
}
