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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class HorizontalWrappedUnwrapTest {

	@Mock private DataNode dataNode1;
	@Mock private DataNode dataNode2;
	@Mock private DataNode dataNode3;
	@Mock private DataNode dataNode4;

	private Unwrap unwrap;

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

	@Test
	public void testLeftNode() {
		NodeWrapper left = unwrap.getResult();
		assertTrue(left instanceof DataNodeWrapper);
		assertEquals(0, left.getIncoming().size());
		assertEquals(1, left.getOutgoing().size());
		assertTrue(((DataNodeWrapper) left).getDataNodeList().contains(dataNode1));
	}

	@Test
	public void testLeftMiddleNode() {
		NodeWrapper leftMiddle = getNext(unwrap.getResult(), 1);
		assertTrue(leftMiddle instanceof DataNodeWrapper);
		assertEquals(1, leftMiddle.getIncoming().size());
		assertEquals(1, leftMiddle.getOutgoing().size());
		assertTrue(((DataNodeWrapper) leftMiddle).getDataNodeList().contains(dataNode2));
		assertTrue(((DataNodeWrapper) leftMiddle.getIncoming().get(0)).getDataNodeList().contains(dataNode1));
	}

	@Test
	public void testRightMiddleNode() {
		NodeWrapper rightMiddle = getNext(unwrap.getResult(), 2);
		assertTrue(rightMiddle instanceof DataNodeWrapper);
		assertEquals(1, rightMiddle.getIncoming().size());
		assertEquals(1, rightMiddle.getOutgoing().size());
		assertTrue(((DataNodeWrapper) rightMiddle).getDataNodeList().contains(dataNode3));
		assertTrue(((DataNodeWrapper) rightMiddle.getIncoming().get(0)).getDataNodeList().contains(dataNode2));
	}

	@Test
	public void testRightNode() {
		NodeWrapper right = getNext(unwrap.getResult(), 3);
		assertTrue(right instanceof DataNodeWrapper);
		assertEquals(1, right.getIncoming().size());
		assertEquals(0, right.getOutgoing().size());
		assertTrue(((DataNodeWrapper) right).getDataNodeList().contains(dataNode4));
		assertTrue(((DataNodeWrapper) right.getIncoming().get(0)).getDataNodeList().contains(dataNode3));
	}

	@Test
	public void dataNodeWrapperCount() {
		assertEquals(4, unwrap.getDataNodeWrappers().size());
	}

	@Test
	public void testNoMorePlaceholders() {
		new NoMorePlaceholdersTest(unwrap.getDataNodeWrappers());
	}

	private HorizontalWrapper getHorizontalWrapper(NodeWrapper wrapper1, NodeWrapper wrapper2) {
		List<NodeWrapper> list = new ArrayList<>(2);
		list.add(wrapper1);
		list.add(wrapper2);
		wrapper1.getOutgoing().add(wrapper2);
		wrapper2.getIncoming().add(wrapper1);
		return new HorizontalWrapper(list, true);
	}

	private NodeWrapper getNext(NodeWrapper node, int times) {
		NodeWrapper result = node;
		for (int i = 0; i < times; i++) {
			result = result.getOutgoing().get(0);
		}
		return result;
	}
}
