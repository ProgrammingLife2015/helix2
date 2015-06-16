package tudelft.ti2806.pl3.controls;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.findgenes.FindgenesController;
import tudelft.ti2806.pl3.sidebar.SideBarController;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests for KeyController
 * Created by Kasper on 10-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class KeyControllerTest {

	@Mock
	Application application;
	@Mock
	KeyEvent keyEvent;
	@Mock
	GraphController graphController;

	@Test
	public void testEscape() {
		KeyController keyController = new KeyController(application);
		when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
		when(application.getGraphController()).thenReturn(mock(GraphController.class));

		keyController.keyPressed(keyEvent);
		verify(application, times(1)).stop();
	}

	@Test
	public void testSpace() {
		KeyController keyController = new KeyController(application);
		when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_SPACE);
		SideBarController sideBarController = mock(SideBarController.class);
		when(application.getSideBarController()).thenReturn(sideBarController);
		when(application.getGraphController()).thenReturn(mock(GraphController.class));

		keyController.keyPressed(keyEvent);
		verify(application, times(1)).getSideBarController();
		verify(sideBarController, times(1)).toggleSideBar();
	}

	@Test
	public void testPlus() {
		KeyController keyController = new KeyController(application);
		when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_EQUALS);
		when(application.getGraphController()).thenReturn(graphController);

		keyController.keyPressed(keyEvent);
		verify(application, times(2)).getGraphController();
		verify(graphController, times(1)).zoomLevelUp();
	}

	@Test
	public void testRight() {
		KeyController keyController = new KeyController(application);
		when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
		when(application.getGraphController()).thenReturn(graphController);

		keyController.keyPressed(keyEvent);
		verify(application, times(2)).getGraphController();
		verify(graphController, times(1)).moveRight();
	}

	@Test
	public void testLeft() {
		KeyController keyController = new KeyController(application);
		when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
		when(application.getGraphController()).thenReturn(graphController);

		keyController.keyPressed(keyEvent);
		verify(application, times(2)).getGraphController();
		verify(graphController, times(1)).moveLeft();
	}

	@Test
	public void testGene() {
		KeyController keyController = new KeyController(application);
		when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_G);
		FindgenesController findgenesController = mock(FindgenesController.class);
		when(application.getFindgenesController()).thenReturn(findgenesController);
		when(application.getGraphController()).thenReturn(mock(GraphController.class));

		keyController.keyPressed(keyEvent);
		verify(application, times(1)).getFindgenesController();
		verify(findgenesController, times(1)).openDialog();
	}

	@Test
	public void testRelease() {
		KeyController keyController = new KeyController(application);
		keyController.release();

		verify(application, times(1)).removeKeyListener(keyController);
	}

	@Test
	public void testEmptyMethods() {
		KeyController keyController = new KeyController(application);

		keyController.keyReleased(keyEvent);
		keyController.keyTyped(keyEvent);

		verifyZeroInteractions(application);
	}


}
