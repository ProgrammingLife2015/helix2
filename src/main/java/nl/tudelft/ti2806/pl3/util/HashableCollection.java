package nl.tudelft.ti2806.pl3.util;

import java.util.Collection;

/**
 * A holder for a collection to make the collection hash able.
 *
 * @param <T>
 *         the collection its type
 * @author Sam Smulders
 */
public class HashableCollection<T> {
    Collection<T> collection;

    public HashableCollection(Collection<T> list) {
        this.collection = list;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((collection == null) ? 0 : collection.hashCode());
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
        if (collection == null) {
            if (other.collection == null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (other.collection == null) {
                return false;
            }
        }
        if (collection.size() != other.collection.size()) {
            return false;
        }
        return collection.containsAll(other.collection);
    }
}
