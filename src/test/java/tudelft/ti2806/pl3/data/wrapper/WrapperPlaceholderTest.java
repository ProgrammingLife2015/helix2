package tudelft.ti2806.pl3.data.wrapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for the {@link WrapperPlaceholder}.
 * 
 * @author Sam Smulders
 */
@RunWith(MockitoJUnitRunner.class)
public class WrapperPlaceholderTest {
	@Test
	public void getterTests() {
		Wrapper placeholder = new WrapperPlaceholder();
		assertEquals(0, placeholder.getBasePairCount(), 0);
		assertTrue(WrapperPlaceholder.ID_STRING.equals(placeholder.getIdString()));
		assertNull(placeholder.getGenome());
		assertEquals(0, placeholder.getX(), 0);
		placeholder.x = 1;
		placeholder.calculateX();
		assertEquals(1, placeholder.getX(), 0);
		assertEquals(0, placeholder.getWidth());
	}
	
	@Mock
	Set<DataNode> set;
	
	@Test
	public void collectDataNodesTest() {
		WrapperPlaceholder wrapper = new WrapperPlaceholder();
		wrapper.collectDataNodes(set);
		Mockito.verifyZeroInteractions(set);
	}
	
	@Test
	public void operationTest() {
		operationTest(new WrapperPlaceholder(), null);
	}
	
	private void operationTest(WrapperPlaceholder wrapper, Wrapper container) {
		WrapperOperation operation = mock(WrapperOperation.class);
		wrapper.calculate(operation, container);
		verify(operation, times(1)).calculate(wrapper, container);
	}
}
