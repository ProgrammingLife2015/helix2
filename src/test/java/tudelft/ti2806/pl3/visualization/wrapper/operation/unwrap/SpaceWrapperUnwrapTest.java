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
 * Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpaceWrapperUnwrapTest {

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

		SpaceWrapper space = new SpaceWrapper(listSpace, true);

		List<NodeWrapper> horizontalList = new ArrayList<>(2);
		horizontalList.add(space);
		horizontalList.add(nodePosition4);
		space.getOutgoing().add(nodePosition4);
		nodePosition4.getIncoming().add(space);

		HorizontalWrapper start = new HorizontalWrapper(horizontalList, true);
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
	public void testMiddleNode() {
		NodeWrapper middle = unwrap.getResult().getOutgoing().get(1);
		assertTrue(middle instanceof DataNodeWrapper);
		assertTrue(((DataNodeWrapper) middle).getDataNodeList().contains(dataNode3));
		assertEquals(2, middle.getIncoming().size());
		assertEquals(1, middle.getOutgoing().size());
		assertTrue(((DataNodeWrapper) middle.getIncoming().get(0)).getDataNodeList().contains(dataNode1));
		assertTrue(((DataNodeWrapper) middle.getIncoming().get(1)).getDataNodeList().contains(dataNode2));
	}

	@Test
	public void testRightNode() {
		NodeWrapper right = unwrap.getResult().getOutgoing().get(1).getOutgoing().get(0);
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
}
