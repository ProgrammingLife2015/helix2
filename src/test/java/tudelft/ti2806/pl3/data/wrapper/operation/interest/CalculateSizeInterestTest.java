package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.interest.CalculateSizeInterest;

/**
 * Test for CalculateSizeInterest.
 * Created by Kasper on 21-5-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculateSizeInterestTest {

	@Mock
	Wrapper parent;
	private final long wrapperSize = 100;
	private final int wrapperInterest = 10;

	private CalculateSizeInterest calculateSizeInterest;

	@Before
	public void before() {
		calculateSizeInterest = new CalculateSizeInterest();
	}

	@Test
	public void testHorizontalWrapper() {
		HorizontalWrapper horizontalWrapper = mock(HorizontalWrapper.class);
		when(horizontalWrapper.getWidth()).thenReturn(wrapperSize);
		calculateSizeInterest.calculate(horizontalWrapper, parent);
		verify(horizontalWrapper).addInterest(wrapperInterest);
	}

	@Test
	public void testVerticalWrapper() {
		VerticalWrapper verticalWrapper = mock(VerticalWrapper.class);
		when(verticalWrapper.getWidth()).thenReturn(wrapperSize);
		calculateSizeInterest.calculate(verticalWrapper, parent);
		verify(verticalWrapper).addInterest(wrapperInterest);
	}

	@Test
	public void testSpaceWrapper() {
		SpaceWrapper spaceWrapper = mock(SpaceWrapper.class);
		when(spaceWrapper.getWidth()).thenReturn(wrapperSize);
		calculateSizeInterest.calculate(spaceWrapper, parent);
		verify(spaceWrapper).addInterest(wrapperInterest);
	}

	@Test
	public void testSingleWrapper() {
		SingleWrapper singleWrapper = mock(SingleWrapper.class);
		when(singleWrapper.getWidth()).thenReturn(wrapperSize);
		when(singleWrapper.getNode()).thenReturn(mock(Wrapper.class));
		calculateSizeInterest.calculate(singleWrapper, parent);
		verify(singleWrapper).addInterest(wrapperInterest);
	}

	@Test
	public void testNodePosition() {
		DataNodeWrapper nodePosition = mock(DataNodeWrapper.class);
		when(nodePosition.getWidth()).thenReturn(wrapperSize);
		calculateSizeInterest.calculate(nodePosition, parent);
		verify(nodePosition).addInterest(wrapperInterest);
	}
}