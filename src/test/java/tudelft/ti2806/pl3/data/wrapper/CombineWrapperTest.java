package tudelft.ti2806.pl3.data.wrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		List<DataNode> list = new ArrayList<>();
		wrapper.collectDataNodes(list);
		assertNotNull(list);
		
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
