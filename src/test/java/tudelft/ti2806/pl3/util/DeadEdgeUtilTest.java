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
import java.util.List;

public class DeadEdgeUtilTest {
	private static List<DataNode> nodeList;
	private static GraphDataRepository gd;

	/**
	 * Run before tests.
	 */
	@BeforeClass
	public static void init() {
		nodeList = new ArrayList<>();

		Genome[] genome = new Genome[]{new Genome("hi")};

		for (int i = 0; i < 10; i++) {
			nodeList.add(new DataNode(i, genome, 0, 0, ""));
		}

		List<Edge> edgeList = new ArrayList<>();
		edgeList.add(new Edge(nodeList.get(0), nodeList.get(1)));
		edgeList.add(new Edge(nodeList.get(0), nodeList.get(2)));
		edgeList.add(new Edge(nodeList.get(1), nodeList.get(3)));
		edgeList.add(new Edge(nodeList.get(2), nodeList.get(3)));
		edgeList.add(new Edge(nodeList.get(3), nodeList.get(4)));
		edgeList.add(new Edge(nodeList.get(4), nodeList.get(5)));
		edgeList.add(new Edge(nodeList.get(5), nodeList.get(6)));
		edgeList.add(new Edge(nodeList.get(5), nodeList.get(7)));
		edgeList.add(new Edge(nodeList.get(7), nodeList.get(8)));
		edgeList.add(new Edge(nodeList.get(8), nodeList.get(9)));
		gd = new GraphDataRepository(nodeList, edgeList,
				new ArrayList<>());
	}

	@Test
	public void removeDeadEdgesTest() {
		Edge deadEdge = new Edge(nodeList.get(0), new DataNode(-1, null, 0, 0, null));
		List<Edge> edgeList = gd.getEdgeListClone();
		edgeList.add(deadEdge);
		DeadEdgeUtil.removeAllDeadEdges(edgeList, gd.getNodeListClone());
		Assert.assertFalse(edgeList.contains(deadEdge));
	}

	@Test
	public void privateConstructorTest() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		new UtilTest<>(DeadEdgeUtil.class)
				.testConstructorIsPrivate();
	}
}
