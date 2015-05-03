package tudelft.ti2806.pl3.data.graph.positioning;

public interface Target {
	
	long getEnd();
	
	long getStart();
	
	/**
	 * Computes a {@link Line}, representing the space it uses on the graph.
	 * 
	 * @param order
	 *            the order of nodes, with each node id as index
	 * @return a {@link Line} representing the space of the target
	 */
	Line getLine(int[] order);
	
}
