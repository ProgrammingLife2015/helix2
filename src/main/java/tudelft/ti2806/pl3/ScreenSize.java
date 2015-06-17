package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.sidebar.SideBarView;
import tudelft.ti2806.pl3.zoombar.ZoomBarView;

/**
 * Singleton variable for the screensize. Since our app is fullscreen, the width
 * and height are different for every screen. Created by Kasper on 8-5-2015.
 */
public class ScreenSize {

	public static int minimumWidth = 800;
	public static int minimumHeight = 600;
	private static ScreenSize size;
	public static int menubarHeight = 25;
	private int width;
	private int height;
	private int zoombarHeight;
	private int sidebarWidth;
	
	/**
	 * Constructor.
	 */
	public ScreenSize() {
	}
	
	/**
	 * Get a unique screensize instance.
	 * 
	 * @return screensize instance
	 */
	public static synchronized ScreenSize getInstance() {
		if (size == null) {
			size = new ScreenSize();
		}
		return size;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getZoombarHeight() {
		return zoombarHeight;
	}
	
	public int getSidebarWidth() {
		return sidebarWidth;
	}

	public int getMenubarHeight() {
		return menubarHeight;
	}

	public static int getMinimumWidth() {
		return minimumWidth;
	}

	public static int getMinimumHeight() {
		return minimumHeight;
	}


	/**
	 * Calculate the sizes of the windows.
	 */
	public void calculate() {
		zoombarHeight = calculate(ZoomBarView.ZOOMBAR_FACTOR, getHeight());
		sidebarWidth = calculate(SideBarView.SIDEBAR_FACTOR, getWidth());
	}
	
	/**
	 * Get the percentage of the size as a integer.
	 * 
	 * @param percentage
	 *            percentage of size
	 * @param integer
	 *            size of the dimension
	 * @return percentage*size rounded to nearest integer
	 */
	public int calculate(double percentage, int integer) {
		return (int) (percentage * integer);
	}
}
