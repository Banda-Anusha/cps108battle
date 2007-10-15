import javax.swing.*;
import java.util.*;
import java.net.URL;

/**
 * Factoring for loading/retrieving icons. Isolate icon creation from rest of
 * code. Currently loads all files, could use lazy-loading.
 * 
 * @author Owen Astrachan
 */

public class IconFactory {
    private static final String DIRNAME = "images";
    private String myDirectory;
    private Map<String,ImageIcon> myMap;
    private String mySuffix;

    public IconFactory() {
        this(DIRNAME, ".gif");
    }

    public IconFactory(String dirname) {
        this(dirname, ".gif");
    }

    public IconFactory(String dirname, String suffix) {
        myDirectory = dirname;
        myMap = new HashMap<String,ImageIcon>();
        mySuffix = suffix;
        int count = 0;
        int fileLabel = 1;
        while (true) {
            String name = myDirectory + "/image" + fileLabel + mySuffix;
            URL u = this.getClass().getResource(name);
            if (u == null) {
                break;
            } else {
                System.out.println("loaded " + name);
            }
            // myMap.put(""+count, new ResizableIcon(u));
            ImageIcon icon = new ImageIcon(u);
            myMap.put("" + count, icon);
            count++;
            fileLabel++;
        }

        URL u = this.getClass().getResource(myDirectory + "/blank" + mySuffix);
        if (u != null) {
            myMap.put("blank", new ImageIcon(u));
        }
    }

    /**
     * @return the number of icons stored by the factory
     */
    public int size() {
        return myMap.size();
    }

    /**
     * Returns an icon
     * 
     * @param index
     *            is in [0..size())
     * @return the index-th icon
     */

    public ImageIcon getIcon(int index) {
        if (index < 0 || index >= myMap.size()) {
            return myMap.get("blank");
        }
        ImageIcon ic = myMap.get("" + index);
        return ic;
    }
}
