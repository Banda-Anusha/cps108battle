import java.util.*;


public class BattleshipModel implements IBattleshipModel{
	
	private static final int MaxPlayers = 2;
	
	private class PlayerData
	{
		public IBattleshipPlayer myPlayer;
		public IBoardState myState;
		public IBattleshipView myView;
		public int myScore;
	}
	
	private IRuleDeck myRuleDeck;
	private ArrayList<PlayerData> myPlayers;
	private IBattleshipPlayer myLoser;
	
	public int getScore(IBattleshipPlayer p) {
		for(PlayerData pd : myPlayers)
			if(pd.myPlayer.equals(p))
				return pd.myScore;
		return -1;
	}

	public boolean isMoveValid(BattleshipMove m) {
		for(PlayerData pd : myPlayers)
			if(!pd.myPlayer.equals(m.getPlayer()))
				return myRuleDeck.isMoveValid(pd.myState, m);
		return false;
	}

	public boolean isShipPlacementValid(BattleshipPlacement place, ShipShape shape) {
		for(PlayerData pd : myPlayers)
			if(pd.myPlayer.equals(place.getPlayer()))
				return myRuleDeck.isShipPlacementValid(pd.myState, place, shape);
		return false;
	}

	public void newGame() {
		myLoser = null;
		for(PlayerData pd : myPlayers)
		{
			pd.myPlayer.newGame();
			if(pd.myView != null)
				pd.myView.newGame();
			pd.myState.newGame(myRuleDeck.getBoardDimensions());
		}
		
		//Round robin
		int turnNumber = 0;
		PlayerData currentOffense = myPlayers.get(0);
		PlayerData currentDefense = myPlayers.get(1);
		placeShips();
		while(myLoser == null)
		{
			if(currentOffense.myView != null)
			{
				currentOffense.myView.setTurn(currentOffense.myPlayer);
			}
			if(currentDefense.myView != null)
			{
				currentDefense.myView.setTurn(currentOffense.myPlayer);
			}
			
			BattleshipMove nextMove = currentOffense.myPlayer.getMove();
			System.err.println("Got ("+nextMove.getCoordinate().myX+","+nextMove.getCoordinate().myY+") from "+nextMove.getPlayer().getName());
			
			CellState pastStateOfCell = currentDefense.myState.getState(nextMove.getCoordinate());
			
			System.err.println("Past State: "+pastStateOfCell);
			
			if(pastStateOfCell == CellState.SHIP) //There was a ship there, this is a hit.
			{
				currentOffense.myPlayer.processMove(nextMove, CellState.HIT, null); 
				currentDefense.myState.processHit(nextMove.getCoordinate());
				//TODO: roll in ShipShape info
				currentDefense.myPlayer.processMove(nextMove, CellState.HIT, null);
			}
			else if(pastStateOfCell == CellState.NOSHIP)
			{
				currentOffense.myPlayer.processMove(nextMove, CellState.MISS, null);
				currentDefense.myState.processMiss(nextMove.getCoordinate());
				currentDefense.myPlayer.processMove(nextMove, CellState.MISS, null);
			}
				
			//Switch turns
			PlayerData temp = currentOffense;
			currentOffense = currentDefense;
			currentDefense = temp;
		}
		IBattleshipPlayer winner = myPlayers.get(0).myPlayer;
		for(PlayerData pd : myPlayers)
			if(!(pd.myPlayer == myLoser))
			{
				winner = pd.myPlayer;
				pd.myScore++;
			}
		for(PlayerData pd : myPlayers)
		{
			if(pd.myView != null)
			{
				pd.myView.gameOver(winner);
				for(PlayerData pd2 : myPlayers)
					pd.myView.updateScore(pd2.myPlayer, pd2.myScore);
			}
			pd.myPlayer.newGame();
		}	
	}

	public void placeShips(){
		for(PlayerData pd : myPlayers){
			for (ShipShape ship : myRuleDeck.getShips()){
				BattleshipPlacement bp = pd.myPlayer.getShipPlacement(ship);
				if(pd.myView != null)
					pd.myView.addBattleship(bp);
				pd.myState.addShip(bp, ship);
			}
			if(pd.myView != null)
				pd.myView.showMessage("Done Placing Ships", MessageType.INFO);
		}
		System.err.println("done placing.");
	}
	
	public boolean registerPlayer(IBattleshipPlayer p, IBattleshipView v) {
		if (myPlayers.size() < BattleshipModel.MaxPlayers)
		{
			PlayerData pd = new PlayerData();
			pd.myPlayer = p;
			pd.myPlayer.setModel(this);
			pd.myView = v;
			if(pd.myView != null)
				pd.myView.setModel(this);
			pd.myState = new BoardState();
			pd.myState.setModel(this);
			pd.myScore = 0;
			myPlayers.add(pd);
			return true;
		}
		else
			return false;
	}

	public void removePlayer(IBattleshipPlayer p) {
		myPlayers.remove(p);
	}

	public void reset() {
		for(PlayerData pd : myPlayers)
		{
			pd.myState.newGame(myRuleDeck.getBoardDimensions());
			if(pd.myView != null)
				pd.myView.reset(myRuleDeck.getBoardDimensions());
			pd.myScore = 0;
		}
	}

	public void setRules(IRuleDeck rules) {
		myRuleDeck = rules;
	}
	
	public BattleshipModel(){
		myPlayers = new ArrayList<PlayerData>();
	}

	public void shipSunk(ShipShape ss, BattleshipPlacement sp) {
		for(PlayerData pd : myPlayers)
		{
				//for (int idx = 0; idx < sp.getPoints().size(); idx++){
				//	pd.myState.setState(sp.getPoints().get(idx), CellState.SUNK);
				//}
				if(pd.myView != null)
					pd.myView.shipSunk(sp, ss);				
		}
	}
	
	public void gameIsOver(IBattleshipPlayer loser)
	{
		myLoser = loser;
	}

	public BoardDimensions getBoardSize() {
		return myRuleDeck.getBoardDimensions();
	}
	
}
