import java.util.ArrayList;


public class PlacementQuery extends NetMessage{
	public ArrayList<Coordinate> myPoints;
	public ShipShape mySS;
	public boolean myResponse = false;
	
	public PlacementQuery(BattleshipPlacement bp, ShipShape ss)
	{
		super(bp.getPlayer(),false,true);
		myPoints = (bp == null) ? null : bp.getPoints();
		mySS = ss;
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		myResponse = m.isShipPlacementValid(new BattleshipPlacement(proxy, myPoints), mySS);
	}
}
