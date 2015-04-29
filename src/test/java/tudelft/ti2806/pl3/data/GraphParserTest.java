package tudelft.ti2806.pl3.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class GraphParserTest {
	private File simpleNodeGraphFile = new File("data/TestNodeGraphFile");
	private File simpleEdgeGraphFile = new File("data/TestEdgeFraphFile");
	
	@Test
	public void parseNodeTest() throws FileNotFoundException {
		Graph graph = new SingleGraph("hi");
		GraphParser.parseNode(graph, new Scanner(simpleNodeGraphFile));
		Node node = graph.getNode("35");
		assertNotNull(node);
		assertTrue(new AttributeArray<String>(new String[] { "TKK-01-0029" })
				.equals(node.getAttribute(NodeAttributes.SOURCE)));
		assertTrue(new Integer(2609451).equals(node
				.getAttribute(NodeAttributes.REF_START_POINT)));
		assertTrue(new Integer(2609452).equals(node
				.getAttribute(NodeAttributes.REF_END_POINT)));
		assertTrue(new AttributeArray<Gene>(new Gene[] { Gene.A }).equals(node
				.getAttribute(NodeAttributes.CONTENT)));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void parseEdgeTest() throws FileNotFoundException {
		Graph graph = GraphParser.parseGraph("hi", simpleNodeGraphFile,
				simpleEdgeGraphFile);
		
		Node nodeA = graph.getNode("35");
		assertTrue(((List<String>) nodeA
				.getAttribute(NodeAttributes.OUTGOING_CONNECTIONS)).size() == 1);
		assertTrue(nodeA.getAttribute(NodeAttributes.INCOMING_CONNECTIONS) == null);
		
		Node nodeB = graph.getNode("1");
		assertTrue(nodeB.getAttribute(NodeAttributes.OUTGOING_CONNECTIONS) == null);
		assertTrue(((List<String>) nodeB
				.getAttribute(NodeAttributes.INCOMING_CONNECTIONS)).size() == 1);
		
	}
}
