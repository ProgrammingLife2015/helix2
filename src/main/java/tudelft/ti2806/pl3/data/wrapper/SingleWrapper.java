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
	
	/**
	 * Constructor of {@link SingleWrapper}. Uses the target of the target when it is wrapping an other
	 * {@link SingleWrapper}
	 * 
	 * @param target
	 *            the target
	 */
	public SingleWrapper(Wrapper target) {
		if (target.getClass() == SingleWrapper.class) {
			this.target = ((SingleWrapper) target).getNode();
		} else {
			this.target = target;
		}
	}
	
	@Override
	public long getBasePairCount() {
		return this.target.getBasePairCount();
	}
	
	@Override
	public String getIdString() {
		return this.target.getIdString();
	}
	
	@Override
	public int getId() {
		return this.target.getId();
	}
	
	@Override
	public Set<Genome> getGenome() {
		return this.target.getGenome();
	}
	
	public Wrapper getNode() {
		return this.target;
	}
	
	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}
	
	@Override
	public void collectDataNodes(Set<DataNode> set) {
		this.target.collectDataNodes(set);
	}
	
	@Override
	public void collectLabels(Set<Label> labels) {
		target.collectLabels(labels);
	}
	
	@Override
	public void calculateX() {
		this.x = this.getNode().getX();
	}
	
	@Override
	public int getWidth() {
		return this.getNode().getWidth();
	}
	
	@Override
	public boolean contains(Wrapper object) {
		return object == this.target || this.target.contains(object);
	}
}
