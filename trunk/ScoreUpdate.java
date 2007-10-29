
public class ScoreUpdate extends NetMessage{

	public int myScore;
	
	public ScoreUpdate(IBattleshipPlayer p, int score)
	{
		super(p,false,false);
		myScore = score;
	}
	
	//TODO This is a hack that depends on there only being 2 players.
	//It would be much better if the Proxies maintained list of BattleshipPlayer-Proxy
	//pairs, and translated accordingly.
	public void clientExecute(IBattleshipPlayer p)
	{
		if(myPlayerName.equals(p.getName()))
			p.updateScore(p, myScore);
		else
			p.updateScore(new BattleshipPlayer(myPlayerName), myScore);
	}
	
}
