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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class GraphParserTest {
	private final File simpleNodeGraphFile = new File("data/testdata/TestNodeGraphFile");
	private final File simpleEdgeGraphFile = new File("data/testdata/TestEdgeGraphFile");
	
	@Test
	public void parseNodeTest() throws FileNotFoundException {
		GraphDataRepository gd = new GraphDataRepository();
		DataNode node = gd.parseNode(new BufferedReader(
						new InputStreamReader(new BufferedInputStream(
								new FileInputStream((simpleNodeGraphFile))))),
				new HashMap<>());
		Set<Genome> set = new HashSet<>(1);
		set.add(new Genome("TKK_01_0029"));
		assertTrue(node.equals(new DataNode(35, set, 3057261, 3057262,
				BasePair.A.name())));
	}
	
	@Test
	public void parseEdgeAndNodeTest() throws FileNotFoundException {
		GraphDataRepository gd = new GraphDataRepository();
		Map<Integer, DataNode> nodeMap = gd.parseNodes(
				simpleNodeGraphFile, new HashMap<>(), null);
		List<Edge> edges = gd.parseEdges(simpleEdgeGraphFile, nodeMap);

		DataNode nodeA = nodeMap.get(35);
		DataNode nodeB = nodeMap.get(1);
		Assert.assertEquals(new Edge(nodeA, nodeB), edges.get(0));
	}
}
