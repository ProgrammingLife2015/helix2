package nl.tudelft.ti2806.pl3.util;

import java.io.InputStream;
import java.net.URL;

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
}
