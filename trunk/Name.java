//This object is mostly useless right now.  It will ideally serve as a means to match proxies with player names.

public class Name extends NetMessage{
	public String myName;
	
	public Name(IBattleshipPlayer p)
	{
		super(p,true,false);
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		myName = p.getName();
	}
}
