import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class BattleshipClientProxy implements IBattleshipModel {

	private NetMessageHandler myNMH;	
	private NetMessageListener myNML;
	private IBattleshipPlayer myPlayer;
	
	public BattleshipClientProxy(IBattleshipPlayer player, ObjectInputStream in, ObjectOutputStream out)
	{
		myNMH = new NetMessageHandler(in, out);
		myPlayer = player;
		myNML = new NetMessageListener(player, null, myNMH, false);
		myNML.start();
		//System.err.println("Started NetMessageListener for BattleshipClientProxy");
		myNMH.start();
		//System.err.println("Started NetMessageHandler for BattleshipClientProxy");
		
		boolean success = registerPlayer(myPlayer);
		if(!success)
		{
			System.err.println("Error: Could not register with the model.");
			System.exit(0);
		}
		else
		{
			//System.err.println("Registered with the model.");
		}
	}

	public void forfeit(IBattleshipPlayer forfeiter) {
		Forfeit nm = new Forfeit(forfeiter);
		myNMH.send(nm);
	}

	public void gameIsOver(IBattleshipPlayer loser) {
		// TODO Auto-generated method stub
		
	}

	public BoardDimensions getBoardSize() {
		BoardSizeQuery nm = new BoardSizeQuery(myPlayer);
		myNMH.send(nm);
		BoardSizeQuery bsq = (BoardSizeQuery) myNMH.receive(BoardSizeQuery.class);
		return bsq.myDims;
	}

	public int getScore(IBattleshipPlayer p) {
		ScoreQuery nm = new ScoreQuery(myPlayer, p);
		myNMH.send(nm);
		ScoreQuery sq = (ScoreQuery) myNMH.receive(ScoreQuery.class);
		return sq.myScore;
	}

	public boolean isMoveValid(BattleshipMove m) {
		MoveQuery nm = new MoveQuery(m);
		myNMH.send(nm);
		MoveQuery mq = (MoveQuery) myNMH.receive(MoveQuery.class);
		return mq.myResponse;
	}

	public boolean isShipPlacementValid(BattleshipPlacement place, ShipShape shape) {
		PlacementQuery nm = new PlacementQuery(place, shape);
		myNMH.send(nm);
		PlacementQuery pq = (PlacementQuery) myNMH.receive(PlacementQuery.class);
		return pq.myResponse;
	}

	//Don't need this
	public void newGame() {
		// TODO Auto-generated method stub
		
	}

	public boolean registerPlayer(IBattleshipPlayer p) {
		Registration r = new Registration(p);
		myNMH.send(r);
		Registration r2 = (Registration) myNMH.receive(Registration.class);
		return r2.myResponse;
	}

	public void removePlayer(IBattleshipPlayer p) {
		Unregistration nm = new Unregistration(p);
		myNMH.send(nm);
	}

	//Not needed for now, rules are set server-side
	public void setRules(IRuleDeck rules) {
		// TODO Auto-generated method stub
		
	}
	
	//Not needed
	public void shipSunk(ShipShape ss, BattleshipPlacement sp) {
		// TODO Auto-generated method stub
		
	}

	//Don't need this
	public int numberOfPlayers() {
		// TODO Auto-generated method stub
		return 0;
	}

	//Don't need this
	public void new1PGame() {
		// TODO Auto-generated method stub
		
	}
	
	public void reset()
	{
		Reset r = new Reset(null);
		myNMH.send(r);
	}
	
}
