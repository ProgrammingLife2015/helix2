package tudelft.ti2806.pl3.data.gene;

import tudelft.ti2806.pl3.util.Resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class containing all gene data.
 */
public class GeneData {

	/**
	 * List of all genes.
	 */
	private ArrayList<Gene> genes;

	/**
	 * Mapping from start index to the actual gene.
	 */
	private Map<Integer, Gene> geneStart;

	/**
	 * Mapping from end index to the actual gene.
	 */
	private Map<Integer, Gene> geneEnd;

	/**
	 * Initialize class with a list of genes and a mapping from their start and
	 * end indices.
	 *
	 * @param genes
	 * 		A list of all genes
	 * @param geneStart
	 * 		A mapping from all start indices to genes
	 * @param geneEnd
	 * 		A mapping from all end indices to genes
	 */
	public GeneData(ArrayList<Gene> genes, Map<Integer, Gene> geneStart,
			Map<Integer, Gene> geneEnd) {
		this.genes = genes;
		this.geneStart = geneStart;
		this.geneEnd = geneEnd;
	}

	public ArrayList<Gene> getGenes() {
		return genes;
	}

	public Map<Integer, Gene> getGeneStart() {
		return geneStart;
	}

	public Map<Integer, Gene> getGeneEnd() {
		return geneEnd;
	}

	/**
	 * Parse the data from UCSC into a list of genes.
	 *
	 * @param filename
	 * 		String specifying the data file
	 * @return GeneData container to hold all genes
	 * @throws IOException
	 * 		when having problems reading the file
	 */
	public static GeneData parseGenes(String filename) throws IOException {
		ArrayList<Gene> genes = new ArrayList<>();
		Map<Integer, Gene> geneStart = new HashMap<>();
		Map<Integer, Gene> geneEnd = new HashMap<>();

		Reader reader;
		try {
			InputStream fileInputStream = Resources.getResourceAsStream(filename);
			reader = new InputStreamReader(fileInputStream);
		} catch (NullPointerException e) {
			InputStream fileInputStream = new FileInputStream(filename);
			reader = new InputStreamReader(fileInputStream);
		}
		BufferedReader bufferedReader = new BufferedReader(reader);

		String line;
		// Read File Line By Line
		while ((line = bufferedReader.readLine()) != null) {
			// don't parse comments
			if (line.charAt(0) != '#') {
				parseGene(line, genes, geneStart, geneEnd);
			}
		}
		bufferedReader.close();

		return new GeneData(genes, geneStart, geneEnd);
	}

	/**
	 * Parse a single line from the file and put in the list.
	 *
	 * @param line
	 * 		the line to scan the gene in
	 * @param genes
	 * 		list of genes this gene will be appended to
	 * @param geneStart
	 * 		hashmap to map this gene to by its start index
	 * @param geneEnd
	 * 		hashmap to map this gene to by its end index
	 */
	protected static void parseGene(String line, ArrayList<Gene> genes, Map<Integer, Gene> geneStart,
			Map<Integer, Gene> geneEnd) {
		String[] tokens = line.split("\t");
		Gene gene = new Gene(tokens[1], Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
		genes.add(gene);
		geneStart.put(Integer.parseInt(tokens[4]), gene);
		geneEnd.put(Integer.parseInt(tokens[5]), gene);
	}

}
