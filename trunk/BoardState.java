import java.util.ArrayList;
import java.util.Map;


public class BoardState implements IBoardState{
	
	private class ShipData
	{
		public ArrayList<Coordinate> myCoords;
		public BattleshipPlacement myBP;
		public ShipShape mySS;
		
		public ShipData(BattleshipPlacement bp, ShipShape ss)
		{
			myCoords = new ArrayList<Coordinate>(bp.getPoints());
			myBP = bp;
			mySS = ss;
		}	
	}
	
	private BoardDimensions myBoardDims;
	private CellState[][] myBoard;
	private IBattleshipModel myModel;
	private ArrayList<ShipData> myShips;

	public BoardState()
	{
		myShips = new ArrayList<ShipData>();
	}
	
	public CellState getState(Coordinate c) {
		//System.err.println(myBoardDims.getWidth()+"x"+myBoardDims.getHeight());
		if(!isCoordinateValid(c))
			return CellState.INVALID;
		return myBoard[c.myX][c.myY];
	}
	
	public void setModel(IBattleshipModel model)
	{
		myModel = model;
	}
	
	public ArrayList<Coordinate> getUnknownCoordinates() {
		ArrayList<Coordinate> unknownCoords = new ArrayList<Coordinate>();
		for (int x = 0; x < myBoardDims.getWidth(); x++){
			for (int y = 0; y < myBoardDims.getHeight(); y++){
				if ((myBoard[x][y] != CellState.HIT) && (myBoard[x][y] != CellState.MISS) && (myBoard[x][y] != CellState.SUNK))
				{
					unknownCoords.add(new Coordinate(x, y));
				}
			}
		}
		return unknownCoords;
	}

	public boolean isCoordinateValid(Coordinate c) {
		return ((c.myX < myBoardDims.getWidth()) && (c.myY < myBoardDims.getHeight()) && (c.myX >= 0) && (c.myY >= 0));
	}

	public void newGame(BoardDimensions bd) {
		myBoardDims = bd;
		myBoard = new CellState[myBoardDims.getWidth()][myBoardDims.getHeight()];
		for (int x = 0; x < myBoardDims.getWidth(); x++){
			for (int y = 0; y < myBoardDims.getHeight(); y++){
				myBoard[x][y] = CellState.NOSHIP;
				}
			}
		}
	
	//@Override
	public boolean setState(Coordinate c, CellState newState) {
		//System.err.println("Setting State: ("+c.myX+","+c.myY+") = "+newState);
		myBoard[c.myX][c.myY] = newState;
		return true;
	}

	//@Override
	public void addShip(BattleshipPlacement bp, ShipShape ss) {
		myShips.add(new ShipData(bp, ss));
		for (Coordinate c : bp.getPoints())
			setState(c, CellState.SHIP);
	}

	public void processHit(Coordinate c) {
		for(ShipData entry : myShips)
		{
			if(entry.myCoords.contains(c))
			{
				//System.err.println("Found a match!  "+entry.mySS.getName());
				//System.err.println("Still has "+entry.myCoords.size()+" blocks left");
				entry.myCoords.remove(c);
				
				if(entry.myCoords.isEmpty())
				{	
					//System.err.println("SUNK IT! ("+entry.mySS.getName()+")");
					myModel.shipSunk(entry.mySS, entry.myBP);
					myShips.remove(entry);
					for(Coordinate coord : entry.myBP.getPoints())
						this.setState(coord, CellState.SUNK);
					if(myShips.isEmpty())
						myModel.gameIsOver(entry.myBP.getPlayer());
				}
				else
					this.setState(c, CellState.HIT);
				break;
			}
		}
	}
	
	public void processMiss(Coordinate c)
	{
		if(isCoordinateValid(c))
			this.setState(c, CellState.MISS);
	}

	//@Override
	public boolean gameOver() {
		return myShips.isEmpty();
	}

}
