import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Component;
import java.net.URL;

/**
 * What pattern is this?
 * 
 * @author Owen Astrachan
 */

public class ResizableIcon extends ImageIcon {
    private ImageIcon myIcon;

    /**
     * Create resizable icon from an icon
     * 
     * @param icon
     *            is the icon used by this resizeable icon
     */
    public ResizableIcon(ImageIcon icon) {
        myIcon = icon;
    }

    /**
     * Create resizable icon from a url
     * 
     * @param url
     *            is the source of this resizable icon
     */
    public ResizableIcon(URL url) {
        myIcon = new ImageIcon(url);
    }

    /**
     * Draws this icon at the proper size.
     * 
     * @param c
     *            is used to determine how big to draw this Icon
     * @param g
     *            is the graphics context in which drawing takes place
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        int w = c.getWidth();
        int h = c.getHeight();
        g.drawImage(myIcon.getImage(), 0, 0, w, h, c);
    }

    public Image getImage() {
        return myIcon.getImage();
    }

    /**
     * Needed for interface, not used in this project.
     */
    public int getIconHeight() {
        return 5;
    }

    /**
     * Needed for interface, not used in this project.
     */
    public int getIconWidth() {
        return 5;
    }
}
