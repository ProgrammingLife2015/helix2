package tudelft.ti2806.pl3.data;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class GraphParserTest {
	private File simpleNodeGraphFile = new File("data/TestNodeGraphFile");
	private File simpleEdgeGraphFile = new File("data/TestEdgeFraphFile");
	
	@Test
	public void parseNodeTest() throws FileNotFoundException {
		Node node = GraphParser.parseNode(new Scanner(simpleNodeGraphFile));
		assertTrue(node.equals(new Node(35, new String[] { "TKK-01-0029" },
				2609451, 2609452, new Gene[] { Gene.A })));
	}
	
	@Test
	public void connectNodesTest() throws FileNotFoundException {
		Scanner scanner = new Scanner(simpleNodeGraphFile);
		Node nodeA = GraphParser.parseNode(scanner);
		Node nodeB = GraphParser.parseNode(scanner);
		
		GraphParser.connectNodes(nodeA, nodeB);
		
		assertTrue(nodeA.outgoingConnections.size() == 1);
		assertTrue(nodeA.incommingConnections.size() == 0);
		
		assertTrue(nodeB.outgoingConnections.size() == 0);
		assertTrue(nodeB.incommingConnections.size() == 1);
	}
	
	@Test
	public void parseEdgeAndNodeTest() throws FileNotFoundException {
		Map<Integer, Node> nodeMap = GraphParser
				.parseNodes(simpleNodeGraphFile);
		GraphParser.parseEdges(simpleEdgeGraphFile, nodeMap);
		
		Node nodeA = nodeMap.get(35);
		assertTrue(nodeA.outgoingConnections.size() == 1);
		assertTrue(nodeA.incommingConnections.size() == 0);
		
		Node nodeB = nodeMap.get(1);
		assertTrue(nodeB.outgoingConnections.size() == 0);
		assertTrue(nodeB.incommingConnections.size() == 1);
	}
	
	@Test
	public void parseGraphTest() throws FileNotFoundException {
		Collection<Node> collection = GraphParser.parseGraph(
				simpleNodeGraphFile, simpleEdgeGraphFile);
		
		List<Node> nodeList = new ArrayList<Node>();
		nodeList.addAll(collection);
		
		Node nodeA = nodeList.get(1);
		assertTrue(nodeA.outgoingConnections.size() == 1);
		assertTrue(nodeA.incommingConnections.size() == 0);
		
		Node nodeB = nodeList.get(0);
		assertTrue(nodeB.outgoingConnections.size() == 0);
		assertTrue(nodeB.incommingConnections.size() == 1);
	}
}
