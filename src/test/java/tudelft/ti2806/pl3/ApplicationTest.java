package tudelft.ti2806.pl3;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import tudelft.ti2806.pl3.findgenes.FindGenesController;
import tudelft.ti2806.pl3.sidebar.SideBarController;
import tudelft.ti2806.pl3.sidebar.phylotree.PhyloController;
import tudelft.ti2806.pl3.ui.util.DialogUtil;
import tudelft.ti2806.pl3.ui.util.YesConfirmOptionPane;
import tudelft.ti2806.pl3.visualization.GraphController;
import tudelft.ti2806.pl3.zoombar.ZoomBarController;

import static org.junit.Assert.assertEquals;

/**
 * Test
 * Created by Kasper on 17-6-2015.
 */

public class ApplicationTest {

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Test
	public void testControllers() {
		Application application = new Application();

		assertEquals(application.getGraphController().getClass(), GraphController.class);
		assertEquals(application.getFindGenesController().getClass(), FindGenesController.class);
		assertEquals(application.getPhyloController().getClass(), PhyloController.class);
		assertEquals(application.getZoomBarController().getClass(), ZoomBarController.class);
		assertEquals(application.getSideBarController().getClass(), SideBarController.class);
	}

	@Test
	public void testStop() {
		Application application = new Application();
		DialogUtil.setConfirmOptionPane(new YesConfirmOptionPane());
		exit.expectSystemExitWithStatus(0);
		application.stop();
	}
}
