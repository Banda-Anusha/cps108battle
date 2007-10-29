
public class BoardSizeQuery extends NetMessage{
	public BoardDimensions myDims;
	
	public BoardSizeQuery(IBattleshipPlayer p)
	{
		super(p,false,true);
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		myDims = m.getBoardSize(); 
	}

}
