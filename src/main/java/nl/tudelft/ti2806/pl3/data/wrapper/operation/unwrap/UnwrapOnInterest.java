package nl.tudelft.ti2806.pl3.data.wrapper.operation.unwrap;

import nl.tudelft.ti2806.pl3.data.wrapper.CombineWrapper;

/**
 * Unwraps the wrappers when the interest value is higher then the given value.
 *
 * @author Sam Smulders
 */
public class UnwrapOnInterest extends Unwrap {

    private final int interestCondition;

    public UnwrapOnInterest(int interestCondition) {
        this.interestCondition = interestCondition;
    }

    @Override
    protected boolean isConditionMet(CombineWrapper node) {
        return node.getInterest() >= interestCondition;
    }

}
