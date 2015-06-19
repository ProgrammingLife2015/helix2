package tudelft.ti2806.pl3.menubar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.Constants;
import tudelft.ti2806.pl3.findgenes.FindGenesController;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.event.ActionEvent;

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
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_FILE_OPEN_FOLDER);
		menuBarController.actionPerformed(actionEvent);

		verify(application, times(1)).makeGraphFromFolder();
	}

	@Test
	public void testActionOpenNodeEdgeFile() {
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_FILE_OPEN_GRAPH_FILES);
		menuBarController.actionPerformed(actionEvent);

		verify(application, times(1)).makeGraphFromFiles();
	}

	@Test
	public void testActionOpenNwk() {
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_FILE_OPEN_NWK_FILE);
		menuBarController.actionPerformed(actionEvent);

		verify(application, times(1)).makePhyloTree();
	}

	@Test
	public void testActionExit() {
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_FILE_EXIT);
		menuBarController.actionPerformed(actionEvent);

		verify(application, times(1)).stop();
	}

	@Test
	public void testActionZoomIn() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_VIEW_ZOOM_IN);
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).zoomLevelUp();
	}

	@Test
	public void testActionZoomOut() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_VIEW_ZOOM_OUT);
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).zoomLevelDown();
	}

	@Test
	public void testActionMoveLeft() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_VIEW_MOVE_LEFT);
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).moveLeft();
	}

	@Test
	public void testActionMoveRight() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_VIEW_MOVE_RIGHT);
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).moveRight();
	}

	@Test
	public void testActionResetZoom() {
		GraphController graphController = mock(GraphController.class);
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_VIEW_RESET);
		when(application.getGraphController()).thenReturn(graphController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getGraphController();
		verify(graphController, times(1)).resetZoom();
	}

	@Test
	public void testActionShowFindGenes() {
		FindGenesController findgenesController = mock(FindGenesController.class);
		when(actionEvent.getActionCommand()).thenReturn(Constants.MENU_VIEW_NAVIGATE_TO_GENE);
		when(application.getFindGenesController()).thenReturn(findgenesController);
		menuBarController.actionPerformed(actionEvent);


		verify(application, times(1)).getFindGenesController();
		verify(findgenesController, times(1)).openDialog();
	}

	@Test
	public void testMakeControls() {
		String expected = Constants.INFO_CONTROLS;
		String controls = menuBarController.makeControls().getText();
		assertEquals(expected, controls);
	}

	@Test
	public void testMakeAbout() {
		String expected = Constants.INFO_ABOUT + "githublink";
		String result = menuBarController.makeAbout().getText();
//		assertEquals(expected, result);
//		assertEquals(result.contains(expected), expected.contains(result));
	}

}
