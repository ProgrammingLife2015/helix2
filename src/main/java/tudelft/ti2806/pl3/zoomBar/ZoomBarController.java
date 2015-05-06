package tudelft.ti2806.pl3.zoomBar;

import tudelft.ti2806.pl3.Controller;

import java.awt.*;

/**
 * Created by Boris Mattijssen on 06-05-15.
 */
public class ZoomBarController implements Controller {

	private ZoomBarView zoomBarView;

	public ZoomBarController() {
		zoomBarView = new ZoomBarView();
	}

	@Override
	public Component getView() {
		return zoomBarView;
	}
}
