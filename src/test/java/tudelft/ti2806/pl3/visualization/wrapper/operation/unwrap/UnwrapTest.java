package tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.visualization.wrapper.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class UnwrapTest {

	@Mock private DataNode dataNode1;
	@Mock private DataNode dataNode2;
	@Mock private DataNode dataNode3;


	@Before
	public void before() {

	}

	@Test
	public void testHorizontalWrapper() {
		NodePosition nodePosition1 = new NodePosition(dataNode1);
		NodePosition nodePosition2 = new NodePosition(dataNode2);
		NodePosition nodePosition3 = new NodePosition(dataNode3);

		List<NodeWrapper> nodeWrapperList = new ArrayList<>(3);
		nodeWrapperList.add(nodePosition1);
		nodeWrapperList.add(nodePosition2);
		nodeWrapperList.add(nodePosition3);
		nodePosition1.getOutgoing().add(nodePosition2);
		nodePosition2.getOutgoing().add(nodePosition3);
		nodePosition2.getIncoming().add(nodePosition1);
		nodePosition3.getIncoming().add(nodePosition2);

		HorizontalWrapper start = new HorizontalWrapper(nodeWrapperList, true);
		Unwrap unwrap = new Unwrap(start);
		assertTrue(unwrap.getResult() instanceof DataNodeWrapper);
		assertTrue(((DataNodeWrapper) unwrap.getResult()).getDataNodeList().contains(dataNode1));
		assertTrue(unwrap.getResult().getOutgoing().get(0) instanceof DataNodeWrapper);
		assertTrue(((DataNodeWrapper) unwrap.getResult().getOutgoing().get(0)).getDataNodeList().contains(dataNode2));
		assertTrue(unwrap.getResult().getOutgoing().get(0).getOutgoing().get(0) instanceof DataNodeWrapper);
		assertTrue(((DataNodeWrapper) unwrap.getResult().getOutgoing().get(0).getOutgoing().get(0)).getDataNodeList().contains(dataNode3));
	}

	@Test
	public void testVerticalWrapper() {
		NodePosition nodePosition1 = new NodePosition(dataNode1);
		NodePosition nodePosition2 = new NodePosition(dataNode1);
		NodePosition nodePosition3 = new NodePosition(dataNode1);
		NodePosition nodePosition4 = new NodePosition(dataNode1);

		List<NodeWrapper> verticalList = new ArrayList<>(2);
		verticalList.add(nodePosition2);
		verticalList.add(nodePosition3);
		VerticalWrapper vertical = new VerticalWrapper(verticalList, true);

		List<NodeWrapper> nodeWrapperList = new ArrayList<>(3);
		nodeWrapperList.add(nodePosition1);
		nodeWrapperList.add(vertical);
		nodeWrapperList.add(nodePosition4);
		nodePosition1.getOutgoing().add(vertical);
		vertical.getOutgoing().add(nodePosition4);
		vertical.getIncoming().add(nodePosition1);
		nodePosition4.getIncoming().add(vertical);

		HorizontalWrapper start = new HorizontalWrapper(nodeWrapperList, true);
		Unwrap unwrap = new Unwrap(start);
		NodeWrapper result = unwrap.getResult();

//		assertEquals(result, nodePosition1);
//		assertEquals(result.getOutgoing().get(0), nodePosition2);
//		assertEquals(result.getOutgoing().get(1), nodePosition3);
//		assertEquals(result.getOutgoing().get(0).getOutgoing().get(0), nodePosition4);
//		assertEquals(result.getOutgoing().get(1).getOutgoing().get(0), nodePosition4);
//		assertEquals(nodePosition1.getOutgoing().get(0), nodePosition2);
//		assertEquals(nodePosition1.getOutgoing().get(1), nodePosition3);
//		assertEquals(nodePosition2.getOutgoing().get(0), nodePosition4);
//		assertEquals(nodePosition3.getOutgoing().get(0), nodePosition4);
//
//		assertEquals(nodePosition1.getIncoming().size(), 0);
//		assertEquals(nodePosition2.getIncoming().get(0), nodePosition1);
//		assertEquals(nodePosition3.getIncoming().get(0), nodePosition1);
//		assertEquals(nodePosition4.getIncoming().get(0), nodePosition2);
//		assertEquals(nodePosition4.getIncoming().get(1), nodePosition3);
	}

}
