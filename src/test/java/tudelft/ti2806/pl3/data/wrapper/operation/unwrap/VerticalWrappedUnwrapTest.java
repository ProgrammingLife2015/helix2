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
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.data.wrapper.WrapperPlaceholder;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the unwrapping of a {@link VerticalWrapper}.
 *
 * <p>
 * It creates a {@link VerticalWrapper} wrapped in a {@link HorizontalWrapper}.
 * </p>
 * <p>
 * It verifies that all the connections between the nodes are correct.
 * </p>
 * Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class VerticalWrappedUnwrapTest {
	
	@Mock
	private DataNode dataNode1;
	@Mock
	private DataNode dataNode2;
	@Mock
	private DataNode dataNode3;
	@Mock
	private DataNode dataNode4;
	
	private Unwrap unwrap;
	
	/**
	 * Runs before each test.
	 */
	@Before
	public void before() {
		DataNodeWrapper nodePosition1 = new DataNodeWrapper(dataNode1);
		DataNodeWrapper nodePosition2 = new DataNodeWrapper(dataNode2);
		DataNodeWrapper nodePosition3 = new DataNodeWrapper(dataNode3);
		DataNodeWrapper nodePosition4 = new DataNodeWrapper(dataNode4);
		
		List<Wrapper> verticalList = new ArrayList<>(2);
		verticalList.add(nodePosition2);
		verticalList.add(nodePosition3);
		VerticalWrapper vertical = new VerticalWrapper(verticalList);
		
		List<Wrapper> horizontalList = new ArrayList<>(3);
		horizontalList.add(nodePosition1);
		horizontalList.add(vertical);
		horizontalList.add(nodePosition4);
		nodePosition1.getOutgoing().add(vertical);
		vertical.getOutgoing().add(nodePosition4);
		vertical.getIncoming().add(nodePosition1);
		nodePosition4.getIncoming().add(vertical);
		
		HorizontalWrapper start = new HorizontalWrapper(horizontalList, true);
		
		unwrap = new UnwrapTest();
		unwrap.compute(start);
	}
	
	@Test
	public void testLeftNode() {
		Wrapper left = unwrap.getResult();
		assertTrue(left instanceof WrapperClone);
		assertEquals(0, left.getIncoming().size());
		assertEquals(2, left.getOutgoing().size());
		assertTrue(((WrapperClone) left).getDataNodeList().contains(dataNode1));
	}
	
	@Test
	public void testTopNode() {
		Wrapper top = unwrap.getResult().getOutgoing().get(0);
		assertTrue(top instanceof WrapperClone);
		assertEquals(1, top.getIncoming().size());
		assertEquals(1, top.getOutgoing().size());
		assertTrue(((WrapperClone) top).getDataNodeList().contains(dataNode2));
		assertTrue(((WrapperClone) top.getIncoming().get(0)).getDataNodeList()
				.contains(dataNode1));
	}
	
	@Test
	public void testBottomNode() {
		Wrapper bottom = unwrap.getResult().getOutgoing().get(1);
		assertTrue(bottom instanceof WrapperClone);
		assertEquals(1, bottom.getIncoming().size());
		assertEquals(1, bottom.getOutgoing().size());
		assertTrue(((WrapperClone) bottom).getDataNodeList()
				.contains(dataNode3));
		assertTrue(((WrapperClone) bottom.getIncoming().get(0))
				.getDataNodeList().contains(dataNode1));
	}
	
	@Test
	public void testRight1Node() {
		Wrapper right1 = unwrap.getResult().getOutgoing().get(0).getOutgoing()
				.get(0);
		assertTrue(right1 instanceof WrapperClone);
		assertEquals(2, right1.getIncoming().size());
		assertEquals(0, right1.getOutgoing().size());
		assertTrue(((WrapperClone) right1).getDataNodeList()
				.contains(dataNode4));
		assertTrue(((WrapperClone) right1.getIncoming().get(0))
				.getDataNodeList().contains(dataNode2));
		assertTrue(((WrapperClone) right1.getIncoming().get(1))
				.getDataNodeList().contains(dataNode3));
	}
	
	@Test
	public void testRight2Node() {
		Wrapper right2 = unwrap.getResult().getOutgoing().get(1).getOutgoing()
				.get(0);
		assertTrue(right2 instanceof WrapperClone);
		assertEquals(2, right2.getIncoming().size());
		assertEquals(0, right2.getOutgoing().size());
		assertTrue(((WrapperClone) right2).getDataNodeList()
				.contains(dataNode4));
		assertTrue(((WrapperClone) right2.getIncoming().get(0))
				.getDataNodeList().contains(dataNode2));
		assertTrue(((WrapperClone) right2.getIncoming().get(1))
				.getDataNodeList().contains(dataNode3));
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
}
