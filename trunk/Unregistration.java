
public class Unregistration extends NetMessage {

	public Unregistration(IBattleshipPlayer p)
	{
		super(p,false,false);
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		m.removePlayer(proxy);
	}
}
