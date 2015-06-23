package tudelft.ti2806.pl3.util;

import java.awt.Image;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;

/**
 * Created by Mathieu Post on 5-6-15.
 */
public class Resources {
	public static URL getResource(String file) {
		return Resources.class.getClassLoader().getResource(file);
	}

	public static InputStream getResourceAsStream(String file) {
		return Resources.class.getClassLoader().getResourceAsStream(file);
	}

	public static List<Image> getIcons() {
		List<Integer> list = Arrays.asList(16, 20, 24, 32, 48, 64, 128, 256, 512);
		return list.stream().map(integer -> new ImageIcon(getResource("pictures/helix" + integer + ".png")).getImage())
				.collect(Collectors.toList());
	}
}
