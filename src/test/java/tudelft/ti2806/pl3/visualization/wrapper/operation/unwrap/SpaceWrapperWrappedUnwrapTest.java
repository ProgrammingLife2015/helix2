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
 * The same test as {@link SpaceWrapperUnwrapTest}, but it also tests the functionality
 * of incoming nodes on the {@link SpaceWrapper}.
 *
 * @see {@link SpaceWrapperUnwrapTest}
 * @see {@link Unwrap#calculate(SpaceWrapper, NodeWrapper)}
 *
 * Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpaceWrapperWrappedUnwrapTest {

	@Mock private DataNode dataNode0;
	@Mock private DataNode dataNode1;
	@Mock private DataNode dataNode2;
	@Mock private DataNode dataNode3;
	@Mock private DataNode dataNode4;

	private Unwrap unwrap;

	/**
	 * @see {@link SpaceWrapperUnwrapTest#before}
	 */
	@Before
	public void before() {
		NodePosition nodePosition0 = new NodePosition(dataNode0);
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

		List<NodeWrapper> horizontalList = new ArrayList<>(3);
		horizontalList.add(nodePosition0);
		horizontalList.add(space);
		horizontalList.add(nodePosition4);
		nodePosition0.getOutgoing().add(space);
		space.getOutgoing().add(nodePosition4);
		space.getIncoming().add(nodePosition0);
		nodePosition4.getIncoming().add(space);

		HorizontalWrapper start = new HorizontalWrapper(horizontalList, true);
		unwrap = new Unwrap(start);
	}

	/**
	 * Verify that the left most node is connected to the first node of the {@link SpaceWrapper}
	 */
	@Test
	public void testLeftNode() {
		NodeWrapper left = unwrap.getResult();
		assertTrue(left instanceof DataNodeWrapper);
		assertEquals(1, left.getOutgoing().size());
	}

	/**
	 * Verify that the left most node is connected to the first node of the {@link SpaceWrapper}
	 */
	@Test
	public void testLeftMiddleNode() {
		NodeWrapper leftMiddle = unwrap.getResult().getOutgoing().get(0);
		assertTrue(leftMiddle instanceof DataNodeWrapper);
		assertEquals(1, leftMiddle.getIncoming().size());
	}

	/**
	 * Verify that we indeed get five {@link DataNodeWrapper}s.
	 */
	@Test
	public void dataNodeWrapperCount() {
		assertEquals(5, unwrap.getDataNodeWrappers().size());
	}

	/**
	 * Verify that no more {@link PlaceholderWrapper}s are left.
	 */
	@Test
	public void testNoMorePlaceholders() {
		new NoMorePlaceholdersTest(unwrap.getDataNodeWrappers());
	}
}
