package tudelft.ti2806.pl3.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ParserLastOpened is  the parser vor LastOpenedStack.
 * It can save the queue to the directory of the JAR.
 * It also read this data when the Application is started again.
 * Created by Kasper on 11-6-2015.
 */
public class ParserLastOpened {

	private static final String saveName = ".save-helix2.txt";
	public static final int limit = 5;

	/**
	 * Saves the output in a .txt file.
	 *
	 * @param output
	 * 		to write
	 * @throws IOException
	 * 		when saving goes wrong
	 */
	public static void saveLastOpened(LastOpenedStack<File> output) throws IOException, InterruptedException {
		String os = System.getProperty("os.name").toLowerCase();
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		File save = new File(s + File.separator + saveName);

		if (os.contains("windows")) {
			Process p = Runtime.getRuntime().exec("attrib -H " + saveName);
			p.waitFor();
		}

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(save.getAbsoluteFile()));
		for (File e : output) {
			bufferedWriter.write(e.toString());
			bufferedWriter.newLine();
		}
		bufferedWriter.close();

		if (os.contains("windows")) {
			Process p = Runtime.getRuntime().exec("attrib +H " + saveName);
			p.waitFor();
		}
	}

	/**
	 * Reads the .txt file with name lastOpenedSave.txt.
	 *
	 * @return Queue of the .txt file
	 * @throws IOException
	 * 		when reading goes wrong
	 */
	public static LastOpenedStack<File> readLastOpened() throws IOException {
		LastOpenedStack<File> result = new LastOpenedStack<>(limit);
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		FileReader fileReader = new FileReader(s + File.separator + saveName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String line;

		while ((line = bufferedReader.readLine()) != null) {
			File readFile = new File(line);
			result.addLast(readFile);
		}

		return result;
	}
}
