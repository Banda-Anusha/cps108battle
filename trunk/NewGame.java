
public class NewGame extends NetMessage{

	public NewGame(IBattleshipPlayer p)
	{
		super(p,false,false);
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		p.newGame();
	}
}
