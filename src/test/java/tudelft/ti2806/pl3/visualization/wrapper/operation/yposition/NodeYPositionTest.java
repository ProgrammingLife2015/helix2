package tudelft.ti2806.pl3.visualization.wrapper.operation.yposition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.util.wrap.HorizontalWrapUtil;
import tudelft.ti2806.pl3.util.wrap.SpaceWrapUtil;
import tudelft.ti2806.pl3.util.wrap.VerticalWrapUtil;
import tudelft.ti2806.pl3.util.wrap.WrapUtil;
import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Test for the NodeYPosition class Created by Kasper on 18-5-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeYPositionTest {
	
	/**
	 * Used to compare floats. We dont care if there is a slight difference.
	 */
	private static final float DELTA = 0.0001f;
	private VerticalWrapper verticalWrapper;
	private HorizontalWrapper horizontalWrapper;
	private SpaceWrapper spaceWrapper;
	
	@Mock
	NodeWrapper container;
	
	@Before
	public void before() {
		when(container.getY()).thenReturn(0.8f);
		when(container.getySpace()).thenReturn(0.6f);
	}
	
	/**
	 * Reads the graph and constructs a vertical wrapped object
	 *
	 * @throws FileNotFoundException
	 */
	public void readVerticalWrapper() throws FileNotFoundException {
		File nodesFile = new File(
				"data/testdata/NodeYPosition/verticalWrapped.node.graph");
		File edgesFile = new File(
				"data/testdata/NodeYPosition/verticalWrapped.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = VerticalWrapUtil
				.collapseGraph(new WrappedGraphData(gdr));
		// root - vertical wrapped - end
		assertEquals(wgd.getPositionedNodes().size(), 3);
		
		verticalWrapper = null;
		for (NodeWrapper nodeWrapper : wgd.getPositionedNodes()) {
			if (nodeWrapper instanceof VerticalWrapper) {
				verticalWrapper = (VerticalWrapper) nodeWrapper;
			}
		}
		
		assertTrue(verticalWrapper != null);
		assertTrue(verticalWrapper.getNodeList().size() == 3);
		// we now know that 3 nodes are Vertical wrapped.
	}
	
	@Test
	public void testVerticalWrappedWithNullContainer()
			throws FileNotFoundException {
		readVerticalWrapper();
		
		NodeYPosition.init(verticalWrapper);
		
		assertEquals(verticalWrapper.getGenome().size(), 10);
		assertEquals(5, verticalWrapper.getNodeList().get(0).getGenome().size());
		assertEquals(4, verticalWrapper.getNodeList().get(1).getGenome().size());
		assertEquals(1, verticalWrapper.getNodeList().get(2).getGenome().size());
		
		assertEquals(0.5f, verticalWrapper.getNodeList().get(0).getySpace(),
				DELTA);
		assertEquals(0.25f, verticalWrapper.getNodeList().get(0).getY(), DELTA);
		assertEquals(0.4f, verticalWrapper.getNodeList().get(1).getySpace(),
				DELTA);
		assertEquals(0.7f, verticalWrapper.getNodeList().get(1).getY(), DELTA);
		assertEquals(0.1f, verticalWrapper.getNodeList().get(2).getySpace(),
				DELTA);
		assertEquals(0.95f, verticalWrapper.getNodeList().get(2).getY(), DELTA);
	}
	
	@Test
	public void testVerticalWrappedWithContainer() throws FileNotFoundException {
		readVerticalWrapper();
		
		NodeYPosition.init(verticalWrapper, container);
		
		assertEquals(verticalWrapper.getGenome().size(), 10);
		assertEquals(5, verticalWrapper.getNodeList().get(0).getGenome().size());
		assertEquals(4, verticalWrapper.getNodeList().get(1).getGenome().size());
		assertEquals(1, verticalWrapper.getNodeList().get(2).getGenome().size());
		
		assertEquals(0.3f, verticalWrapper.getNodeList().get(0).getySpace(),
				DELTA);
		// assertEquals(0.95f, verticalWrapper.getNodeList().get(0).getY(),
		// DELTA);
		// assertEquals(0.24f, verticalWrapper.getNodeList().get(1).getySpace(),
		// DELTA);
		// assertEquals(1.22f, verticalWrapper.getNodeList().get(1).getY(),
		// DELTA);
		// assertEquals(0.06f, verticalWrapper.getNodeList().get(2).getySpace(),
		// DELTA);
		// assertEquals(1.37f, verticalWrapper.getNodeList().get(2).getY(),
		// DELTA);
	}
	
	/**
	 * Reads the horizontal wrapped data.
	 *
	 * @throws FileNotFoundException
	 */
	public void readHorizontalWrapped() throws FileNotFoundException {
		File nodesFile = new File(
				"data/testdata/NodeYPosition/horizontalWrapped.node.graph");
		File edgesFile = new File(
				"data/testdata/NodeYPosition/horizontalWrapped.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = HorizontalWrapUtil
				.collapseGraph(new WrappedGraphData(gdr));
		
		assertEquals(wgd.getPositionedNodes().size(), 1);
		horizontalWrapper = (HorizontalWrapper) wgd.getPositionedNodes().get(0);
		assertEquals(5, horizontalWrapper.getNodeList().size());
		// we now know for sure that 5 nodes are horizontal wrapped
	}
	
	@Test
	public void testHorizontalWrappedWithNullContainer()
			throws FileNotFoundException {
		readHorizontalWrapped();
		NodeYPosition.init(horizontalWrapper);
		
		for (NodeWrapper nodeWrapper : horizontalWrapper.getNodeList()) {
			assertEquals(0.0f, nodeWrapper.getY(), 0.0f);
			assertEquals(1.0f, nodeWrapper.getySpace(), 0.0f);
		}
	}
	
	@Test
	public void testHorizontalWrappedWithContainer() throws Exception {
		readHorizontalWrapped();
		
		NodeYPosition.init(horizontalWrapper, container);
		
		for (NodeWrapper nodeWrapper : horizontalWrapper.getNodeList()) {
			assertEquals(0.8f, nodeWrapper.getY(), 0.0f);
			assertEquals(0.6f, nodeWrapper.getySpace(), 0.0f);
		}
	}
	
	/**
	 * Reads the horizontal wrapped data.
	 *
	 * @throws FileNotFoundException
	 */
	public void readSpaceWrapped() throws FileNotFoundException {
		File nodesFile = new File(
				"data/testdata/NodeYPosition/spaceWrappedSimple.node.graph");
		File edgesFile = new File(
				"data/testdata/NodeYPosition/spaceWrappedSimple.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = SpaceWrapUtil
				.collapseGraph(new WrappedGraphData(gdr));
		assertEquals(wgd.getPositionedNodes().size(), 1);
		spaceWrapper = (SpaceWrapper) wgd.getPositionedNodes().get(0);
		assertEquals(spaceWrapper.getNodeList().size(), 3);
		
	}
	
	public SpaceWrapper readSpaceWrappedHard() throws FileNotFoundException {
		SpaceWrapper spaceWrapper;
		File nodesFile = new File(
				"data/testdata/NodeYPosition/spaceWrappedHard.node.graph");
		File edgesFile = new File(
				"data/testdata/NodeYPosition/spaceWrappedHard.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = SpaceWrapUtil
				.collapseGraph(new WrappedGraphData(gdr));
		assertEquals(wgd.getPositionedNodes().size(), 1);
		spaceWrapper = (SpaceWrapper) wgd.getPositionedNodes().get(0);
		assertEquals(spaceWrapper.getNodeList().size(), 5);
		
		return spaceWrapper;
		
	}
	
	@Test
	public void testSpaceWrappedWithNullContainer()
			throws FileNotFoundException {
		readSpaceWrapped();
		
		NodeYPosition.init(spaceWrapper);
		
		// first and last node
		assertEquals(0.0f, spaceWrapper.getNodeList().get(0).getY(), 0.0f);
		assertEquals(1.0f, spaceWrapper.getNodeList().get(0).getySpace(), 0.0f);
		assertEquals(0.0f, spaceWrapper.getNodeList().get(1).getY(), 0.0f);
		assertEquals(1.0f, spaceWrapper.getNodeList().get(1).getySpace(), 0.0f);
		
		// space node
		// assertEquals(0.25f, spaceWrapper.getNodeList().get(2).getY(), 0.0f);
		// assertEquals(0.5f, spaceWrapper.getNodeList().get(2).getySpace(),
		// 0.0f);
	}
	
	@Test
	public void testSpaceWrappedWithContainer() throws FileNotFoundException {
		readSpaceWrapped();
		
		NodeYPosition.init(spaceWrapper, container);
		
		// first and last node
		assertEquals(0.8f, spaceWrapper.getNodeList().get(0).getY(), 0.0f);
		assertEquals(0.6f, spaceWrapper.getNodeList().get(0).getySpace(), 0.0f);
		assertEquals(0.8f, spaceWrapper.getNodeList().get(1).getY(), 0.0f);
		assertEquals(0.6f, spaceWrapper.getNodeList().get(1).getySpace(), 0.0f);
		
		// space node
		// assertEquals(0.95f, spaceWrapper.getNodeList().get(2).getY(), DELTA);
		// assertEquals(0.3f, spaceWrapper.getNodeList().get(2).getySpace(),
		// DELTA);
	}
	
	@Test
	public void testSpaceWrappedHardWithNull() throws FileNotFoundException {
		SpaceWrapper spaceWrapper = readSpaceWrappedHard();
		
		NodeYPosition.init(spaceWrapper);
		
		// first and last node
		assertEquals(0.0f, spaceWrapper.getNodeList().get(0).getY(), 0.0f);
		assertEquals(1.0f, spaceWrapper.getNodeList().get(0).getySpace(), 0.0f);
		assertEquals(0.0f, spaceWrapper.getNodeList().get(1).getY(), 0.0f);
		assertEquals(1.0f, spaceWrapper.getNodeList().get(1).getySpace(), 0.0f);
		
		// space nodes
		// assertEquals(0.1666f, spaceWrapper.getNodeList().get(2).getY(),
		// DELTA);
		// assertEquals(0.5f, spaceWrapper.getNodeList().get(3).getY(), DELTA);
		// assertEquals(0.75f, spaceWrapper.getNodeList().get(4).getY(), DELTA);
		// assertEquals(0.3333f, spaceWrapper.getNodeList().get(2).getySpace(),
		// DELTA);
		// assertEquals(0.3333f, spaceWrapper.getNodeList().get(3).getySpace(),
		// DELTA);
		// assertEquals(0.1666f, spaceWrapper.getNodeList().get(4).getySpace(),
		// DELTA);
	}
	
	@Test
	public void testSpaceWrappedHardWithContainer()
			throws FileNotFoundException {
		SpaceWrapper spaceWrapper = readSpaceWrappedHard();
		
		NodeYPosition.init(spaceWrapper, container);
		
		// first and last node
		assertEquals(0.8f, spaceWrapper.getNodeList().get(0).getY(), 0.0f);
		assertEquals(0.6f, spaceWrapper.getNodeList().get(0).getySpace(), 0.0f);
		assertEquals(0.8f, spaceWrapper.getNodeList().get(1).getY(), 0.0f);
		assertEquals(0.6f, spaceWrapper.getNodeList().get(1).getySpace(), 0.0f);
		
		// space nodes
		// assertEquals(0.90f, spaceWrapper.getNodeList().get(2).getY(), DELTA);
		// assertEquals(0.2f, spaceWrapper.getNodeList().get(2).getySpace(),
		// DELTA);
		// assertEquals(1.1f, spaceWrapper.getNodeList().get(3).getY(), DELTA);
		// assertEquals(0.2f, spaceWrapper.getNodeList().get(3).getySpace(),
		// DELTA);
		// assertEquals(1.25f, spaceWrapper.getNodeList().get(4).getY(), DELTA);
		// assertEquals(0.1f, spaceWrapper.getNodeList().get(4).getySpace(),
		// DELTA);
	}
	
	private void displayGraph(CombineWrapper wrapper) {
		Graph graph = new SingleGraph("");
		for (NodeWrapper node : wrapper.getNodeList()) {
			
			graph.addNode(node.getIdString()).setAttribute("xy",
					node.getPreviousNodesCount(), node.getY());
			graph.getNode(node.getIdString()).addAttribute("ui.label",
					"     " + node.getIdString());
		}
		for (NodeWrapper node : wrapper.getNodeList()) {
			for (NodeWrapper to : node.getOutgoing()) {
				graph.addEdge(node.getIdString() + "-" + to.getIdString(),
						node.getIdString(), to.getIdString());
			}
		}
		for (NodeWrapper node : wrapper.getNodeList()) {
			
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
	
	public void readfullgraph() throws FileNotFoundException {
		File nodesFile = new File(
				"data/testdata/NodeYPosition/fullgraph.node.graph");
		File edgesFile = new File(
				"data/testdata/NodeYPosition/fullgraph.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = WrapUtil.collapseGraph(
				new WrappedGraphData(gdr), Integer.MAX_VALUE);
		
		assertEquals(wgd.getPositionedNodes().size(), 1);
		System.out.println(wgd.getPositionedNodes().get(0).getClass());
		SpaceWrapper nodeWrapper = (SpaceWrapper) wgd.getPositionedNodes().get(
				0);
		NodeYPosition.init(nodeWrapper);
		displayGraph(nodeWrapper);
		
	}
	
}
