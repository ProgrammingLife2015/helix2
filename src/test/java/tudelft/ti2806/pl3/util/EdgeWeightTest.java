package tudelft.ti2806.pl3.util;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tudelft.ti2806.pl3.data.gene.GeneData;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.unwrap.Unwrap;
import tudelft.ti2806.pl3.data.wrapper.operation.unwrap.UnwrapTest;
import tudelft.ti2806.pl3.data.wrapper.util.WrapUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by Boris Mattijssen on 09-06-15.
 */
public class EdgeWeightTest {

	private Wrapper[] wrappers;

	/**
	 * Constructs a graph from the test file and calculates the edge weight on the edges.
	 * Then it puts all nodes in an array to make them easier to test.
	 *
	 * @throws IOException
	 * 		When something went wrong in the parser.
	 */
	@Before
	public void before() throws IOException {
		File nodesFile = new File("data/testdata/edgeWeightTest.node.graph");
		File edgesFile = new File("data/testdata/edgeWeightTest.edge.graph");
		GeneData geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");
		GraphDataRepository graphDataRepository = new GraphDataRepository();
		graphDataRepository.parseGraph(nodesFile, edgesFile, geneData);
		WrappedGraphData wrappedGraphData = new WrappedGraphData(graphDataRepository);
		Wrapper collapsedNode = WrapUtil.collapseGraph(wrappedGraphData)
				.getPositionedNodes().get(0);
		new CanUnwrapOperation().calculate(collapsedNode, null);
		Unwrap unwrap = new UnwrapTest();
		unwrap.compute(collapsedNode);
		EdgeUtil.setEdgeWeight(unwrap.getWrapperClones());
		wrappers = new Wrapper[unwrap.getWrapperClones().size()];
		wrappers[0] = unwrap.getResult();
		wrappers[1] = wrappers[0].getOutgoing().get(0);
		wrappers[2] = wrappers[1].getOutgoing().get(0);
		wrappers[3] = wrappers[1].getOutgoing().get(1);
		wrappers[4] = wrappers[3].getOutgoing().get(0);
		wrappers[5] = wrappers[3].getOutgoing().get(1);
		wrappers[6] = wrappers[3].getOutgoing().get(2);
		wrappers[7] = wrappers[4].getOutgoing().get(0);
	}

	/**
	 * Test whether all weights are correct.
	 */
	@Test
	public void testWeight() {
		assertTrue(wrappers[0].getOutgoingWeight().get(0).equals(3));
		assertTrue(wrappers[1].getOutgoingWeight().get(0).equals(2));
		assertTrue(wrappers[1].getOutgoingWeight().get(1).equals(1));
		assertTrue(wrappers[2].getOutgoingWeight().get(0).equals(2));
		assertTrue(wrappers[3].getOutgoingWeight().get(0).equals(1));
		assertTrue(wrappers[3].getOutgoingWeight().get(1).equals(1));
		assertTrue(wrappers[3].getOutgoingWeight().get(2).equals(1));
		assertTrue(wrappers[4].getOutgoingWeight().get(0).equals(1));
		assertTrue(wrappers[5].getOutgoingWeight().get(0).equals(1));
		assertTrue(wrappers[6].getOutgoingWeight().get(0).equals(1));
	}

}
