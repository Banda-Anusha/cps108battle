import java.util.ArrayList;
import java.io.Serializable;
/**
 * This Interface comprises all the methods that must be implemented for the Model to get information about the Battleship board.
 * @author Matt Johnson
 *
 */
public interface IBoardState extends Serializable{
	/**
	 * This method is used to determine if a particular Coordinate is within the boundaries of the board
	 * @param c The (x,y) coordinate in question
	 * @return Whether or not the Coordinate is within the board.
	 */
	public boolean isCoordinateValid(Coordinate c);
	/**
	 * This method is used to determine the particular state of a given coordinate, from a particular Player's point of view.
	 * This includes whether or not a Coordinate has been "tried" before, and what the result was.
	 * @param c The (x,y) coordinate in question
	 * @return An enumerated type representing the state of the specified Coordinate.
	 */
	public CellState getState(Coordinate c);
	
	/**
	 * This method is used to update the state of the board.
	 * @param c The coordinate whose state is being changed
	 * @param newState The new state of this cell.
	 * @param whichShip If the new state is a HIT or SUNK, this parameter will be the ship HIT or SUNK.  Otherwise, it will be null.
	 * @return Whether or not the coordinate given was valid, and thus, whether or not the state change was successful.
	 */
	public boolean setState(Coordinate c, CellState newState);
	
	/**
	 * This method is used to figure out which coordinates are, as yet, untried.
	 * @return A list of valid (within the board) coordinates which haven't been tried this game.
	 */
	public ArrayList<Coordinate> getUnknownCoordinates();	
	/**
	 * This method should be called at the beginning of each game to clear the state of all the cells on the board
	 * @param bd The dimensions of the board for this game.
	 */
	public void newGame(BoardDimensions bd);
	
	public void setModel(IBattleshipModel model);
	public void addShip(BattleshipPlacement bp, ShipShape ss);
	public void processHit(Coordinate c);
	public void processMiss(Coordinate c);
	
	/**
	 * This method determines if the game is over
	 * @return true if the game is over (there are no ships on the board) and false otherwise
	 */
	public boolean gameOver();
}
