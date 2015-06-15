package nl.tudelft.ti2806.pl3.data.wrapper.operation.interest;

import nl.tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

/**
 * For each {@link CombineWrapper} or {@link SingleWrapper}, add the max
 * interest value of their node (list).
 *
 * @author Sam Smulders
 */
public class CalculateAddMaxOfWrapped extends WrapperOperation {
    @Override
    public void calculate(HorizontalWrapper wrapper, Wrapper container) {
        super.calculate(wrapper, container);
        wrapper.addInterest(wrapper.getNodeList().stream()
                .map(Wrapper::getInterest).max(Float::compare).get());
    }

    @Override
    public void calculate(VerticalWrapper wrapper, Wrapper container) {
        super.calculate(wrapper, container);
        wrapper.addInterest(wrapper.getNodeList().stream()
                .map(Wrapper::getInterest).max(Float::compare).get());
    }

    @Override
    public void calculate(SpaceWrapper wrapper, Wrapper container) {
        super.calculate(wrapper, container);
        wrapper.addInterest(wrapper.getNodeList().stream()
                .map(Wrapper::getInterest).max(Float::compare).get());
    }

    @Override
    public void calculate(SingleWrapper wrapper, Wrapper container) {
        super.calculate(wrapper, container);
        wrapper.addInterest(wrapper.getNode().getInterest());
    }
}
