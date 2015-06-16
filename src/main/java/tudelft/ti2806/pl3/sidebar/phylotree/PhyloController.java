package tudelft.ti2806.pl3.sidebar.phylotree;

import newick.NewickParser;
import newick.ParseException;
import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.util.observable.LoadingObservable;
import tudelft.ti2806.pl3.util.observers.LoadingObserver;
import tudelft.ti2806.pl3.data.filter.GenomeFilter;
import tudelft.ti2806.pl3.ui.util.DialogUtil;
import tudelft.ti2806.pl3.util.TreeParser;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Phylo controller controls the phylogentic tree view.
 * Created by Kasper on 20-5-2015.
 */
public class PhyloController implements Controller, ActionListener, LoadingObservable {


	private PhyloView view;
	private ControllerContainer cc;
	private PhyloModel phyloModel;

	private ArrayList<LoadingObserver> observers = new ArrayList<>();

	private boolean loaded;

	/**
	 * Construct the controller.
	 *
	 * @param cc
	 * 		reference to all controllers
	 */
	public PhyloController(ControllerContainer cc) {
		this.cc = cc;
		phyloModel = new PhyloModel();
		view = new PhyloView(phyloModel);
		view.addButtonListener(this);
		phyloModel.addObserver(view);
		setLoaded(false);
	}

	public Component getPanel() {
		return view;
	}

	/**
	 * When the button is clicked on the view.
	 *
	 * @param event
	 * 		the click event
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		List<String> selected = view.getSelected();
		if (selected.size() != 0) {
			cc.getGraphController().addFilter(GenomeFilter.NAME, new GenomeFilter(selected));
			view.resetSelected();
		}
	}

	/**
	 * Parse the file containing the newick tree.
	 *
	 * @param treeFile
	 * 		the file
	 * @throws ParseException
	 * 		when the file was in incorrect file format
	 */
	public void parseTree(File treeFile) throws ParseException {
		try {
			notifyLoadingObservers(true);
			NewickParser.TreeNode tree = TreeParser.parseTreeFile(treeFile);
			phyloModel.setTree(tree);
			notifyLoadingObservers(false);
			setLoaded(true);
		} catch (IOException e) {
			if (DialogUtil.confirm("Parse error", "A random error occurred while "
					+ "parsing the phylotree file. "
					+ "Retrying could help. Would you like to try again now?")) {
				parseTree(treeFile);
			}
		}
	}

	public PhyloView getView() {
		return view;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public boolean isLoaded(){
		return loaded;
	}

	@Override
	public void addLoadingObserver(LoadingObserver loadingObserver) {
		observers.add(loadingObserver);
	}

	@Override
	public void addLoadingObserversList(ArrayList<LoadingObserver> loadingObservers) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			addLoadingObserver(loadingObserver);
		}
	}

	@Override
	public void deleteLoadingObserver(LoadingObserver loadingObserver) {
		observers.remove(loadingObserver);
	}

	@Override
	public void notifyLoadingObservers(Object loading) {
		for (LoadingObserver observer : observers) {
			observer.update(this, loading);
		}
	}
}
