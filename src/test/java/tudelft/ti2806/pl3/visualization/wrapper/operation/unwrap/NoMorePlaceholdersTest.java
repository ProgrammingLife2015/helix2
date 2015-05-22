package tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap;

import static org.junit.Assert.fail;

import tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.PlaceholderWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.List;

/**
 * Created by Boris Mattijssen on 20-05-15.
 */
public class NoMorePlaceholdersTest extends WrapperOperation {
	
	public NoMorePlaceholdersTest(List<DataNodeWrapper> dataNodeWrappers) {
		for (DataNodeWrapper d : dataNodeWrappers) {
			d.getOutgoing().forEach(n -> n.calculate(this, d));
			d.getIncoming().forEach(n -> n.calculate(this, d));
		}
	}
	
	@Override
	public void calculate(PlaceholderWrapper p, NodeWrapper container) {
		fail("Placeholder detected! Detected in via container " + container);
	}
	
}
