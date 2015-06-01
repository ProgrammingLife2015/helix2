package tudelft.ti2806.pl3.data.wrapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for non abstract {@link DataNodeWrapper} methods.
 * 
 * @author Sam Smulders
 */
public class DataNodeWrapperTest {
	@Test
	public void getterTests() {
		Set<Genome> genomeSet = new HashSet<>();
		DataNode node = new DataNode(0, genomeSet, 0, 1, new byte[] { 0 }, null);
		
		DataNodeWrapper wrapper = new DataNodeWrapper(node);
		assertEquals(node, wrapper.getNode());
		assertEquals(node.getBasePairCount(), wrapper.getBasePairCount());
		assertEquals(Integer.toString(node.getId()), wrapper.getIdString());
		assertEquals(node.getCurrentGenomeSet(), wrapper.getGenome());
		List<DataNode> dataNodeList = new ArrayList<>();
		wrapper.collectDataNodes(dataNodeList);
		assertEquals(1, dataNodeList.size());
		assertEquals(node, dataNodeList.get(0));
		wrapper.previousNodesCount = 1;
		wrapper.calculateX();
		assertEquals(1, wrapper.getX(), 0);
		assertEquals(1, wrapper.getWidth(), 0);
	}
	
	@Test
	public void operationTest() {
		operationTest(new DataNodeWrapper(null), null);
	}
	
	private void operationTest(DataNodeWrapper wrapper, Wrapper container) {
		WrapperOperation operation = mock(WrapperOperation.class);
		wrapper.calculate(operation, container);
		verify(operation, times(1)).calculate(wrapper, container);
	}
}
