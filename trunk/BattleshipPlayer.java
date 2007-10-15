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
		if(!myModel.isMoveValid(move))
			return false;
		else
		{
			myMoves.add(move);
			return true;
		}
	}

	public boolean addShipPlacement(BattleshipPlacement place, ShipShape shape) {
		if(!myModel.isShipPlacementValid(place, shape))
			return false;
		else
		{
			myPlacements.add(place);
			return true;
		}
	}

	public BattleshipMove getMove() {
		while(myMoves.isEmpty())
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		return myMoves.remove(0);		
	}

	public String getName() {
		return myName;
	}

	public BattleshipPlacement getShipPlacement(ShipShape shape) {
		return new BattleshipPlacement(this, myView.needPlacement(shape));
	}

	public void newGame() {
		// TODO Auto-generated method stub
		
	}

	public void processMove(BattleshipMove m, CellState newState, ShipShape shape) {
		myView.showMove(m, newState);
	}
}
