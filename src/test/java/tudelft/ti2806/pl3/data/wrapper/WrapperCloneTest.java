package tudelft.ti2806.pl3.data.wrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the {@link WrapperClone}.
 * 
 * @author Sam Smulders
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WrapperCloneTest {
	@Test
	public void getterTests() {
		Wrapper testWrapper = new TestWrapper();
		WrapperClone wrapperClone = new WrapperClone(
				testWrapper.getDataNodes(), testWrapper);
		assertEquals(testWrapper.getBasePairCount(),
				wrapperClone.getBasePairCount());
		assertEquals(testWrapper.getIdString(), wrapperClone.getIdString());
		assertEquals(testWrapper.getGenome(), wrapperClone.getGenome());
		assertEquals(testWrapper.getGenome(), wrapperClone.getGenome());
		assertEquals(testWrapper.getWidth(), wrapperClone.getWidth());
		
		testWrapper.calculateX();
		wrapperClone.calculateX();
		assertEquals(testWrapper.getX(), wrapperClone.getX(), 0);
		
		assertEquals(testWrapper.getDataNodes(), wrapperClone.getDataNodeList());
		assertEquals(testWrapper, wrapperClone.getOriginalNode());
	}
	
	@Test
	public void collectDataNodesTest() {
		Wrapper testWrapper = new TestWrapper();
		Wrapper wrapperClone = new WrapperClone(testWrapper.getDataNodes(),
				testWrapper);
		List<DataNode> list1 = new ArrayList<>();
		wrapperClone.collectDataNodes(list1);
		assertNotNull(list1);
		List<DataNode> list2 = new ArrayList<>();
		testWrapper.collectDataNodes(list1);
		assertEquals(list1, list2);
	}
	
	@Test
	public void operationTest() {
		operationTest(new WrapperClone(null, new TestWrapper()), null);
	}
	
	private void operationTest(WrapperClone wrapper, Wrapper container) {
		WrapperOperation operation = mock(WrapperOperation.class);
		wrapper.calculate(operation, container);
		verify(operation, times(1)).calculate(wrapper, container);
	}
}
