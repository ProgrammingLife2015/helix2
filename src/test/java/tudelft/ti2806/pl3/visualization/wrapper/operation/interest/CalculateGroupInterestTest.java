package tudelft.ti2806.pl3.visualization.wrapper.operation.interest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for group interest
 * Created by Kasper on 21-5-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculateGroupInterestTest {

	private CalculateGroupInterest groupInterest;
	private int group1_interest = 10;

	private Set<Genome> interestingSet;
	private Set<Genome> notInterestingSet;


	@Mock
	Genome genome1;
	@Mock
	Genome genome2;
	@Mock
	Genome genome3;
	@Mock
	Genome genome4;
	@Mock
	NodeWrapper parent;

	@Before
	public void before() {
		interestingSet = new HashSet<>();
		interestingSet.add(genome2);
		interestingSet.add(genome1);

		notInterestingSet = new HashSet<>();
		notInterestingSet.add(genome3);
		notInterestingSet.add(genome4);

		List<Set<Genome>> genomeList = new ArrayList<>();
		genomeList.add(interestingSet);
		genomeList.add(interestingSet);
		groupInterest = new CalculateGroupInterest(genomeList, group1_interest);
	}

	@Test
	public void testNotInterestingHorizontalWrapper() {
		HorizontalWrapper horizontalWrapper = mock(HorizontalWrapper.class);
		when(horizontalWrapper.getGenome()).thenReturn(notInterestingSet);
		groupInterest.calculate(horizontalWrapper, parent);
		verify(horizontalWrapper, times(0)).addInterest(group1_interest);
	}
	@Test
	public void testInterestingHorizontalWrapper() {
		HorizontalWrapper horizontalWrapper = mock(HorizontalWrapper.class);
		when(horizontalWrapper.getGenome()).thenReturn(interestingSet);
		groupInterest.calculate(horizontalWrapper, parent);
		verify(horizontalWrapper, times(1)).addInterest(group1_interest);
	}
	@Test
	public void testNotInterestingVerticalWrapper() {
		VerticalWrapper verticalWrapper = mock(VerticalWrapper.class);
		when(verticalWrapper.getGenome()).thenReturn(notInterestingSet);
		groupInterest.calculate(verticalWrapper, parent);
		verify(verticalWrapper, times(0)).addInterest(group1_interest);
	}
	@Test
	public void testInterestingVerticalWrapper() {
		VerticalWrapper verticalWrapper = mock(VerticalWrapper.class);
		when(verticalWrapper.getGenome()).thenReturn(interestingSet);
		groupInterest.calculate(verticalWrapper, parent);
		verify(verticalWrapper, times(1)).addInterest(group1_interest);
	}
	@Test
	public void testNotInterestingSpaceWrapper() {
		SpaceWrapper spaceWrapper = mock(SpaceWrapper.class);
		when(spaceWrapper.getGenome()).thenReturn(notInterestingSet);
		groupInterest.calculate(spaceWrapper, parent);
		verify(spaceWrapper, times(0)).addInterest(group1_interest);
	}
	@Test
	public void testInterestingSpaceWrapper() {
		SpaceWrapper spaceWrapper = mock(SpaceWrapper.class);
		when(spaceWrapper.getGenome()).thenReturn(interestingSet);
		groupInterest.calculate(spaceWrapper, parent);
		verify(spaceWrapper, times(1)).addInterest(group1_interest);
	}
	@Test
	public void testNotInterestingSingleWrapper() {
		SingleWrapper singleWrapper = mock(SingleWrapper.class);
		when(singleWrapper.getNode()).thenReturn(mock(NodeWrapper.class));
		when(singleWrapper.getGenome()).thenReturn(notInterestingSet);
		groupInterest.calculate(singleWrapper, parent);
		verify(singleWrapper, times(0)).addInterest(group1_interest);
	}
	@Test
	public void testInterestingSingleWrapper() {
		SingleWrapper singleWrapper = mock(SingleWrapper.class);
		when(singleWrapper.getNode()).thenReturn(mock(NodeWrapper.class));
		when(singleWrapper.getGenome()).thenReturn(interestingSet);
		groupInterest.calculate(singleWrapper, parent);
		verify(singleWrapper, times(1)).addInterest(group1_interest);
	}
}