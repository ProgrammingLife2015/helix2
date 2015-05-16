package tudelft.ti2806.pl3.util.wrap;

import org.junit.Assert;
import org.junit.Test;

import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.visualization.wrapper.NodePosition;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.io.File;
import java.io.FileNotFoundException;
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
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		List<List<NodeWrapper>> list = HorizontalWrapUtil
				.findCombineableNodes(new WrappedGraphData(gdr)
						.getPositionedNodes());
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
		
		// Vertical wrap test
		WrappedGraphData pgd = new WrappedGraphData(gdr);
		list = VerticalWrapUtil.findCombineableNodes(pgd.getPositionedNodes());
		Assert.assertTrue(list.size() == 1);
		WrappedGraphData newPgd = VerticalWrapUtil.collapseGraph(pgd);
		Assert.assertTrue(pgd.getPositionedNodes().size() == 10);
		Assert.assertTrue(newPgd.getPositionedNodes().size() == 9);
	}
	
	private int[] getIdList(List<NodeWrapper> group) {
		int[] idList = new int[group.size()];
		for (int i = 0; i < group.size(); i++) {
			idList[i] = ((NodePosition) group.get(i)).getNode().getId();
		}
		return idList;
	}
	
	@Test
	public void combineNodesVerticalWithMultipleIncomming()
			throws FileNotFoundException {
		File nodesFile = new File("data/testdata/6TestCombineNodes.node.graph");
		File edgesFile = new File("data/testdata/6TestCombineNodes.edge.graph");
		WrappedGraphData[] pgd = new WrappedGraphData[3];
		pgd[0] = new WrappedGraphData(GraphDataRepository.parseGraph(nodesFile,
				edgesFile));
		
		List<List<NodeWrapper>> list = VerticalWrapUtil
				.findCombineableNodes(pgd[0].getPositionedNodes());
		Assert.assertTrue(list.size() == 3);
		Assert.assertTrue(pgd[0].getPositionedNodes().size() == 6);
		
		pgd[1] = VerticalWrapUtil.collapseGraph(pgd[0]);
		Assert.assertTrue(pgd[1].getPositionedNodes().size() == 3);
		pgd[2] = HorizontalWrapUtil.collapseGraph(pgd[1]);
		Assert.assertTrue(pgd[2].getPositionedNodes().size() == 1);
		
		// WrapUtil collapse test
		WrappedGraphData graph = WrapUtil.collapseGraph(pgd[0], 1);
		Assert.assertTrue(graph.getPositionedNodes().size() == 1);
		Assert.assertTrue(graph.getLongestNodePath() == 0);
	}
	
	@Test
	public void spaceWrapUtilTest() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/spaceWrapUtilTest.node.graph");
		File edgesFile = new File("data/testdata/spaceWrapUtilTest.edge.graph");
		WrappedGraphData original = new WrappedGraphData(
				GraphDataRepository.parseGraph(nodesFile, edgesFile));
		Assert.assertTrue(original.getPositionedNodes().size() == 7);
		Assert.assertNull(VerticalWrapUtil.collapseGraph(original));
		Assert.assertNull(HorizontalWrapUtil.collapseGraph(original));
		List<List<NodeWrapper>> combineableNodes = SpaceWrapUtil
				.findCombineableNodes(original.getPositionedNodes());
		Assert.assertTrue(combineableNodes.size() == 1);
		WrappedGraphData nwgd = SpaceWrapUtil.collapseGraph(original);
		Assert.assertTrue(nwgd.getPositionedNodes().size() == 3);
		Assert.assertNull(VerticalWrapUtil.collapseGraph(nwgd));
		Assert.assertNull(HorizontalWrapUtil.collapseGraph(nwgd));
		nwgd = SpaceWrapUtil.collapseGraph(nwgd);
		Assert.assertTrue(nwgd.getPositionedNodes().size() == 1);
		
	}
}
