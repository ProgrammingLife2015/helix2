package tudelft.ti2806.pl3.sidebar.phylotree;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.util.observers.LoadingObserver;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Test for PhyloController
 * Created by Kasper on 17-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class PhyloControllerTest {

	@Mock
	LoadingObserver loadingObserver;
	@Mock
	LoadingObserver loadingObserver1;
	@Mock
	ControllerContainer cc;

	PhyloController phyloController;

	@Before
	public void setUp() {
		phyloController = new PhyloController(cc);
	}

	@Test
	public void testMultipleObservers() {
		ArrayList<LoadingObserver> list = new ArrayList<>();
		list.add(loadingObserver);
		list.add(loadingObserver1);
		phyloController.addLoadingObserversList(list);

		phyloController.notifyLoadingObservers("hello observers");

		for (LoadingObserver observer : list) {
			verify(observer, times(1)).update(phyloController, "hello observers");
		}

		phyloController.deleteLoadingObserver(loadingObserver);
		phyloController.notifyLoadingObservers("hello observer");

		verify(loadingObserver1, times(1)).update(phyloController, "hello observer");
		verifyZeroInteractions(loadingObserver);

	}

	@Test
	public void testGetView() {
		assertEquals(PhyloView.class, phyloController.getView().getClass());
	}
}
