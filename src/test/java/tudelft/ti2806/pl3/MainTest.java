package tudelft.ti2806.pl3;

import org.junit.Test;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.UIManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for main
 * Created by Kasper on 17-6-2015.
 */
public class MainTest {

	@Test
	public void testLookAndFeel() {
		Main.main(null);
		assertEquals(UIManager.getSystemLookAndFeelClassName(), UIManager.getLookAndFeel().getClass().getName());
		assertTrue(UIManager.getDefaults().getBoolean("Button.showMnemonics"));
	}

//	@Test
//	public void testSize() {
//		Main.main(null);
//		GraphicsEnvironment ge = GraphicsEnvironment
//				.getLocalGraphicsEnvironment();
//		Rectangle bounds = ge.getMaximumWindowBounds();
//
//		assertEquals((int) bounds.getWidth(), ScreenSize.getInstance().getWidth());
//		assertEquals((int)bounds.getHeight(),ScreenSize.getInstance().getHeight());
//	}
}
