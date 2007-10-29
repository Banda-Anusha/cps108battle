
public class GameOver extends NetMessage{
	
	public GameOver(IBattleshipPlayer winner)
	{
		super(winner,false,false); //Doesn't matter what this is.
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		if(p.getName().equals(myPlayerName))
			p.gameIsOver(p);
		else
			p.gameIsOver(new BattleshipPlayer(myPlayerName));
	}
}
