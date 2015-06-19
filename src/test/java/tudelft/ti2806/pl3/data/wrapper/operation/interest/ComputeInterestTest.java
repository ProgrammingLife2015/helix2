package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import org.junit.Test;

import tudelft.ti2806.pl3.testutil.UtilTest;

import java.lang.reflect.InvocationTargetException;

public class ComputeInterestTest {
	@Test
	public void utilClassTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			InstantiationException {
		new UtilTest<>(ComputeInterest.class).testConstructorIsPrivate();
	}
	
}
