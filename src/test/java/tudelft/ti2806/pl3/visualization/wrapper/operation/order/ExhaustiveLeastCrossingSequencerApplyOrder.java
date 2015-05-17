package tudelft.ti2806.pl3.visualization.wrapper.operation.order;

import org.junit.Assert;
import org.junit.Test;

import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.util.wrap.WrapUtil;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ExhaustiveLeastCrossingSequencerApplyOrder {
	
	@Test
	public void applyOrderTest() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/applyOrderTest.node.graph");
		File edgesFile = new File("data/testdata/applyOrderTest.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = WrapUtil.collapseGraph(
				new WrappedGraphData(gdr), 1);
		
		// Test if test data is correct
		Assert.assertTrue(wgd.getPositionedNodes().size() == 1);
		System.out
				.println(wgd.getPositionedNodes().get(0).getClass().getName());
		Assert.assertTrue(wgd.getPositionedNodes().get(0) instanceof SpaceWrapper);
		SpaceWrapper wrapper = (SpaceWrapper) wgd.getPositionedNodes().get(0);
		Assert.assertTrue(wrapper.getNodeList().size() == 7);
		Pair<Boolean, Long> direction = ExhaustiveLeastCrossingsSequencer
				.getBestDirection(wrapper.getNodeList());
		Assert.assertTrue(direction.getSecond() == 8);
		Assert.assertFalse(direction.getFirst());
		long outgoingDirection = ExhaustiveLeastCrossingsSequencer
				.getOptionCountFromLeftToRight(wrapper.getNodeList());
		Assert.assertTrue(outgoingDirection == 12);
		
		// applyOrder tests
		Assert.assertTrue(ExhaustiveLeastCrossingsSequencer.applyOrder(wrapper));
		List<Pair<List<NodeWrapper>, NodeWrapper[]>> order = ExhaustiveLeastCrossingsSequencer
				.getOrder(ExhaustiveLeastCrossingsSequencer
						.getOutgoingList(wrapper.getNodeList()));
		for (int i = 0; i < outgoingDirection; i++) {
			System.out.println(i);
			Assert.assertTrue(ExhaustiveLeastCrossingsSequencer
					.applyConfiguration(i, order, wrapper));
		}
	}
	
	/**
	 * Test if the method returns false when a configuration is not possible.
	 * 
	 * @throws FileNotFoundException
	 *             if test file was not found
	 */
	@Test
	public void applyOrderTestWithWrongOrders() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/alwaysCrossTest.node.graph");
		File edgesFile = new File("data/testdata/alwaysCrossTest.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = WrapUtil.collapseGraph(
				new WrappedGraphData(gdr), 1);
		
		// Test if test data is correct
		Assert.assertTrue(wgd.getPositionedNodes().size() == 1);
		System.out
				.println(wgd.getPositionedNodes().get(0).getClass().getName());
		Assert.assertTrue(wgd.getPositionedNodes().get(0) instanceof SpaceWrapper);
		SpaceWrapper wrapper = (SpaceWrapper) wgd.getPositionedNodes().get(0);
		Assert.assertTrue(wrapper.getNodeList().size() == 7);
		Assert.assertTrue(ExhaustiveLeastCrossingsSequencer
				.getOptionCountFromRightToLeft(wrapper.getNodeList()) == 24);
		long outgoingDirection = ExhaustiveLeastCrossingsSequencer
				.getOptionCountFromLeftToRight(wrapper.getNodeList());
		Assert.assertTrue(outgoingDirection == 24);
		
		// applyOrder tests
		Assert.assertTrue(ExhaustiveLeastCrossingsSequencer.applyOrder(wrapper));
		List<Pair<List<NodeWrapper>, NodeWrapper[]>> order = ExhaustiveLeastCrossingsSequencer
				.getOrder(ExhaustiveLeastCrossingsSequencer
						.getOutgoingList(wrapper.getNodeList()));
		int invalidConfigurationCount = 0;
		for (int i = 0; i < outgoingDirection; i++) {
			if (ExhaustiveLeastCrossingsSequencer.applyConfiguration(i, order,
					wrapper)) {
				invalidConfigurationCount++;
			}
		}
		/*
		 * The edges 1-4, 2-4 or 1-3 and 2-3 causes conflicts when the
		 * configuration configures the order as: 1-4, 1-3 in node 1 and 2-3,
		 * 2-4 in the other or when this order is reversed. Both orders, this
		 * order and the reversed, should appear each in 1/4th of the orders.
		 * Because they can't appear on the same time, they add up to 1/2 times,
		 * which leads to 24/2 = 12 conflicts.
		 */
		Assert.assertTrue(invalidConfigurationCount == 12);
	}
}
