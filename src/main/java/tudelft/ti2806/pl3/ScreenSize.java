package tudelft.ti2806.pl3;

import tudelft.ti2806.pl3.sidebar.SideBarView;
import tudelft.ti2806.pl3.zoomBar.ZoomBarView;

/**
 * Singleton variable for the screensize.
 * Since our app is fullscreen, the width and height are different for every screen.
 * Created by Kasper on 8-5-2015.
 */
public class ScreenSize {
	private static ScreenSize size;

	private int width;
	private int height;
	private int zoombarHeight;
	private int sidebarWidth;

	/**
	 * Constructor
	 */
	public ScreenSize() {
	}

	/**
	 * Get a unique screensize instance.
	 * @return screensize instance
	 */
	public static ScreenSize getInstance(){
		if(size == null){
			size = new ScreenSize();
		}
		return size;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int h) {
		this.height = h;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public int getZoombarHeight() {
		return zoombarHeight;
	}

	public int getSidebarWidth() {
		return sidebarWidth;
	}

	/**
	 * Get the percentage of the size as a integer.
	 * @param percentage percentage of size
	 * @param s size of the dimension
	 * @return percentage*size rounded to nearest integer
	 */
	public int calculate(double percentage, int s){
		return (int)(percentage*s);
	}

	/**
	 * Calculate the sizes of the windows.
	 */
	public void calculate() {
		zoombarHeight = calculate(ZoomBarView.ZOOMBAR_FACTOR, getHeight());
		sidebarWidth = calculate(SideBarView.SIDEBAR_FACTOR, getWidth());
	}
}
