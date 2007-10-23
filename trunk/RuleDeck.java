import java.util.*;


public class RuleDeck implements IRuleDeck{
	
	
	private static int Width = 10;
	private static int Height = 10;
	private static int MaxPlayers = 2;
	private ArrayList<ShipShape> myShips;
	
	public RuleDeck() {
		myShips = new ArrayList<ShipShape>();
		initializeShips();
	}
	
	/**
	 * This method will initialize myShips to the standard set of five ships
	 * in the original game
	 */
	private void initializeShips() {
		myShips = new ArrayList<ShipShape>();
		ArrayList<Coordinate> shipCoords = new ArrayList<Coordinate>();
		for (int x=0; x < 2; x++){
			shipCoords.add(new Coordinate(x,0));
		}
		myShips.add(new ShipShape("Destroyer", shipCoords));
		shipCoords.add(new Coordinate(2,0));
		myShips.add(new ShipShape("Submarine", shipCoords));
		myShips.add(new ShipShape("Cruiser", shipCoords));
		shipCoords.add(new Coordinate(3,0));
		myShips.add(new ShipShape("Battleship", shipCoords));
		shipCoords.add(new Coordinate(4,0));
		myShips.add(new ShipShape("Carrier", shipCoords));
	}

	public BoardDimensions getBoardDimensions() {
		return new BoardDimensions(Width, Height);
	}

	public int getMaxPlayers() {
		return MaxPlayers;
	}

	public ArrayList<ShipShape> getShips() {
		return new ArrayList<ShipShape>(myShips);
	}

	public boolean isMoveValid(IBoardState bs, BattleshipMove m) {
		CellState cs = bs.getState(m.getCoordinate());
		System.err.println("isMoveValid State: "+cs+" @ ("+m.getCoordinate().myX+","+m.getCoordinate().myY+")");
		if(cs == CellState.INVALID)
			return false;
		if((cs == CellState.MISS) || (cs == CellState.HIT) || (cs == CellState.SUNK))
			return false;
		return true;
	}

	public boolean isShipPlacementValid(IBoardState bs, BattleshipPlacement bp, ShipShape sh) {
		ArrayList<Coordinate> points = bp.getPoints();
		
		if(points.size() != sh.getPoints().size())
			return false;
		
		if(points.size() < 2)
			return true;
		
		Collections.sort(points, new Comparator<Coordinate>()
		{
		       public int compare(Coordinate c1, Coordinate c2) {
		            if(c1.myX != c2.myX)
		            	return (new Integer(c1.myX).compareTo(c2.myX));
		            else
		            	return (new Integer(c1.myY).compareTo(c2.myY));
		        }
		});
		
		Coordinate c1 = points.get(0);
		Coordinate c2 = points.get(1);
		if(c1.myX != c2.myX) //Horizontal ship
		{
			//Check to make sure c2 is 1 to the right of c1
			if((c2.myY != c1.myY) || (c2.myX != (c1.myX + 1)))
				return false;
			//Make sure the rest of the points are 1 to the right of one another.
			for(int i = 2; i < points.size(); i++)
				if((points.get(i).myY != c1.myY) || (points.get(i).myX != (c1.myX + i)))
					return false;
		}
		else //Vertical ship
		{
			//Check to make sure c2 is 1 up from c1
			if((c2.myX != c1.myX) || (c2.myY != (c1.myY + 1)))
				return false;
			//Make sure the rest of the points are 1 up from one another.
			for(int i = 2; i < points.size(); i++)
				if((points.get(i).myX != c1.myX) || (points.get(i).myY != (c1.myY + i)))
					return false;
		}
		
		//Check to make sure none of the coordinates are already taken
		for(Coordinate c : points)
		{
			if(bs.getState(c) != CellState.NOSHIP)
				return false;
			System.err.println("("+c.myX+","+c.myY+")");
		}
		
		System.err.println("Valid placement for "+sh.getName());
		
		return true;
	}

}
