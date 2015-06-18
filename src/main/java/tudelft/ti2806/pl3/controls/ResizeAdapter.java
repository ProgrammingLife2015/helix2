package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.ScreenSize;

import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Creates an adapter that updates screen sizes for the components in the view.
 * <p>
 * Created by Kasper on 16-6-2015.
 */
public class ResizeAdapter extends ComponentAdapter {

	private Application application;

	public ResizeAdapter(Application application) {
		super();
		this.application = application;
	}


	@Override
	public void componentResized(ComponentEvent e) {
		Rectangle bounds = application.getBounds();
		ScreenSize size = ScreenSize.getInstance();

		size.setWidth((int) bounds.getWidth());
		size.setHeight((int) bounds.getHeight());
		size.calculate();

		application.getSideBarController().getPanel().setBounds(0, size.getMenubarHeight(),
				size.getSidebarWidth(), size.getHeight());
		application.getGraphController().getPanel().setBounds(0, 0, size.getWidth(),
				size.getHeight() - size.getZoombarHeight());
		application.getZoomBarController().getPanel().setBounds(0,
				size.getHeight() - size.getZoombarHeight(),
				size.getWidth(), size.getZoombarHeight());
		application.getPhyloController().getView().updateSize();

		application.repaint();
	}

	public ComponentAdapter getResizer() {
		return this;
	}
}
