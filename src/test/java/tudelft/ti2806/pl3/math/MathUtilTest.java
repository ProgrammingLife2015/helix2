package tudelft.ti2806.pl3.math;

import org.junit.Assert;
import org.junit.Test;

import tudelft.ti2806.pl3.testutil.UtilTest;

import java.lang.reflect.InvocationTargetException;

public class MathUtilTest {
	@Test
	public void factorialTest() {
		Assert.assertEquals(1, MathUtil.integerFactorial(0), 0);
		Assert.assertEquals(-1, MathUtil.integerFactorial(-1), 0);
		Assert.assertEquals(1, MathUtil.integerFactorial(1), 0);
		Assert.assertEquals(6, MathUtil.integerFactorial(3), 0);
		Assert.assertEquals(24, MathUtil.integerFactorial(4), 0);
		Assert.assertEquals(479001600, MathUtil.integerFactorial(12), 0);
		Assert.assertEquals(Integer.MAX_VALUE, MathUtil.integerFactorial(13), 0);
	}
	
	@Test
	public void privateConstructorTest() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		new UtilTest<MathUtil>(MathUtil.class).testConstructorIsPrivate();
	}
}
