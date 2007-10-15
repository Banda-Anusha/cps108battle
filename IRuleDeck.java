import java.util.ArrayList;
/**
 * This Interface comprises the methods that must be implemented by any Rule Deck so that the Model
 * can obtain the appropriate information about the rules of the current game.
 * @author Matt Johnson
 *
 */
public interface IRuleDeck {
	/**
	 * This method is used for the Model to relay ship placements from the Players, and ask if they are valid or not, based on the rules of the game.
	 * @param bs The current state of the board (what other ships have been placed)
	 * @param bp The proposed location and orientation of the ship
	 * @param sh An object representing the canonical shape of the ship
	 * @return Whether or not the proposed placement is valid.
	 */
	public boolean isShipPlacementValid(IBoardState bs, BattleshipPlacement bp, ShipShape sh);
	/**
	 * This method is used to determine if a proposed move is valid, given the rules of the game.
	 * @param bs The current state of the board (what cells have been tried, hit or miss, sunk ship, etc..)
	 * @param m The proposed move
	 * @return Whether or not the proposed move is valid
	 */
	public boolean isMoveValid(IBoardState bs, BattleshipMove m);
	/**
	 * This method is used to get a list of all the ships that need to be placed.  The Model will call this method, and 
	 * then tell the Players to place the ships.  The structure of IRuleDeck is such that each player must have the same number
	 * and shape of ships.  This currently prevents handicapping and other forms of assymetry in the game,
	 * but this can be easily remedied by giving the Rule Deck knowledge of the Players and integrating it more into the Model.
	 * @return An ArrayList of all ShipShapes that need to be placed by each Player.
	 */
	public ArrayList<ShipShape> getShips();
	/**
	 * This method is used by the Model to determine how big the board is for the series.  Note that the current constraint is that the
	 * board must be rectangular, with an integer number of cells in the x (height) and y (width) dimensions.
	 * @return The height and width of the board.
	 */
	public BoardDimensions getBoardDimensions();
	
}
