package tudelft.ti2806.pl3.util;

import java.util.LinkedList;

/**
 * LastOpenedStack is a extension on LinkedList that only can hold a limited amount of elements.
 * Created by Kasper on 11-6-2015.
 */
public class LastOpenedStack<E> extends LinkedList<E> {
	private int limit;

	public LastOpenedStack(int limit) {
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
		if (this.contains(o)) {
			this.remove(o);
		}

		if (this.size() < limit) {
			this.addFirst(o);
		} else {
			this.removeLast();
			this.addFirst(o);
		}

		return true;
	}
}
