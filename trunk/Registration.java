
public class Registration extends NetMessage {
	
	public boolean myResponse = false;
	public Registration(IBattleshipPlayer p)
	{
		super(p,false,true);
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		myResponse = m.registerPlayer(proxy);
	}
}
