package tudelft.ti2806.pl3.data.graph;

import org.junit.Assert;
import org.junit.Test;
import tudelft.ti2806.pl3.data.BasePair;
import tudelft.ti2806.pl3.data.Genome;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class GraphParserTest {
	private File simpleNodeGraphFile = new File("data/testdata/TestNodeGraphFile");
	private File simpleEdgeGraphFile = new File("data/testdata/TestEdgeFraphFile");
	
	@Test
	public void parseNodeTest() throws FileNotFoundException {
		GraphDataRepository gd = new GraphDataRepository();
		DataNode node = gd.parseNode(new BufferedReader(
						new InputStreamReader(new BufferedInputStream(
								new FileInputStream((simpleNodeGraphFile))))),
				new HashMap<>());
		assertTrue(node.equals(new DataNode(35, new Genome[]{new Genome(
				"TKK_01_0029")}, 2609451, 2609452,
				new byte[]{BasePair.A.storeByte})));
	}
	
	@Test
	public void parseEdgeAndNodeTest() throws FileNotFoundException {
		GraphDataRepository gd = new GraphDataRepository();
		Map<Integer, DataNode> nodeMap = gd.parseNodes(
				simpleNodeGraphFile, new HashMap<>());
		List<Edge> edges = gd.parseEdges(simpleEdgeGraphFile,
				nodeMap);

		DataNode nodeA = nodeMap.get(35);
		DataNode nodeB = nodeMap.get(1);
		Assert.assertEquals(new Edge(nodeA, nodeB), edges.get(0));
	}
}
