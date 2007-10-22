
/**
 * This Interface defines the methods a Player class must implement in order to pass information between itself, the Model, and the View.
 * @author Matt Johnson
 *
 */
public interface IBattleshipPlayer {
	/**
	 * This method returns the name of this individual Player (not the implementation class name).
	 * @return The Player's name.
	 */
	public String getName();
	
	/**
	 * This method tells the Player that a new game has begun.  The player might reset its board state, change strategies, etc..
	 */
	public void newGame();
	
	/**
	 * This method is used by the model to tell the Player the result of its previous move.
	 * @param m The move whose result is given.
	 * @param newState The new state of that move's coordinate.
	 * @param shape If the previous move was a HIT or SINK, this parameter corresponds to the ship that was 
	 * hit or sunk.  Otherwise, this parameter will be null.
	 */
	public void processMove(BattleshipMove m, CellState newState, ShipShape shape);
	
	/**
	 * This method is used by the Model to get a move from the player.  This paradigm is preferable to the Player "telling" the Model
	 * what its next move will be because it always ensures that nobody goes out of turn and prevents cheating.
	 * @return The next move the Player will make (already validated by the Rule Deck through the Model).
	 */
	public BattleshipMove getMove();
	/**
	 * This method is used by the Model to get ship placements from the player.  The method will be called once for each shape,
	 * and the Player class can either calculate the placement itself (if it is an AI player) or ask the view to place the ship.
	 * @param shape The shape of the ship to be placed
	 * @return The placement for the ship (already validated by the Rule Deck through the Model)
	 */
	public BattleshipPlacement getShipPlacement(ShipShape shape);
	/**
	 * This method gives the View a back-end way to insert moves into the queue.  This allows the action of
	 * the Player asking the View to place a ship, and the View telling the Player where the ship is to be placed, to be asynchronous to one another.
	 * @param move The move to be potentially performed by the Player.
	 * @return Whether or not the move is valid, as determined by the Rule Deck through the Model.  The caller should try again if <code>false</code> is returned.
	 */
	public boolean addMove(BattleshipMove move);
	/**
	 * This method is the analog of addMove() for adding ship placements.
	 * @param place The location and orientation of the ship.
	 * @param shape The original ShipShape passed from the Rule Deck through the 
	 * Model to the Player, allowing the Player, Model, and Rule Deck to correlate
	 * the placement to the original ship when validating the placement.
	 * @return Whether or not the placement is valid.  The caller should try again if <code>false</code> is returned.
	 */
	public boolean addShipPlacement(BattleshipPlacement place, ShipShape shape);
	
	public void setModel(IBattleshipModel ibm);
	public void setName(String name);
}
