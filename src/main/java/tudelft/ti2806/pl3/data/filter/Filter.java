package tudelft.ti2806.pl3.data.filter;

import java.util.List;

/**
 * Filter is an interface for filters. The filter is supposed to be calculated
 * on initialising of the instance over the entire data set, so the filters can
 * be applied individually in any order.
 *
 * @param <T>
 *         the element type to filter
 * @author Sam Smulders
 */
public interface Filter<T> {

    void filter(List<T> list);
}
