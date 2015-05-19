package tudelft.ti2806.pl3.visualization.wrapper.operation.yposition;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.util.wrap.HorizontalWrapUtil;
import tudelft.ti2806.pl3.util.wrap.VerticalWrapUtil;
import tudelft.ti2806.pl3.visualization.wrapper.*;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Test for the NodeYPosition class
 * Created by Kasper on 18-5-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeYPositionTest {
	private static HorizontalWrapper horizontalWrapper;
	private static VerticalWrapper verticalWrapper;
	private static SpaceWrapper spaceWrapper;
	private static SingleWrapper singleWrapper;

	@Mock
	NodeWrapper container;

	@Before
	public void before() {
		when(container.getY()).thenReturn(0.8f);
	}

	@Test
	public void testVerticalWrapperWithNullContainer() throws FileNotFoundException {
		File nodesFile = new File("data/testdata/NodeYPosition/verticalWrapped.node.graph");
		File edgesFile = new File("data/testdata/NodeYPosition/verticalWrapped.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = VerticalWrapUtil.collapseGraph(
				new WrappedGraphData(gdr));
		// root - vertical wrapped - end
		assertEquals(wgd.getPositionedNodes().size(), 3);

		VerticalWrapper verticalWrapper = null;
		for (NodeWrapper nodeWrapper : wgd.getPositionedNodes()) {
			if (nodeWrapper instanceof VerticalWrapper) {
				verticalWrapper = (VerticalWrapper) nodeWrapper;
			}
		}
		assertTrue(verticalWrapper != null);
		assertTrue(verticalWrapper.getNodeList().size() == 3);
		// we now know that 3 nodes are Vertical wrapped.

		new NodeYPosition().calculate(verticalWrapper, null);

		assertEquals(verticalWrapper.getGenome().size(), 10);
		assertEquals(5, verticalWrapper.getNodeList().get(0).getGenome().size());
		assertEquals(4, verticalWrapper.getNodeList().get(1).getGenome().size());
		assertEquals(1, verticalWrapper.getNodeList().get(2).getGenome().size());

		assertEquals(0.5f, verticalWrapper.getNodeList().get(0).getY(), 0.0f);
		assertEquals(0.4f, verticalWrapper.getNodeList().get(1).getY(), 0.0f);
		assertEquals(0.1f, verticalWrapper.getNodeList().get(2).getY(), 0.0f);

	}

	@Test
	public void testHorizontalWrapperWithNullContainer() throws Exception {
		File nodesFile = new File("data/testdata/NodeYPosition/horizontalWrapped.node.graph");
		File edgesFile = new File("data/testdata/NodeYPosition/horizontalWrapped.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = HorizontalWrapUtil.collapseGraph(
				new WrappedGraphData(gdr));

		assertEquals(wgd.getPositionedNodes().size(), 1);
		HorizontalWrapper horizontalWrapper = (HorizontalWrapper) wgd.getPositionedNodes().get(0);
		assertEquals(5, horizontalWrapper.getNodeList().size());
		// we now know for sure that 5 nodes are horizontal wrapped

		new NodeYPosition().calculate(horizontalWrapper, null);

		for (NodeWrapper nodeWrapper : horizontalWrapper.getNodeList()) {
			assertEquals(0.5f, nodeWrapper.getY(), 0.0f);
		}
	}

	@Test
	public void testHorizontalWrapperWithContainer() throws Exception {
		File nodesFile = new File("data/testdata/NodeYPosition/horizontalWrapped.node.graph");
		File edgesFile = new File("data/testdata/NodeYPosition/horizontalWrapped.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		WrappedGraphData wgd = HorizontalWrapUtil.collapseGraph(
				new WrappedGraphData(gdr));

		assertEquals(wgd.getPositionedNodes().size(), 1);
		HorizontalWrapper horizontalWrapper = (HorizontalWrapper) wgd.getPositionedNodes().get(0);
		assertEquals(5, horizontalWrapper.getNodeList().size());
		// we now know for sure that 5 nodes are horizontal wrapped

		new NodeYPosition().calculate(horizontalWrapper, container);

		for (NodeWrapper nodeWrapper : horizontalWrapper.getNodeList()) {
			assertEquals(0.8f, nodeWrapper.getY(), 0.0f);
		}
	}

	@Test
	public void calculate2Test() throws Exception {

	}

	@Test
	public void calculate3Test() throws Exception {

	}

	@Test
	public void calculate4Test() throws Exception {

	}

	private void displayGraph(CombineWrapper wrapper) {
		Graph graph = new SingleGraph("");
		for (NodeWrapper node : wrapper.getNodeList()) {
			graph.addNode(node.getIdString()).setAttribute("xy",
					node.getPreviousNodesCount(), node.getY());
		}
		for (NodeWrapper node : wrapper.getNodeList()) {
			for (NodeWrapper to : node.getOutgoing()) {
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
}