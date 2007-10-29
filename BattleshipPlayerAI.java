import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class BattleshipPlayerAI implements IBattleshipPlayer {
	
	private String myName;
	IBattleshipModel myModel;
	IBattleshipView myView;
	BoardDimensions myDims;
	
	int myMissCount = 0;
	BattleshipMove myLastHit;
	
	CellState[][] myShots;
	
	public BattleshipPlayerAI(String name){
		myName = name;
	}

	public boolean addMove(BattleshipMove move) {
		return false;
	}

	public boolean addShipPlacement(BattleshipPlacement place, ShipShape shape) {
		return false;
	}

	public BattleshipMove getMove() {
		BattleshipMove move;
		Random rand = new Random();

			if (myLastHit == null)
			{
				while(true)
				{
					move = new BattleshipMove(this, new Coordinate(rand.nextInt(myDims.getWidth()), rand.nextInt(myDims.getHeight())));
					if (myModel.isMoveValid(move)){
						return move;
					}
				}
			}
			
				if (myModel.isMoveValid(new BattleshipMove(this, new Coordinate(myLastHit.getCoordinate().myX, myLastHit.getCoordinate().myY-1)))){
					return new BattleshipMove(this, new Coordinate(myLastHit.getCoordinate().myX, myLastHit.getCoordinate().myY-1));
				}
				else if (myModel.isMoveValid(new BattleshipMove(this, new Coordinate(myLastHit.getCoordinate().myX+1, myLastHit.getCoordinate().myY)))){
					return new BattleshipMove(this, new Coordinate(myLastHit.getCoordinate().myX+1, myLastHit.getCoordinate().myY));
				}
				else if (myModel.isMoveValid(new BattleshipMove(this, new Coordinate(myLastHit.getCoordinate().myX, myLastHit.getCoordinate().myY+1)))){
					return new BattleshipMove(this, new Coordinate(myLastHit.getCoordinate().myX, myLastHit.getCoordinate().myY+1));
				}
				else if (myModel.isMoveValid(new BattleshipMove(this, new Coordinate(myLastHit.getCoordinate().myX-1, myLastHit.getCoordinate().myY)))){
					return new BattleshipMove(this, new Coordinate(myLastHit.getCoordinate().myX-1, myLastHit.getCoordinate().myY));
				}
				else{
					while(true)
					{
						move = new BattleshipMove(this, new Coordinate(rand.nextInt(myDims.getWidth()), rand.nextInt(myDims.getHeight())));
						if (myModel.isMoveValid(move)){
							return move;
						}
					}
				}
	}

	public CellState getState(Coordinate c){
		CellState state;
		try{
			state = myShots[c.myX][c.myY];
		}
		catch (ArrayIndexOutOfBoundsException e){
			state = CellState.INVALID;
		}
		
		return state;
	}
	
	public String getName() {
		return myName;
	}

	public BattleshipPlacement getShipPlacement(ShipShape shape) {	
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		Random rand = new Random();

		while(true){
			list.add(new Coordinate(rand.nextInt(myDims.getWidth()), rand.nextInt(myDims.getHeight())));
			
			switch (rand.nextInt(4)){
			case 0: //up
				for (int idx = 0; idx < (shape.getPoints().size()-1); idx++){
					list.add(new Coordinate(list.get(idx).myX, list.get(idx).myY-1));
				}
				break;
			case 1: //right
				for (int idx = 0; idx < (shape.getPoints().size()-1); idx++){
					list.add(new Coordinate(list.get(idx).myX+1, list.get(idx).myY));
				}
				break;
			case 2: //down
				for (int idx = 0; idx < (shape.getPoints().size()-1); idx++){
					list.add(new Coordinate(list.get(idx).myX, list.get(idx).myY+1));
				}
				break;
			case 3: //left
				for (int idx = 0; idx < (shape.getPoints().size()-1); idx++){
					list.add(new Coordinate(list.get(idx).myX-1, list.get(idx).myY));
				}
				break;
			}

			BattleshipPlacement place = new BattleshipPlacement(this, list);
			if (myModel.isShipPlacementValid(place, shape)){
				return place;
			}
			list.clear();
		}
}

	public void newGame() {
		myShots = new CellState[myDims.getWidth()][myDims.getHeight()];
		for(int x = 0; x < myDims.getWidth(); x++){
			for (int y = 0; y < myDims.getHeight(); y++){
				myShots[x][y] = CellState.UNKNOWN;
			}
		}
		if(myView != null)
			myView.newGame();
	}

	public void processMove(BattleshipMove m, CellState newState, ShipShape shape) {
		if(myView != null)
			myView.showMove(m, newState);
		myShots[m.getCoordinate().myX][m.getCoordinate().myY] = newState;

		if (newState == CellState.HIT){
			myMissCount = 0;
			myLastHit = m;
		}
		else 
			myMissCount++;
		
		if (myMissCount > 3) myLastHit = null;//If there's nothing on any side of the last hit, move on.
	}

	public void setModel(IBattleshipModel ibm) {
		myModel = ibm;
		myDims = myModel.getBoardSize();
	}
	
	public void setView(IBattleshipView ibv){
		myView = ibv;
	}
	
	public void setName(String name) {
		myName = name;
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
	
	public void setTurn(IBattleshipPlayer name)
	{
		if(myView != null)
			myView.setTurn(name);
	}
	
	public void updateScore(IBattleshipPlayer player, int newScore)
	{
		if(myView != null)
			myView.updateScore(player, newScore);
	}
	
	public void reset()
	{
		myDims = myModel.getBoardSize();
		if(myView != null)
			myView.reset(myDims);
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
