import java.util.*;
import java.io.Serializable;

public class BattleshipModel implements IBattleshipModel, Serializable{
	
	private static final int MaxPlayers = 2;
	
	private class PlayerData implements Serializable
	{
		public IBattleshipPlayer myPlayer;
		public IBoardState myState;
		public int myScore;
	}
	
	private IRuleDeck myRuleDeck;
	private ArrayList<PlayerData> myPlayers;
	private IBattleshipPlayer myLoser = null;
	private IBattleshipPlayer myForfeiter = null;
	
	public int getScore(IBattleshipPlayer p) {
		for(PlayerData pd : myPlayers)
			if(pd.myPlayer.equals(p))
				return pd.myScore;
		return -1;
	}

	public boolean isMoveValid(BattleshipMove m) {
		if(m == null)
			return false;
		for(PlayerData pd : myPlayers)
			if(!pd.myPlayer.equals(m.getPlayer()))
				return myRuleDeck.isMoveValid(pd.myState, m);
		return false;
	}

	public boolean isShipPlacementValid(BattleshipPlacement place, ShipShape shape)
	{
		if(place == null || shape == null)
			return false;
		for(PlayerData pd : myPlayers)
			if(pd.myPlayer.equals(place.getPlayer()))
				return myRuleDeck.isShipPlacementValid(pd.myState, place, shape);
		return false;
	}

	public void newGame() {
		myLoser = null;
		for(PlayerData pd : myPlayers)
		{
			//System.out.println("newGame() on "+pd.myPlayer);
			pd.myPlayer.newGame();
			pd.myState.newGame(myRuleDeck.getBoardDimensions());
		}
		
		//Round robin
		int turnNumber = 0;
		PlayerData currentOffense = myPlayers.get(0);
		PlayerData currentDefense = myPlayers.get(1);
		if(!placeShips(true))
			return;
		while(myLoser == null)
		{
			currentOffense.myPlayer.setTurn(currentOffense.myPlayer);
			currentDefense.myPlayer.setTurn(currentOffense.myPlayer);
			
			BattleshipMove nextMove = currentOffense.myPlayer.getMove();
			if(nextMove == null || myForfeiter != null) //Game over
			{
				myForfeiter = null;
				return;
			}
			
			//System.err.println("Got ("+nextMove.getCoordinate().myX+","+nextMove.getCoordinate().myY+") from "+nextMove.getPlayer().getName());
			
			CellState pastStateOfCell = currentDefense.myState.getState(nextMove.getCoordinate());
			
			//System.err.println("Past State: "+pastStateOfCell);
			
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
			pd.myPlayer.gameIsOver(winner);
			for(PlayerData pd2 : myPlayers)
				pd.myPlayer.updateScore(pd2.myPlayer, pd2.myScore);
		}	
		//System.err.println("Game done.");
	}

	public boolean placeShips(boolean allPlace){
		for(int i = ((allPlace) ? 0 : (myPlayers.size() - 1)); i < myPlayers.size(); i++){
			PlayerData pd = myPlayers.get(i);
			for (ShipShape ship : myRuleDeck.getShips()){
				BattleshipPlacement bp = pd.myPlayer.getShipPlacement(ship);
				if(bp == null || myForfeiter != null)
				{
					myForfeiter = null;
					return false;
				}
				pd.myPlayer.addShip(bp);
				pd.myState.addShip(bp, ship);
			}
		}
		//System.err.println("done placing.");
		return true;	
	}
	
	public void new1PGame()
	{
		myLoser = null;
		for(PlayerData pd3 : myPlayers)
		{
			pd3.myState.newGame(myRuleDeck.getBoardDimensions());
			pd3.myPlayer.newGame();
		}
		
		//Player 0 always gets to go
		int turnNumber = 1;
		PlayerData currentOffense = myPlayers.get(0);
		PlayerData currentDefense = myPlayers.get(1);
		if(!placeShips(false))
			return;
		while(myLoser == null)
		{
			currentOffense.myPlayer.setTurn(currentOffense.myPlayer);
			currentDefense.myPlayer.setTurn(currentOffense.myPlayer);
			
			BattleshipMove nextMove = currentOffense.myPlayer.getMove();
			if(nextMove == null || myForfeiter != null) //Game over
			{
				myForfeiter = null;
				return;
			}
			
			//System.err.println("Got ("+nextMove.getCoordinate().myX+","+nextMove.getCoordinate().myY+") from "+nextMove.getPlayer().getName());
			
			CellState pastStateOfCell = currentDefense.myState.getState(nextMove.getCoordinate());
			
			//System.err.println("Past State: "+pastStateOfCell);
			
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
			turnNumber++;
		}
		IBattleshipPlayer winner = myPlayers.get(0).myPlayer;
		for(PlayerData pd : myPlayers)
			if(!(pd.myPlayer == myLoser))
			{
				winner = pd.myPlayer;
				pd.myScore += turnNumber;
			}
		for(PlayerData pd : myPlayers)
		{
			pd.myPlayer.gameIsOver(winner);
			for(PlayerData pd2 : myPlayers)
				pd.myPlayer.updateScore(pd2.myPlayer, pd2.myScore);
		}	
		//System.err.println("Game done.");
	}
	
	public boolean registerPlayer(IBattleshipPlayer p) {
		if (myPlayers.size() < BattleshipModel.MaxPlayers)
		{
			PlayerData pd = new PlayerData();
			pd.myPlayer = p;
			pd.myPlayer.setModel(this);
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
			pd.myPlayer.reset();
			pd.myScore = 0;
			for(PlayerData pd2 : myPlayers)
				pd.myPlayer.updateScore(pd2.myPlayer, 0);
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
			pd.myPlayer.shipSunk(sp, ss);				
		}
	}
	
	public void gameIsOver(IBattleshipPlayer loser)
	{
		myLoser = loser;
	}
	
	public void forfeit(IBattleshipPlayer forfeiter)
	{
		myForfeiter = forfeiter;
		IBattleshipPlayer winner = forfeiter; //Will be changed in 2P mode.
		for(PlayerData pd : myPlayers)
			if(pd.myPlayer != forfeiter)
			{
				winner = pd.myPlayer;
				break;
			}
		for(PlayerData pd : myPlayers)
		{
			pd.myPlayer.addMove(null);
			pd.myPlayer.addShipPlacement(null, null);
			pd.myPlayer.gameIsOver(winner);
		}
	}
	
	public BoardDimensions getBoardSize() {
		return myRuleDeck.getBoardDimensions();
	}	
	
	public int numberOfPlayers() {
		return myPlayers.size();
	}
}
