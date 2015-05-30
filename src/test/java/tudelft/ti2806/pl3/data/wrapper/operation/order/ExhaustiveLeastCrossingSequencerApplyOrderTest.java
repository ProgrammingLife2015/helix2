package tudelft.ti2806.pl3.data.wrapper.operation.order;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.gene.GeneData;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.util.WrapUtil;
import tudelft.ti2806.pl3.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ExhaustiveLeastCrossingSequencerApplyOrderTest {
	private static final int EXPECTED_APPLY_ORDER_TEST_GRAPH_OUTGOING_SIZE = 12;
	private static final int EXPECTED_ALWAYS_CROSS_TEST_GRAPH_OUTGOING_SIZE = 24;
	
	ExhaustiveLeastCrossingsSequencer testObject = new ExhaustiveLeastCrossingsSequencer(
			0);
	
	@Mock
	SpaceWrapper mockedSpaceWrapper;
	
	@Test
	public void maxIterationTest() throws FileNotFoundException {
		testObject.calculate(mockedSpaceWrapper, null);
		Mockito.verify(mockedSpaceWrapper, Mockito.times(2)).getNodeList();
	}
	
	/**
	 * Parsing the applyOrderTest graph.
	 * 
	 * @return SpaceWrapper
	 * 
	 * @throws FileNotFoundException
	 *             if test file was not found
	 */
	public SpaceWrapper applyOrderTestData() throws IOException {
		File nodesFile = new File("data/testdata/applyOrderTest.node.graph");
		File edgesFile = new File("data/testdata/applyOrderTest.edge.graph");
		GeneData geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");

		GraphDataRepository gdr = new GraphDataRepository();
		gdr.parseGraph(nodesFile, edgesFile, geneData);

		WrappedGraphData wgd = WrapUtil.collapseGraph(
				new WrappedGraphData(gdr));
		
		// Test if test data is correct
		Assert.assertTrue(wgd.getPositionedNodes().size() == 1);
		Assert.assertTrue(wgd.getPositionedNodes().get(0) instanceof SpaceWrapper);
		SpaceWrapper applyOrderTestGraphWrapper = (SpaceWrapper) wgd
				.getPositionedNodes().get(0);
		Assert.assertTrue(applyOrderTestGraphWrapper.getNodeList().size() == 7);
		// Pair<Boolean, Long> direction = ExhaustiveLeastCrossingsSequencer
		// .getBestDirection(applyOrderTestGraphWrapper.getNodeList());
		// Assert.assertTrue(direction.getSecond() ==
		// EXPECTED_APPLY_ORDER_TEST_GRAPH_INCOMING_SIZE);
		// Assert.assertFalse(direction.getFirst());
		long outgoingDirection = testObject
				.getOptionCountFromLeftToRight(applyOrderTestGraphWrapper
						.getNodeList());
		Assert.assertTrue(outgoingDirection == EXPECTED_APPLY_ORDER_TEST_GRAPH_OUTGOING_SIZE);
		return applyOrderTestGraphWrapper;
	}
	
	/**
	 * Parsing the alwaysCrossTest graph.
	 * 
	 * @throws FileNotFoundException
	 *             if test file was not found
	 */
	public SpaceWrapper alwaysCrossTestData() throws IOException {
		File nodesFile = new File("data/testdata/alwaysCrossTest.node.graph");
		File edgesFile = new File("data/testdata/alwaysCrossTest.edge.graph");
		GeneData geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");

		GraphDataRepository gdr = new GraphDataRepository();
		gdr.parseGraph(nodesFile, edgesFile, geneData);
		WrappedGraphData wgd = WrapUtil.collapseGraph(
				new WrappedGraphData(gdr));
		
		// Test if test data is correct
		Assert.assertTrue(wgd.getPositionedNodes().size() == 1);
		Assert.assertTrue(wgd.getPositionedNodes().get(0) instanceof SpaceWrapper);
		SpaceWrapper alwaysCrossTestGraphWrapper = (SpaceWrapper) wgd
				.getPositionedNodes().get(0);
		Assert.assertTrue(alwaysCrossTestGraphWrapper.getNodeList().size() == 7);
		Assert.assertTrue(testObject
				.getOptionCountFromLeftToRight(alwaysCrossTestGraphWrapper
						.getNodeList()) == EXPECTED_ALWAYS_CROSS_TEST_GRAPH_OUTGOING_SIZE);
		return alwaysCrossTestGraphWrapper;
	}
	
	@Test
	public void applyOrderTest() throws IOException {
		SpaceWrapper applyOrderTestGraphWrapper = alwaysCrossTestData();
		// applyOrder tests
		Assert.assertNotNull(testObject
				.applyOrderToY(applyOrderTestGraphWrapper));
		List<Pair<List<Wrapper>, Wrapper[]>> order = testObject
				.getOrder(testObject
						.getOutgoingLists(applyOrderTestGraphWrapper
								.getNodeList()));
		for (int i = 0; i < EXPECTED_APPLY_ORDER_TEST_GRAPH_OUTGOING_SIZE; i++) {
			testObject.applyConfigurationToOrder(i, order);
			Assert.assertNotNull(testObject
					.applyOrderToY(applyOrderTestGraphWrapper));
		}
	}
	
	/**
	 * Test if the method returns false when a configuration is not possible.
	 *
	 * @throws FileNotFoundException
	 *             if test file was not found
	 */
	@Test
	public void applyOrderTestWithWrongOrders() throws IOException {
		SpaceWrapper alwaysCrossTestGraphWrapper = alwaysCrossTestData();
		// applyOrder tests
		Assert.assertNotNull(testObject.applyOrderToY(alwaysCrossTestGraphWrapper));
		List<Pair<List<Wrapper>, Wrapper[]>> order
				= testObject.getOrder(testObject.getOutgoingLists(alwaysCrossTestGraphWrapper
				.getNodeList()));
		int invalidConfigurationCount = 0;
		
		for (int i = 0; i < EXPECTED_ALWAYS_CROSS_TEST_GRAPH_OUTGOING_SIZE; i++) {
			testObject.applyConfigurationToOrder(i, order);
			if (!testObject.applyOrderToY(alwaysCrossTestGraphWrapper)) {
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
	
	@Test
	public void calculateBestConfigTest() throws IOException {
		SpaceWrapper alwaysCrossTestGraphWrapper = alwaysCrossTestData();
		List<Pair<List<Wrapper>, Wrapper[]>> order
				= testObject.getOrder(testObject.getOutgoingLists(alwaysCrossTestGraphWrapper
				.getNodeList()));
		int bestConfig = testObject.calculateBestConfig(alwaysCrossTestGraphWrapper,
				EXPECTED_ALWAYS_CROSS_TEST_GRAPH_OUTGOING_SIZE, order);
		testObject.applyConfigurationToOrder(bestConfig, order);
		testObject.applyOrderToY(alwaysCrossTestGraphWrapper);
		Assert.assertEquals(1, testObject.countIntersections(alwaysCrossTestGraphWrapper));
	}
	
	private void displayGraph(SpaceWrapper alwaysCrossTestGraphWrapper) {
		Graph graph = new SingleGraph("");
		for (Wrapper node : alwaysCrossTestGraphWrapper.getNodeList()) {
			graph.addNode(node.getIdString()).setAttribute("xy",
					node.getPreviousNodesCount(), node.getY());
		}
		for (Wrapper node : alwaysCrossTestGraphWrapper.getNodeList()) {
			for (Wrapper to : node.getOutgoing()) {
				graph.addEdge(node.getIdString() + "-" + to.getIdString(),
						node.getIdString(), to.getIdString());
			}
		}
		graph.display(false);
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void rightToLeftSearchTest() throws IOException {
		SpaceWrapper applyOrderTestGraphWrapper = applyOrderTestData();
		// TODO rewrite test?
		// If this test fails, the test is useless
		Assert.assertNotEquals(0,
				testObject.countIntersections(applyOrderTestGraphWrapper));
		new ExhaustiveLeastCrossingsSequencer(100).calculate(
				applyOrderTestGraphWrapper, null);
		// displayGraph(applyOrderTestGraphWrapper);
		Assert.assertEquals(0, testObject.countIntersections(applyOrderTestGraphWrapper));
		// Assert.assertEquals(0,
		// countIntersections(applyOrderTestGraphWrapper, false));
	}
}
