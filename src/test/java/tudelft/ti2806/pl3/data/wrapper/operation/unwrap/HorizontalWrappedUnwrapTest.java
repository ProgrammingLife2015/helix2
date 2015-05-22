package tudelft.ti2806.pl3.data.wrapper.operation.unwrap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.data.wrapper.WrapperPlaceholder;
import tudelft.ti2806.pl3.data.wrapper.operation.unwrap.Unwrap;

import java.util.ArrayList;
import java.util.List;

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
	 * Create four {@link DataNodeWrapper}s and wrap these in two separate {@link HorizontalWrapper}s.
	 */
	@Before
	public void before() {
		DataNodeWrapper nodePosition1 = new DataNodeWrapper(dataNode1);
		DataNodeWrapper nodePosition2 = new DataNodeWrapper(dataNode2);
		DataNodeWrapper nodePosition3 = new DataNodeWrapper(dataNode3);
		DataNodeWrapper nodePosition4 = new DataNodeWrapper(dataNode4);

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
		Wrapper left = unwrap.getResult();
		assertTrue(left instanceof WrapperClone);
		assertEquals(0, left.getIncoming().size());
		assertEquals(1, left.getOutgoing().size());
		assertTrue(((WrapperClone) left).getDataNodeList().contains(dataNode1));
	}

	/**
	 * Verify that the left middle node contains dataNode2 and has 1 outgoing node and 1 incoming node.
	 */
	@Test
	public void testLeftMiddleNode() {
		Wrapper leftMiddle = getNext(unwrap.getResult(), 1);
		assertTrue(leftMiddle instanceof WrapperClone);
		assertEquals(1, leftMiddle.getIncoming().size());
		assertEquals(1, leftMiddle.getOutgoing().size());
		assertTrue(((WrapperClone) leftMiddle).getDataNodeList().contains(dataNode2));
		assertTrue(((WrapperClone) leftMiddle.getIncoming().get(0)).getDataNodeList().contains(dataNode1));
	}

	/**
	 * Verify that the right middle node contains dataNode3 and has 1 outgoing node and 1 incoming node.
	 */
	@Test
	public void testRightMiddleNode() {
		Wrapper rightMiddle = getNext(unwrap.getResult(), 2);
		assertTrue(rightMiddle instanceof WrapperClone);
		assertEquals(1, rightMiddle.getIncoming().size());
		assertEquals(1, rightMiddle.getOutgoing().size());
		assertTrue(((WrapperClone) rightMiddle).getDataNodeList().contains(dataNode3));
		assertTrue(((WrapperClone) rightMiddle.getIncoming().get(0)).getDataNodeList().contains(dataNode2));
	}

	/**
	 * Verify that the right node contains dataNode4 and has 1 incoming node.
	 */
	@Test
	public void testRightNode() {
		Wrapper right = getNext(unwrap.getResult(), 3);
		assertTrue(right instanceof WrapperClone);
		assertEquals(1, right.getIncoming().size());
		assertEquals(0, right.getOutgoing().size());
		assertTrue(((WrapperClone) right).getDataNodeList().contains(dataNode4));
		assertTrue(((WrapperClone) right.getIncoming().get(0)).getDataNodeList().contains(dataNode3));
	}

	/**
	 * Verify that we indeed get four {@link WrapperClone}s.
	 */
	@Test
	public void dataNodeWrapperCount() {
		assertEquals(4, unwrap.getWrapperClones().size());
	}

	/**
	 * Verify that no more {@link WrapperPlaceholder}s are left.
	 */
	@Test
	public void testNoMorePlaceholders() {
		new NoMorePlaceholdersTest(unwrap.getWrapperClones());
	}

	/**
	 * Create a {@link HorizontalWrapper} given the two nodes.
	 * 
	 * @param wrapper1
	 *          node 1 for the wrapper
	 * @param wrapper2
	 *          node 2 for the wrapper
	 * @return
	 *          a {@link HorizontalWrapper} containing wrapper1 and wrapper2
	 */
	private HorizontalWrapper getHorizontalWrapper(Wrapper wrapper1, Wrapper wrapper2) {
		List<Wrapper> list = new ArrayList<>(2);
		list.add(wrapper1);
		list.add(wrapper2);
		wrapper1.getOutgoing().add(wrapper2);
		wrapper2.getIncoming().add(wrapper1);
		return new HorizontalWrapper(list, true);
	}

	/**
	 * Get the next node in the {@link HorizontalWrapper}.
	 * 
	 * @param node
	 *          start node
	 * @param times
	 *          how many times do you want to get the next
	 * @return
	 *          the next node
	 */
	private Wrapper getNext(Wrapper node, int times) {
		Wrapper result = node;
		for (int i = 0; i < times; i++) {
			result = result.getOutgoing().get(0);
		}
		return result;
	}
}
