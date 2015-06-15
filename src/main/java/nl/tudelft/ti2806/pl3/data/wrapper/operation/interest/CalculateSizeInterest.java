package nl.tudelft.ti2806.pl3.data.wrapper.operation.interest;

import nl.tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

/**
 * Adds an interest value equal to the square root of the node its size. Bigger nodes are more interesting because their
 * mutation is more likely to be more significant.
 *
 * @author Sam Smulders
 */
public class CalculateSizeInterest extends WrapperOperation {
    @Override
    public void calculate(DataNodeWrapper wrapper, Wrapper container) {
        if (wrapper.getBasePairCount() < 1) {
            return;
        }
        wrapper.multiplyInterest((int) Math.sqrt(wrapper.getBasePairCount()));
    }

}
