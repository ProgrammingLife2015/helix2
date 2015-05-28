package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

public class FixWrapper extends Wrapper {
	private static int ID_COUNT = 0;
	private int id = 0;
	Set<Genome> genome;
	
	@Override
	public long getBasePairCount() {
		return 0;
	}
	
	@Override
	public String getIdString() {
		return "[FIX]";
	}

	@Override public int getId() {
		if (id == 0) {
			ID_COUNT--;
			id = ID_COUNT;
		}
		return id;
	}

	@Override
	public Set<Genome> calculateGenome() {
		return genome;
	}
	
	@Override
	public void calculate(WrapperOperation operation, Wrapper container) {
		operation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(List<DataNode> list) {

	}

	public void setGenome(Set<Genome> genome) {
		this.genome = genome;
	}

	@Override
	public void calculateX() {
		this.x = this.getPreviousNodesCount();
	}

	@Override
	public int getWidth() {
		return 0;
	}
}
