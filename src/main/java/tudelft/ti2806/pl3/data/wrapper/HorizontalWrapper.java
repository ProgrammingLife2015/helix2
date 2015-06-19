package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

/**
 * A collection of horizontal combine able nodes. All nodes should be in a row
 * with only one connection and with the same set of genomes.
 * 
 * @author Mathieu Post
 * @author Sam Smulders
 */
public class HorizontalWrapper extends CombineWrapper {
	
	private boolean canUnwrap = true;
	
	/**
	 * An collection of {@link SNodes} which can be used as a single SNode.
	 * 
	 * @param nodePosList
	 *            a connected and sorted list of edges.
	 */
	public HorizontalWrapper(List<Wrapper> nodePosList, boolean canUnwrap) {
		super(nodePosList);
		this.canUnwrap = canUnwrap;
	}
	
	@Override
	public boolean canUnwrap() {
		return canUnwrap;
	}

	public void setCanUnwrap(boolean canUnwrap) {
		this.canUnwrap = canUnwrap;
	}

	@Override
	public long getBasePairCount() {
		long sum = 0;
		for (Wrapper node : nodeList) {
			sum += node.getBasePairCount();
		}
		return sum;
	}
	
	@Override
	public Set<Genome> getGenome() {
		return this.getFirst().getGenome();
	}
	
	@Override
	public void calculate(WrapperOperation wrapperSequencer, Wrapper container) {
		wrapperSequencer.calculate(this, container);
	}
	
	@Override
	public String getIdString() {
		return "H" + super.getIdString();
	}
	
	@Override
	public int getWidth() {
		return this.getNodeList().stream().mapToInt(Wrapper::getWidth).sum();
	}
}
