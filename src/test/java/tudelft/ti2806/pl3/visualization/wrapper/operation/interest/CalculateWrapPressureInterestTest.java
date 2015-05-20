package tudelft.ti2806.pl3.visualization.wrapper.operation.interest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.visualization.wrapper.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Test class to verify that the wrap pressure is correctly calculated.
 *
 * @see {@link CalculateWrapPressureInterest}
 * Created by Boris Mattijssen on 20-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculateWrapPressureInterestTest {

	private final int pressureMultiplier = 10;
	private final int nodeWrapper1Interest = 5;
	private final int nodeWrapper2Interest = 55;

	@Mock
	NodeWrapper nodeWrapper1;
	@Mock
	NodeWrapper nodeWrapper2;

	private List<NodeWrapper> nodeWrapperList;

	private CalculateWrapPressureInterest calculateWrapPressureInterest;


	@Before
	public void before() {
		when(nodeWrapper1.getInterest()).thenReturn(nodeWrapper1Interest);
		when(nodeWrapper2.getInterest()).thenReturn(nodeWrapper2Interest);
		nodeWrapperList = new ArrayList<>();
		nodeWrapperList.add(nodeWrapper1);
		nodeWrapperList.add(nodeWrapper2);

		calculateWrapPressureInterest = new CalculateWrapPressureInterest(pressureMultiplier);
	}

	@Test
	public void testSpaceWrapper() {
		SpaceWrapper spaceWrapper = mock(SpaceWrapper.class);
		when(spaceWrapper.getNodeList()).thenReturn(nodeWrapperList);
		calculateWrapPressureInterest.calculate(spaceWrapper, nodeWrapper1);
		verify(spaceWrapper).addInterest(pressureMultiplier * nodeWrapper1Interest * nodeWrapper2Interest);
	}

	@Test
	public void testVerticalWrapper() {
		VerticalWrapper verticalWrapper = mock(VerticalWrapper.class);
		when(verticalWrapper.getNodeList()).thenReturn(nodeWrapperList);
		calculateWrapPressureInterest.calculate(verticalWrapper, nodeWrapper1);
		verify(verticalWrapper).addInterest(pressureMultiplier * nodeWrapper1Interest * nodeWrapper2Interest);
	}

	@Test
	public void testSingleWrapper() {
		SingleWrapper singleWrapper = mock(SingleWrapper.class);
		when(singleWrapper.getNode()).thenReturn(nodeWrapper1);
		calculateWrapPressureInterest.calculate(singleWrapper, nodeWrapper1);
		verify(singleWrapper).addInterest(nodeWrapper1Interest);
	}

}
