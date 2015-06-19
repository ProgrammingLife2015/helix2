package tudelft.ti2806.pl3.testutil;

import org.junit.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class UtilTest<T> {
	
	private final Class<T> clazz;
	
	public UtilTest(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * Tests if a constructor is private. TODO
	 * 
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public void testConstructorIsPrivate() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		Constructor<T> constructor = clazz.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Assert.assertNotNull(constructor.newInstance());
	}
}
