package tudelft.ti2806.pl3.data.graph.positioning;

import tudelft.ti2806.pl3.data.graph.Edge;

import java.util.ArrayList;
import java.util.List;

public class EdgeTarget implements Target {
	protected final Edge edge;
	protected List<Target> intersectCandidates = new ArrayList<Target>();
	
	public EdgeTarget(Edge edge) {
		this.edge = edge;
	}
	
	/**
	 * Select all candidates for intersection.
	 * 
	 * @param targets
	 *            all targets form the graph
	 */
	public void selectCandidates(List<Target> targets) {
		long start = getStart();
		long end = getEnd();
		for (Target target : targets) {
			if (target.getEnd() > start && target.getStart() < end
					&& target != this) {
				intersectCandidates.add(target);
			}
		}
	}
	
	/**
	 * Counts the number of intersects with this order configuration.
	 * 
	 * @param order
	 *            the order of nodes, with each node id as index
	 * @return the count of intersects detected
	 */
	public int calculateIntersects(int[] order) {
		int count = 0;
		Line line = getLine(order);
		for (Target target : intersectCandidates) {
			if (target.getLine(order).intersect(line)) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public Line getLine(int[] order) {
		return new Line(this.getStart(),
				order[this.edge.getFrom().getId()], this.getEnd(),
				order[this.edge.getTo().getId()]);
	}
	
	public long getStart() {
		return edge.getFrom().getXEnd() + edge.getFrom().getPreviousNodesCount();
	}
	
	public long getEnd() {
		return edge.getTo().getXStart() + edge.getTo().getPreviousNodesCount();
	}
}
