package tudelft.ti2806.pl3.util;

/**
 * Combines two types together as one type. Makes it possible to create return
 * types, keys or values with two types.
 *
 * @param <U>
 *         the first type
 * @param <T>
 *         the second type
 * @author Sam Smulders
 */
public class Pair<U, T> {
    public final U first;
    public final T second;

    public Pair(U first, T second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
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
        Pair<U, T> other = (Pair<U, T>) obj;
        if (first == null) {
            if (other.first != null) {
                return false;
            }
        } else if (!first.equals(other.first)) {
            return false;
        }
        if (second == null) {
            if (other.second != null) {
                return false;
            }
        } else if (!second.equals(other.second)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pair [first=" + first + ", second=" + second + "]";
    }

}
