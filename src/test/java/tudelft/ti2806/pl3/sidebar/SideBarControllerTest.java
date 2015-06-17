package tudelft.ti2806.pl3.sidebar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.sidebar.phylotree.PhyloController;
import tudelft.ti2806.pl3.util.observers.LoadingObserver;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Test for the SideBarController
 * Created by Kasper on 17-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SideBarControllerTest {


	@Mock
	ControllerContainer cc;
	@Mock
	LoadingObserver loadingObserver;
	@Mock
	LoadingObserver loadingObserver1;

	SideBarController sideBarController;

	@Before
	public void setUp() {
		sideBarController = new SideBarController(cc);
	}

	@Test
	public void testToggle() {
		assertTrue(sideBarController.getPanel().isVisible());
		sideBarController.toggleSideBar();
		assertFalse(sideBarController.getPanel().isVisible());
		sideBarController.toggleSideBar();
		assertTrue(sideBarController.getPanel().isVisible());
	}

	@Test
	public void testPhylo() {
		assertEquals(PhyloController.class, sideBarController.getPhyloController().getClass());
	}

	@Test
	public void testLoadingObservers() {
		SideBarView view = (SideBarView) sideBarController.getPanel();
		view.addLoadingObserver(loadingObserver);

		view.notifyLoadingObservers("hello observer");

		verify(loadingObserver, times(1)).update(view, "hello observer");
	}

	@Test
	public void testMultipleObservers() {
		SideBarView view = (SideBarView) sideBarController.getPanel();
		ArrayList<LoadingObserver> list = new ArrayList<>();
		list.add(loadingObserver);
		list.add(loadingObserver1);
		view.addLoadingObserversList(list);

		view.notifyLoadingObservers("hello observerS");

		for (LoadingObserver observer : list) {
			verify(observer, times(1)).update(view, "hello observerS");
		}

		view.deleteLoadingObserver(loadingObserver);

		view.notifyLoadingObservers("hello observer");

		verify(loadingObserver1, times(1)).update(view, "hello observer");
		verifyZeroInteractions(loadingObserver);
	}

	@Test
	public void testPhyloObserver() {
		ArrayList<LoadingObserver> list = new ArrayList<>();
		list.add(loadingObserver);
		list.add(loadingObserver1);
		sideBarController.addLoadingObserversList(list);

		PhyloController phyloController = sideBarController.getPhyloController();
		phyloController.notifyLoadingObservers("hello observers");

		for (LoadingObserver observer : list) {
			verify(observer, times(1)).update(phyloController,"hello observers");
		}
	}
}
