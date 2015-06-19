package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.label.Label;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Set;

public class FixWrapper extends Wrapper {
	private final int id;
	public static final String ID_STRING = "[FIX]";
	private Set<Genome> genome;
	
	public FixWrapper(int id) {
		this.id = id;
	}
	
	@Override
	public long getBasePairCount() {
		return 0;
	}
	
	@Override
	public String getIdString() {
		return ID_STRING;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public boolean canUnwrap() {
		return false;
	}

	@Override
	public Set<Genome> getGenome() {
		return genome;
	}
	
	@Override
	public void calculate(WrapperOperation operation, Wrapper container) {
		operation.calculate(this, container);
	}
	
	@Override
	public void collectDataNodes(Set<DataNode> set) {
	}
	
	@Override
	public void collectLabels(Set<Label> labels) {
	}
	
	public void setGenome(Set<Genome> genome) {
		this.genome = genome;
	}
	
	@Override
	public void calculateX() {
		this.setX(this.getPreviousNodesCount());
	}
	
	@Override
	public int getWidth() {
		return 0;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	@Override
	public boolean contains(Wrapper object) {
		return false;
	}
}
