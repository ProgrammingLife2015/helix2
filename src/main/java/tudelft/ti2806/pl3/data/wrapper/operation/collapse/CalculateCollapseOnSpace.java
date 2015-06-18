package tudelft.ti2806.pl3.data.wrapper.operation.collapse;

import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Comparator;

/**
 * Computes the collapse value, based on the space left between nodes when unwrapped.
 * 
 * @author Sam Smulders
 */
public class CalculateCollapseOnSpace extends WrapperOperation {
    @Override
    public void calculate(HorizontalWrapper wrapper, Wrapper container) {
        if (wrapper.canUnwrap()) {
            super.calculate(wrapper, container);
            wrapper.addCollapse(getMinSpaceLeft(wrapper));
        }
    }
    
    @Override
    public void calculate(SpaceWrapper wrapper, Wrapper container) {
        super.calculate(wrapper, container);
        wrapper.addCollapse(getCompensatedMinSpaceLeft(wrapper));
    }
    
    @Override
    public void calculate(VerticalWrapper wrapper, Wrapper container) {
        super.calculate(wrapper, container);
        wrapper.addCollapse(Float.MAX_VALUE);
    }
    
    /**
     * Computes if there is enough space for a wrapper to unfold it.
     * 
     * @param wrapper
     *            the wrapper of which to determine if there is enough space to unfold it
     * @return a value of the average space between nodes.
     */
    static float getMinSpaceLeft(HorizontalWrapper wrapper) {
        return getMinDistance(wrapper.getFirst(), wrapper.getLast());
    }
    
    static float getCompensatedMinSpaceLeft(SpaceWrapper wrapper) {
        return 1.1f * ((wrapper.getLast().getX() - wrapper.getFirst().getX()) / (wrapper.getLast()
                .getPreviousNodesCount() - wrapper.getFirst().getPreviousNodesCount())
        /* + getMinSpaceLeft(wrapper) */);
    }
    
    static int getShortestPath(Wrapper first, Wrapper last) {
        int min = Integer.MAX_VALUE;
        for (Wrapper wrapper : first.getOutgoing()) {
            if (wrapper == last) {
                return 1;
            }
            min = Math.min(min, getShortestPath(wrapper, last));
        }
        return min + 1;
    }
    
    static float getMinDistance(Wrapper first, Wrapper last) {
        Wrapper wrapper = first.getOutgoing().get(0);
        if (wrapper == last) {
            return wrapper.getX() - first.getX();
        }
        return Math.min(wrapper.getX() - first.getX(), getMinDistance(wrapper, last));
    }
    
    public class XComparator implements Comparator<Wrapper> {
        @Override
        public int compare(Wrapper w1, Wrapper w2) {
            return (int) Math.signum(w1.getX() - w2.getX());
        }
    }
}
