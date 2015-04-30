package tudelft.ti2806.pl3.visualization;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Edge;
import tudelft.ti2806.pl3.data.Node;
import tudelft.ti2806.pl3.data.SNode;
import tudelft.ti2806.pl3.data.filter.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayModelTest {
	private static Node[] nodes;
	private static DisplayModel dpm;
	
	private static List<Node> nodeList;
	private static List<Edge> edgeList;
	private static Map<String, Edge> map;
	
	@BeforeClass
	public static void init() {
		nodeList = new ArrayList<Node>();
		nodes = new Node[] { new SNode(0, null, 0, 0, null),
				new SNode(1, null, 0, 0, null), new SNode(2, null, 0, 0, null),
				new SNode(3, null, 0, 0, null), new SNode(4, null, 0, 0, null),
				new SNode(5, null, 0, 0, null), new SNode(6, null, 0, 0, null),
				new SNode(7, null, 0, 0, null), new SNode(8, null, 0, 0, null),
				new SNode(9, null, 0, 0, null) };
		
		for (Node node : nodes) {
			nodeList.add(node);
		}
		map = new HashMap<String, Edge>();
		map.put("0-1", new Edge(nodes[0], nodes[1]));
		map.put("0-2", new Edge(nodes[0], nodes[2]));
		map.put("1-3", new Edge(nodes[1], nodes[3]));
		map.put("2-3", new Edge(nodes[2], nodes[3]));
		map.put("3-4", new Edge(nodes[3], nodes[4]));
		map.put("4-5", new Edge(nodes[4], nodes[5]));
		map.put("5-6", new Edge(nodes[5], nodes[6]));
		map.put("5-7", new Edge(nodes[5], nodes[7]));
		map.put("7-8", new Edge(nodes[7], nodes[8]));
		map.put("8-9", new Edge(nodes[8], nodes[9]));
		edgeList = new ArrayList<Edge>();
		edgeList.addAll(map.values());
		
		dpm = new DisplayModel(edgeList, nodeList);
	}
	
	@Test
	public void findFromEdgesTest() {
		List<Edge> list = dpm.findFromEdges(dpm.getEdgeListClone());
		assertTrue(list.contains(map.get("1-3")));
		assertTrue(list.contains(map.get("2-3")));
		assertTrue(list.contains(map.get("3-4")));
		assertTrue(list.contains(map.get("4-5")));
		assertTrue(list.contains(map.get("7-8")));
		assertTrue(list.contains(map.get("8-9")));
		assertTrue(list.size() == 6);
	}
	
	@Test
	public void findToEdgesTest() {
		List<Edge> list = dpm.findToEdges(dpm.getEdgeListClone());
		assertTrue(list.contains(map.get("0-1")));
		assertTrue(list.contains(map.get("0-2")));
		assertTrue(list.contains(map.get("3-4")));
		assertTrue(list.contains(map.get("4-5")));
		assertTrue(list.contains(map.get("5-6")));
		assertTrue(list.contains(map.get("5-7")));
		assertTrue(list.contains(map.get("7-8")));
		assertTrue(list.contains(map.get("8-9")));
		assertTrue(list.size() == 8);
	}
	
	@Test
	public void findCombinableNodesTest() {
		List<Edge> list = dpm.findCombineableNodes(dpm.getNodeListClone(),
				dpm.getEdgeListClone());
		assertTrue(list.contains(map.get("3-4")));
		assertTrue(list.contains(map.get("4-5")));
		assertTrue(list.contains(map.get("7-8")));
		assertTrue(list.contains(map.get("8-9")));
		assertTrue(list.size() == 4);
	}
	
	@Test
	public void removeDeadEdgesTest() {
		Edge deadEdge = new Edge(nodes[0], new SNode(-1, null, 0, 0, null));
		List<Edge> edgeList = dpm.getEdgeListClone();
		edgeList.add(deadEdge);
		dpm.removeAllDeadEdges(edgeList, dpm.getNodeListClone());
		assertFalse(edgeList.contains(deadEdge));
	}
	
	@Test
	public void combineNodesTest() {
		List<Node> nodeList = dpm.getNodeListClone();
		List<Edge> edgeList = dpm.getEdgeListClone();
		dpm.combineNodes(dpm.findCombineableNodes(nodeList, edgeList),
				nodeList, edgeList);
		for (Edge edge : edgeList) {
			System.out.println(edge.toString());
		}
	}
}
