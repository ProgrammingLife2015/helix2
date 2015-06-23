package tudelft.ti2806.pl3.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Boris Mattijssen on 17-06-15.
 */
public class CollectionUtil {

	private CollectionUtil() {}


	/**
	 * Calculates the intersection between two collections.
	 *
	 * @param collection1
	 * 		collection 1
	 * @param collection2
	 * 		collection 2
	 * @param <T>
	 * 		type of both collections
	 * @return the intersection between the two collections
	 */
	public static <T> Collection<T> intersection(Collection<T> collection1, Collection<T> collection2) {
		return filter(collection1, collection2).collect(Collectors.toList());
	}

	/**
	 * Calculates the intersection between two collections.
	 *
	 * @param collection1
	 * 		collection 1
	 * @param collection2
	 * 		collection 2
	 * @param <T>
	 * 		type of both collections
	 * @return the intersection between the two collections
	 */
	public static <T> Set<T> intersectionListSet(List<T> collection1, Set<T> collection2) {
		return filter(collection1, collection2).collect(Collectors.toSet());
	}

	/**
	 * Calculates the intersection between two collections.
	 * 
	 * @param collection1
	 * 		collection 1
	 * @param collection2
	 * 		collection 2
	 * @param <T>
	 * 		type of both collections
	 * @return the intersection between the two collections as a stream
	 */
	public static <T> Stream<T> filter(Collection<T> collection1, Collection<T> collection2) {
		return collection1.stream().filter(collection2::contains);
	}
}
