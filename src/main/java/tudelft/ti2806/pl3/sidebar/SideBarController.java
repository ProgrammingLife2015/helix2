package tudelft.ti2806.pl3.sidebar;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.graph.GraphController;

import java.awt.Component;

/**
 * Controller that controls the sidebar.
 * The user can select options in the sidebar.
 * Created by Kasper on 7-5-15.
 */
public class SideBarController implements Controller {

    public GraphController graphController;
    private SideBarView sideBarView;

    public SideBarController(GraphController graphController) {
        this.graphController = graphController;
        sideBarView = new SideBarView();
    }

    @Override
    public Component getView() {
        return sideBarView;
    }
}
