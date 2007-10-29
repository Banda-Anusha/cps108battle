import java.util.*;


public class BattleshipPlayer implements IBattleshipPlayer{

	private String myName;
	IBattleshipModel myModel;
	IBattleshipView myView;
	List<BattleshipMove> myMoves;
	List<BattleshipPlacement> myPlacements;
	
	public BattleshipPlayer(String name)
	{
		myName = name;
		myMoves = Collections.synchronizedList(new ArrayList<BattleshipMove>());
		myPlacements = Collections.synchronizedList(new ArrayList<BattleshipPlacement>());
	}
	
	public void setModel(IBattleshipModel ibm)
	{
		myModel = ibm;
	}
	
	public void setView(IBattleshipView ibv)
	{
		myView = ibv;
	}
	
	public void setName(String name)
	{
		myName = name;
	}
	
	public boolean addMove(BattleshipMove move) {
		if(move == null || myModel.isMoveValid(move))
			myMoves.add(move);
		return myModel.isMoveValid(move);
	}

	public boolean addShipPlacement(BattleshipPlacement place, ShipShape shape) {
		if(place == null || myModel.isShipPlacementValid(place,shape))
			myPlacements.add(place);
		return myModel.isShipPlacementValid(place, shape);
	}

	public BattleshipMove getMove() {
		if(!myMoves.isEmpty())
			return myMoves.remove(0);
		myView.needMove();
		return myMoves.remove(0);		
	}

	public String getName() {
		return myName;
	}

	public BattleshipPlacement getShipPlacement(ShipShape shape) {
		if(!myPlacements.isEmpty())
			return myPlacements.remove(0);
		ArrayList<Coordinate> a = myView.needPlacement(shape);
		return (a == null) ? null : new BattleshipPlacement(this, a);
	}

	public void newGame() {
		myMoves.clear();
		myPlacements.clear();
		if(myView != null)
			myView.newGame();
	}

	public void processMove(BattleshipMove m, CellState newState, ShipShape shape) {
		myView.showMove(m, newState);
	}

	public void forfeit()
	{
		myModel.forfeit(this);
	}
	
	public void gameIsOver(IBattleshipPlayer winner)
	{
		if(myView != null)
			myView.gameOver(winner);
	}
	
	public void setTurn(IBattleshipPlayer itsYourTurn)
	{
		if(myView != null)
			myView.setTurn(itsYourTurn);
	}
	
	public void updateScore(IBattleshipPlayer player, int newScore)
	{
		if(myView != null)
			myView.updateScore(player, newScore);
	}
	
	public void reset()
	{
		if(myView != null)
			myView.reset(myModel.getBoardSize());
	}

	public boolean isMoveValid(Coordinate move)
	{
		return myModel.isMoveValid(new BattleshipMove(this, move));
	}
	
	public boolean isPlacementValid(ArrayList<Coordinate> al, ShipShape ss)
	{
		return myModel.isShipPlacementValid(new BattleshipPlacement(this, al), ss);
	}
	
	public void addShip(BattleshipPlacement sp)
	{
		if(myView != null)
			myView.addBattleship(sp);
	}
	
	public void shipSunk(BattleshipPlacement sp, ShipShape ss)
	{
		if(myView != null)
			myView.shipSunk(sp, ss);
	}
}
