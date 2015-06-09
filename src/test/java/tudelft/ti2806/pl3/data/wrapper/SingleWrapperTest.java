package tudelft.ti2806.pl3.data.wrapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for the {@link SingleWrapper}.
 * 
 * @author Sam Smmmulders
 */
@RunWith(MockitoJUnitRunner.class)
public class SingleWrapperTest {
	@Test
	public void constructorTest() {
		TestWrapper testWrapper = new TestWrapper();
		SingleWrapper singleWrapper1 = new SingleWrapper(testWrapper);
		assertTrue(testWrapper == singleWrapper1.getNode());
		SingleWrapper singleWrapper2 = new SingleWrapper(singleWrapper1);
		assertTrue(testWrapper == singleWrapper2.getNode());
	}
	
	@Test
	public void singleWrapTest() {
		TestWrapper testWrapper = new TestWrapper();
		SingleWrapper singleWrapper = new SingleWrapper(testWrapper);
		assertEquals(testWrapper.getBasePairCount(),
				singleWrapper.getBasePairCount());
		assertEquals(testWrapper.getIdString(), singleWrapper.getIdString());
		assertEquals(testWrapper.getGenome(), singleWrapper.getGenome());
		assertEquals(testWrapper.getGenome(), singleWrapper.getGenome());
		assertEquals(testWrapper.getWidth(), singleWrapper.getWidth());
		
		testWrapper.calculateX();
		singleWrapper.calculateX();
		assertEquals(testWrapper.getX(), singleWrapper.getX(), 0);
	}
	
	@Test
	public void collectDataNodesTest() {
		TestWrapper testWrapper = new TestWrapper();
		SingleWrapper singleWrapper = new SingleWrapper(testWrapper);
		Set<DataNode> set1 = new HashSet<>();
		singleWrapper.collectDataNodes(set1);
		assertNotNull(set1);
		Set<DataNode> set2 = new HashSet<>();
		testWrapper.collectDataNodes(set1);
		assertEquals(set1, set2);
	}
	
	@Test
	public void operationTest() {
		operationTest(new SingleWrapper(new TestWrapper()), null);
	}
	
	private void operationTest(SingleWrapper wrapper, Wrapper container) {
		WrapperOperation operation = mock(WrapperOperation.class);
		wrapper.calculate(operation, container);
		verify(operation, times(1)).calculate(wrapper, container);
	}
}
