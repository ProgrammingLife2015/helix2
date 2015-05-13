package tudelft.ti2806.pl3.util;

import java.util.List;

public class HashableList<T> {
	List<T> list;
	
	public HashableList(List<T> list) {
		this.list = list;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		HashableList<T> other = (HashableList<T>) obj;
		if (list == null) {
			if (other.list != null) {
				return false;
			}
		} else if (!list.equals(other.list)) {
			return false;
		}
		return list.containsAll(other.list);
	}
}
