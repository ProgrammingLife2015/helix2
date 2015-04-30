package tudelft.ti2806.pl3.data;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GraphParserTest {
	private File simpleNodeGraphFile = new File("data/TestNodeGraphFile");
	private File simpleEdgeGraphFile = new File("data/TestEdgeFraphFile");
	
	@Test
	public void parseNodeTest() throws FileNotFoundException {
		Node node = GraphParser.parseNode(new Scanner(simpleNodeGraphFile));
		assertTrue(node.equals(new SNode(35, new String[] { "TKK-01-0029" },
				2609451, 2609452, new Gene[] { Gene.A })));
	}
	
	@Test
	public void parseEdgeAndNodeTest() throws FileNotFoundException {
		Map<Integer, Node> nodeMap = GraphParser
				.parseNodes(simpleNodeGraphFile);
		List<Edge> edges = GraphParser.parseEdges(simpleEdgeGraphFile, nodeMap);
		
		Node nodeA = nodeMap.get(35);
		Node nodeB = nodeMap.get(1);
		System.out.println(edges.get(0).toString());
		assertTrue(edges.get(0).equals(new Edge(nodeA, nodeB)));
		
	}
}
