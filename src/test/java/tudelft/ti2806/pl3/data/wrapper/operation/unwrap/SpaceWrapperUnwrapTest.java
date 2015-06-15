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
 * This class tests the unwrapping of a {@link SpaceWrapper}.
 * <p/>
 * <p>
 * It creates a {@link SpaceWrapper} in a {@link HorizontalWrapper}.
 * </p>
 * <p>
 * It verifies that all the edge connections between the nodes are correct.
 * </p>
 * <p/>
 * <p>
 * Created by Boris Mattijssen on 19-05-15.
 * </p>
 */
@RunWith(MockitoJUnitRunner.class)
public class SpaceWrapperUnwrapTest {

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
     * Create a {@link SpaceWrapper} wrapped in a {@link HorizontalWrapper}.
     */
    @Before
    public void before() {
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

        List<Wrapper> horizontalList = new ArrayList<>(2);
        horizontalList.add(space);
        horizontalList.add(nodePosition4);
        space.getOutgoing().add(nodePosition4);
        nodePosition4.getIncoming().add(space);

        HorizontalWrapper start = new HorizontalWrapper(horizontalList, true);

        unwrap = new UnwrapTest();
        unwrap.compute(start);
    }

    /**
     * Verify that the left node contains dataNode1 and has 2 outgoing nodes
     * (going to dataNode2 and dataNode3).
     */
    @Test
    public void testLeftNode() {
        Wrapper left = unwrap.getResult();
        assertTrue(left instanceof WrapperClone);
        assertEquals(0, left.getIncoming().size());
        assertEquals(2, left.getOutgoing().size());
        assertTrue(((WrapperClone) left).getDataNodes().contains(dataNode1));
    }

    /**
     * Verify that the top node contains dataNode2 and has 1 outgoing node
     * (dataNode1) and 1 incoming node (dataNode3).
     */
    @Test
    public void testTopNode() {
        Wrapper top = unwrap.getResult().getOutgoing().get(0);
        assertTrue(top instanceof WrapperClone);
        assertEquals(1, top.getIncoming().size());
        assertEquals(1, top.getOutgoing().size());
        assertTrue(((WrapperClone) top).getDataNodes().contains(dataNode2));
        assertTrue(((WrapperClone) top.getIncoming().get(0)).getDataNodes().contains(dataNode1));
    }

    /**
     * Verify that the middle node contains dataNode3 and has 1 outgoing node
     * (dataNode4) and 2 incoming nodes (dataNode1 and dataNode2).
     */
    @Test
    public void testMiddleNode() {
        Wrapper middle = unwrap.getResult().getOutgoing().get(1);
        assertTrue(middle instanceof WrapperClone);
        assertTrue(((WrapperClone) middle).getDataNodes().contains(dataNode3));
        assertEquals(2, middle.getIncoming().size());
        assertEquals(1, middle.getOutgoing().size());
        assertTrue(((WrapperClone) middle.getIncoming().get(0)).getDataNodes().contains(dataNode1));
        assertTrue(((WrapperClone) middle.getIncoming().get(1)).getDataNodes().contains(dataNode2));
    }

    /**
     * Verify that the right node contains dataNode4 and has 1 incoming node
     * (dataNode3).
     */
    @Test
    public void testRightNode() {
        Wrapper right = unwrap.getResult().getOutgoing().get(1).getOutgoing()
                .get(0);
        assertTrue(right instanceof WrapperClone);
        assertEquals(1, right.getIncoming().size());
        assertEquals(0, right.getOutgoing().size());
        assertTrue(((WrapperClone) right).getDataNodes().contains(dataNode4));
        assertTrue(((WrapperClone) right.getIncoming().get(0)).getDataNodes().contains(dataNode3));
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
