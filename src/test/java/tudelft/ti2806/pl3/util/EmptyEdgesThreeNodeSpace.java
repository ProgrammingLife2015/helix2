package tudelft.ti2806.pl3.util;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Boris Mattijssen on 27-05-15.
 */
public class EmptyEdgesThreeNodeSpace extends EmptyEdgesAbstract {

    @Before
    public void before() throws IOException {
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
