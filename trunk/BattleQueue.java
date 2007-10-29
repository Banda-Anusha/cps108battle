import java.io.*;
import java.net.*;
import java.util.*;

public class BattleQueue extends Thread {
    public Queue<Socket> myQueue;
    
    public BattleQueue(){
        myQueue = new LinkedList<Socket>();
    }
    
    public synchronized void add(Socket s){
        myQueue.add(s);
    }
    
    public void run(){
        while (true){
            synchronized (this){
                if (myQueue.size() >= 2){
                    Socket s1 = myQueue.remove();
                    Socket s2 = myQueue.remove();
                    System.out.println("Starting Game");
                    IBattleshipModel m = new BattleshipModel();
                   	RuleDeck rules = new RuleDeck(); //Default rule deck
                    m.setRules(rules);
                    BattleGame bg = new BattleGame(m,s1,s2);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("error in BattleQueue on sleep "+e);
                break;
            }
        }
    }
}
