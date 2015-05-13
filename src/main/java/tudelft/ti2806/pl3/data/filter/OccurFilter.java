package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.graph.Node;

import java.util.Collection;
import java.util.List;

public class OccurFilter implements Filter<Node> {
	protected final int occurCount;
	
	public OccurFilter(int occurCount) {
		this.occurCount = occurCount;
	}

	@Override
	public void filter(Collection<Node> list) {

	}
}
