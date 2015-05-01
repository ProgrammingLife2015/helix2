package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.BasePair;
import tudelft.ti2806.pl3.data.Genome;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GraphData {
	private List<Node> nodes;
	private List<Edge> edges;
	private List<Genome> genomes;
	
	/**
	 * Initiate a instance of {@code GraphData}.
	 * 
	 * @param nodes
	 *            the nodes of the graph
	 * @param edges
	 *            the edges of the graph
	 * @param genomes
	 *            all {@link Genome} that are in the graph
	 */
	public GraphData(List<Node> nodes, List<Edge> edges, List<Genome> genomes) {
		this.nodes = nodes;
		this.edges = edges;
		this.genomes = genomes;
	}
	
	/**
	 * Creates a clone of the edge list without cloning its elements.
	 * 
	 * @return a clone of the edge list of this graph
	 */
	public List<Edge> getEdgeListClone() {
		List<Edge> clone = new ArrayList<Edge>();
		clone.addAll(edges);
		return clone;
	}
	
	/**
	 * Creates a clone of the node list without cloning its elements.
	 * 
	 * @return a clone of the node list
	 */
	public List<Node> getNodeListClone() {
		List<Node> clone = new ArrayList<Node>();
		clone.addAll(nodes);
		return clone;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
	public List<Genome> getGenomes() {
		return genomes;
	}
	
	/**
	 * Parse a node and edge file of a graph into a {@code GraphData}.
	 * 
	 * @param nodesFile
	 *            the file of nodes to be read
	 * @param edgesFile
	 *            the file of edges to be read
	 * @return the parsed {@code GraphData}
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	public static GraphData parseGraph(File nodesFile, File edgesFile)
			throws FileNotFoundException {
		Map<String, Genome> genomeMap = new HashMap<String, Genome>();
		Map<Integer, Node> nodeMap = parseNodes(nodesFile, genomeMap);
		List<Node> nodeList = new ArrayList<Node>();
		nodeList.addAll(nodeMap.values());
		List<Genome> genomeList = new ArrayList<Genome>();
		genomeList.addAll(genomeMap.values());
		return new GraphData(nodeList, parseEdges(edgesFile, nodeMap),
				genomeList);
	}
	
	/**
	 * Parse the nodes file, creating nodes from the file its data.
	 * 
	 * @param nodesFile
	 *            the file of nodes to be read
	 * @param genomeMap
	 *            {@link Genome} mapped on their identifier
	 * @return a list of all nodes, mapped by their node id
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	public static Map<Integer, Node> parseNodes(File nodesFile,
			Map<String, Genome> genomeMap) throws FileNotFoundException {
		Scanner scanner = new Scanner(nodesFile);
		Map<Integer, Node> nodes = new HashMap<Integer, Node>();
		while (scanner.hasNext()) {
			Node node = parseNode(scanner, genomeMap);
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
	protected static Node parseNode(Scanner scanner, Map<String, Genome> genomes) {
		String[] indexData = scanner.nextLine().replaceAll("[> ]", "")
				.split("\\|");
		Node node = new SingleNode(Integer.parseInt(indexData[0]),
				parseGenomeIdentifiers(indexData[1].split(","), genomes),
				Integer.parseInt(indexData[2]), Integer.parseInt(indexData[3]),
				BasePair.getBasePairString(scanner.nextLine()));
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
	protected static List<Edge> parseEdges(File edgesFile,
			Map<Integer, Node> nodes) throws FileNotFoundException {
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
