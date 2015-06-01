package tudelft.ti2806.pl3.data.wrapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VerticalWrapperTest {
	@Test
	public void getterTests() {
		final Genome genome1 = new Genome("1");
		Wrapper wrapper1 = new TestWrapper(1, genome1);
		final Genome genome2 = new Genome("2");
		Wrapper wrapper2 = new TestWrapper(2, genome2);
		final Genome genome3 = new Genome("3");
		Wrapper wrapper3 = new TestWrapper(4, genome3);
		List<Wrapper> wrapperList = new ArrayList<>();
		wrapperList.add(wrapper1);
		wrapperList.add(wrapper2);
		wrapperList.add(wrapper3);
		VerticalWrapper wrapper = new VerticalWrapper(wrapperList);
		assertEquals(4, wrapper.getBasePairCount(), 0);
		
		Set<Genome> completeSet = new HashSet<>();
		completeSet.add(genome1);
		completeSet.add(genome2);
		completeSet.add(genome3);
		assertEquals(completeSet, wrapper.getGenome());
		
		assertEquals('V', wrapper.getIdString().charAt(0));
		assertEquals(1, wrapper.getWidth(), 0);
	}
	
	@Test
	public void operationTest() {
		operationTest(new VerticalWrapper(null), null);
	}
	
	private void operationTest(VerticalWrapper wrapper, Wrapper container) {
		WrapperOperation operation = mock(WrapperOperation.class);
		wrapper.calculate(operation, container);
		verify(operation, times(1)).calculate(wrapper, container);
	}
}
