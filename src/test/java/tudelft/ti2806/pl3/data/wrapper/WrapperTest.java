package tudelft.ti2806.pl3.data.wrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import tudelft.ti2806.pl3.data.graph.DataNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Tests the methods in the abstract class {@link Wrapper}.
 *
 * @author Sam Smulders
 */
public class WrapperTest {
    private TestWrapper wrapper;

    @Before
    public void before() {
        wrapper = new TestWrapper(0);
    }

    @Test
    public void getSetTest() {
        assertEquals(1f, wrapper.getInterest(), 0f);
        wrapper.addInterest(1);
        assertEquals(2, wrapper.getInterest(), 0f);
        assertEquals(0, wrapper.getX(), 0);
        wrapper.x = 1;
        assertEquals(1, wrapper.getX(), 0);
        assertEquals(0, wrapper.getY(), 0);
        wrapper.setY(1);
        assertEquals(1, wrapper.getY(), 0);
        assertNotNull(wrapper.getIncoming());
        assertEquals(0, wrapper.getIncoming().size());
        assertNotNull(wrapper.getOutgoing());
        assertEquals(0, wrapper.getOutgoing().size());
        List<Wrapper> list = new ArrayList<>();
        list.add(wrapper);
        wrapper.setIncoming(list);
        assertEquals(wrapper.getIncoming().size(), 1);
        assertEquals(wrapper.getIncoming().get(0), wrapper);
        wrapper.setOutgoing(list);
        assertEquals(wrapper.getOutgoing().size(), 1);
        assertEquals(wrapper.getOutgoing().get(0), wrapper);
    }

    @Test
    public void getDataNodesTest() {
        Set<DataNode> result = wrapper.getDataNodes();
        assertNotNull(result);
        assertEquals(result.size(), 0);
    }

    @Test
    public void compareTest() {
        List<Wrapper> wrapperList = new ArrayList<>();
        Wrapper wrapper1 = new TestWrapper(1);
        Wrapper wrapper2 = new TestWrapper(2);
        Wrapper wrapper3 = new TestWrapper(3);
        Wrapper wrapper4 = new TestWrapper(4);

        wrapper1.getOutgoing().add(wrapper2);
        wrapper2.getOutgoing().add(wrapper3);
        wrapper4.getOutgoing().add(wrapper3);

        wrapper2.getIncoming().add(wrapper1);
        wrapper3.getIncoming().add(wrapper2);
        wrapper3.getIncoming().add(wrapper4);

        wrapperList.add(wrapper4);
        wrapperList.add(wrapper1);
        wrapperList.add(wrapper2);
        wrapperList.add(wrapper3);

        Wrapper.computeLongestPaths(wrapperList);

        assertEquals(0, wrapper1.getPreviousNodesCount());
        assertEquals(1, wrapper2.getPreviousNodesCount());
        assertEquals(2, wrapper3.getPreviousNodesCount());
        assertEquals(1, wrapper4.getPreviousNodesCount());

        assertEquals(-1, wrapper1.compareTo(wrapper2));
        assertEquals(1, wrapper2.compareTo(wrapper1));
        assertEquals(0, wrapper1.compareTo(wrapper1));
        assertNotEquals(0, wrapper2.compareTo(wrapper4));
    }
}
