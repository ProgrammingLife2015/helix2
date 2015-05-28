package tudelft.ti2806.pl3.data.wrapper.util;

import org.junit.Assert;
import org.junit.Test;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.testutil.UtilTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class NodeCombineUtilTest {
	/**
	 * Tests if the HorizontalWrapUtil finds the horizontal combine-able groups
	 * {3,4,5} and {7,8,9}. The data nodes should be in this order, however the
	 * order of groups should not matter.
	 * 
	 * @throws FileNotFoundException
	 *             if file is not found
	 */
	@Test
	public void horizontalVerticalCollapseTest() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/wrapTest.node.graph");
		File edgesFile = new File("data/testdata/wrapTest.edge.graph");
		GraphDataRepository gdr = new GraphDataRepository();
		gdr.parseGraph(nodesFile, edgesFile);
		List<List<Wrapper>> list = HorizontalWrapUtil
				.findCombineableNodes(new WrappedGraphData(gdr)
						.getPositionedNodes());
		Assert.assertEquals(list.size(), 2);
		Assert.assertEquals(list.get(0).size(), 3);
		Assert.assertEquals(list.get(1).size(), 3);
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
		
		// Vertical wrap test
		WrappedGraphData pgd = new WrappedGraphData(gdr);
		list = VerticalWrapUtil.findCombineableNodes(pgd.getPositionedNodes());
		Assert.assertEquals(list.size(), 1);
		WrappedGraphData newPgd = VerticalWrapUtil.collapseGraph(pgd);
		Assert.assertEquals(pgd.getPositionedNodes().size(), 10);
		Assert.assertEquals(newPgd.getPositionedNodes().size(), 9);
	}
	
	private int[] getIdList(List<Wrapper> group) {
		int[] idList = new int[group.size()];
		for (int i = 0; i < group.size(); i++) {
			idList[i] = ((DataNodeWrapper) group.get(i)).getNode().getId();
		}
		return idList;
	}
	
	@Test
	public void combineNodesVerticalWithMultipleIncomming()
			throws FileNotFoundException {
		File nodesFile = new File("data/testdata/6TestCombineNodes.node.graph");
		File edgesFile = new File("data/testdata/6TestCombineNodes.edge.graph");
		WrappedGraphData[] pgd = new WrappedGraphData[3];
		GraphDataRepository gdr = new GraphDataRepository();
		gdr.parseGraph(nodesFile,edgesFile);
		pgd[0] = new WrappedGraphData(gdr);
		
		List<List<Wrapper>> list = VerticalWrapUtil
				.findCombineableNodes(pgd[0].getPositionedNodes());
		Assert.assertEquals(list.size(), 3);
		Assert.assertEquals(pgd[0].getPositionedNodes().size(), 6);
		
		pgd[1] = VerticalWrapUtil.collapseGraph(pgd[0]);
		Assert.assertEquals(pgd[1].getPositionedNodes().size(), 3);
		pgd[2] = HorizontalWrapUtil.collapseGraph(pgd[1]);
		Assert.assertEquals(pgd[2].getPositionedNodes().size(), 1);
		
		// WrapUtil collapse test
		WrappedGraphData graph = WrapUtil.collapseGraph(pgd[0]);
		Assert.assertEquals(graph.getPositionedNodes().size(), 1);
		Assert.assertEquals(graph.getLongestNodePath(), 0);
	}
	
	@Test
	public void spaceWrapUtilTest() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/spaceWrapUtilTest.node.graph");
		File edgesFile = new File("data/testdata/spaceWrapUtilTest.edge.graph");
		GraphDataRepository gdr = new GraphDataRepository();
		gdr.parseGraph(nodesFile, edgesFile);
		WrappedGraphData original = new WrappedGraphData(gdr);
		Assert.assertEquals(original.getPositionedNodes().size(), 7);
		Assert.assertNull(VerticalWrapUtil.collapseGraph(original));
		Assert.assertNull(HorizontalWrapUtil.collapseGraph(original));
		List<List<Wrapper>> combineableNodes = SpaceWrapUtil
				.findCombineableNodes(original.getPositionedNodes());
		Assert.assertEquals(combineableNodes.size(), 1);
		WrappedGraphData nwgd = SpaceWrapUtil.collapseGraph(original);
		Assert.assertEquals(nwgd.getPositionedNodes().size(), 3);
		Assert.assertNull(VerticalWrapUtil.collapseGraph(nwgd));
		Assert.assertNull(HorizontalWrapUtil.collapseGraph(nwgd));
		nwgd = SpaceWrapUtil.collapseGraph(nwgd);
		Assert.assertEquals(nwgd.getPositionedNodes().size(), 1);
	}
	
	@Test
	public void privateConstructorTest() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		new UtilTest<SpaceWrapUtil>(SpaceWrapUtil.class)
				.testConstructorIsPrivate();
		new UtilTest<VerticalWrapUtil>(VerticalWrapUtil.class)
				.testConstructorIsPrivate();
		new UtilTest<HorizontalWrapUtil>(HorizontalWrapUtil.class)
				.testConstructorIsPrivate();
		new UtilTest<WrapUtil>(WrapUtil.class).testConstructorIsPrivate();
	}
	
	@Test
	public void nodeFixTest() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/graphFixTest.node.graph");
		File edgesFile = new File("data/testdata/graphFixTest.edge.graph");
		GraphDataRepository gdr = new GraphDataRepository();
		gdr.parseGraph(nodesFile, edgesFile);
		WrappedGraphData original = new WrappedGraphData(gdr);
		Assert.assertNull(VerticalWrapUtil.collapseGraph(original));
		Assert.assertNull(SpaceWrapUtil.collapseGraph(original));
		Assert.assertEquals(1, original.getPositionedNodes().size(), 4);
		Assert.assertEquals(1, WrapUtil.applyFixNode(original)
				.getPositionedNodes().size(), 6);
		Assert.assertEquals(1, WrapUtil.collapseGraph(original)
				.getPositionedNodes().size(), 1);
	}
}
