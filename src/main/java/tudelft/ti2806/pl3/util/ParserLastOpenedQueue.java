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
 * ParserLastOpenedQueue is  the parser vor LastOpenedQueue.
 * It can save the queue to the directory of the JAR.
 * It also read this data when the Application is started again.
 * Created by Kasper on 11-6-2015.
 */
public class ParserLastOpenedQueue {

	private static final String saveName = "lastOpenedSave.txt";
	public static final int limit = 5;

	/**
	 * Saves the output in a .txt file.
	 * @param output to write
	 * @throws IOException
	 */
	public static void saveLastOpened(LastOpenedQueue<File> output) throws IOException {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		File save = new File(s + File.separator + saveName);

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(save.getAbsoluteFile()));
		for (File e : output) {
			bufferedWriter.write(e.toString());
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}

	/**
	 * Reads the .txt file with name lastOpenedSave.txt.
	 * @return Queue of the .txt file
	 * @throws IOException
	 */
	public static LastOpenedQueue<File> readLastOpened() throws IOException {
		LastOpenedQueue<File> result = new LastOpenedQueue<>(limit);
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		FileReader fileReader = new FileReader(s + File.separator + saveName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String line;

		while ((line = bufferedReader.readLine()) != null) {
			File readFile = new File(line);
			result.add(readFile);
		}

		return result;
	}
}
