import java.io.Serializable;
/**
 * This class packages up an X and Y coordinate for a point on a Euclidean Battleship board.
 * @author Matt Johnson
 *
 */
public class Coordinate implements Serializable {
	public int myX; //No reason to make these private or implement accessors, this is strictly a 2-primitive wrapper.
	public int myY;
	
	public Coordinate(int x, int y)
	{
		myX = x;
		myY = y;
	}

	@Override
	public boolean equals(Object obj) {
		Coordinate other = (Coordinate) obj;
		return ((myX == other.myX) && (myY == other.myY));
	}
	
	
}
