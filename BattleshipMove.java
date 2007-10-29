import java.io.Serializable;

/**
 * This class packages up a Player identifier and the proposed coordinate of that Player's next move in a game of Battleship.
 * @author Matt Johnson
 *
 */
public class BattleshipMove implements Serializable{
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

	public boolean equals(Object obj) {
		BattleshipMove bm = (BattleshipMove) obj;
		return (bm.myPlayer == myPlayer) && (bm.myCoordinate.myX == myCoordinate.myX) && (bm.myCoordinate.myY == myCoordinate.myY);
	}	
}
