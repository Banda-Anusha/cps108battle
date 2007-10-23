
public class BattleMain {
    public static void main(String[] args){
    	
    	
    	RuleDeck rules = new RuleDeck();
    	BoardDimensions boardSize = rules.getBoardDimensions();
    	
    	IBattleshipPlayer p1, p2;
    	
    	
    	if(args[0].equalsIgnoreCase("h"))
    		p1 = new BattleshipPlayer("Player 1");
    	else
    		p1 = new BattleshipPlayerAI("Player 1");
    	
    	if(args[1].equalsIgnoreCase("h"))
    		p2 = new BattleshipPlayer("Player 2");
    	else
    		p2 = new BattleshipPlayerAI("Player 2");
    	
        BattlePanel p1ShipPanel = new BattlePanel(boardSize);
        BattlePanel p1ShotPanel = new BattlePanel(boardSize);
        BattleshipView p1BattleView = new BattleshipView(p1, p1ShipPanel, p1ShotPanel, "Your Battleship Board");
        p1.setView(p1BattleView);
        
        BattlePanel p2ShipPanel = new BattlePanel(boardSize);
        BattlePanel p2ShotPanel = new BattlePanel(boardSize);
        BattleshipView p2BattleView = new BattleshipView(p2, p2ShipPanel, p2ShotPanel, "Their Battleship Board");
        p2.setView(p2BattleView);
        IBattleshipModel gameModel = new BattleshipModel();
                
        gameModel.registerPlayer(p1, p1BattleView);
        gameModel.registerPlayer(p2, p2BattleView);
        gameModel.setRules(rules);
        
        gameModel.newGame();
    }
}
