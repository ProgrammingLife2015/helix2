package tudelft.ti2806.pl3.detailedView;

import tudelft.ti2806.pl3.Controller;

/**
 * Created by Mathieu Post on 8-6-15.
 */
public class DetailController implements Controller {

	private DetailView detailView;

	/**
	 * Construct the side bar controller and add all filters to its view.
	 *
	 * @param detailView
	 * 		the view that is controlled
	 *
	 */
	public DetailController(DetailView detailView) {
		this.detailView = detailView;
	}


}
