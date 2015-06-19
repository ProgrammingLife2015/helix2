package tudelft.ti2806.pl3.data.graph;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathieu Post on 19-5-15.
 */
public class GraphDataTest {
	private GraphData graphData;
	private AbstractGraphData abstractGraphData;
	private final List<DataNode> nodeList = new ArrayList<>();
	private final List<Edge> edgeList = new ArrayList<>();
	private final List<Genome> genomeList = new ArrayList<>();
	
	/**
	 * Runs before each test.
	 * 
	 * @throws Exception
	 *             if an exception is thrown
	 */
	@Before
	public void setUp() {
		abstractGraphData = mock(AbstractGraphData.class);
		when(abstractGraphData.getNodeListClone()).thenReturn(nodeList);
		when(abstractGraphData.getEdgeListClone()).thenReturn(edgeList);
		when(abstractGraphData.getGenomeClone()).thenReturn(genomeList);
		graphData = new GraphData(abstractGraphData);
	}
	
	@Test
	public void testGetNodes() throws Exception {
		assertEquals(nodeList, graphData.getNodes());
	}
	
	@Test
	public void testGetEdges() throws Exception {
		assertEquals(edgeList, graphData.getEdges());
	}
	
	@Test
	public void testGetGenomes() throws Exception {
		assertEquals(genomeList, graphData.getGenomes());
	}
	
	@Test
	public void testGetOrigin() throws Exception {
		assertEquals(abstractGraphData, graphData.getOrigin());
	}
}
