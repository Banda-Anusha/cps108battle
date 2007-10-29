
public class Forfeit extends NetMessage {
	
	public Forfeit(IBattleshipPlayer p)
	{
		super(p,false,false);
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		m.forfeit(proxy);
	}
}
