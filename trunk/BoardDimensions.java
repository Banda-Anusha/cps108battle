import java.io.Serializable;
/**
 * This class contains the X (height) and Y (width) dimensions of a rectangular Battleship board.
 * @author Matt Johnson
 *
 */
public class BoardDimensions implements Serializable{
	private int myHeight;
	private int myWidth;
	
	public BoardDimensions(int w, int h)
	{
		myHeight = h;
		myWidth = w;
	}
	
	public int getHeight()
	{
		return myHeight;
	}
	
	public int getWidth()
	{
		return myWidth;
	}
	
}
