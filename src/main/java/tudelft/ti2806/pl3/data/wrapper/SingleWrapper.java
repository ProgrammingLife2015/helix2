package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.label.Label;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Set;

/**
 * Wraps a single {@link Wrapper}.
 * 
 * @author Sam Smulders
 */
public class SingleWrapper extends Wrapper {
	
	private Wrapper target;
	
	public SingleWrapper(Wrapper target) {
		if (target.getClass() == SingleWrapper.class) {
			this.target = ((SingleWrapper) target).getNode();
		} else {
			this.target = target;
		}
	}
	
	@Override
	public long getBasePairCount() {
		return target.getBasePairCount();
	}
	
	@Override
	public String getIdString() {
		return target.getIdString();
	}

	@Override public int getId() {
		return target.getId();
	}

	@Override
	public Set<Genome> getGenome() {
		return target.getGenome();
	}

	@Override
	public Set<Label> getLabels() {
		return target.getLabels();
	}

	public Wrapper getNode() {
		return target;
	}
	
	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}
	
	@Override
	public void collectDataNodes(Set<DataNode> set) {
		target.collectDataNodes(set);
	}
	
	@Override
	public void calculateX() {
		this.x = this.getNode().getX();
	}
	
	@Override
	public int getWidth() {
		return this.getNode().getWidth();
	}
}
