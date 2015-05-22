package tudelft.ti2806.pl3.util;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.testutil.UtilTest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeadEdgeUtilTest {
	private static DataNode[] nodes;
	private static List<DataNode> nodeList;
	private static List<Edge> edgeList;
	private static Map<String, Edge> map;
	private static GraphDataRepository gd;
	
	/**
	 * Run before tests.
	 */
	@BeforeClass
	public static void init() {
		nodeList = new ArrayList<DataNode>();
		
		Genome[] genome = new Genome[] { new Genome("hi") };
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
		gd = new GraphDataRepository(nodeList, edgeList,
				new ArrayList<Genome>());
	}
	
	@Test
	public void removeDeadEdgesTest() {
		Edge deadEdge = new Edge(nodes[0], new DataNode(-1, null, 0, 0, null));
		List<Edge> edgeList = gd.getEdgeListClone();
		edgeList.add(deadEdge);
		DeadEdgeUtil.removeAllDeadEdges(edgeList, gd.getNodeListClone());
		Assert.assertFalse(edgeList.contains(deadEdge));
	}
	
	@Test
	public void privateConstructorTest() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		new UtilTest<DeadEdgeUtil>(DeadEdgeUtil.class)
				.testConstructorIsPrivate();
	}
}
