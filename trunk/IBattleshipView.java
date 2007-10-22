import java.util.ArrayList;

/**
 * This interface should be implemented by all Views in the MVC framework for Battleship.
 * It has methods for the Model and main class to push events to the view, including message-passing,
 * game-related events, and lists of ships needed for placement.  This interface is well-suited to 
 * implementation in an ncurses- or GUI-based environment.
 * @author Matt Johnson, Al Waldron
 *
 */
public interface IBattleshipView {
	/**
	 * This method tells the view that a battleship has been added, who placed it, and where.
	 * @param loc The player and ship location.
	 */
	public void addBattleship(BattleshipPlacement loc);
	/**
	 * This method should be called once at the beginning of each "series", and should reset scores and so forth.  The new board dimensions are also provided.
	 * @param bd The board dimensions for the upcoming series.
	 */
	public void reset(BoardDimensions bd);
	/**
	 * This method should be called when a new game is to be started.
	 */
	public void newGame();
	/**
	 * This method updates a player's score.
	 * @param player Which player's score is referenced.
	 * @param newScore What the new score is.
	 */
	public void updateScore(IBattleshipPlayer player, int newScore);
	/**
	 * This method is used to tell the view that the game is over, and who won.
	 * @param winner The player who won this game.
	 */
	public void gameOver(IBattleshipPlayer winner);
	/**
	 * This method is used to tell the view that a move has been entered, and the view should update itself accordingly.
	 * @param move The move to be shown (has already been validated by the rule deck).
	 * @param newState The new state of that cell
	 */
	public void showMove(BattleshipMove move, CellState newState);
	/**
	 * This method is used to tell the view that it is now another player's turn, and the view should update itself accordingly.
	 * @param itsYourTurn The Player whose turn it is.
	 */
	public void setTurn(IBattleshipPlayer itsYourTurn);
	/**
	 * This method is used to tell the model that a ship has been sunk.  The view might want to update itself accordingly.
	 * @param ship Which ship has been sunk.
	 * @param shape TODO
	 */
	public void shipSunk(BattleshipPlacement ship, ShipShape shape);
	/**
	 * This method is used to pass messages from the Player/Model/Rule Deck to the view.
	 * @param msg The text of the message to be displayed/stored.
	 * @param type The type of message.  <code>MessageType</code> is an enumerated type, and different types will be handled
	 * differently by each implementation of this interface. 
	 */
	public void showMessage(String msg, MessageType type);
	/**
	 * This method may be used to notify the view that the specified ship needs to be placed (usually if the Player associated
	 * with this view is human.
	 * @param shape The ship shape to be placed.  The View can relay the human's chosen placement to the Player asynchronously through the addShipPlacement() method.
	 */
	public ArrayList<Coordinate> needPlacement(ShipShape shape);
	
	public void setModel(IBattleshipModel ibm);
	/**
	 * @author Al Waldron
	 * This method shows a hit on both players' boards
	 * @param mySS The shape of the ship that has been hit
	 */
	public void showHit(ShipShape mySS, BattleshipPlacement ship, Coordinate coord);
	/**
	 * @author Al Waldron
	 * This method shows a miss on both players' boards
	 * @param coord This is the coordinate of the errant shot
	 * @param player This is the player that is processing the miss
	 */
	public void showMiss(Coordinate coord, IBattleshipPlayer player);
}
