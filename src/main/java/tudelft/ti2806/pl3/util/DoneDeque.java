package tudelft.ti2806.pl3.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A deque which has a set cap and keeps track of what is already on the deque.
 *
 * @param <T>
 * 		the type of elements to store
 * @author Sam Smulders
 */
public class DoneDeque<T> {

	private Set<T> done;
	private T[] values;
	private int read = 0;
	private int add = 0;

	@SuppressWarnings("unchecked")
	public DoneDeque(int cap) {
		done = new HashSet<T>(cap);
		values = (T[]) new Object[cap];
	}

	/**
	 * Add an element to the deque.
	 *
	 * @param element
	 * 		the element to add
	 */
	public void add(T element) {
		if (!done.contains(element)) {
			done.add(element);
			values[add++] = element;
		}
	}

	/**
	 * Add all elements from the collection, in order of its iterator, to the
	 * tail of the deque.
	 *
	 * @param collection
	 * 		all elements to add
	 */
	public void addAll(Collection<T> collection) {
		for (T element : collection) {
			if (!done.contains(element)) {
				done.add(element);
				values[add++] = element;
			}
		}
	}

	/**
	 * Polls an element from the head.
	 *
	 * @return the element on the head
	 */
	public T poll() {
		return values[read++];
	}

	/**
	 * Checks if all elements in the given collection are already on the deque.
	 *
	 * @param elements
	 * 		all elements to check for
	 * @return {@code true} if all of the given elements are already on the deque
	 * {@code false} if one of the given elements is not on the deque
	 */
	public boolean doneAll(Collection<T> elements) {
		for (T element : elements) {
			if (!done.contains(element)) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty() {
		return add == read;
	}
}
