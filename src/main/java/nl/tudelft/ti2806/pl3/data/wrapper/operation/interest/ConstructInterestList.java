package nl.tudelft.ti2806.pl3.data.wrapper.operation.interest;

import nl.tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris Mattijssen on 21-05-15.
 */
public class ConstructInterestList extends WrapperOperation {

    private ArrayList<Float> interests;

    public ConstructInterestList() {
        this.interests = new ArrayList<>();
        this.interests.add(Float.MAX_VALUE);
    }

    public List<Float> getInterests() {
        return this.interests;
    }

    @Override
    public void calculate(HorizontalWrapper nodeWrapper, Wrapper container) {
        for (int i = nodeWrapper.getNodeList().size(); i > 1; i--) {
            this.interests.add(nodeWrapper.getInterest());
        }
        super.calculate(nodeWrapper, container);
    }

    @Override
    public void calculate(VerticalWrapper nodeWrapper, Wrapper container) {
        for (int i = nodeWrapper.getNodeList().size(); i > 1; i--) {
            this.interests.add(nodeWrapper.getInterest());
        }
        super.calculate(nodeWrapper, container);
    }

    @Override
    public void calculate(SpaceWrapper nodeWrapper, Wrapper container) {
        for (int i = nodeWrapper.getNodeList().size(); i > 1; i--) {
            this.interests.add(nodeWrapper.getInterest());
        }
        super.calculate(nodeWrapper, container);
    }
}
