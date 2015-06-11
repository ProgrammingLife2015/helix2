package tudelft.ti2806.pl3.util;

import java.util.LinkedList;

/**
 * LastOpenedQueue is a extension on LinkedList that only can hold a limited amount of elements.
 * Created by Kasper on 11-6-2015.
 */
public class LastOpenedQueue<E> extends LinkedList<E> {
	private int limit;

	public LastOpenedQueue(int limit) {
		super();
		this.limit = limit;
	}

	/**
	 * Adds element to the queue if its not already in it.
	 * Only limit elements will be in the queue.
	 *
	 * @param o
	 * 		object to add
	 * @return true if added.
	 */
	@Override
	public boolean add(E o) {
		if (!this.contains(o)) {
			super.add(o);
		}
		if (this.size() > limit) {
			super.removeFirst();
		}
		return true;
	}
}
