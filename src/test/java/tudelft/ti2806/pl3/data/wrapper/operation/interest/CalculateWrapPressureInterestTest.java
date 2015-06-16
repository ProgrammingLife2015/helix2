package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Test class to verify that the wrap pressure is correctly calculated.
 *
 * @see {@link CalculateWrapPressureInterest} Created by Boris Mattijssen on
 *      20-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculateWrapPressureInterestTest {
	
	private final float nodeWrapper1Interest = 5f;
	private final float nodeWrapper2Interest = 55f;
	
	@Mock
	Wrapper nodeWrapper1;
	@Mock
	Wrapper nodeWrapper2;
	@Mock
	Genome genome;
	
	private List<Wrapper> nodeWrapperList;
	
	private CalculateWrapPressureInterest calculateWrapPressureInterest;
	
	/**
	 * Runs before each test.
	 */
	@Before
	public void before() {
		Set<Genome> genomeList = new HashSet<>();
		genomeList.add(genome);
		when(nodeWrapper1.getInterest()).thenReturn(nodeWrapper1Interest);
		when(nodeWrapper2.getInterest()).thenReturn(nodeWrapper2Interest);
		when(nodeWrapper1.getGenome()).thenReturn(genomeList);
		when(nodeWrapper2.getGenome()).thenReturn(genomeList);
		
		nodeWrapperList = new ArrayList<>();
		nodeWrapperList.add(nodeWrapper1);
		nodeWrapperList.add(nodeWrapper2);
		
		calculateWrapPressureInterest = new CalculateWrapPressureInterest();
	}
	
	@Test
	public void testSpaceWrapper() {
		SpaceWrapper spaceWrapper = mock(SpaceWrapper.class);
		when(spaceWrapper.getNodeList()).thenReturn(nodeWrapperList);
		calculateWrapPressureInterest.calculate(spaceWrapper, nodeWrapper1);
		verify(spaceWrapper).addInterest(1);
	}
	
	@Test
	public void testVerticalWrapper() {
		VerticalWrapper verticalWrapper = mock(VerticalWrapper.class);
		when(verticalWrapper.getNodeList()).thenReturn(nodeWrapperList);
		calculateWrapPressureInterest.calculate(verticalWrapper, nodeWrapper1);
		verify(verticalWrapper).addInterest(1);
	}
	
}
