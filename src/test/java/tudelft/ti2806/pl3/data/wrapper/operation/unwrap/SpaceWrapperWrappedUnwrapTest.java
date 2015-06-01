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
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.data.wrapper.WrapperPlaceholder;

import java.util.ArrayList;
import java.util.List;

/**
 * The same test as {@link SpaceWrapperUnwrapTest}, but it also tests the
 * functionality of incoming nodes on the {@link SpaceWrapper}.
 *
 * @see {@link SpaceWrapperUnwrapTest}
 * @see {@link Unwrap#calculate(SpaceWrapper, Wrapper)}
 *
 *      <p>
 *      Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpaceWrapperWrappedUnwrapTest {
	
	@Mock
	private DataNode dataNode0;
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
	 * @see {@link SpaceWrapperUnwrapTest#before}.
	 */
	@Before
	public void before() {
		DataNodeWrapper nodePosition0 = new DataNodeWrapper(dataNode0);
		DataNodeWrapper nodePosition1 = new DataNodeWrapper(dataNode1);
		DataNodeWrapper nodePosition2 = new DataNodeWrapper(dataNode2);
		DataNodeWrapper nodePosition3 = new DataNodeWrapper(dataNode3);
		DataNodeWrapper nodePosition4 = new DataNodeWrapper(dataNode4);
		
		List<Wrapper> listSpace = new ArrayList<>(3);
		listSpace.add(nodePosition1);
		listSpace.add(nodePosition2);
		listSpace.add(nodePosition3);
		
		nodePosition1.getOutgoing().add(nodePosition2);
		nodePosition1.getOutgoing().add(nodePosition3);
		nodePosition2.getOutgoing().add(nodePosition3);
		
		nodePosition2.getIncoming().add(nodePosition1);
		nodePosition3.getIncoming().add(nodePosition1);
		nodePosition3.getIncoming().add(nodePosition2);
		
		SpaceWrapper space = new SpaceWrapper(listSpace);
		
		List<Wrapper> horizontalList = new ArrayList<>(3);
		horizontalList.add(nodePosition0);
		horizontalList.add(space);
		horizontalList.add(nodePosition4);
		nodePosition0.getOutgoing().add(space);
		space.getOutgoing().add(nodePosition4);
		space.getIncoming().add(nodePosition0);
		nodePosition4.getIncoming().add(space);
		
		HorizontalWrapper start = new HorizontalWrapper(horizontalList);
		
		unwrap = new UnwrapTest();
		unwrap.compute(start);
	}
	
	/**
	 * Verify that the left most node is connected to the first node of the
	 * {@link SpaceWrapper}.
	 */
	@Test
	public void testLeftNode() {
		Wrapper left = unwrap.getResult();
		assertTrue(left instanceof WrapperClone);
		assertEquals(1, left.getOutgoing().size());
	}
	
	/**
	 * Verify that the left most node is connected to the first node of the
	 * {@link SpaceWrapper}.
	 */
	@Test
	public void testLeftMiddleNode() {
		Wrapper leftMiddle = unwrap.getResult().getOutgoing().get(0);
		assertTrue(leftMiddle instanceof WrapperClone);
		assertEquals(1, leftMiddle.getIncoming().size());
	}
	
	/**
	 * Verify that we indeed get five {@link WrapperClone}s.
	 */
	@Test
	public void dataNodeWrapperCount() {
		assertEquals(5, unwrap.getWrapperClones().size());
	}
	
	/**
	 * Verify that no more {@link WrapperPlaceholder}s are left.
	 */
	@Test
	public void testNoMorePlaceholders() {
		new NoMorePlaceholdersTest(unwrap.getWrapperClones());
	}
}
