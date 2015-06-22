package tudelft.ti2806.pl3.data.meta;

import tudelft.ti2806.pl3.data.Gender;
import tudelft.ti2806.pl3.data.Genome;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by tombrouws on 15/06/15.
 */
public class MetaParser {
	
	/**
	 * Parses a metadata file and puts the information in genomes if they exist.
	 *
	 * @param metadata
	 *            the file to parse
	 * @param genomeMap
	 *            the map of genomes to put the data in
	 * @throws FileNotFoundException
	 *             when the file cannot be found
	 */
	public static void parseMeta(File metadata, Map<String, Genome> genomeMap) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(
				metadata))));
		parseMeta(br, genomeMap);
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses a metadata file and puts the information in genomes if they exist.
	 * 
	 * @param br
	 *            the {@link BufferedReader} to read from
	 * @param genomeMap
	 *            the map of genomes to put the data in
	 */
	private static void parseMeta(BufferedReader br, Map<String, Genome> genomeMap) {
		try {
			if (br.ready()) {
				br.readLine();
			}
			while (br.ready()) {
				String[] data = br.readLine().split("\t");
				data[0] = data[0].replaceAll("-", "_");
				readData(data, genomeMap);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses a piece of meta data for a single {@link Genome}.
	 * 
	 * @param data
	 *            the string array to parse
	 * @param genomeMap
	 *            the map of genomes to put the data in
	 */
	private static void readData(String[] data, Map<String, Genome> genomeMap) {
		if (!genomeMap.containsKey(data[0])) {
			return;
		}
		Genome g = genomeMap.get(data[0]);
		g.setHivStatus(!data[1].equals("Negative"));
		g.setAge(Integer.parseInt(data[2]));
		g.setGender(Gender.parse(data[3]));
		g.setLocation(data[4]);
		g.setIsolationDate(data[5]);
	}
}
