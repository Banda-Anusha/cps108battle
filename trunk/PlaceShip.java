import java.util.ArrayList;


public class PlaceShip extends NetMessage {

	public ArrayList<Coordinate> myPoints;
	public ShipShape mySS;
	
	public PlaceShip(BattleshipPlacement bp, ShipShape ss)
	{
		super(bp == null ? null : bp.getPlayer(),true,false);
		myPoints = (bp == null) ? null : bp.getPoints();
		mySS = ss;
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		myPoints = p.getShipPlacement(mySS).getPoints();
	}
}
