package tudelft.ti2806.pl3.data.filter;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;

import java.util.List;

public class GenomeFilter extends Filter<DataNodeInterface> {
	protected final Genome genome;
	
	public GenomeFilter(Genome genome) {
		this.genome = genome;
	}
	
	@Override
	public void calculateFilter(List<DataNodeInterface> list) {
		for (DataNodeInterface node : list) {
			for (Genome source : node.getSource()) {
				if (source.equals(genome)) {
					filter.add(node);
				}
			}
		}
	}
}
