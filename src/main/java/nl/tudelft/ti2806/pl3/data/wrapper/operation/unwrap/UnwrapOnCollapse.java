package nl.tudelft.ti2806.pl3.data.wrapper.operation.unwrap;

import nl.tudelft.ti2806.pl3.data.wrapper.CombineWrapper;

/**
 * Unwraps the wrappers when the collapse value is higher then the given value.
 *
 * @author Sam Smulders
 */
public class UnwrapOnCollapse extends Unwrap {

    private final float spaceCondition;

    public UnwrapOnCollapse(float spaceCondition) {
        this.spaceCondition = spaceCondition;
    }

    @Override
    protected boolean isConditionMet(CombineWrapper node) {
        return node.getCollapse() >= spaceCondition;
    }
}
