import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class BattleshipServerProxy implements Runnable, IBattleshipPlayer {

	private NetMessageHandler myNMH;	
	private NetMessageListener myNML;
	private IBattleshipModel myModel;
	private String myPlayerName = null;
	
	public BattleshipServerProxy(IBattleshipModel model, ObjectInputStream in, ObjectOutputStream out)
	{
		myNMH = new NetMessageHandler(in, out);
		myNMH.start();
		myNML = new NetMessageListener(this, model, myNMH, true);
		myNML.start();
		myModel = model;
		//System.err.println("Server proxy "+this);
	}
	
	public void run()
	{
		Registration r = (Registration) myNMH.receive(Registration.class); //Wait till the player registers
		//System.out.println(r.myPlayerName+" Registered through "+this);
		myPlayerName = r.myPlayerName;
		r.myResponse = myModel.registerPlayer(this);
		myNMH.send(r);
		
		myNMH.receive(Unregistration.class);
		myModel.removePlayer(this);
	}
	
	 //Don't need this
	public boolean addMove(BattleshipMove move) {
		return false;
	}

	
	public void addShip(BattleshipPlacement bp) {
		AddShip nm = new AddShip(bp);
		myNMH.send(nm);
	}

	 //Don't need this
	public boolean addShipPlacement(BattleshipPlacement place, ShipShape shape) {
		// TODO Auto-generated method stub
		return false;
	}

	 //Don't need this
	public void forfeit() {
		// TODO Auto-generated method stub

	}

	
	public void gameIsOver(IBattleshipPlayer winner) {
		GameOver nm = new GameOver(winner);
		myNMH.send(nm);
	}

	
	public BattleshipMove getMove() {
		NeedMove nm = new NeedMove(null);
		myNMH.send(nm);
		NeedMove m = (NeedMove) myNMH.receive(NeedMove.class);
		return new BattleshipMove(this, m.myCoordinate);
	}

	
	public String getName() {
		if(myPlayerName != null)
			return myPlayerName;
		Name nm = new Name(null);
		myNMH.send(nm);
		Name n = (Name) myNMH.receive(Name.class);
		myPlayerName = n.myName;
		return myPlayerName;
	}

	
	public BattleshipPlacement getShipPlacement(ShipShape shape) {
		PlaceShip ps = new PlaceShip(null, shape);
		myNMH.send(ps);
		PlaceShip p = (PlaceShip) myNMH.receive(PlaceShip.class);
		return new BattleshipPlacement(this, p.myPoints);
	}

	 //Don't need this
	public boolean isMoveValid(Coordinate move) {
		// TODO Auto-generated method stub
		return false;
	}

	 //Don't need this
	public boolean isPlacementValid(ArrayList<Coordinate> al, ShipShape ss) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void newGame() {
		//System.err.println("ServerProxy "+this+" creating NewGame message.");
		NewGame ng = new NewGame(null);
		//System.err.println("ServerProxy "+this+" sending NewGame message.");
		myNMH.send(ng);
		//System.err.println("ServerProxy "+this+" sent NewGame message.");
	}

	
	public void processMove(BattleshipMove m, CellState newState,
			ShipShape shape) {
		Move move = new Move(m, newState, shape);
		myNMH.send(move);
	}

	
	public void reset() {
		Reset r = new Reset(null);
		myNMH.send(r);
	}

	 //Don't need this
	public void setModel(IBattleshipModel ibm) {
		// TODO Auto-generated method stub

	}

	 //Don't need this
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	
	public void setTurn(IBattleshipPlayer whoseTurn) {
		SetTurn st = new SetTurn(whoseTurn);
		myNMH.send(st);
	}

	//Don't need this
	public void setView(IBattleshipView yourBattleView) {
		// TODO Auto-generated method stub

	}

	
	public void shipSunk(BattleshipPlacement bp, ShipShape ss) {
		ShipSunk shipsunk = new ShipSunk(bp, ss);
		myNMH.send(shipsunk);
	}

	
	public void updateScore(IBattleshipPlayer player, int newScore) {
		ScoreUpdate su = new ScoreUpdate(player, newScore);
		myNMH.send(su);
	}

}
