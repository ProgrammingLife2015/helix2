package tudelft.ti2806.pl3.visualisation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test for the GraphController
 * Created by Kasper on 17-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphControllerTest {

	@Mock
	GraphDataRepository graphDataRepository;

	GraphController graphController;

	@Test
	public void testParseGraph() throws IOException {

		graphController = new GraphController(graphDataRepository);
		File nodefile = new File("data/testdata/LastOpenedTest/test.node.graph");
		File edgefile = new File("data/testdata/LastOpenedTest/test.edge.graph");

		graphController.parseGraph(nodefile,edgefile);
		verify(graphDataRepository, times(1)).parseGraph(nodefile, edgefile, graphController.getGeneData());
	}
}
