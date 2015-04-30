package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Node;

import java.util.List;

public class OccurFilter extends Filter<Node> {
	protected final int occurCount;
	
	public OccurFilter(int occurCount) {
		this.occurCount = occurCount;
	}
	
	@Override
	public void calculateFilter(List<Node> list) {
		for (Node node : list) {
			if (node.getSource().length < this.occurCount) {
				filter.add(node);
			}
		}
	}
}
