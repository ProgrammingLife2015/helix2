package tudelft.ti2806.pl3.graph;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Locale;

/**
 * Let the user select the correct node and egdes files.
 * Created by Kasper on 7-5-15.
 */
public class FileSelector {

    /**
     * Selects a file
     * @param title of the file chooser
     * @param frame in which file chooser must be shown
     * @param filter of the files
     * @return File that is chosen
     */
    public static File selectFile(String title, JFrame frame, String filter) {
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        chooser.setMultiSelectionEnabled(true);
        chooser.setDialogTitle(title);
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String path = file.getAbsolutePath().toLowerCase(Locale.ENGLISH);
                    if (path.endsWith(filter)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String getDescription() {
                return filter;
            }
        });
        int option = chooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File[] sf = chooser.getSelectedFiles();
            if (sf.length == 1) {
                return sf[0];
            }
        }
        return null;
    }
}
