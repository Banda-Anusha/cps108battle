
public class MoveQuery extends NetMessage {
	public Coordinate myCoordinate;
	public boolean myResponse = false;
	
	public MoveQuery(BattleshipMove move)
	{
		super(move.getPlayer(),false,true);
		myCoordinate = move.getCoordinate();
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		myResponse = m.isMoveValid(new BattleshipMove(proxy, myCoordinate));
	}
	
}
