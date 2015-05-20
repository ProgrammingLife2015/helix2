package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.graph.node.DataNode;

import java.util.List;

public class OccurFilter extends Filter<DataNode> {
	protected final int occurCount;
	
	public OccurFilter(int occurCount) {
		this.occurCount = occurCount;
	}
	
	@Override
	public void calculateFilter(List<DataNode> list) {
		for (DataNode node : list) {
			if (node.getSource().length < this.occurCount) {
				filter.add(node);
			}
		}
	}
}
