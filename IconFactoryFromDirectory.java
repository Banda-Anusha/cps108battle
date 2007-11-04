import java.net.URL;
import java.util.ArrayList;
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
    
    public IconFactoryFromDirectory(String dirname, ArrayList<String> imageNames) {
        myMap = new HashMap<String,ImageIcon>();
            
        for(String s : imageNames)
        {
            URL u = this.getClass().getResource(dirname+"/"+s);
//            if (u == null) {
//                System.out.println("failed for "+name);
//                continue;
//            } else {
//                System.out.println("loaded " + name);
//            }
           ;
            ImageIcon icon = new ImageIcon(u);
            //if (icon != null){
                //System.out.println("loaded "+f.getName());
            //}
            myMap.put(s, new ResizableIcon(icon));
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
