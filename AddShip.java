import java.util.ArrayList;


public class AddShip extends NetMessage{

	private ArrayList<Coordinate> myPoints;
	
	public AddShip(BattleshipPlacement bp)
	{
		super(bp.getPlayer(),false,false);
		myPoints = (bp == null) ? null : bp.getPoints();
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		if(p.getName().equals(myPlayerName))
			p.addShip(new BattleshipPlacement(p, myPoints));
		else
			p.addShip(new BattleshipPlacement(new BattleshipPlayer(myPlayerName), myPoints));
	}
	
}
