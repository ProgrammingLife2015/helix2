package tudelft.ti2806.pl3.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DoneDeque<T> {
	
	Set<T> done;
	T[] values;
	int read = 0;
	int add = 0;
	
	@SuppressWarnings("unchecked")
	public DoneDeque(int cap) {
		done = new HashSet<T>(cap);
		values = (T[]) new Object[cap];
	}
	
	public void add(T element) {
		values[add++] = element;
	}
	
	public void addAll(Collection<T> collection) {
		for (T element : collection) {
			values[add++] = element;
		}
	}
	
	public T poll() {
		done.add(values[read]);
		return values[read++];
	}
	
	public boolean doneAll(Collection<T> elements) {
		for (T element : elements) {
			if (!done.contains(element)) {
				return false;
			}
		}
		return true;
	}
}
