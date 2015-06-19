package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.util.observable.LoadingObservable;
import tudelft.ti2806.pl3.util.observers.LoadingObserver;
import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.View;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * The view for the sidebar. The user can select options in the sidebar. Created
 * by Kasper on 7-5-15.
 */

@SuppressWarnings("serial")
public class SideBarView extends JPanel implements View, LoadingObservable {
	public static final double SIDEBAR_FACTOR = 0.40;
	private final ArrayList<LoadingObserver> loadingObservers = new ArrayList<>();

	/**
	 * Constructs the sidebar view with a fixed width.
	 */
	public SideBarView() {
		notifyLoadingObservers(true);
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(layout);
		setPreferredSize(new Dimension(ScreenSize.getInstance()
				.getSideBarWidth(), ScreenSize.getInstance().getHeight()));
		setMinimumSize(new Dimension(ScreenSize.getInstance()
				.getSideBarWidth(), ScreenSize.getInstance().getHeight()));
		notifyLoadingObservers(false);
	}

	@Override
	public Component getPanel() {
		return this;
	}

	@Override
	public void addLoadingObserver(LoadingObserver loadingObserver) {
		this.loadingObservers.add(loadingObserver);
	}

	@Override
	public void addLoadingObserversList(ArrayList<LoadingObserver> loadingObservers) {
		this.loadingObservers.addAll(loadingObservers);
	}

	@Override
	public void deleteLoadingObserver(LoadingObserver loadingObserver) {
		this.loadingObservers.remove(loadingObserver);
	}

	@Override
	public void notifyLoadingObservers(Object arguments) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			loadingObserver.update(this, arguments);
		}
	}
}
