package tudelft.ti2806.pl3.visualization.wrapper.operation.interest;

import tudelft.ti2806.pl3.visualization.wrapper.*;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;

/**
 * Created by Boris Mattijssen on 21-05-15.
 */
public class ConstructInterestList extends WrapperOperation {

	private ArrayList<Integer> interests;

	public ConstructInterestList() {
		interests = new ArrayList<>();
		interests.add(Integer.MAX_VALUE);
	}

	public ArrayList<Integer> getInterests() {
		return interests;
	}

	public void calculate(HorizontalWrapper nodeWrapper, NodeWrapper container) {
		for(int i = nodeWrapper.getNodeList().size(); i>1; i--) {
			interests.add(nodeWrapper.getInterest());
		}
		super.calculate(nodeWrapper, container);
	}
	public void calculate(VerticalWrapper nodeWrapper, NodeWrapper container) {
		for(int i = nodeWrapper.getNodeList().size(); i>1; i--) {
			interests.add(nodeWrapper.getInterest());
		}
		super.calculate(nodeWrapper, container);
	}
	public void calculate(SpaceWrapper nodeWrapper, NodeWrapper container) {
		for(int i = nodeWrapper.getNodeList().size(); i>1; i--) {
			interests.add(nodeWrapper.getInterest());
		}
		super.calculate(nodeWrapper, container);
	}
}
