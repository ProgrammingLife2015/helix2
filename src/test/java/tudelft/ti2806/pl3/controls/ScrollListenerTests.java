package tudelft.ti2806.pl3.controls;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.visualization.GraphController;

import java.awt.event.MouseWheelEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for ScrollListener
 * Created by Kasper on 17-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScrollListenerTests {

	int NEGATIVE_SCROLL = -1;
	int POSITIVE_SCROLL = 1;

	@Mock
	Application application;
	@Mock
	GraphController graphcontroller;

	@Test
	public void testsMouseWheelUp() {
		MouseWheelEvent event = mock(MouseWheelEvent.class);
		when(event.getWheelRotation()).thenReturn(NEGATIVE_SCROLL);
		when(application.getGraphController()).thenReturn(graphcontroller);

		ScrollListener scroll = new ScrollListener(application);
		scroll.mouseWheelMoved(event);

		verify(application, times(1)).getGraphController();
		verify(graphcontroller, times(1)).zoomLevelUp();
	}
	@Test
	public void testsMouseWheelDown(){
		MouseWheelEvent event = mock(MouseWheelEvent.class);
		when(event.getWheelRotation()).thenReturn(POSITIVE_SCROLL);
		when(application.getGraphController()).thenReturn(graphcontroller);

		ScrollListener scroll = new ScrollListener(application);
		scroll.mouseWheelMoved(event);

		verify(application, times(1)).getGraphController();
		verify(graphcontroller, times(1)).zoomLevelDown();
	}
}
