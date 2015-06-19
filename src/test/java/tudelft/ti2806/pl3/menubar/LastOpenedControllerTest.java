package tudelft.ti2806.pl3.menubar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.Application;
import tudelft.ti2806.pl3.util.FileSelector;
import tudelft.ti2806.pl3.util.LastOpenedStack;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for lastopenend controller
 * Created by Kasper on 17-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class LastOpenedControllerTest {

	@Mock
	Application application;
	@Mock
	ActionEvent actionEvent;


	LastOpenedController lastOpenedController;

	@Before
	public void setUp() {
		FileSelector.lastopened = new LastOpenedStack<>(5);
		lastOpenedController = new LastOpenedController(application);
	}

	@Test
	public void testGetLastOpened() {
		FileSelector.lastopened.add(new File("testfile"));
		JMenu menu = lastOpenedController.getLastOpenedMenu();
		assertEquals("Open recent files", menu.getText());
	}

	@Test
	public void testNwkAction() {
		when(actionEvent.getActionCommand()).thenReturn("test.nwk");
		lastOpenedController.actionPerformed(actionEvent);

		File file = new File("test.nwk");
		verify(application, times(1)).makePhyloTree(file);
	}

	@Test
	public void testNodeEdgeAction() {
		String path = "data/testdata/LastOpenedTest/test.node.graph";
		when(actionEvent.getActionCommand()).thenReturn(path);
		lastOpenedController.actionPerformed(actionEvent);

		File nodefile = new File(path);
		File edgefile = new File(nodefile.getAbsolutePath().replace("node", "edge"));
		verify(application, times(1)).makeGraph(nodefile, edgefile, null);
	}

	@Test
	public void testFolder() {
		String path = "data/testdata/LastOpenedTest";
		when(actionEvent.getActionCommand()).thenReturn(path);
		lastOpenedController.actionPerformed(actionEvent);

		File nodefile = new File(path + "/test.node.graph");
		File edgefile = new File(path + "/test.edge.graph");
		File nwkfile = new File(path + "/test.nwk");

		verify(application, times(1)).makeGraph(nodefile, edgefile, nwkfile);
	}

	@Test
	public void testUpdate() {
		FileSelector.lastopened.add(new File("beforeupdate"));
		LastOpenedController lastOpenedController = new LastOpenedController(application);

		for (Component component : lastOpenedController.getLastOpenedMenu().getMenuComponents()) {
			JMenuItem item = (JMenuItem) component;
			assertEquals("beforeupdate", item.getText());
		}

		FileSelector.lastopened = new LastOpenedStack<>(5);
		FileSelector.lastopened.add(new File("afterupdate"));

		lastOpenedController.update();

		for (Component component : lastOpenedController.getLastOpenedMenu().getMenuComponents()) {
			JMenuItem item = (JMenuItem) component;
			assertEquals("afterupdate", item.getText());
		}

	}

}
