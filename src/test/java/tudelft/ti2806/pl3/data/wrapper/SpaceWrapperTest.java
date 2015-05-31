package tudelft.ti2806.pl3.data.wrapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mockito;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpaceWrapperTest {
	@Test
	public void getterTests() {
		final Genome genome1 = new Genome("1");
		final Genome genome2 = new Genome("2");
		final Wrapper wrapper1 = new TestWrapper(1, genome1, genome2);
		final Wrapper wrapper2 = new TestWrapper(2, genome1);
		final Wrapper wrapper3 = new TestWrapper(4, genome1, genome2);
		List<Wrapper> wrapperList = new ArrayList<>();
		wrapperList.add(wrapper1);
		wrapperList.add(wrapper2);
		wrapperList.add(wrapper3);
		SpaceWrapper wrapper = new SpaceWrapper(wrapperList);
		assertEquals(7, wrapper.getBasePairCount(), 0);
		
		Set<Genome> completeSet = new HashSet<>();
		completeSet.add(genome1);
		completeSet.add(genome2);
		assertEquals(completeSet, wrapper.getGenome());
		
		assertEquals('S', wrapper.getIdString().charAt(0));
		assertEquals(3, wrapper.getWidth(), 0);
	}
	
	@Test
	public void alreadyComputedTest() {
		@SuppressWarnings("unchecked")
		List<Wrapper> wrappers = mock(List.class);
		SpaceWrapper wrapper = new SpaceWrapper(wrappers);
		wrapper.basePairCount = 0;
		assertEquals(0, wrapper.getBasePairCount(), 0);
		Mockito.verifyNoMoreInteractions(wrappers);
		wrapper.width = 0;
		assertEquals(0, wrapper.getWidth(), 0);
		Mockito.verifyNoMoreInteractions(wrappers);
	}
	
	@Test
	public void operationTest() {
		operationTest(new SpaceWrapper(null), null);
	}
	
	private void operationTest(SpaceWrapper wrapper, Wrapper container) {
		WrapperOperation operation = mock(WrapperOperation.class);
		wrapper.calculate(operation, container);
		verify(operation, times(1)).calculate(wrapper, container);
	}
}
