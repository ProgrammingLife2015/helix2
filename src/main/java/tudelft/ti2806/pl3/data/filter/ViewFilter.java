package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.graph.Node;

import java.util.List;

public class ViewFilter extends Filter<Node> {
	
	private int min;
	private int max;
	
	public ViewFilter(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public void calculateFilter(List<Node> list) {
		for (Node node : list) {
			if (node.getRefEndPoint() <= min && node.getRefStartPoint() >= max) {
				filter.add(node);
			}
		}
	}
}
