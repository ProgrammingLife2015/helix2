package tudelft.ti2806.pl3.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.util.observers.Observer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for LastOpenedStack
 * Created by Kasper on 15-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class LastOpenedStackTest {

	private LastOpenedStack<Integer> stack;
	private int limit;
	@Mock
	Observer observer;

	@Before
	public void setUp() {
		limit = 5;
		stack = new LastOpenedStack<>(limit);
		stack.addObserver(observer);
	}

	@Test
	public void testAddWithoutDuplicateWithoutLimit() {
		for (int i = 1; i <= 3; i++) {
			stack.add(i);
		}
		assertTrue(stack.size() == 3);
		assertEquals(new Integer(3), stack.removeFirst());
		assertTrue(stack.size() == 2);
		assertEquals(new Integer(2), stack.removeFirst());
		assertTrue(stack.size() == 1);
		assertEquals(new Integer(1), stack.removeFirst());
		assertTrue(stack.isEmpty());
	}

	@Test
	public void testAddWithoutDuplicateWithLimit(){
		for (int i = 0; i < limit; i++) {
			stack.add(i);
		}
		stack.add(5);

		assertTrue(stack.size() == 5);

		for (int i = 5; i >= 1; i--) {
			assertEquals(new Integer(i), stack.removeFirst());
		}
		assertTrue(stack.isEmpty());

	}

	@Test
	public void testAddWithDuplicateWithoutLimit() {
		stack.add(1);
		stack.add(2);
		stack.add(2);

		assertTrue(stack.size() == 2);
		assertEquals(new Integer(2), stack.removeFirst());
		assertEquals(new Integer(1), stack.removeFirst());
		assertTrue(stack.isEmpty());

		stack.add(1);
		stack.add(2);
		stack.add(1);

		assertTrue(stack.size() == 2);
		assertEquals(new Integer(1), stack.removeFirst());
		assertEquals(new Integer(2), stack.removeFirst());
		assertTrue(stack.isEmpty());
	}
	@Test
	public void testAddWithDuplicateWithLimit(){
		for (int i = 0; i < limit; i++) {
			stack.add(i);
		}
		stack.add(2);

		assertTrue(stack.size() == 5);

		assertEquals(new Integer(2), stack.removeFirst());
		assertEquals(new Integer(4), stack.removeFirst());
		assertEquals(new Integer(3), stack.removeFirst());
		assertEquals(new Integer(1), stack.removeFirst());
		assertEquals(new Integer(0), stack.removeFirst());

		assertTrue(stack.isEmpty());
	}

	@After
	public void tearDown() {
		stack = null;
		limit = 0;
	}
}