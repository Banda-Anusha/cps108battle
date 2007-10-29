
public class ScoreQuery extends NetMessage {
	public IBattleshipPlayer mySubject;
	public int myScore;
	
	public ScoreQuery(IBattleshipPlayer me, IBattleshipPlayer subject)
	{
		super(me,false,true);
		mySubject = subject;
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		myScore = m.getScore(mySubject);
	}
	
}
