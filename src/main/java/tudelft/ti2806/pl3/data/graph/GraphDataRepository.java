package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.BasePair;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.Node;
import tudelft.ti2806.pl3.data.graph.node.SingleNode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphDataRepository extends AbstractGraphData {
	private int longestNodePath;
	
	/**
	 * Construct a instance of {@code GraphDataRepository}.
	 * 
	 * @param nodes
	 *            the nodes of the graph
	 * @param edges
	 *            the edges of the graph
	 * @param genomes
	 *            all {@link Genome} that are present in the graph
	 */
	public GraphDataRepository(List<Node> nodes, List<Edge> edges,
			List<Genome> genomes) {
		this.nodes = nodes;
		this.edges = edges;
		this.genomes = genomes;
		init();
	}
	
	/**
	 * Initialise the instance.<br>
	 * Calculates the longestNodePath, size and genome order.
	 */
	private void init() {
		int index = 0;
		for (Genome genome : genomes) {
			genome.setYposition(index++);
		}
	}
	
	public List<Node> getNodes() {
		return this.getNodeListClone();
	}
	
	public List<Edge> getEdges() {
		return this.getEdgeListClone();
	}
	
	public List<Genome> getGenomes() {
		return this.getGenomeClone();
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
	public static GraphDataRepository parseGraph(File nodesFile, File edgesFile)
			throws FileNotFoundException {
		Map<String, Genome> genomeMap = new HashMap<String, Genome>();
		Map<Integer, SingleNode> nodeMap = parseNodes(nodesFile, genomeMap);
		List<Node> nodeList = new ArrayList<Node>();
		nodeList.addAll(nodeMap.values());
		List<Genome> genomeList = new ArrayList<Genome>();
		genomeList.addAll(genomeMap.values());
		return new GraphDataRepository(nodeList,
				parseEdges(edgesFile, nodeMap), genomeList);
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
	public static Map<Integer, SingleNode> parseNodes(File nodesFile,
			Map<String, Genome> genomeMap) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(nodesFile))));
		Map<Integer, SingleNode> nodes = new HashMap<Integer, SingleNode>();
		try {
			while (br.ready()) {
				SingleNode node = parseNode(br, genomeMap);
				nodes.put(node.getId(), node);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}
	
	/**
	 * Parses the next two lines of the scanner into a Node.
	 * 
	 * @param br
	 *            the BufferedReader with two available lines to read
	 * @return the read node
	 */
	protected static SingleNode parseNode(BufferedReader br,
			Map<String, Genome> genomes) {
		String[] indexData = new String[0];
		try {
			indexData = br.readLine().replaceAll("[> ]", "").split("\\|");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SingleNode node = null;
		try {
			node = new SingleNode(Integer.parseInt(indexData[0]),
					parseGenomeIdentifiers(indexData[1].split(","), genomes),
					Integer.parseInt(indexData[2]),
					Integer.parseInt(indexData[3]),
					BasePair.getBasePairString(br.readLine()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return node;
	}
	
	private static Genome[] parseGenomeIdentifiers(String[] identifiers,
			Map<String, Genome> genomes) {
		Genome[] result = new Genome[identifiers.length];
		for (int i = 0; i < identifiers.length; i++) {
			Genome genome = genomes.get(identifiers[i]);
			if (genome == null) {
				genome = new Genome(identifiers[i], genomes.size());
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
	public static List<Edge> parseEdges(File edgesFile,
			Map<Integer, SingleNode> nodes) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(edgesFile))));
		List<Edge> list = new ArrayList<Edge>();
		try {
			while (br.ready()) {
				String[] index = br.readLine().split(" ");
				SingleNode nodeFrom = nodes.get(Integer.parseInt(index[0]));
				SingleNode nodeTo = nodes.get(Integer.parseInt(index[1]));
				list.add(new Edge(nodeFrom, nodeTo));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Search for the node in the graph with the given id.
	 * 
	 * @param id
	 *            the id of the node to search
	 * @return the found node<br>
	 *         {@code null} if there is no node with this id in the graph
	 */
	public Node getNodeByNodeId(int id) {
		for (Node node : nodes) {
			if (node.getId() == id) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Search for the edge in the graph with the given from and to id.
	 * 
	 * @param fromId
	 *            the id of the from node on the edge
	 * @param toId
	 *            the id of the to node on the edge
	 * @return the found edge<br>
	 *         {@code null} if there is no node with this id in the graph
	 */
	public Edge getEdge(int fromId, int toId) {
		for (Edge edge : edges) {
			if (edge.getFrom().getId() == fromId
					&& edge.getTo().getId() == toId) {
				return edge;
			}
		}
		return null;
	}
	
	public int getLongestNodePath() {
		return longestNodePath;
	}
	
	public AbstractGraphData getOrigin() {
		return this;
	}
}
