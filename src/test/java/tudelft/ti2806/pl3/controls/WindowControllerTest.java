package tudelft.ti2806.pl3.controls;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.Application;

import java.awt.event.WindowEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test for WindowController
 * Created by Kasper on 10-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class WindowControllerTest {

	@Mock
	Application application;

	@Test
	public void testClosing() {
		WindowController windowController = new WindowController(application);
		WindowEvent windowEvent = mock(WindowEvent.class);
		windowController.windowClosing(windowEvent);

		verify(application, times(1)).stop();
	}
}
