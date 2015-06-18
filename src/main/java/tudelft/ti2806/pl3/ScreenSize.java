package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.sidebar.SideBarView;
import tudelft.ti2806.pl3.zoombar.ZoomBarView;

/**
 * Singleton object for the screen size. Since our app is fullscreen, the width
 * and height are different for every screen. Created by Kasper on 8-5-2015.
 */
public class ScreenSize {

	private static final int minimumWidth = 800;
	private static final int minimumHeight = 600;
	private static final int menubarHeight = 25;
	private static ScreenSize size;
	private int width;
	private int height;
	private int zoomBarHeight;
	private int sideBarWidth;
	
	/**
	 * Constructor.
	 */
	private ScreenSize() {
	}
	
	/**
	 * Get a unique ScreenSize instance.
	 * 
	 * @return ScreenSize instance
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
	
	public int getZoomBarHeight() {
		return zoomBarHeight;
	}
	
	public int getSideBarWidth() {
		return sideBarWidth;
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
		zoomBarHeight = calculate(ZoomBarView.ZOOMBAR_FACTOR, getHeight());
		sideBarWidth = calculate(SideBarView.SIDEBAR_FACTOR, getWidth());
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

	public int calculateHeight(double percentage) {
		return calculate(percentage, height);
	}

	public int calculateWidth(double percentage) {
		return calculate(percentage, width);
	}
}
