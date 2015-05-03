package tudelft.ti2806.pl3.data.graph;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;

public class SingleNodeTest {
	private static SingleNode s0;
	private static SingleNode s1;
	private static SingleNode s2;
	private static SingleNode s3;
	
	@BeforeClass
	public static void init() {
		s0 = new SingleNode(0, new Genome[0], 0, 0, new byte[5]);
		s1 = new SingleNode(1, new Genome[0], 0, 0, new byte[10]);
		s2 = new SingleNode(2, new Genome[0], 0, 0, new byte[20]);
		s3 = new SingleNode(3, new Genome[0], 0, 0, new byte[2]);
		
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
		assertTrue(s0.getXaxisStart() == 0);
		assertTrue(s1.getXaxisStart() == 6);
		assertTrue(s2.getXaxisStart() == 17);
		assertTrue(s3.getXaxisStart() == 6);
	}
	
	@Test
	public void calculateWhitespaceOnRightSideTest() {
		assertTrue(s0.calculateWhitespaceOnRightSide() == 1);
		assertTrue(s1.calculateWhitespaceOnRightSide() == 1);
		assertTrue(s2.calculateWhitespaceOnRightSide() == Long.MAX_VALUE
				- s2.getXEnd());
		assertTrue(s3.calculateWhitespaceOnRightSide() == 9);
	}
}
