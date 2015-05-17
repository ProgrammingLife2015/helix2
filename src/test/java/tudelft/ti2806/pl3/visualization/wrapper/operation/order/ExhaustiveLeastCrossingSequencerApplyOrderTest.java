package tudelft.ti2806.pl3.visualization.wrapper.operation.order;

import org.junit.Assert;
import org.junit.Before;
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

public class ExhaustiveLeastCrossingSequencerApplyOrderTest {
	private static final int EXPECTED_APPLY_ORDER_TEST_GRAPH_OUTGOING_SIZE = 12;
	private static final int EXPECTED_APPLY_ORDER_TEST_GRAPH_INCOMING_SIZE = 8;
	private static final int EXPECTED_ALWAYS_CROSS_TEST_GRAPH_OUTGOING_SIZE = 24;
	private static final int EXPECTED_ALWAYS_CROSS_TEST_GRAPH_INCOMING_SIZE = 24;
	private SpaceWrapper applyOrderTestGraphwrapper;
	private SpaceWrapper alwaysCrossTestGraphwrapper;
	
	/**
	 * Run before tests, parsing the applyOrderTest graph.
	 * 
	 * @throws FileNotFoundException
	 *             if test file was not found
	 */
	@Before
	public void init() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/applyOrderTest.node.graph");
		File edgesFile = new File("data/testdata/applyOrderTest.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = WrapUtil.collapseGraph(
				new WrappedGraphData(gdr), 1);
		
		// Test if test data is correct
		Assert.assertTrue(wgd.getPositionedNodes().size() == 1);
		Assert.assertTrue(wgd.getPositionedNodes().get(0) instanceof SpaceWrapper);
		applyOrderTestGraphwrapper = (SpaceWrapper) wgd.getPositionedNodes()
				.get(0);
		Assert.assertTrue(applyOrderTestGraphwrapper.getNodeList().size() == 7);
		Pair<Boolean, Long> direction = ExhaustiveLeastCrossingsSequencer
				.getBestDirection(applyOrderTestGraphwrapper.getNodeList());
		Assert.assertTrue(direction.getSecond() == EXPECTED_APPLY_ORDER_TEST_GRAPH_INCOMING_SIZE);
		Assert.assertFalse(direction.getFirst());
		long outgoingDirection = ExhaustiveLeastCrossingsSequencer
				.getOptionCountFromLeftToRight(applyOrderTestGraphwrapper
						.getNodeList());
		Assert.assertTrue(outgoingDirection == EXPECTED_APPLY_ORDER_TEST_GRAPH_OUTGOING_SIZE);
	}
	
	/**
	 * Run before tests, parsing the alwaysCrossTest graph.
	 * 
	 * @throws FileNotFoundException
	 *             if test file was not found
	 */
	@Before
	public void init2() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/alwaysCrossTest.node.graph");
		File edgesFile = new File("data/testdata/alwaysCrossTest.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = WrapUtil.collapseGraph(
				new WrappedGraphData(gdr), 1);
		
		// Test if test data is correct
		Assert.assertTrue(wgd.getPositionedNodes().size() == 1);
		Assert.assertTrue(wgd.getPositionedNodes().get(0) instanceof SpaceWrapper);
		alwaysCrossTestGraphwrapper = (SpaceWrapper) wgd.getPositionedNodes()
				.get(0);
		Assert.assertTrue(alwaysCrossTestGraphwrapper.getNodeList().size() == 7);
		Assert.assertTrue(ExhaustiveLeastCrossingsSequencer
				.getOptionCountFromRightToLeft(alwaysCrossTestGraphwrapper
						.getNodeList()) == EXPECTED_ALWAYS_CROSS_TEST_GRAPH_INCOMING_SIZE);
		Assert.assertTrue(ExhaustiveLeastCrossingsSequencer
				.getOptionCountFromRightToLeft(alwaysCrossTestGraphwrapper
						.getNodeList()) == EXPECTED_ALWAYS_CROSS_TEST_GRAPH_OUTGOING_SIZE);
	}
	
	@Test
	public void applyOrderTest() {
		
		// applyOrder tests
		Assert.assertTrue(ExhaustiveLeastCrossingsSequencer
				.applyOrder(applyOrderTestGraphwrapper));
		List<Pair<List<NodeWrapper>, NodeWrapper[]>> order = ExhaustiveLeastCrossingsSequencer
				.getOrder(ExhaustiveLeastCrossingsSequencer
						.getOutgoingList(applyOrderTestGraphwrapper
								.getNodeList()));
		for (int i = 0; i < EXPECTED_APPLY_ORDER_TEST_GRAPH_OUTGOING_SIZE; i++) {
			Assert.assertTrue(ExhaustiveLeastCrossingsSequencer
					.applyConfiguration(i, order, applyOrderTestGraphwrapper));
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
		// applyOrder tests
		Assert.assertTrue(ExhaustiveLeastCrossingsSequencer
				.applyOrder(alwaysCrossTestGraphwrapper));
		List<Pair<List<NodeWrapper>, NodeWrapper[]>> order = ExhaustiveLeastCrossingsSequencer
				.getOrder(ExhaustiveLeastCrossingsSequencer
						.getOutgoingList(alwaysCrossTestGraphwrapper
								.getNodeList()));
		int invalidConfigurationCount = 0;
		for (int i = 0; i < EXPECTED_ALWAYS_CROSS_TEST_GRAPH_OUTGOING_SIZE; i++) {
			if (ExhaustiveLeastCrossingsSequencer.applyConfiguration(i, order,
					alwaysCrossTestGraphwrapper)) {
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
		Assert.assertTrue(invalidConfigurationCount == EXPECTED_ALWAYS_CROSS_TEST_GRAPH_OUTGOING_SIZE / 2);
	}
}
