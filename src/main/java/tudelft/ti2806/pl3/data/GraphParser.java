package tudelft.ti2806.pl3.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GraphParser {
	
	private GraphParser() {
	}
	
	/**
	 * Parse the nodesFile and edgesFile into the graph.
	 * 
	 * @param nodesFile
	 *            the file with nodes to be read
	 * @param edgesFile
	 *            the file with edges to be read
	 * @return a collection of all nodes in the graph
	 * @throws FileNotFoundException
	 *             if one of the files is not found
	 */
	public static Collection<Node> parseGraph(File nodesFile, File edgesFile)
			throws FileNotFoundException {
		Map<Integer, Node> nodes = parseNodes(nodesFile);
		parseEdges(edgesFile, nodes);
		return nodes.values();
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
	protected static Map<Integer, Node> parseNodes(File nodesFile)
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
	protected static Node parseNode(Scanner scanner) {
		String[] indexData = scanner.nextLine().replaceAll("[> ]", "")
				.split("\\|");
		
		Node node = new Node(Integer.parseInt(indexData[0]),
				indexData[1].split(","), Integer.parseInt(indexData[2]),
				Integer.parseInt(indexData[3]), Gene.getGeneString(scanner
						.nextLine()));
		return node;
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
	protected static void parseEdges(File edgesFile, Map<Integer, Node> nodes)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(edgesFile);
		while (scanner.hasNext()) {
			String[] index = scanner.nextLine().split(" ");
			connectNodes(nodes.get(Integer.parseInt(index[0])),
					nodes.get(Integer.parseInt(index[1])));
		}
		scanner.close();
	}
	
	/**
	 * Add a connection on both nodes between the nodes.
	 * 
	 * @param from
	 *            the {@link Node} where the connection is coming from
	 * @param to
	 *            the {@link Node} where the connection is going to
	 */
	protected static void connectNodes(Node from, Node to) {
		to.addIncomming(from);
		from.addOutgoing(to);
	}
}
