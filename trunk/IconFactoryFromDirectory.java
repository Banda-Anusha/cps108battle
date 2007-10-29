import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import javax.swing.ImageIcon;
import java.io.Serializable;

public class IconFactoryFromDirectory implements Serializable{
    
    private static final String DIRNAME = "images";
    private String myDirectory;
    private Map<String,ImageIcon> myMap;
    private String mySuffix;
    
    public IconFactoryFromDirectory(String dirname) {
        myMap = new HashMap<String,ImageIcon>();
        myDirectory = dirname;
        File dir = new File(dirname);
        File[] list = dir.listFiles();
        for(File f : list){
            
            String name = f.getAbsolutePath();
//            URL u = this.getClass().getResource(name);
//            if (u == null) {
//                System.out.println("failed for "+name);
//                continue;
//            } else {
//                System.out.println("loaded " + name);
//            }
            
            ImageIcon icon = new ImageIcon(name);
            if (icon != null){
                //System.out.println("loaded "+f.getName());
            }
            myMap.put(f.getName(), new ResizableIcon(icon));
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

    public ImageIcon getIcon(String name) {
       return myMap.get(name);
    }
}
