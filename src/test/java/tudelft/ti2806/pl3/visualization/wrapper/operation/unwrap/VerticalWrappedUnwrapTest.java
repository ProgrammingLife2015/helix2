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
 * This class tests the unwrapping of a {@link VerticalWrapper}.
 *
 * <p>It creates a {@link VerticalWrapper} wrapped in a {@link HorizontalWrapper}.</p>
 * <p>It verifies that all the connections between the nodes are correct.</p>
 * Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerticalWrappedUnwrapTest {

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

		List<NodeWrapper> verticalList = new ArrayList<>(2);
		verticalList.add(nodePosition2);
		verticalList.add(nodePosition3);
		VerticalWrapper vertical = new VerticalWrapper(verticalList);

		List<NodeWrapper> horizontalList = new ArrayList<>(3);
		horizontalList.add(nodePosition1);
		horizontalList.add(vertical);
		horizontalList.add(nodePosition4);
		nodePosition1.getOutgoing().add(vertical);
		vertical.getOutgoing().add(nodePosition4);
		vertical.getIncoming().add(nodePosition1);
		nodePosition4.getIncoming().add(vertical);

		HorizontalWrapper start = new HorizontalWrapper(horizontalList);
		unwrap = new Unwrap(start);
	}

	@Test
	public void testLeftNode() {
		NodeWrapper left = unwrap.getResult();
		assertTrue(left instanceof DataNodeWrapper);
		assertEquals(0, left.getIncoming().size());
		assertEquals(2, left.getOutgoing().size());
		assertTrue(((DataNodeWrapper) left).getDataNodeList().contains(dataNode1));
	}

	@Test
	public void testTopNode() {
		NodeWrapper top = unwrap.getResult().getOutgoing().get(0);
		assertTrue(top instanceof DataNodeWrapper);
		assertEquals(1, top.getIncoming().size());
		assertEquals(1, top.getOutgoing().size());
		assertTrue(((DataNodeWrapper) top).getDataNodeList().contains(dataNode2));
		assertTrue(((DataNodeWrapper) top.getIncoming().get(0)).getDataNodeList().contains(dataNode1));
	}

	@Test
	public void testBottomNode() {
		NodeWrapper bottom = unwrap.getResult().getOutgoing().get(1);
		assertTrue(bottom instanceof DataNodeWrapper);
		assertEquals(1, bottom.getIncoming().size());
		assertEquals(1, bottom.getOutgoing().size());
		assertTrue(((DataNodeWrapper) bottom).getDataNodeList().contains(dataNode3));
		assertTrue(((DataNodeWrapper) bottom.getIncoming().get(0)).getDataNodeList().contains(dataNode1));
	}

	@Test
	public void testRight1Node() {
		NodeWrapper right1 = unwrap.getResult().getOutgoing().get(0).getOutgoing().get(0);
		assertTrue(right1 instanceof DataNodeWrapper);
		assertEquals(2, right1.getIncoming().size());
		assertEquals(0, right1.getOutgoing().size());
		assertTrue(((DataNodeWrapper) right1).getDataNodeList().contains(dataNode4));
		assertTrue(((DataNodeWrapper) right1.getIncoming().get(0)).getDataNodeList().contains(dataNode2));
		assertTrue(((DataNodeWrapper) right1.getIncoming().get(1)).getDataNodeList().contains(dataNode3));
	}

	@Test
	public void testRight2Node() {
		NodeWrapper right2 = unwrap.getResult().getOutgoing().get(1).getOutgoing().get(0);
		assertTrue(right2 instanceof DataNodeWrapper);
		assertEquals(2, right2.getIncoming().size());
		assertEquals(0, right2.getOutgoing().size());
		assertTrue(((DataNodeWrapper) right2).getDataNodeList().contains(dataNode4));
		assertTrue(((DataNodeWrapper) right2.getIncoming().get(0)).getDataNodeList().contains(dataNode2));
		assertTrue(((DataNodeWrapper) right2.getIncoming().get(1)).getDataNodeList().contains(dataNode3));
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
