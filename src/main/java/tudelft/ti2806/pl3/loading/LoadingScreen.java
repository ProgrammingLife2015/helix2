package tudelft.ti2806.pl3.loading;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.LoadingObservable;
import tudelft.ti2806.pl3.LoadingObserver;
import tudelft.ti2806.pl3.View;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Dialog screen for our LoadingScreen
 * Created by Kasper on 27-5-2015.
 */
public class LoadingScreen extends JDialog implements LoadingObserver,View {

	private final JLabel text;

	public LoadingScreen(Application application) {
		super(application, "Loading");
		setSize(200, 150);
		setLocationRelativeTo(application);
		setVisible(true);
		setAlwaysOnTop(true);
		text = new JLabel("Starting...");
		add(text);
	}
	@Override
	public void update(LoadingObservable loadingObservable, Object arguments) {
		setVisible(true);
		text.setText(arguments.toString());
		System.out.println(arguments);
	}


	@Override
	public JDialog getPanel() {
		return this;
	}

	@Override
	public Controller getController() {
		return null;
	}
}
