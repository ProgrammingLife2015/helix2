package tudelft.ti2806.pl3.math;

import org.junit.Assert;
import org.junit.Test;

public class LineTest {
	@Test
	public void getYTest() {
		Line line = new Line(-5, 5, 1f, 0f);
		Assert.assertEquals(5, line.getY(5), 5 - line.getY(5));
		Assert.assertEquals(-10.5f, line.getY(-10.5f),
				-10.5f - line.getY(-10.5f));
		Assert.assertEquals(0, line.getY(0), line.getY(0));
	}
	
	@Test
	public void equalityTest() {
		// Test same object
		Line line1 = new Line(0, 5f, 4, 5f);
		Line line2 = new Line(0, 4, 0f, 5f);
		Assert.assertEquals(line1, line1);
		Assert.assertEquals(line1.hashCode(), line1.hashCode());
		Assert.assertTrue(line1.toString().contains(Line.class.getSimpleName()));
		
		// Test equal line
		Assert.assertTrue(line1.intersect(line2));
		Assert.assertEquals(line1, line2);
		Assert.assertEquals(line1.hashCode(), line2.hashCode());
		Assert.assertEquals(line1.toString(), line2.toString());
		
		// Test non-lines
		Assert.assertNotEquals(line1, null);
		Assert.assertNotEquals(line1, false);
		
		// Test slightly different lines
		Line line3 = new Line(0, 5, 0f, 5f);
		Line line4 = new Line(1, 4, 0f, 5f);
		Line line5 = new Line(0, 4, 0.1f, 5f);
		Line line6 = new Line(0, 4, 0f, 4.9f);
		Assert.assertNotEquals(line1, line3);
		Assert.assertNotEquals(line1, line4);
		Assert.assertNotEquals(line1, line5);
		Assert.assertNotEquals(line1, line6);
	}
	
	@Test
	public void domainTest() {
		Line line = new Line(0, 5, 0f, 5f);
		// isWithingDomain number
		// Off points
		Assert.assertFalse(line.isWithinDomain(0));
		Assert.assertFalse(line.isWithinDomain(5));
		// Out points
		Assert.assertFalse(line.isWithinDomain(6));
		Assert.assertFalse(line.isWithinDomain(-1));
		// In points
		Assert.assertTrue(line.isWithinDomain(0.01f));
		Assert.assertTrue(line.isWithinDomain(4.99f));
		
		// isWithingDomain Line
		// Off points
		Line line2 = new Line(-5, 0, 0f, 5f);
		Assert.assertFalse(line.isWithingDomain(line2));
		Line line3 = new Line(5, 10, 0f, 5f);
		Assert.assertFalse(line.isWithingDomain(line3));
		// Out points
		Assert.assertFalse(line2.isWithingDomain(line3));
		Assert.assertFalse(line3.isWithingDomain(line2));
		// In points
		Assert.assertTrue(line.isWithingDomain(line));
		Line line4 = new Line(0, 5, 0f, 10f);
		Assert.assertTrue(line.isWithingDomain(line4));
	}
	
	@Test
	public void intersectTest() {
		Line line = new Line(0, 5, 0f, 5f);
		
		// Off points
		Line line2 = new Line(-5, 0, 0f, 5f);
		Assert.assertFalse(line.intersect(line2));
		Line line3 = new Line(5, 10, 0f, 5f);
		Assert.assertFalse(line.intersect(line3));
		// Out points
		Assert.assertFalse(line2.intersect(line3));
		Assert.assertFalse(line3.intersect(line2));
		Line line4 = new Line(0, 5, 0f, 10f);
		Assert.assertFalse(line.intersect(line4));
		// In points
		Assert.assertTrue(line.intersect(line));
		
		// Off points TODO: is this indeed an outpoint?
		Line line5 = new Line(-1, 5, 1f, 5f);
		Assert.assertFalse(line.intersect(line5)); // x = 0
		Assert.assertFalse(line5.intersect(line)); // x = 0
		// In points
		Line line6 = new Line(0, 5, 1f, 4f);
		Assert.assertTrue(line.intersect(line6)); // x = 1
		// Assert.assertFalse(line5.intersect(line)); // x = 0
		
	}
}
