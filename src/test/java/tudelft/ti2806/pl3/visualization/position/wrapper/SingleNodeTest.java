package tudelft.ti2806.pl3.visualization.position.wrapper;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.visualization.position.wrapper.NodePosition;

public class SingleNodeTest {
	private static NodePosition s0;
	private static NodePosition s1;
	private static NodePosition s2;
	private static NodePosition s3;
	
	/**
	 * Run before tests.
	 */
	@BeforeClass
	public static void init() {
		s0 = new NodePosition(new DataNode(0, new Genome[0], 0, 0, new byte[5]));
		s1 = new NodePosition(
				new DataNode(1, new Genome[0], 0, 0, new byte[10]));
		s2 = new NodePosition(
				new DataNode(2, new Genome[0], 0, 0, new byte[20]));
		s3 = new NodePosition(new DataNode(3, new Genome[0], 0, 0, new byte[2]));
		
		s0.getOutgoing().add(s1);
		s1.getIncoming().add(s0);
		
		s0.getOutgoing().add(s2);
		s2.getIncoming().add(s0);
		
		s0.getOutgoing().add(s3);
		s3.getIncoming().add(s0);
		
		s1.getOutgoing().add(s2);
		s2.getIncoming().add(s1);
		
		s3.getOutgoing().add(s2);
		s2.getIncoming().add(s3);
		
		s2.calculateStartX();
	}
	
	@Test
	public void calculateStartXTest() {
		assertTrue(s0.getXStart() == 0);
		assertTrue(s1.getXStart() == 5);
		assertTrue(s2.getXStart() == 15);
		assertTrue(s3.getXStart() == 5);
	}
	
	@Test
	public void calculateWhitespaceOnRightSideTest() {
		assertTrue(s0.calculateWhitespaceOnRightSide() == 0);
		assertTrue(s1.calculateWhitespaceOnRightSide() == 0);
		assertTrue(s2.calculateWhitespaceOnRightSide() == Long.MAX_VALUE
				- s2.getXEnd());
		assertTrue(s3.calculateWhitespaceOnRightSide() == 8);
	}
}
