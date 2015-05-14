package tudelft.ti2806.pl3.util;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.graph.PositionedGraphData;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.node.NodePosition;
import tudelft.ti2806.pl3.visualization.node.NodePositionWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeCombineUtilTest {
	private static DataNode[] nodes;
	private static List<DataNodeInterface> nodeList;
	private static List<Edge> edgeList;
	private static Map<String, Edge> map;
	private static GraphDataRepository gdr;
	private static PositionedGraphData pgd;
	
	/**
	 * Run before tests.
	 */
	@BeforeClass
	public static void init() {
		nodeList = new ArrayList<DataNodeInterface>();
		
		Genome[] genome = new Genome[] { new Genome("hi", 0) };
		nodes = new DataNode[] { new DataNode(0, genome, 0, 0, new byte[0]),
		new DataNode(1, genome, 0, 0, new byte[0]),
		new DataNode(2, genome, 0, 0, new byte[0]),
		new DataNode(3, genome, 0, 0, new byte[0]),
		new DataNode(4, genome, 0, 0, new byte[0]),
		new DataNode(5, genome, 0, 0, new byte[0]),
		new DataNode(6, genome, 0, 0, new byte[0]),
		new DataNode(7, genome, 0, 0, new byte[0]),
		new DataNode(8, genome, 0, 0, new byte[0]),
		new DataNode(9, genome, 0, 0, new byte[0]) };
		
		for (DataNode node : nodes) {
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
		gdr = new GraphDataRepository(nodeList, edgeList,
				new ArrayList<Genome>());
		pgd = new PositionedGraphData(gdr);
	}
	
	/**
	 * Tests if the HorizontalWrapUtil finds the horizontal combine-able groups
	 * {3,4,5} and {7,8,9}. The data nodes should be in this order, however the
	 * order of groups should not matter.
	 */
	@Test
	public void findCombinableNodesTest() {
		List<List<NodePositionWrapper>> list = HorizontalWrapUtil
				.findCombineableNodes(pgd.getPositionedNodes());
		Assert.assertTrue(list.size() == 2);
		Assert.assertTrue(list.get(0).size() == 3);
		Assert.assertTrue(list.get(1).size() == 3);
		// Test order
		int[] order;
		order = getIdList(list.get(0));
		Assert.assertTrue(Arrays.equals(order, new int[] { 3, 4, 5 })
				|| Arrays.equals(order, new int[] { 7, 8, 9 }));
		order = getIdList(list.get(1));
		Assert.assertTrue(Arrays.equals(order, new int[] { 3, 4, 5 })
				|| Arrays.equals(order, new int[] { 7, 8, 9 }));
		// Guaranty existence of both groups.
		Assert.assertFalse(Arrays.equals(getIdList(list.get(0)),
				getIdList(list.get(1))));
	}
	
	private int[] getIdList(List<NodePositionWrapper> group) {
		int[] idList = new int[group.size()];
		for (int i = 0; i < group.size(); i++) {
			idList[i] = ((NodePosition) group.get(i)).getNode().getId();
		}
		return idList;
	}
	
	@Test
	public void verticalCombineTest() {
		PositionedGraphData pgd = new PositionedGraphData(gdr);
		List<List<NodePositionWrapper>> list = VerticalWrapUtil
				.findCombineableNodes(pgd.getPositionedNodes());
		Assert.assertTrue(list.size() == 1);
		PositionedGraphData newPgd = VerticalWrapUtil.collapseGraph(pgd);
		Assert.assertTrue(pgd.getPositionedNodes().size() == 10);
		Assert.assertTrue(newPgd.getPositionedNodes().size() == 9);
	}
	
	@Test
	public void combineNodesVerticalWithMultipleIncomming()
			throws FileNotFoundException {
		File nodesFile = new File("data/6TestCombineNodes.node.graph");
		File edgesFile = new File("data/6TestCombineNodes.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		PositionedGraphData[] pgd = new PositionedGraphData[3];
		pgd[0] = new PositionedGraphData(gdr);
		
		List<List<NodePositionWrapper>> list = VerticalWrapUtil
				.findCombineableNodes(pgd[0].getPositionedNodes());
		Assert.assertTrue(list.size() == 3);
		Assert.assertTrue(pgd[0].getPositionedNodes().size() == 6);
		
		pgd[1] = VerticalWrapUtil.collapseGraph(pgd[0]);
		Assert.assertTrue(pgd[1].getPositionedNodes().size() == 3);
		pgd[2] = HorizontalWrapUtil.collapseGraph(pgd[1]);
		for (NodePositionWrapper some : pgd[2].getPositionedNodes()) {
			System.out.println(some.getIncoming().size() + ":in - out:" + some.getOutgoing().size());
			System.out.println(some.getIdString());
		}
		Assert.assertTrue(pgd[2].getPositionedNodes().size() == 1);
	}
}
