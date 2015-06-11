package tudelft.ti2806.pl3.util;

import java.util.LinkedList;

/**
 * LastOpenedQueue is a extension on LinkedList that only can hold a limited amount of elements
 * Created by Kasper on 11-6-2015.
 */
public class LastOpenedQueue<E> extends LinkedList<E> {
	private int limit;

	public LastOpenedQueue(int limit) {
		super();
		this.limit = limit;
	}

	@Override
	public boolean add(E o) {
		super.add(o);
		if (this.size() > limit) {
			super.removeFirst();
		}
		return true;
	}
}
