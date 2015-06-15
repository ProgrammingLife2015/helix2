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
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

/**
 * Test for CalculateSizeInterest. Created by Kasper on 21-5-2015.
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
    public void testNodePosition() {
        DataNodeWrapper nodePosition = mock(DataNodeWrapper.class);
        when(nodePosition.getBasePairCount()).thenReturn(wrapperSize);
        calculateSizeInterest.calculate(nodePosition, parent);
        verify(nodePosition).multiplyInterest(wrapperInterest);
    }
}
