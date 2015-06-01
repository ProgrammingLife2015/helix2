package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Set;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
public class WrapperPlaceholder extends Wrapper {
	static final String ID_STRING = "[PLACEHOLDER]";
	
	@Override
	public long getBasePairCount() {
		return 0;
	}
	
	@Override
	public String getIdString() {
		return ID_STRING;
	}
	
	@Override
	public Set<Genome> getGenome() {
		return null;
	}
	
	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}
	
	@Override
	public void collectDataNodes(Set<DataNode> set) {
	}
	
	@Override
	public void calculateX() {
	}
	
	@Override
	public int getWidth() {
		return 0;
	}
	
}
