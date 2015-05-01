package tudelft.ti2806.pl3.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GraphParser {
	
	private GraphParser() {
	}
	
	/**
	 * Parse the nodes file, creating nodes from the file its data.
	 * 
	 * @param nodesFile
	 *            the file of nodes to be read
	 * @return a list of all nodes, mapped by their node id
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	public static Map<Integer, Node> parseNodes(File nodesFile)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(nodesFile);
		Map<Integer, Node> nodes = new HashMap<Integer, Node>();
		while (scanner.hasNext()) {
			Node node = parseNode(scanner);
			nodes.put(node.getNodeId(), node);
		}
		scanner.close();
		return nodes;
	}
	
	/**
	 * Parses the next two lines of the scanner into a Node.
	 * 
	 * @param scanner
	 *            the scanner with two available lines to read
	 * @return the read node
	 */
	static Node parseNode(Scanner scanner) {
		String[] indexData = scanner.nextLine().replaceAll("[> ]", "")
				.split("\\|");
		Map<String, Genome> genomes = new HashMap<String, Genome>();
		Node node = new SNode(Integer.parseInt(indexData[0]),
				parseGenomeIdentifiers(indexData[1].split(","), genomes),
				Integer.parseInt(indexData[2]), Integer.parseInt(indexData[3]),
				BasePair.getGeneString(scanner.nextLine()));
		return node;
	}
	
	private static Genome[] parseGenomeIdentifiers(String[] identifiers,
			Map<String, Genome> genomes) {
		Genome[] result = new Genome[identifiers.length];
		for (int i = 0; i < identifiers.length; i++) {
			Genome genome = genomes.get(identifiers[i]);
			if (genome == null) {
				genome = new Genome(identifiers[i]);
				genomes.put(identifiers[i], genome);
			}
			result[i] = genome;
		}
		return result;
	}
	
	/**
	 * Parse the edges file, adding the connections between the nodes.
	 * 
	 * @param edgesFile
	 *            the file of edges to be read
	 * @param nodes
	 *            a list of nodes mapped by their node id.
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	public static List<Edge> parseEdges(File edgesFile, Map<Integer, Node> nodes)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(edgesFile);
		List<Edge> list = new ArrayList<Edge>();
		while (scanner.hasNext()) {
			String[] index = scanner.nextLine().split(" ");
			list.add(new Edge(nodes.get(Integer.parseInt(index[0])), nodes
					.get(Integer.parseInt(index[1]))));
		}
		scanner.close();
		return list;
	}
}
