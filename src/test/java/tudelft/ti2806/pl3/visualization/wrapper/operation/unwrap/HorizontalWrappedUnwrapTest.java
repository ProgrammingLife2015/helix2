package tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap;

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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the unwrapping of a {@link HorizontalWrapper}.
 *
 * <p>It creates two {@link HorizontalWrapper}s in a {@link HorizontalWrapper} to
 * verify that the wrappers are recursively unwrapped.</p>
 * <p>It thereby also verifies that the start of the graph is updated correctly.</p>
 * Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class HorizontalWrappedUnwrapTest {

	@Mock private DataNode dataNode1;
	@Mock private DataNode dataNode2;
	@Mock private DataNode dataNode3;
	@Mock private DataNode dataNode4;

	private Unwrap unwrap;

	/**
	 * Create four {@link NodePosition}s and wrap these in two separate {@link HorizontalWrapper}s.
	 */
	@Before
	public void before() {
		NodePosition nodePosition1 = new NodePosition(dataNode1);
		NodePosition nodePosition2 = new NodePosition(dataNode2);
		NodePosition nodePosition3 = new NodePosition(dataNode3);
		NodePosition nodePosition4 = new NodePosition(dataNode4);

		HorizontalWrapper start = getHorizontalWrapper(
				getHorizontalWrapper(nodePosition1, nodePosition2),
				getHorizontalWrapper(nodePosition3, nodePosition4));

		unwrap = new Unwrap(start);
	}

	/**
	 * Verify that the left node contains dataNode1 and has 1 outgoing node.
	 */
	@Test
	public void testLeftNode() {
		NodeWrapper left = unwrap.getResult();
		assertTrue(left instanceof DataNodeWrapper);
		assertEquals(0, left.getIncoming().size());
		assertEquals(1, left.getOutgoing().size());
		assertTrue(((DataNodeWrapper) left).getDataNodeList().contains(dataNode1));
	}

	/**
	 * Verify that the left middle node contains dataNode2 and has 1 outgoing node and 1 incoming node.
	 */
	@Test
	public void testLeftMiddleNode() {
		NodeWrapper leftMiddle = getNext(unwrap.getResult(), 1);
		assertTrue(leftMiddle instanceof DataNodeWrapper);
		assertEquals(1, leftMiddle.getIncoming().size());
		assertEquals(1, leftMiddle.getOutgoing().size());
		assertTrue(((DataNodeWrapper) leftMiddle).getDataNodeList().contains(dataNode2));
		assertTrue(((DataNodeWrapper) leftMiddle.getIncoming().get(0)).getDataNodeList().contains(dataNode1));
	}

	/**
	 * Verify that the right middle node contains dataNode3 and has 1 outgoing node and 1 incoming node.
	 */
	@Test
	public void testRightMiddleNode() {
		NodeWrapper rightMiddle = getNext(unwrap.getResult(), 2);
		assertTrue(rightMiddle instanceof DataNodeWrapper);
		assertEquals(1, rightMiddle.getIncoming().size());
		assertEquals(1, rightMiddle.getOutgoing().size());
		assertTrue(((DataNodeWrapper) rightMiddle).getDataNodeList().contains(dataNode3));
		assertTrue(((DataNodeWrapper) rightMiddle.getIncoming().get(0)).getDataNodeList().contains(dataNode2));
	}

	/**
	 * Verify that the right node contains dataNode4 and has 1 incoming node.
	 */
	@Test
	public void testRightNode() {
		NodeWrapper right = getNext(unwrap.getResult(), 3);
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

	/**
	 * Create a {@link HorizontalWrapper} given the two nodes
	 * @param wrapper1
	 *          node 1 for the wrapper
	 * @param wrapper2
	 *          node 2 for the wrapper
	 * @return
	 *          a {@link HorizontalWrapper} containing wrapper1 and wrapper2
	 */
	private HorizontalWrapper getHorizontalWrapper(NodeWrapper wrapper1, NodeWrapper wrapper2) {
		List<NodeWrapper> list = new ArrayList<>(2);
		list.add(wrapper1);
		list.add(wrapper2);
		wrapper1.getOutgoing().add(wrapper2);
		wrapper2.getIncoming().add(wrapper1);
		return new HorizontalWrapper(list);
	}

	/**
	 * Get the next node in the {@link HorizontalWrapper}
	 * @param node
	 *          start node
	 * @param times
	 *          how many times do you want to get the next
	 * @return
	 *          the next node
	 */
	private NodeWrapper getNext(NodeWrapper node, int times) {
		NodeWrapper result = node;
		for (int i = 0; i < times; i++) {
			result = result.getOutgoing().get(0);
		}
		return result;
	}
}
