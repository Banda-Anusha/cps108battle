
public class Reset extends NetMessage{

	public Reset(IBattleshipPlayer p)
	{
		super(p,false,false);
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		p.reset();
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		m.reset();
	}
}
