import java.util.ArrayList;
import java.io.Serializable;
/**
 * This class represents the canonical shape of a ship.  This allows arbitrary shapes to be defined.
 * The constraints for the representation to be canonical are:
 * 1) One of the points of the ship must be at (0,0)
 * 2) All points must be in the first quadrant ({x,y} | x<0, y<0)
 * @author Matt Johnson
 *
 */

public class ShipShape implements Serializable{
	/**
	 * The name of this class of ship.
	 */
	private String myName;
	/**
	 * An array of coordinates corresponding to the canonical shape of the ship
	 */
	private ArrayList<Coordinate> myPoints;
	
	/**
	 * Constructor
	 * @param points The list of points to be used in the ShipShape.
	 */
	public ShipShape(String s, ArrayList<Coordinate> points)
	{
		myName = s;
		myPoints = new ArrayList<Coordinate>(points);
	}
	/**
	 * This method is used to make sure that the 2 constraints (origin point defined, all points in first quadrant) are satisfied.
	 * @return Whether or not the provided points satisfy the 2 requirements.
	 */
	public boolean validate()
	{
		boolean has00 = false; //Whether or not the origin point is present
		for(Coordinate c : myPoints)
		{
			if((c.myX < 0) || (c.myY < 0))
				return false;
			if((c.myX == 0) || (c.myY == 0))
				has00 = true;
		}
		return has00;				
	}
	
	/**
	 * This method gets the points used in the shape
	 * @return An ArrayList of Coordinates representing the canonical shape of the ship.
	 */
	public ArrayList<Coordinate> getPoints()
	{
		return new ArrayList<Coordinate>(myPoints);
	}
	
	/**
	 * This method gets the name of this class of ship
	 * @return The name of this class of ship.
	 */
	public String getName()
	{
		return myName;
	}
}
