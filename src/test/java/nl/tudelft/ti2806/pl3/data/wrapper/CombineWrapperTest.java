package nl.tudelft.ti2806.pl3.data.wrapper;

import nl.tudelft.ti2806.pl3.data.Genome;
import nl.tudelft.ti2806.pl3.data.graph.DataNode;
import nl.tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the methods in the abstract class {@link CombineWrapper}.
 *
 * @author Sam Smulders
 */
public class CombineWrapperTest {
    private static final String INNER_ID_STRING_REGDEX = "[\\[][A-Za-z0-9]*[\\]]";

    @Test
    public void combineWrapperGetTests() {
        Wrapper wrapper1 = new TestWrapper(1);
        Wrapper wrapper2 = new TestWrapper(2);
        Wrapper wrapper3 = new TestWrapper(3);

        // Test node list
        List<Wrapper> wrappers = new ArrayList<>(3);
        wrappers.add(wrapper1);
        wrappers.add(wrapper2);
        wrappers.add(wrapper3);
        CombineWrapper wrapper = new TestCombineWrapper(wrappers);
        assertTrue(wrapper1 == wrapper.getFirst());
        assertTrue(wrapper3 == wrapper.getLast());
        assertTrue(wrappers == wrapper.getNodeList());
        // Test toIdString
        assertTrue(wrapper.getIdString().matches(
                "\\{" + INNER_ID_STRING_REGDEX + INNER_ID_STRING_REGDEX
                        + INNER_ID_STRING_REGDEX + "\\}"));
        Set<DataNode> set = new HashSet<>();
        wrapper.collectDataNodes(set);
        assertNotNull(set);

        wrapper1.x = 0;
        wrapper2.x = 1;
        wrapper3.x = 2;

        wrapper.calculateX();

        assertEquals(1, wrapper.getX(), 0);
    }

    private static class TestCombineWrapper extends CombineWrapper {
        public TestCombineWrapper(List<Wrapper> nodeList) {
            super(nodeList);
        }

        @Override
        public long getBasePairCount() {
            return 0;
        }

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public Set<Genome> getGenome() {
            return null;
        }

        @Override
        public void calculate(WrapperOperation wrapperSequencer,
                Wrapper container) {
        }

    }
}
