package tudelft.ti2806.pl3.data;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * GraphParser is an util class to parse .graph files.
 * 
 * @author Sam Smulders
 */
public class GraphParser {
	/**
	 * Parse the nodesFile and edgesFile into the graph.
	 * 
	 * @param name
	 *            the name of the graph
	 * @param nodesFile
	 *            the file with nodes to be read
	 * @param edgesFile
	 *            the file with edges to be read
	 * @return the Graph parsed from the files
	 * @throws FileNotFoundException
	 *             if one of the files is not found
	 */
	public static Graph parseGraph(String name, File nodesFile, File edgesFile)
			throws FileNotFoundException {
		Graph graph = new SingleGraph(name);
		parseNodes(graph, nodesFile);
		parseEdges(graph, edgesFile);
		return graph;
	}
	
	/**
	 * Parse the edges file, adding the connections between the nodes.
	 * 
	 * @param graph
	 *            the graph to add the edges on
	 * @param edgesFile
	 *            the file of edges to read
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	protected static void parseEdges(Graph graph, File edgesFile)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(edgesFile);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] index = line.split(" ");
			String fromId = index[0];
			String toId = index[1];
			Node from = graph.getNode(fromId);
			Node to = graph.getNode(toId);
			graph.addEdge(line, from, to);
			connect(from, toId, NodeAttributes.OUTGOING_CONNECTIONS);
			connect(to, fromId, NodeAttributes.INCOMING_CONNECTIONS);
		}
		scanner.close();
	}
	
	/**
	 * Add a connection to a node its outgoing or incoming connection list.
	 * 
	 * @param node
	 *            the node to add the connection to
	 * @param target
	 *            the target id
	 * @param attributeId
	 *            the attribute to add the target id to
	 */
	protected static void connect(Node node, String target, String attributeId) {
		List<String> incomming = node.getAttribute(attributeId);
		if (incomming == null) {
			incomming = new ArrayList<String>();
			node.setAttribute(attributeId, incomming);
		}
		incomming.add(target);
	}
	
	/**
	 * Parse the nodes file, creating nodes from the file its data.
	 * 
	 * @param graph
	 *            the graph to add the nodes on
	 * @param nodesFile
	 *            the file of nodes to be read
	 * @throws FileNotFoundException
	 *             if the file is not found
	 */
	protected static void parseNodes(Graph graph, File nodesFile)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(nodesFile);
		while (scanner.hasNext()) {
			parseNode(graph, scanner);
		}
		scanner.close();
	}
	
	/**
	 * Parse a single node.
	 * 
	 * @param graph
	 *            the graph to add the node on
	 * @param scanner
	 *            the scanner with two available lines to read
	 */
	protected static void parseNode(Graph graph, Scanner scanner) {
		String[] indexData = scanner.nextLine().replaceAll("[> ]", "")
				.split("\\|");
		Node node = graph.addNode(indexData[0]);
		node.addAttribute(NodeAttributes.SOURCE, new AttributeArray<String>(
				indexData[1].split(",")));
		node.addAttribute(NodeAttributes.REF_START_POINT,
				Integer.parseInt(indexData[2]));
		node.addAttribute(NodeAttributes.REF_END_POINT,
				Integer.parseInt(indexData[3]));
		node.addAttribute(
				NodeAttributes.CONTENT,
				new AttributeArray<Gene>(Gene.getGeneString(scanner.nextLine())));
	}
}
