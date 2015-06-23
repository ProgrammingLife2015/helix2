package tudelft.ti2806.pl3;

import org.junit.Test;
import tudelft.ti2806.pl3.sidebar.SideBarView;
import tudelft.ti2806.pl3.zoombar.ZoomBarView;

import static org.junit.Assert.assertEquals;

/**
 * Test for the singleton ScreenSize
 * Created by Kasper on 17-6-2015.
 */
public class ScreensizeTest {

	ScreenSize screenSize = ScreenSize.getInstance();
	final int testWidth = 222;
	final int testHeight = 111;


	@Test
	public void testWidth() {
		screenSize.setWidth(testWidth);

		assertEquals(testWidth, screenSize.getWidth());
		assertEquals(800, screenSize.getMinimumWidth());
	}
	@Test
	public void testHeight() {
		screenSize.setHeight(testHeight);

		assertEquals(testHeight, screenSize.getHeight());
		assertEquals(600, screenSize.getMinimumHeight());
	}

	@Test
	public void testCalculateZoombar() {
		screenSize.setHeight(testHeight);
		screenSize.calculate();
		int zoombarheight = (int) (ZoomBarView.ZOOM_BAR_FACTOR * testHeight);
		assertEquals(zoombarheight, screenSize.getZoomBarHeight());

	}

	@Test
	public void testCalculateSidebar(){
		screenSize.setWidth(testWidth);
		screenSize.calculate();
		int sidebarwidth = (int) (SideBarView.SIDEBAR_FACTOR * testWidth);
		assertEquals(sidebarwidth, screenSize.getSideBarWidth());
	}

	@Test
	public void testMenubarHeight() {
		assertEquals(25, screenSize.getMenubarHeight());
	}


}
