import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;
public class BattleClient extends Thread{

	BattleshipView myMainView;
	Socket mySocket;
	IBattleshipModel myCurrentModel;
	IBattleshipModel myLocalModel;
	IBattleshipModel myNetworkModel;
	ArrayList<IBattleshipPlayer> myLocalPlayers;
	BattleshipClientProxy myProxy;
	NetMessageHandler myNMH;
	InetAddress myServerIP;
	int myServerPortNum = -1;
	int myGameType = -1;
	
	public BattleClient(String name)
	{
		myLocalPlayers = new ArrayList<IBattleshipPlayer>();
		
		String n = name;
		while(n == null)
		{	
			n = JOptionPane.showInputDialog("Enter Your Player Name");
		}
		
		myLocalPlayers.add(new BattleshipPlayer(n));
		myMainView = new BattleshipView(myLocalPlayers.get(0), "CPS108 Battleship -- "+name);
		myLocalPlayers.get(0).setView(myMainView);
		myLocalModel = new BattleshipModel();
		myLocalModel.registerPlayer(myLocalPlayers.get(0));
		myCurrentModel = myLocalModel;
		
		Object[] possibilities = {"Solo (vs. Computer Ships)", "Player vs. Computer", "Player vs. Player (local)", "Player vs. Player (network)"};
		
		String s = null;
		int type = JOptionPane.PLAIN_MESSAGE;
		while (s == null)
		{
			s = (String)JOptionPane.showInputDialog(
		                    myMainView,
		                    "Choose your play mode:\n",
		                    "Welcome To Battleship108",
		                    type,
		                    null,
		                    possibilities,
		                    "Player vs. Computer");
			type = JOptionPane.WARNING_MESSAGE;
		}
		
		if(s.equals("Solo (vs. Computer Ships)"))
			myGameType = 1;
		if(s.equals("Player vs. Computer"))
			myGameType = 2;
		if(s.equals("Player vs. Player (local)"))
			myGameType = 3;
		if(s.equals("Player vs. Player (network)"))
			myGameType = 4;
		
		if(myGameType != 4)
		{
			while(true)
			{
				type = JOptionPane.PLAIN_MESSAGE;
				String boardDims;
				boardDims = (String) JOptionPane.showInputDialog(
						myMainView,
						"Enter Your Board Size (Width x Height):\n",
						"Board Size",
						type,
						null,
						null,
                    	"10x10");
				type = JOptionPane.ERROR_MESSAGE;
				if(boardDims == null)
					System.exit(0);
				if((boardDims = boardDims.toLowerCase()).indexOf('x') < 0)
					continue;
				
				int x;
				int y;
				try
				{
					x = Integer.parseInt(boardDims.substring(0,boardDims.indexOf('x')));
					y = Integer.parseInt(boardDims.substring(boardDims.indexOf('x')+1));
				} catch (NumberFormatException e){
					x = -1;
					y = -1;
				};
				if((x < 0) || (y < 0))
						continue;
				RuleDeck rd = new RuleDeck(x,y,2);
				myLocalModel.setRules(rd);
				break;
			}
		}
		
		if(myGameType == 4) //Network play
		{
			//Get IP address
			String ip = null;
			type = JOptionPane.PLAIN_MESSAGE;
			boolean validip = false;
			while (!validip)
			{
				s = (String)JOptionPane.showInputDialog(
			                    myMainView,
			                    "IPv4: iii.iii.iii.iii:ppppp\n"+
			                    "IPv6: iiii.iiii.iiii.iiii.iiii.iiii.iiii.iiii:ppppp",
			                    "Enter IP Address And Port #",
			                    type,
			                    null,
			                    null,
			                    "127.0.0.1:1057");
				
				if(s == null)
					continue;
				try {
					if(s.indexOf(':') > 0)
					{
						myServerIP = InetAddress.getByName(s.substring(0,s.indexOf(':')));
						myServerPortNum = Integer.parseInt(s.substring(s.indexOf(':')+1));
						if((myServerPortNum > 0) && (myServerPortNum < (1 << 16)))
							validip = true;
					}
				} catch (UnknownHostException e1) {
					System.err.println("Invalid IP/Port : "+s);
					continue;
				} catch (NumberFormatException e) {
					System.err.println("Invalid number format in user-entered IP/Port : "+s);
					continue;
				}
				type = JOptionPane.ERROR_MESSAGE;
			}
		}
		/*
		try {
			myServerIP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.err.println("Error: Couldn't get localhost in BattleClient constructor.");
			System.exit(0);
		}
		*/
	}
	
	public void run()
	{
		if(myGameType != 4) //PvC Ships, PvC or PvP local
		{
			if(myGameType == 3) //Human
	    		myLocalPlayers.add(new BattleshipPlayer("Player 2"));
	    	else //AI
	    		myLocalPlayers.add(new BattleshipPlayerAI("Player 2"));
	        
			//Figure out if the user wants the AI's board to be visible
			
			int v = 1;
			if(myGameType == 2)
			{
				v = JOptionPane.showConfirmDialog(myMainView, "Should the AI's Board Be Visible?", "Player vs AI", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
			if((myGameType == 3) || (v == 0)) //user selected yes
			{
				BattleshipView p2BattleView = new BattleshipView(myLocalPlayers.get(1), "CPS108 Battleship, Player 2");
				myLocalPlayers.get(1).setView(p2BattleView);
			}
			
	       
	        myLocalModel.registerPlayer(myLocalPlayers.get(1));
	        myLocalPlayers.get(0).setModel(myLocalModel);
	        myLocalPlayers.get(0).reset();
	        myLocalPlayers.get(1).setModel(myLocalModel);
	        myLocalPlayers.get(1).reset();
	        while(true)
	        {
	        	if(myGameType == 1)
	        		myLocalModel.new1PGame();
	        	else
	        		myLocalModel.newGame();
	        }
		}
		
		if(myGameType == 4) //Networked
		{
			System.out.println("CONNECTING...");
			//Open the socket
			try {
				mySocket = new Socket(myServerIP, myServerPortNum);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(myMainView, "Sorry, couldn't connect to \n"+myServerIP+" Port "+myServerPortNum, "Fatal Battleshipian Error!", JOptionPane.ERROR_MESSAGE);
				System.err.println("Couldn't open a socket to talk to "+myServerIP+":"+myServerPortNum);
				System.exit(0);
			}
			System.out.println("CONNECTED");
				
			try {
				ObjectOutputStream o = new ObjectOutputStream(mySocket.getOutputStream());
				ObjectInputStream i = new ObjectInputStream(mySocket.getInputStream());
				myProxy = new BattleshipClientProxy(myLocalPlayers.get(0), i, o);
				//myProxy.start();
			} catch (IOException e) {
				System.err.println("Error: Couldn't create Object I/O Streams for Client Proxy");
				System.exit(0);
			}
			
			myCurrentModel = myProxy;
			myLocalPlayers.get(0).setModel(myCurrentModel);
			//myLocalPlayers.get(0).reset();
			while(true)
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
		
		
		
	}
	
	public static void main(String[] args)
	{
		BattleClient bc = new BattleClient((args.length == 0) ? null : args[0]);
		bc.start();
	}	
}
