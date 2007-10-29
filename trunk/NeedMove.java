
public class NeedMove extends NetMessage{
	
	Coordinate myCoordinate;
	
	public NeedMove(IBattleshipPlayer p)
	{
		super(p,true,false);
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		myCoordinate = p.getMove().getCoordinate();
	}
}
