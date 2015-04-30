package tudelft.ti2806.pl3.visualization;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Edge;
import tudelft.ti2806.pl3.data.Node;
import tudelft.ti2806.pl3.data.SNode;

import java.util.ArrayList;
import java.util.List;

public class DisplayModelTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void test() {
		List<Node> nodeList = new ArrayList<Node>();
		Node[] nodes = new Node[] { new SNode(0, null, 0, 0, null),
		new SNode(1, null, 0, 0, null), new SNode(2, null, 0, 0, null),
		new SNode(3, null, 0, 0, null), new SNode(4, null, 0, 0, null),
		new SNode(5, null, 0, 0, null), new SNode(6, null, 0, 0, null) };
		
		for (Node node : nodes) {
			nodeList.add(node);
		}
		
		List<Edge> edgeList = new ArrayList<Edge>();
		edgeList.add(new Edge(nodes[0], nodes[1]));
		edgeList.add(new Edge(nodes[0], nodes[2]));
		edgeList.add(new Edge(nodes[1], nodes[3]));
		edgeList.add(new Edge(nodes[2], nodes[3]));
		Edge edgeA = new Edge(nodes[3], nodes[4]);
		edgeList.add(edgeA);
		edgeList.add(new Edge(nodes[4], nodes[5]));
		edgeList.add(new Edge(nodes[4], nodes[6]));
		List<Edge> list = DisplayModel.findCombineableNodes(nodeList, edgeList);
		assertTrue(list.contains(edgeA));
		assertTrue(list.size() == 1);
	}
}
