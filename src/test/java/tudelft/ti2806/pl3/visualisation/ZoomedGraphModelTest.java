package tudelft.ti2806.pl3.visualisation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.visualization.FilteredGraphModel;
import tudelft.ti2806.pl3.visualization.ZoomedGraphModel;

import static org.junit.Assert.assertEquals;

/**
 * Test for ZoomedGraphModel
 * Created by Kasper on 17-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZoomedGraphModelTest {

	@Mock
	FilteredGraphModel filteredGraphModel;

	ZoomedGraphModel zoomedGraphModel;

	@Before
	public void setUp() {
		zoomedGraphModel = new ZoomedGraphModel(filteredGraphModel);
	}

	@Test
	public void testZoom() {
		assertEquals(1, zoomedGraphModel.getZoomLevel());
		zoomedGraphModel.setZoomLevel(5);
		assertEquals(5, zoomedGraphModel.getZoomLevel());
		zoomedGraphModel.setZoomLevel(-1);
		assertEquals(5, zoomedGraphModel.getZoomLevel());
	}
}
