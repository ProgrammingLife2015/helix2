package tudelft.ti2806.pl3.testutil;

import org.junit.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class UtilTest<T> {
	
	private Class<T> classs;
	
	public UtilTest(Class<T> classs) {
		this.classs = classs;
	}
	
	/**
	 * Tests if a constructor is private. TODO
	 * 
	 * @param class
	 * 
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public void testConstructorIsPrivate() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		Constructor<T> constructor = classs.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}
