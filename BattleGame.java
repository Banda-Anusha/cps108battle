import java.net.Socket;
import java.io.*;


public class BattleGame {
    private Socket[] mySocks;
    private ObjectInputStream[] myIns;
    private ObjectOutputStream[] myOuts;
    private Thread myGame;
    private IBattleshipModel myModel;
    private BattleshipServerProxy[] myProxies;
    
    public BattleGame(IBattleshipModel m, Socket s1, Socket s2){
        mySocks = new Socket[2];
        mySocks[0] = s1;
        mySocks[1] = s2;
        myIns = new ObjectInputStream[2];
        myOuts = new ObjectOutputStream[2];
        myGame = new GameThread();
        myModel = m;
        myProxies = new BattleshipServerProxy[2]; 
        myGame.start();
    }
    
    
    
    private class GameThread extends Thread{
    	    	
        public void run(){
        	//System.err.println("Initializing Game Variables/Streams");
            for(int k=0; k < 2; k++){
            	//System.err.println("Doing P"+(k+1)+":");
                try {
                    myIns[k] = new ObjectInputStream(mySocks[k].getInputStream());
                    //System.err.println("Got ObjectInputStream");
                    myOuts[k] = new ObjectOutputStream(mySocks[k].getOutputStream());
                    //System.err.println("Got ObjectOutputStream");
                    myProxies[k] = new BattleshipServerProxy(myModel, myIns[k], myOuts[k]);
                    //System.err.println("Created Proxy");
                    new Thread(myProxies[k]).start();
                    //System.err.println("Forked Proxy");
                } catch (Exception e) {
                    System.out.println("Server error opening streams " + e);
                    System.err.println("Server going down...");
                    System.exit(0);
                }
            }            
            
            while (true){
                if(myModel.numberOfPlayers() >= 2)
                	break;
                System.err.println("Waiting for 2 Players To Register");
                try
                {
                	Thread.sleep(100);
                } catch(InterruptedException e){
                	System.err.println("InterruptedException on BattleGame.run() sleep().");
                	System.err.println("Server exiting...");
                	System.exit(0);
                }
            }
            
            //System.out.println("newGame() being called on server side");
            //myModel.reset();
            do
            {
            	myModel.newGame();
        	} while(myModel.numberOfPlayers() == 2);
            //myModel.newGame();
            /*
            while (true){
                if(myModel.numberOfPlayers() < 2)
                	break;
                try
                {
                	Thread.sleep(100);
                } catch(InterruptedException e){
                	System.err.println("InterruptedException on BattleGame.run() sleep().");
                	System.err.println("Server exiting...");
                	System.exit(0);
                }
            }
            */
            System.err.println("Game exiting...");
        }
    }
}