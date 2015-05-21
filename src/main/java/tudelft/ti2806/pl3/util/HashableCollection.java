package tudelft.ti2806.pl3.util;

import java.util.Collection;

public class HashableCollection<T> {
	Collection<T> list;
	
	public HashableCollection(Collection<T> list) {
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
		HashableCollection<T> other = (HashableCollection<T>) obj;
		if (list == null) {
			if (other.list == null) {
				return true;
			} else {
				return false;
			}
		}else{
			if( other.list == null){
				return false;
			}
		}
		if (list.size() != other.list.size()) {
			return false;
		}
		return list.containsAll(other.list);
	}
}
