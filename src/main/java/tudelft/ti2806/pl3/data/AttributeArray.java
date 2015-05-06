package tudelft.ti2806.pl3.data;

import java.util.Arrays;

public class AttributeArray<T> {
	public AttributeArray(T[] array) {
		this.array = array;
	}
	
	public T[] array;
	
	@SuppressWarnings("unchecked")
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
		AttributeArray<T> other = (AttributeArray<T>) obj;
		if (!Arrays.equals(array, other.array)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(array);
	}
}
