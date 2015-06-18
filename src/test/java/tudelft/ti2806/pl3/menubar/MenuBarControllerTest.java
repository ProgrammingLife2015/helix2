package tudelft.ti2806.pl3.menubar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.findgenes.FindgenesController;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.event.ActionEvent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for the MenuBarController
 * Created by Kasper on 17-6-2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class MenuBarControllerTest {

	@Mock
	Application application;
	@Mock
	ActionEvent actionEvent;

	MenuBarController menuBarController;

	@Before
	public void setUp() {
		menuBarController = new MenuBarController(application);
	}

	@Test
	public void testGetMenuBar() {
		MenuBarView menuBarView = new MenuBarView();
		assertEquals(menuBarView.getClass(), menuBarController.getMenuBar().getClass());
	}

	@Test
	public void testActionOpenFolder() {
		when(actionEvent.getActionCommand()).thenReturn("Open folder");
		menuBarController.actionPerformed(actionEvent);

		verify(application, times(1)).makeGraphFromFolder();
	}

	@Test
	public void testActionOpenNodeEdgeFile() {
		when(actionEvent.getActionCommand()).thenReturn("Open node and edge file");
		menuBarController.actionPerformed(actionEvent);

		verify(application, times(1)).makeGraphFromFiles();
	}

	@Test
	public void testActionOpenNwk() {
		when(actionEvent.getActionCommand()).thenReturn("Open .nwk file");
		menuBarController.actionPerformed(actionEvent);

		verify(application, times(1)).makePhyloTree();
	}

	@Test
	public void testActionExit() {
		when(actionEvent.getActionCommand()).thenReturn("Exit");
		menuBarController.actionPerformed(actionEvent);

		verify(application, times(1)).stop();
	}

	@Test
	public void testActionZoomIn() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn("Zoom in");
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).zoomLevelUp();
	}

	@Test
	public void testActionZoomOut() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn("Zoom out");
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).zoomLevelDown();
	}

	@Test
	public void testActionMoveLeft() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn("Move left");
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).moveLeft();
	}

	@Test
	public void testActionMoveRight() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn("Move right");
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).moveRight();
	}

	@Test
	public void testActionResetZoom() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn("Reset view");
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).resetZoom();
	}

	@Test
	public void testActionShowFindGenes() {
		FindgenesController findgenesController = mock(FindgenesController.class);
		when(actionEvent.getActionCommand()).thenReturn("Navigate to gene");
		when(application.getFindgenesController()).thenReturn(findgenesController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getFindgenesController();
		verify(findgenesController, times(1)).openDialog();
	}

	@Test
	public void testMakeControls() {
		String expected = MenuBarController.controls;
		String controls = menuBarController.makeControls().getText();
		assertEquals(expected, controls);
	}

	@Test
	public void testMakeAbout() {
		String expected = MenuBarController.about + "githublink";
		String result = menuBarController.makeAbout().getText();
		assertEquals(expected, result);
		//assertEquals(result.contains(expected), expected.contains(result));
	}

}
