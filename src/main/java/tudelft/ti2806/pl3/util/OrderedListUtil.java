package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An utility class to merge ordered lists.
 * 
 * @author Sam Smulders
 */
public class OrderedListUtil {
	private OrderedListUtil() {
	}
	
	/**
	 * Merges all given lists together without violating any of the given lists
	 * their element order.
	 * 
	 * <p>
	 * Warning, all elements and lists are removed from their lists after
	 * executing this method, or some of them when the method returns
	 * {@code null}.
	 * 
	 * <p>
	 * Method cost O(nm)
	 * <ul>
	 * <li>n being equal to the number of unique elements.
	 * <li>m being equal to the number of elements left in all lists together.
	 * </ul>
	 * 
	 * @param listsToMerge
	 *            the list of lists to merge. Each list should at least contain
	 *            one element
	 * @return a list containing all elements of the given lists without
	 *         violating the order of any of the given lists<br>
	 *         {@code null} if the lists could not be merged without violating
	 *         one of the lists its orders
	 */
	public static List<NodeWrapper> mergeOrderedLists(
			List<List<NodeWrapper>> listsToMerge) {
		List<NodeWrapper> lastElements = new ArrayList<NodeWrapper>(
				listsToMerge.size());
		for (List<NodeWrapper> list : listsToMerge) {
			lastElements.add(list.remove(list.size() - 1));
		}
		List<NodeWrapper> result = new ArrayList<NodeWrapper>();
		int lastResultSize = -1;
		while (listsToMerge.size() > 0) {
			/*
			 * If the result size doesn't grow, there is a conflict.
			 */
			if (lastResultSize == result.size()) {
				return null;
			}
			lastResultSize = result.size();
			for (int i = listsToMerge.size() - 1; i >= 0; i--) {
				if (!listContainsElement(listsToMerge, lastElements.get(i))) {
					int size = listsToMerge.get(i).size();
					if (size == 0) {
						NodeWrapper element = lastElements.remove(i);
						if (!result.contains(element)) {
							result.add(element);
						}
						listsToMerge.remove(i);
					} else {
						/*
						 * Adds the last element to the result list. This
						 * element is replaced on the lastElements list by the
						 * new last element on the list from the listsToCombine
						 * bound to this index. This last element again removed
						 * from that list.
						 */
						NodeWrapper element = lastElements.set(i, listsToMerge
								.get(i).remove(size - 1));
						if (!result.contains(element)) {
							result.add(element);
						}
					}
				}
			}
		}
		Collections.reverse(result);
		return result;
	}
	
	/**
	 * Checks if the given {@code element} is in one of the {@code lists}.
	 * 
	 * @param lists
	 *            the lists to search trough
	 * @param element
	 *            the element to search for
	 * @return {@code true} if the given {@code element} is in one of the
	 *         {@code lists}<br>
	 *         {@code false} otherwise
	 */
	static boolean listContainsElement(List<List<NodeWrapper>> lists,
			NodeWrapper element) {
		for (List<NodeWrapper> list : lists) {
			if (list.contains(element)) {
				return true;
			}
		}
		return false;
	}
}
