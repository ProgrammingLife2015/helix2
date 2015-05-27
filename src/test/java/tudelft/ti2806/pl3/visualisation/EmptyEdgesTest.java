package tudelft.ti2806.pl3.visualisation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.visualization.FilteredGraphModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Boris Mattijssen on 27-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class EmptyEdgesTest {

	@Mock DataNodeWrapper node1;
	@Mock DataNodeWrapper node2;
	@Mock DataNodeWrapper node3;
	@Mock WrappedGraphData wrappedGraphData;

	@Before
	public void before() {
		Genome genome = new Genome("A");

		Set<Genome> set =  new HashSet<>();
		set.add(genome);

		List<Wrapper> outgoing1 = new ArrayList<>(2);
		outgoing1.add(node2);
		outgoing1.add(node3);
		List<Wrapper> outgoing2 = new ArrayList<>(1);
		outgoing2.add(node3);
		List<Wrapper> outgoing3 = new ArrayList<>(0);

		List<Wrapper> incoming1 = new ArrayList<>(0);
		List<Wrapper> incoming2 = new ArrayList<>(1);
		incoming2.add(node1);
		List<Wrapper> incoming3 = new ArrayList<>(2);
		incoming3.add(node1);
		incoming3.add(node2);

		List<Wrapper> wrapperList = new ArrayList<>();
		wrapperList.add(node1);
		wrapperList.add(node2);
		wrapperList.add(node3);
		when(node1.getGenome()).thenReturn(set);
		when(node2.getGenome()).thenReturn(set);
		when(node3.getGenome()).thenReturn(set);
		when(node1.getPreviousNodesCount()).thenReturn(1);
		when(node2.getPreviousNodesCount()).thenReturn(2);
		when(node3.getPreviousNodesCount()).thenReturn(3);
		when(node1.getOutgoing()).thenReturn(outgoing1);
		when(node2.getOutgoing()).thenReturn(outgoing2);
		when(node3.getOutgoing()).thenReturn(outgoing3);
		when(node1.getIncoming()).thenReturn(incoming1);
		when(node2.getIncoming()).thenReturn(incoming2);
		when(node3.getIncoming()).thenReturn(incoming3);
		when(wrappedGraphData.getPositionedNodes()).thenReturn(wrapperList);

		FilteredGraphModel.removeAllEmptyEdges(wrappedGraphData);
	}

	@Test
	public void testOutgoing() {
		assertEquals(1, node1.getOutgoing().size());
		assertEquals(1, node2.getOutgoing().size());
		assertEquals(0, node3.getOutgoing().size());
		assertEquals(node2, node1.getOutgoing().get(0));
		assertEquals(node3, node2.getOutgoing().get(0));
	}

	@Test
	public void testIncoming() {
		assertEquals(0, node1.getIncoming().size());
		assertEquals(1, node2.getIncoming().size());
		assertEquals(1, node3.getIncoming().size());
		assertEquals(node1, node2.getIncoming().get(0));
		assertEquals(node2, node3.getIncoming().get(0));
	}

}
