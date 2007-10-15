/**
 * This Interface defines the functions for a Model in an MVC framework for a Battleship game.
 * The model keeps track of the board state, score, and so forth, and is really the core of the game.
 * The interface contains methods for two main consumers: Players and the main class.
 * The Players can access game-related information like score, and whether or not a given ship placement or move is valid.
 * The main class can reset a series of games or start individual games, and set the Rule Deck the model will use to determine move and placement validity as well as ship and board shapes and sizes.
 * @author Matt Johnson
 *
 */

public interface IBattleshipModel {
	/**
	 * This method should be called when a "series" is to be started.  
	 * This should be called when the Battleship "program" is first invoked,
	 * as well as any time we want to bring in new players,
	 * or when we want to reset the score.
	 */
	public void reset();
	/**
	 * This method should be called whenever we want to add a player to the game.
	 * @param p The player to be added.
	 * @param v The view with which this player is to be associated (null if none).
	 * @return Whether or not the player was successfully added.  It will most likely fail if you're in the middle of an existing game or
	 * if the maximum number of players has already been reached.
	 */
	public boolean registerPlayer(IBattleshipPlayer p, IBattleshipView v);
	/**
	 * This method removes a player from the game.
	 * @param p The player to be removed.
	 */
	public void removePlayer(IBattleshipPlayer p);
	/**
	 * This should be called whenever a new game of Battleship should be started.  This does not reset the score, which is the running tally of games won for each player.
	 * This method actually plays a game of Battleship, using the IBattleshipPlayers already registered.
	 */
	public void newGame();
	/**
	 * Players can use this method to ask the model if a proposed ship placement is valid, given the rules of the game and previous ship placements, etc.
	 * The model will usually forward the request on to the rule deck.
	 * @param place The proposed placement of the ship
	 * @param shape The shape of the ship the Player is trying to place.  This can be used to correlate the request to a particular ship type.
	 * @return Whether or not the proposed placement is valid.
	 */
	public boolean isShipPlacementValid(BattleshipPlacement place, ShipShape shape);
	/**
	 * Players can use this method to ask the model if a proposed move is valid, given the state of the game.
	 * The model will usually pass this request along to the Rule Deck, along with the current state.
	 * @param m The proposed move to be performed.
	 * @return Whether or not the proposed move is allowed.
	 */
	public boolean isMoveValid(BattleshipMove m);
	/**
	 * This method is used to set the rule deck used for placement/move validity.
	 * @param rules The rule deck to be used
	 */
	public void setRules(IRuleDeck rules);
	/**
	 * This method is used to get the score for a particular Player.
	 * @param p The Player whose score is desired.
	 * @return The Player's score if the Player is playing in the current game, or -1 if not playing.
	 */
	public int getScore(IBattleshipPlayer p);
	
	public void shipSunk(ShipShape ss, BattleshipPlacement sp);
	public void gameIsOver(IBattleshipPlayer winner);
}
