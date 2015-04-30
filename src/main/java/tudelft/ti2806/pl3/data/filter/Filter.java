package tudelft.ti2806.pl3.data.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter is an interface for filters. The filter is supposed to be calculated
 * on initialising of the instance over the entire data set, so the filters can
 * be applied individually in any order.
 * 
 * @author Sam Smulders
 *
 * @param <T>
 *            the element type to filter
 */
public abstract class Filter<T> {
	protected List<T> filter = new ArrayList<T>();
	
	/**
	 * Calculates what elements have to be removed from the list and stores it.
	 * 
	 * @param list
	 *            a list of the entire data set to filter
	 */
	protected abstract void calculateFilter(List<T> list);
	
	public final void filter(List<T> list) {
		list.removeAll(filter);
	}
}
