package tudelft.ti2806.pl3.util;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Boris Mattijssen on 27-05-15.
 */
public class EmptyEdgesFourNodeSpace extends EmptyEdgesAbstract {

	@Before
	public void before() throws FileNotFoundException {
		this.loadWrappedGraphData("fourNodeSpace");
	}

	@Test
	public void testOutgoing() {
		assertEquals(1, nodes.get(0).getOutgoing().size());
		assertEquals(1, nodes.get(1).getOutgoing().size());
		assertEquals(1, nodes.get(2).getOutgoing().size());
		assertEquals(0, nodes.get(3).getOutgoing().size());
		assertEquals(nodes.get(1), nodes.get(0).getOutgoing().get(0));
		assertEquals(nodes.get(2), nodes.get(1).getOutgoing().get(0));
		assertEquals(nodes.get(3), nodes.get(2).getOutgoing().get(0));
	}

	@Test
	public void testIncoming() {
		assertEquals(0, nodes.get(0).getIncoming().size());
		assertEquals(1, nodes.get(1).getIncoming().size());
		assertEquals(1, nodes.get(2).getIncoming().size());
		assertEquals(1, nodes.get(3).getIncoming().size());
		assertEquals(nodes.get(0), nodes.get(1).getIncoming().get(0));
		assertEquals(nodes.get(1), nodes.get(2).getIncoming().get(0));
		assertEquals(nodes.get(2), nodes.get(3).getIncoming().get(0));
	}

}
