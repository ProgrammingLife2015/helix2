package tudelft.ti2806.pl3.visualization.wrapper.operation.interest;

import org.junit.Test;
import org.mockito.Mock;

import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;

import java.util.ArrayList;
import java.util.List;

public class CalculateGroupInterestTest {
	@Mock
	NodeWrapper nodeWrapper;
	
	@Test
	public void aaTest() {
		CalculateWrapPressureInterest cwp = new CalculateWrapPressureInterest(1);
		List<NodeWrapper> nodeList = new ArrayList<>();
		nodeList.add(nodeWrapper);
		nodeList.add(nodeWrapper);
		
	}
}
