package tudelft.ti2806.pl3.controls;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.ControllerContainer;

import java.awt.event.ComponentAdapter;

/**
 * Creates an adapter that updates screen sizes for the components in the view.
 *
 * Created by Kasper on 16-6-2015.
 */
public class ResizeAdapter extends ComponentAdapter {

	private ControllerContainer cc;
	public ResizeAdapter(Application cc) {
		this.cc = cc;
	}

//	@Override
//	public void componentResized(ComponentEvent e) {
//		Rectangle bounds = new Rectangle(cc.getWidth(), cc.getHeight());
//
//		size.setWidth((int) bounds.getWidth());
//		size.setHeight((int) bounds.getHeight());
//		size.calculate();
//
//		getSideBarController().getPanel().setBounds(0, size.getMenubarHeight(),
//				size.getSidebarWidth(), size.getHeight());
//		getGraphController().getPanel().setBounds(0, 0, size.getWidth(),
//				size.getHeight() - size.getZoombarHeight());
//		getZoomBarController().getPanel().setBounds(0,
//				size.getHeight() - size.getZoombarHeight(),
//				size.getWidth(), size.getZoombarHeight());
//		getPhyloController().getView().updateSize();
//
//		main.repaint();
//	}
}
