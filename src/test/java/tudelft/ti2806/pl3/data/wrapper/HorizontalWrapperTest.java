package tudelft.ti2806.pl3.data.wrapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the {@link HorizontalWrapper}.
 *
 * @author Sam Smulders
 */
public class HorizontalWrapperTest {
    @Test
    public void getterTests() {
        Wrapper wrapper1 = new TestWrapper(1, 1);
        Wrapper wrapper2 = new TestWrapper(2, 2);
        Wrapper wrapper3 = new TestWrapper(3, 4);
        List<Wrapper> wrapperList = new ArrayList<>();
        wrapperList.add(wrapper1);
        wrapperList.add(wrapper2);
        wrapperList.add(wrapper3);
        HorizontalWrapper wrapper = new HorizontalWrapper(wrapperList, true);
        assertEquals(7, wrapper.getBasePairCount(), 0);
        assertEquals(wrapper1.getGenome(), wrapper.getGenome());
        assertEquals('H', wrapper.getIdString().charAt(0));
        assertEquals(3, wrapper.getWidth(), 0);
    }

    @Test
    public void operationTest() {
        operationTest(new HorizontalWrapper(null, true), null);
    }

    private void operationTest(HorizontalWrapper wrapper, Wrapper container) {
        WrapperOperation operation = mock(WrapperOperation.class);
        wrapper.calculate(operation, container);
        verify(operation, times(1)).calculate(wrapper, container);
    }
}
