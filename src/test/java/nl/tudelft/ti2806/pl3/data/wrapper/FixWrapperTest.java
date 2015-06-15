package nl.tudelft.ti2806.pl3.data.wrapper;

import nl.tudelft.ti2806.pl3.data.Genome;
import nl.tudelft.ti2806.pl3.data.graph.DataNode;
import nl.tudelft.ti2806.pl3.data.wrapper.FixWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FixWrapperTest {
    @Test
    public void getTests() {
        FixWrapper fixWrapper = new FixWrapper();
        assertEquals(0, fixWrapper.getBasePairCount());
        assertEquals(0, fixWrapper.getWidth());
        assertEquals(FixWrapper.ID_STRING, fixWrapper.getIdString());
        assertNull(fixWrapper.getGenome());
        Set<Genome> genome = new HashSet<Genome>();
        genome.add(new Genome(""));
        fixWrapper.setGenome(genome);
        assertEquals(genome, fixWrapper.getGenome());
        fixWrapper.calculateX();
        assertEquals(fixWrapper.previousNodesCount, fixWrapper.getX(), 0);
    }

    @Mock
    Set<DataNode> list;

    @Test
    public void collectDataNodesTest() {
        FixWrapper wrapper = new FixWrapper();
        wrapper.collectDataNodes(list);
        Mockito.verifyZeroInteractions(list);
    }

    @Test
    public void operationTest() {
        operationTest(new FixWrapper(), null);
    }

    private void operationTest(FixWrapper wrapper, Wrapper container) {
        WrapperOperation operation = mock(WrapperOperation.class);
        wrapper.calculate(operation, container);
        verify(operation, times(1)).calculate(wrapper, container);
    }
}
