import java.util.ArrayList;

/**
 * This class packages up a Player identifier and a list of points corresponding to a proposed placement of a ship on the board.
 * @author Matt Johnson
 *
 */
public class BattleshipPlacement {
	private IBattleshipPlayer myPlayer;
	private ArrayList<Coordinate> myPoints;
	
	public BattleshipPlacement()
	{
		myPoints = new ArrayList<Coordinate>();
	}
	
	public BattleshipPlacement(IBattleshipPlayer p)
	{
		myPlayer = p;
	}
	
	public BattleshipPlacement(ArrayList<Coordinate> list)
	{
		myPoints = new ArrayList<Coordinate>(list);
	}
	
	public BattleshipPlacement(IBattleshipPlayer p, ArrayList<Coordinate> list)
	{
		myPlayer = p;
		myPoints = new ArrayList<Coordinate>(list);
	}
	
	public BattleshipPlacement(BattleshipPlacement other)
	{
		myPoints = new ArrayList<Coordinate>(other.myPoints);
	}

	public ArrayList<Coordinate> getPoints()
	{
		return (new ArrayList<Coordinate>(myPoints));
	}
	
	public IBattleshipPlayer getPlayer()
	{
		return myPlayer;
	}
	
}
