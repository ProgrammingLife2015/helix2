package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.util.observable.Observable;
import tudelft.ti2806.pl3.util.observers.Observer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * LastOpenedStack is a extension on LinkedList that only can hold a limited amount of elements.
 * Created by Kasper on 11-6-2015.
 */
public class LastOpenedStack<E> extends LinkedList<E> implements Observable {
	private final int limit;
	private final ArrayList<Observer> observers = new ArrayList<>();

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
		notifyObservers();

		return true;
	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void addObserversList(ArrayList<Observer> observer) {
		observers.addAll(observer);
	}

	@Override
	public void deleteObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
}
