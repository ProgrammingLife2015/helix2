package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.List;

public class FixWrapper extends NodeWrapper {
	
	List<Genome> genome;
	long pos;
	
	public FixWrapper(List<Genome> genome, long pos) {
		this.genome = genome;
		this.pos = pos;
	}
	
	@Override
	public long getXStart() {
		return pos;
	}
	
	@Override
	public long getXEnd() {
		return pos;
	}
	
	@Override
	public long getWidth() {
		return pos;
	}
	
	@Override
	public String getIdString() {
		return "[FIX]";
	}
	
	@Override
	public List<Genome> getGenome() {
		return genome;
	}
	
	@Override
	public void calculate(WrapperOperation operation, NodeWrapper container) {
		operation.calculate(this, container);
	}
	
}
