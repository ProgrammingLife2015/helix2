package tudelft.ti2806.pl3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javax.swing.UIManager;

/**
 * Test for main Created by Kasper on 17-6-2015.
 */
public class MainTest {
	
	@Test
	public void testLookAndFeel() {
		Main.main(null);
		assertEquals(UIManager.getSystemLookAndFeelClassName(), UIManager.getLookAndFeel().getClass().getName());
		assertTrue(UIManager.getDefaults().getBoolean("Button.showMnemonics"));
	}
}
