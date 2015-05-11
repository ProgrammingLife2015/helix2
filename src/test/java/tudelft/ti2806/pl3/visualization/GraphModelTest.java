package tudelft.ti2806.pl3.visualization;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.*;
import tudelft.ti2806.pl3.visualization.GraphModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphModelTest {
	private static SingleNode[] nodes;
	private static GraphModel dpm;
	
	private static List<Node> nodeList;
	private static List<Edge> edgeList;
	private static Map<String, Edge> map;
	private static GraphDataRepository gd;


	/**
	 * Run before tests.
	 */
	@BeforeClass
	public static void init() {
		nodeList = new ArrayList<Node>();

		Genome[] genome = new Genome[]{new Genome("hi", 0)};
		nodes = new SingleNode[] { new SingleNode(0, genome, 0, 0, new byte[0]),
		new SingleNode(1, genome, 0, 0, new byte[0]),
		new SingleNode(2, genome, 0, 0, new byte[0]),
		new SingleNode(3, genome, 0, 0, new byte[0]),
		new SingleNode(4, genome, 0, 0, new byte[0]),
		new SingleNode(5, genome, 0, 0, new byte[0]),
		new SingleNode(6, genome, 0, 0, new byte[0]),
		new SingleNode(7, genome, 0, 0, new byte[0]),
		new SingleNode(8, genome, 0, 0, new byte[0]),
		new SingleNode(9, genome, 0, 0, new byte[0]) };
		
		for (SingleNode node : nodes) {
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
		gd = new GraphDataRepository(nodeList, edgeList,
				new ArrayList<Genome>());
		dpm = new GraphModel(gd);
	}
	
	@Test
	public void findFromEdgesTest() {
		List<Edge> list = dpm.findFromEdges(gd.getEdgeListClone());
		Assert.assertTrue(list.contains(map.get("1-3")));
		Assert.assertTrue(list.contains(map.get("2-3")));
		Assert.assertTrue(list.contains(map.get("3-4")));
		Assert.assertTrue(list.contains(map.get("4-5")));
		Assert.assertTrue(list.contains(map.get("7-8")));
		Assert.assertTrue(list.contains(map.get("8-9")));
		Assert.assertTrue(list.size() == 6);
	}
	
	@Test
	public void findToEdgesTest() {
		List<Edge> list = dpm.findToEdges(gd.getEdgeListClone());
		Assert.assertTrue(list.contains(map.get("0-1")));
		Assert.assertTrue(list.contains(map.get("0-2")));
		Assert.assertTrue(list.contains(map.get("3-4")));
		Assert.assertTrue(list.contains(map.get("4-5")));
		Assert.assertTrue(list.contains(map.get("5-6")));
		Assert.assertTrue(list.contains(map.get("5-7")));
		Assert.assertTrue(list.contains(map.get("7-8")));
		Assert.assertTrue(list.contains(map.get("8-9")));
		Assert.assertTrue(list.size() == 8);
	}
	
	@Test
	public void findCombinableNodesTest() {
		List<Edge> list = dpm.findCombineableNodes(gd.getNodeListClone(),
				gd.getEdgeListClone());
		Assert.assertTrue(list.contains(map.get("3-4")));
		Assert.assertTrue(list.contains(map.get("4-5")));
		Assert.assertTrue(list.contains(map.get("7-8")));
		Assert.assertTrue(list.contains(map.get("8-9")));
		Assert.assertTrue(list.size() == 4);
	}
	
	@Test
	public void removeDeadEdgesTest() {
		Edge deadEdge = new Edge(nodes[0], new SingleNode(-1, null, 0, 0, null));
		List<Edge> edgeList = gd.getEdgeListClone();
		edgeList.add(deadEdge);
		dpm.removeAllDeadEdges(edgeList, gd.getNodeListClone());
		Assert.assertFalse(edgeList.contains(deadEdge));
	}
	
	@Test
	public void combineNodesTest() {
		List<Node> nodeList = gd.getNodeListClone();
		List<Edge> edgeList = gd.getEdgeListClone();
		
		dpm.combineNodes(dpm.findCombineableNodes(nodeList, edgeList),
				nodeList, edgeList);
		Assert.assertTrue(nodeList.size() == 6);
		Assert.assertTrue(edgeList.size() == 6);
	}
}
