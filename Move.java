
public class Move extends NetMessage {
	
	public Coordinate myCoordinate = null;
	public CellState myCS = null;
	public ShipShape mySS = null;
	
	public Move(IBattleshipPlayer me, Coordinate c)
	{
		super(me,false,false);
		myCoordinate = c;
	}
	
	public Move(BattleshipMove bm, CellState newState, ShipShape ss)
	{
		super(bm.getPlayer(),false,false);
		myCoordinate = bm.getCoordinate();
		myCS = newState;
		mySS = ss;
	}
	public void clientExecute(IBattleshipPlayer p)
	{
		if(p.getName().equals(myPlayerName))
			p.processMove(new BattleshipMove(p, myCoordinate), myCS, mySS);
		else
			p.processMove(new BattleshipMove(new BattleshipPlayer(myPlayerName), myCoordinate), myCS, mySS);
	}
}
