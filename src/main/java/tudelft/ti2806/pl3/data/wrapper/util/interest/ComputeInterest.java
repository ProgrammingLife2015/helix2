package tudelft.ti2806.pl3.data.wrapper.util.interest;

import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.List;

public final class ComputeInterest {
	private ComputeInterest() {
	}

	public static void compute(List<Wrapper> wrappers) {
		for (Wrapper wrapper : wrappers) {
			wrapper.addInterest((float) (Math.sqrt(wrapper.getBasePairCount() * wrapper.getGenome().size()) * computeRation(wrapper)));
		}
	}

	/**
	 * Computes the ratio of N's in the {@link DataNode}s within the given wrapper.
	 *
	 * @param wrapper
	 *            the wrapper
	 * @return the ratio, between 1.0 and 0.0
	 */
	static float computeRation(Wrapper wrapper) {
		long nCount = 0;
		long totalCount = 0;
		for (DataNode dataNode : wrapper.getDataNodes()) {
			totalCount += dataNode.getBasePairCount();
			nCount += dataNode.getNCount();
		}
		if (totalCount == 0) {
			return 1;
		}
		return ((float) totalCount - nCount) / totalCount;
	}
}
