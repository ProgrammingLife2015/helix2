//package tudelft.ti2806.pl3.data;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Mathieu Post on 30-4-15.
// */
//public class CNodeTest {
//    static CNode cNode;
//    static SNode node1;
//    static SNode node2;
//    static SNode node3;
//
//    @Before
//    public static void setup() throws Exception {
//        System.out.println("uhh hallo");
//        node1 = new SNode(35, new String[]{"TKK-01-0029"}, 2609451, 2609452, new Gene[]{Gene.A});
//        node2 = new SNode(4, new String[]{"TKK-01-0029"}, 2609452, 2609453, new Gene[]{Gene.C});
//        node3 = new SNode(57, new String[]{"TKK-01-0029"}, 2609453, 2609454, new Gene[]{Gene.G});
//
//        Edge edge1 = new Edge(node1, node2);
//        Edge edge2 = new Edge(node2, node3);
//
//        List<Edge> edgeList = new ArrayList<>(2);
//        edgeList.add(edge1);
//        edgeList.add(edge2);
//
//        cNode = new CNode(edgeList);
//    }
//
//    @Test
//    public void testGetNodeId() {
//        assertEquals(cNode.getNodeId(), node1.getNodeId());
//    }
//
//    @Test
//    public void testGetSource() {
//        assertEquals(cNode.getSource(), node1.getSource());
//    }
//
//    @Test
//    public void testGetRefStartPoint() {
//        assertEquals(cNode.getRefStartPoint(), node1.getRefStartPoint());
//    }
//
//    @Test
//    public void testGetRefEndPoint() {
//        assertNotNull(cNode);
//        assertNotNull(node3);
//        assertEquals(cNode.getRefEndPoint(), node3.getRefEndPoint());
//    }
//
//    @Test
//    public void testGetContent() throws Exception {
//        assertEquals(cNode.getContent(), new Gene[]{Gene.A, Gene.C, Gene.G});
//    }
//}