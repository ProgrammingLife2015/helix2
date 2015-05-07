package tudelft.ti2806.pl3.data.graph;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tudelft.ti2806.pl3.data.BasePair;
import tudelft.ti2806.pl3.data.Genome;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GraphParserTest {
	private File simpleNodeGraphFile = new File("data/TestNodeGraphFile");
	private File simpleEdgeGraphFile = new File("data/TestEdgeFraphFile");
	
	@Test
	public void parseNodeTest() throws FileNotFoundException {
		Node node = GraphData.parseNode(new Scanner(simpleNodeGraphFile),
				new HashMap<String, Genome>());
		assertTrue(node.equals(new SingleNode(35, new Genome[] { new Genome(
				"TKK-01-0029") }, 2609451, 2609452,
				new byte[] { BasePair.A.storeByte })));
	}
	
	@Test
	public void parseEdgeAndNodeTest() throws FileNotFoundException {
		Map<Integer, Node> nodeMap = GraphData.parseNodes(simpleNodeGraphFile,
				new HashMap<String, Genome>());
		List<Edge> edges = GraphData.parseEdges(simpleEdgeGraphFile, nodeMap);
		
		Node nodeA = nodeMap.get(35);
		Node nodeB = nodeMap.get(1);
		assertTrue(edges.get(0).equals(new Edge(nodeA, nodeB)));
		
	}
}