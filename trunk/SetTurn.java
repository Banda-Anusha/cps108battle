
public class SetTurn extends NetMessage {
	
	public SetTurn(IBattleshipPlayer whoseTurnNow)
	{
		super(whoseTurnNow,false,false);
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		if(p.getName().equals(myPlayerName))
				p.setTurn(p);
		else
			p.setTurn(new BattleshipPlayer(myPlayerName));
	}
	
}
