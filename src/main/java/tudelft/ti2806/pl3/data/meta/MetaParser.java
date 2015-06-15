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
	 * 		the file to parse
	 * @param genomeMap
	 * 		the map of genomes to put the data in
	 * @throws FileNotFoundException
	 * 		when the file cannot be found
	 */
	public static void parseMeta(File metadata, Map<String, Genome> genomeMap) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(metadata))));
		try {
			if (br.ready()) {
				br.readLine();
			}
			while (br.ready()) {
				String[] data = br.readLine().split(" ");
				if (genomeMap.containsKey(data[0])) {
					Genome g = genomeMap.get(data[0]);

					if (data[1].equals("Negative")) {
						g.setHivStatus(false);
					} else {
						g.setHivStatus(true);
					}
					g.setAge(Integer.parseInt(data[2]));

					if (data[3].equals("Male")) {
						g.setGender(Gender.MALE);
					} else {
						g.setGender(Gender.FEMALE);
					}

					g.setLocation(data[4]);
					g.setIsolationDate(data[5]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
