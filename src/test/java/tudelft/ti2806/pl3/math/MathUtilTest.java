package tudelft.ti2806.pl3.math;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilTest {
	@Test
	public void factorialTest() {
		int actual = MathUtil.integerFactorial(0);
		Assert.assertEquals(1, actual, actual - 1);
		actual = MathUtil.integerFactorial(-1);
		Assert.assertEquals(-1, actual, actual + 1);
		actual = MathUtil.integerFactorial(1);
		Assert.assertEquals(1, actual, actual - 1);
		actual = MathUtil.integerFactorial(3);
		Assert.assertEquals(6, actual, actual - 6);
		actual = MathUtil.integerFactorial(4);
		Assert.assertEquals(24, actual, actual - 24);
		actual = MathUtil.integerFactorial(12);
		Assert.assertEquals(479001600, actual, actual - 479001600);
		actual = MathUtil.integerFactorial(13);
		Assert.assertEquals(Integer.MAX_VALUE, actual, actual
				- Integer.MAX_VALUE);
	}
}
