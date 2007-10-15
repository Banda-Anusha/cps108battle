/**
 * This class packages up a Player identifier and the proposed coordinate of that Player's next move in a game of Battleship.
 * @author Matt Johnson
 *
 */
public class BattleshipMove {
	private IBattleshipPlayer myPlayer;
	private Coordinate myCoordinate;
	
	public BattleshipMove(IBattleshipPlayer p, Coordinate c)
	{
		myPlayer = p;
		myCoordinate = c;
	}
	
	public IBattleshipPlayer getPlayer()
	{
		return myPlayer;
	}
	
	public Coordinate getCoordinate()
	{
		return myCoordinate;
	}
}
