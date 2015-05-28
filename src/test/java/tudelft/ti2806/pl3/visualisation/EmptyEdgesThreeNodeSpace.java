package tudelft.ti2806.pl3.visualisation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.visualization.FilteredGraphModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Boris Mattijssen on 27-05-15.
 */
public class EmptyEdgesThreeNodeSpace extends EmptyEdgesAbstract {

	@Before
	public void before() throws FileNotFoundException {
		this.loadWrappedGraphData("threeNodeSpace");
	}

	@Test
	public void testOutgoing() {
		assertEquals(1, nodes.get(0).getOutgoing().size());
		assertEquals(1, nodes.get(1).getOutgoing().size());
		assertEquals(0, nodes.get(2).getOutgoing().size());
		assertEquals(nodes.get(1), nodes.get(0).getOutgoing().get(0));
		assertEquals(nodes.get(2), nodes.get(1).getOutgoing().get(0));
	}

	@Test
	public void testIncoming() {
		assertEquals(0, nodes.get(0).getIncoming().size());
		assertEquals(1, nodes.get(1).getIncoming().size());
		assertEquals(1, nodes.get(2).getIncoming().size());
		assertEquals(nodes.get(0), nodes.get(1).getIncoming().get(0));
		assertEquals(nodes.get(1), nodes.get(2).getIncoming().get(0));
	}

}
