import java.util.ArrayList;


public class ShipSunk extends NetMessage{

	public ArrayList<Coordinate> myPoints;
	public ShipShape mySS;
	
	public ShipSunk(BattleshipPlacement bp, ShipShape ss)
	{
		super(bp.getPlayer(),false,false);
		myPoints = bp.getPoints();
		mySS = ss;
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		if(p.getName().equals(myPlayerName))
			p.shipSunk(new BattleshipPlacement(p,myPoints), mySS);
		else
			p.shipSunk(new BattleshipPlacement(new BattleshipPlayer(myPlayerName), myPoints), mySS);
	}
	
}
