
public class BattleMain {
    public static void main(String[] args){
    	
    	
    	RuleDeck rules = new RuleDeck();
    	BoardDimensions boardSize = rules.getBoardDimensions();
    	
    	BattleshipPlayer p1 = new BattleshipPlayer("Player 1");
    	//p1.setName("Peter");
    	BattleshipPlayer p2 = new BattleshipPlayer("Player 2");
    	//p2.setName("Mary");
    	
        BattlePanel yourShipPanel = new BattlePanel(boardSize);
        BattlePanel yourShotPanel = new BattlePanel(boardSize);
        BattleshipView yourBattleView = new BattleshipView(p1, yourShipPanel, yourShotPanel, "Your Battleship Board");
        p1.setView(yourBattleView);
        
        BattlePanel theirShipPanel = new BattlePanel(boardSize);
        BattlePanel theirShotPanel = new BattlePanel(boardSize);
        BattleshipView theirBattleView = new BattleshipView(p2, theirShipPanel, theirShotPanel, "Their Battleship Board");
        p2.setView(theirBattleView);
        IBattleshipModel gameModel = new BattleshipModel();
                
        gameModel.registerPlayer(p1, yourBattleView);
        gameModel.registerPlayer(p2, theirBattleView);
        gameModel.setRules(rules);
        
        gameModel.newGame();
    }
}
