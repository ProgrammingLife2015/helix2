package tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodePosition;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.PlaceholderWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the unwrapping of a {@link SpaceWrapper}.
 *
 * <p>It creates a {@link SpaceWrapper} in a {@link HorizontalWrapper}.</p>
 * <p>It verifies that all the edge connections between the nodes are correct.</p>
 * 
 * <p>Created by Boris Mattijssen on 19-05-15.</p>
 */
@RunWith(MockitoJUnitRunner.class)
public class SpaceWrapperUnwrapTest {

	@Mock private DataNode dataNode1;
	@Mock private DataNode dataNode2;
	@Mock private DataNode dataNode3;
	@Mock private DataNode dataNode4;

	private Unwrap unwrap;

	/**
	 * Create a {@link SpaceWrapper} wrapped in a {@link HorizontalWrapper}.
	 */
	@Before
	public void before() {
		NodePosition nodePosition1 = new NodePosition(dataNode1);
		NodePosition nodePosition2 = new NodePosition(dataNode2);
		NodePosition nodePosition3 = new NodePosition(dataNode3);
		NodePosition nodePosition4 = new NodePosition(dataNode4);

		List<NodeWrapper> listSpace = new ArrayList<>(3);
		listSpace.add(nodePosition1);
		listSpace.add(nodePosition2);
		listSpace.add(nodePosition3);

		nodePosition1.getOutgoing().add(nodePosition2);
		nodePosition1.getOutgoing().add(nodePosition3);
		nodePosition2.getOutgoing().add(nodePosition3);

		nodePosition2.getIncoming().add(nodePosition1);
		nodePosition3.getIncoming().add(nodePosition1);
		nodePosition3.getIncoming().add(nodePosition2);

		SpaceWrapper space = new SpaceWrapper(listSpace,true);

		List<NodeWrapper> horizontalList = new ArrayList<>(2);
		horizontalList.add(space);
		horizontalList.add(nodePosition4);
		space.getOutgoing().add(nodePosition4);
		nodePosition4.getIncoming().add(space);

		HorizontalWrapper start = new HorizontalWrapper(horizontalList,true);
		unwrap = new Unwrap(start);
	}

	/**
	 * Verify that the left node contains dataNode1 and has 2 outgoing nodes
	 * (going to dataNode2 and dataNode3).
	 */
	@Test
	public void testLeftNode() {
		NodeWrapper left = unwrap.getResult();
		assertTrue(left instanceof DataNodeWrapper);
		assertEquals(0, left.getIncoming().size());
		assertEquals(2, left.getOutgoing().size());
		assertTrue(((DataNodeWrapper) left).getDataNodeList().contains(dataNode1));
	}

	/**
	 * Verify that the top node contains dataNode2 and has 1 outgoing node (dataNode1) and
	 * 1 incoming node (dataNode3).
	 */
	@Test
	public void testTopNode() {
		NodeWrapper top = unwrap.getResult().getOutgoing().get(0);
		assertTrue(top instanceof DataNodeWrapper);
		assertEquals(1, top.getIncoming().size());
		assertEquals(1, top.getOutgoing().size());
		assertTrue(((DataNodeWrapper) top).getDataNodeList().contains(dataNode2));
		assertTrue(((DataNodeWrapper) top.getIncoming().get(0)).getDataNodeList().contains(dataNode1));
	}

	/**
	 * Verify that the middle node contains dataNode3 and has 1 outgoing node (dataNode4) and
	 * 2 incoming nodes (dataNode1 and dataNode2).
	 */
	@Test
	public void testMiddleNode() {
		NodeWrapper middle = unwrap.getResult().getOutgoing().get(1);
		assertTrue(middle instanceof DataNodeWrapper);
		assertTrue(((DataNodeWrapper) middle).getDataNodeList().contains(dataNode3));
		assertEquals(2, middle.getIncoming().size());
		assertEquals(1, middle.getOutgoing().size());
		assertTrue(((DataNodeWrapper) middle.getIncoming().get(0)).getDataNodeList().contains(dataNode1));
		assertTrue(((DataNodeWrapper) middle.getIncoming().get(1)).getDataNodeList().contains(dataNode2));
	}

	/**
	 * Verify that the right node contains dataNode4 and has 1 incoming node (dataNode3).
	 */
	@Test
	public void testRightNode() {
		NodeWrapper right = unwrap.getResult().getOutgoing().get(1).getOutgoing().get(0);
		assertTrue(right instanceof DataNodeWrapper);
		assertEquals(1, right.getIncoming().size());
		assertEquals(0, right.getOutgoing().size());
		assertTrue(((DataNodeWrapper) right).getDataNodeList().contains(dataNode4));
		assertTrue(((DataNodeWrapper) right.getIncoming().get(0)).getDataNodeList().contains(dataNode3));
	}

	/**
	 * Verify that we indeed get four {@link DataNodeWrapper}s.
	 */
	@Test
	public void dataNodeWrapperCount() {
		assertEquals(4, unwrap.getDataNodeWrappers().size());
	}

	/**
	 * Verify that no more {@link PlaceholderWrapper}s are left.
	 */
	@Test
	public void testNoMorePlaceholders() {
		new NoMorePlaceholdersTest(unwrap.getDataNodeWrappers());
	}
}
