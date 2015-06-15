package tudelft.ti2806.pl3.data.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class EdgeTest {
    @Test
    public void equalsTest() {
        DataNode node1 = mock(DataNode.class);
        DataNode node2 = mock(DataNode.class);

        doReturn(0).when(node1).getId();
        doReturn(1).when(node2).getId();

        Edge edge1 = new Edge(node1, node2);
        assertEquals(edge1, edge1);
        assertNotEquals(edge1, null);
        assertNotEquals(edge1, new Object());
        assertEquals(edge1.hashCode(), edge1.hashCode());

        Edge edge2 = new Edge(node1, node2);
        assertEquals(edge1, edge2);
        assertEquals(edge1.hashCode(), edge2.hashCode());
        assertEquals(edge1.toString(), edge2.toString());
        assertEquals(edge1.getName(), edge2.getName());

        Edge edge3 = new Edge(node2, node1);
        assertNotEquals(edge1, edge3);
        Edge edge4 = new Edge(null, null);
        assertNotEquals(edge4, edge1);
        Edge edge5 = new Edge(null, null);
        assertEquals(edge4, edge5);
        assertNotEquals(edge1.hashCode(), edge5.hashCode());
        Edge edge6 = new Edge(null, node1);
        assertNotEquals(edge4, edge6);
        Edge edge7 = new Edge(node1, node1);
        assertNotEquals(edge1, edge7);
    }

    @Test
    public void getterTests() {
        DataNode node1 = mock(DataNode.class);
        DataNode node2 = mock(DataNode.class);

        doReturn(0).when(node1).getId();
        doReturn(1).when(node2).getId();

        Edge edge = new Edge(node1, node2);
        assertTrue(node1 == edge.getFrom());
        assertTrue(node2 == edge.getTo());

        assertEquals("0_1", edge.getName());

        assertEquals(0, edge.getFromId());
        assertEquals(1, edge.getToId());
    }
}
