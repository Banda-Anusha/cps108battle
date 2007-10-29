
public class BattleMain {
    public static void main(String[] args){
    	
    	IBattleshipModel gameModel = new BattleshipModel();
    	RuleDeck rules = new RuleDeck();
    	gameModel.setRules(rules);
    	
    	IBattleshipPlayer p1, p2;
    	
    	
    	if(args[0].equalsIgnoreCase("h"))
    		p1 = new BattleshipPlayer("Player 1");
    	else
    		p1 = new BattleshipPlayerAI("Player 1");
    	
    	if(args[1].equalsIgnoreCase("h"))
    		p2 = new BattleshipPlayer("Player 2");
    	else
    		p2 = new BattleshipPlayerAI("Player 2");
    	
        BattleshipView p1BattleView = new BattleshipView(p1, "CPS108 Battleship, Player 1");
        p1.setView(p1BattleView);
        
        BattleshipView p2BattleView = new BattleshipView(p2, "CPS108 Battleship, Player 2");
        p2.setView(p2BattleView);
                
        gameModel.registerPlayer(p1);
        gameModel.registerPlayer(p2);
        
        gameModel.newGame();
        while(true)
        {
        	gameModel.newGame();
        }
    }
}
