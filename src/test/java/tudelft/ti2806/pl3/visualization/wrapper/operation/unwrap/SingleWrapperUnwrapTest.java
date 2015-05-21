package tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.visualization.wrapper.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the unwrapping of a {@link tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper}.
 *
 * <p>It creates two {@link tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper}s in a {@link tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper} to
 * verify that the wrappers are recursively unwrapped.</p>
 * <p>It thereby also verifies that the start of the graph is updated correctly.</p>
 * Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SingleWrapperUnwrapTest {

	@Mock private DataNode dataNode1;
	@Mock private DataNode dataNode2;
	@Mock private DataNode dataNode3;

	private Unwrap unwrap;

	/**
	 * Create four {@link tudelft.ti2806.pl3.visualization.wrapper.NodePosition}s and wrap these in two separate {@link tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper}s.
	 */
	@Before
	public void before() {
		NodePosition nodePosition1 = new NodePosition(dataNode1);
		NodePosition nodePosition2 = new NodePosition(dataNode2);
		NodePosition nodePosition3 = new NodePosition(dataNode3);

		SingleWrapper singleWrapper = new SingleWrapper(new SingleWrapper(new SingleWrapper(nodePosition2)));


		List<NodeWrapper> list = new ArrayList<>();
		list.add(nodePosition1);
		list.add(singleWrapper);
		list.add(nodePosition3);
		nodePosition1.getOutgoing().add(singleWrapper);
		singleWrapper.getOutgoing().add(nodePosition3);
		singleWrapper.getIncoming().add(nodePosition1);
		nodePosition3.getIncoming().add(singleWrapper);
		HorizontalWrapper start = new HorizontalWrapper(list,true);

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
	public void testMiddleNode() {
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
	public void testRightNode() {
		NodeWrapper rightMiddle = getNext(unwrap.getResult(), 2);
		assertTrue(rightMiddle instanceof DataNodeWrapper);
		assertEquals(1, rightMiddle.getIncoming().size());
		assertEquals(0, rightMiddle.getOutgoing().size());
		assertTrue(((DataNodeWrapper) rightMiddle).getDataNodeList().contains(dataNode3));
		assertTrue(((DataNodeWrapper) rightMiddle.getIncoming().get(0)).getDataNodeList().contains(dataNode2));
	}

	/**
	 * Verify that we indeed get four {@link tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper}s.
	 */
	@Test
	public void dataNodeWrapperCount() {
		assertEquals(3, unwrap.getDataNodeWrappers().size());
	}

	/**
	 * Verify that no more {@link tudelft.ti2806.pl3.visualization.wrapper.PlaceholderWrapper}s are left.
	 */
	@Test
	public void testNoMorePlaceholders() {
		new NoMorePlaceholdersTest(unwrap.getDataNodeWrappers());
	}

	/**
	 * Get the next node in the {@link tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper}
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
