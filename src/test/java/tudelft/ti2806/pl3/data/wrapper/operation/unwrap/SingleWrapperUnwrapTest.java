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
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the unwrapping of a
 * {@link tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper}.
 * <p/>
 * <p>
 * It creates two {@link tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper}s in
 * a {@link tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper} to verify that
 * the wrappers are recursively unwrapped.
 * </p>
 * <p>
 * It thereby also verifies that the start of the graph is updated correctly.
 * </p>
 * Created by Boris Mattijssen on 19-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SingleWrapperUnwrapTest {

    @Mock
    private DataNode dataNode1;
    @Mock
    private DataNode dataNode2;
    @Mock
    private DataNode dataNode3;

    private Unwrap unwrap;

    /**
     * Create four {@link tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper} s and
     * wrap these in two separate
     * {@link tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper}s.
     */
    @Before
    public void before() {
        DataNodeWrapper nodePosition1 = new DataNodeWrapper(dataNode1);
        DataNodeWrapper nodePosition2 = new DataNodeWrapper(dataNode2);
        DataNodeWrapper nodePosition3 = new DataNodeWrapper(dataNode3);

        SingleWrapper singleWrapper = new SingleWrapper(new SingleWrapper(
                new SingleWrapper(nodePosition2)));

        List<Wrapper> list = new ArrayList<>();
        list.add(nodePosition1);
        list.add(singleWrapper);
        list.add(nodePosition3);
        nodePosition1.getOutgoing().add(singleWrapper);
        singleWrapper.getOutgoing().add(nodePosition3);
        singleWrapper.getIncoming().add(nodePosition1);
        nodePosition3.getIncoming().add(singleWrapper);
        HorizontalWrapper start = new HorizontalWrapper(list, true);

        unwrap = new UnwrapTest();
        unwrap.compute(start);
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
        assertTrue(((WrapperClone) left).getDataNodes().contains(
                dataNode1));
    }

    /**
     * Verify that the left middle node contains dataNode2 and has 1 outgoing
     * node and 1 incoming node.
     */
    @Test
    public void testMiddleNode() {
        Wrapper leftMiddle = getNext(unwrap.getResult(), 1);
        assertTrue(leftMiddle instanceof WrapperClone);
        assertEquals(1, leftMiddle.getIncoming().size());
        assertEquals(1, leftMiddle.getOutgoing().size());
        assertTrue(((WrapperClone) leftMiddle).getDataNodes().contains(
                dataNode2));
        assertTrue(((WrapperClone) leftMiddle.getIncoming().get(0))
                .getDataNodes().contains(dataNode1));
    }

    /**
     * Verify that the right middle node contains dataNode3 and has 1 outgoing
     * node and 1 incoming node.
     */
    @Test
    public void testRightNode() {
        Wrapper rightMiddle = getNext(unwrap.getResult(), 2);
        assertTrue(rightMiddle instanceof WrapperClone);
        assertEquals(1, rightMiddle.getIncoming().size());
        assertEquals(0, rightMiddle.getOutgoing().size());
        assertTrue(((WrapperClone) rightMiddle).getDataNodes().contains(
                dataNode3));
        assertTrue(((WrapperClone) rightMiddle.getIncoming().get(0))
                .getDataNodes().contains(dataNode2));
    }

    /**
     * Verify that we indeed get four
     * {@link tudelft.ti2806.pl3.data.wrapper.WrapperClone}s.
     */
    @Test
    public void dataNodeWrapperCount() {
        assertEquals(3, unwrap.getWrapperClones().size());
    }

    /**
     * Verify that no more
     * {@link tudelft.ti2806.pl3.data.wrapper.WrapperPlaceholder}s are left.
     */
    @Test
    public void testNoMorePlaceholders() {
        new NoMorePlaceholdersTest(unwrap.getWrapperClones());
    }

    /**
     * Get the next node in the
     * {@link tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper}.
     *
     * @param node
     *         start node
     * @param times
     *         how many times do you want to get the next
     * @return the next node
     */
    private Wrapper getNext(Wrapper node, int times) {
        Wrapper result = node;
        for (int i = 0; i < times; i++) {
            result = result.getOutgoing().get(0);
        }
        return result;
    }
}
