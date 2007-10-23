import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class BattleshipView extends JFrame implements IBattleshipView{
	
	private static final String POPUPTitle = "Battleship Message";
	
	private JTextArea myDisplay;
	private BattlePanel myShipPanel;
	private BattlePanel myShotPanel;
	private List<Coordinate> myCoords;
	private int myNumCoordsNeeded;
	private IBattleshipPlayer myPlayer;
	private IBattleshipModel myModel;
	private boolean expectingShipPresses;
	private boolean expectingShotPresses;
	
	
    public BattleshipView(IBattleshipPlayer p, BattlePanel shipPanel, BattlePanel shotPanel, String title){
    	myPlayer = p;
    	myShipPanel = shipPanel;
    	myShipPanel.setView(this);
    	myShotPanel = shotPanel;
    	myShotPanel.setView(this);
        setTitle((p != null) ? p.getName() : "Computer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel content = (JPanel) getContentPane();
        myDisplay = new JTextArea(5,40);
        
         makeMenu();
         
        content.setLayout(new BorderLayout());
        content.add(shotPanel, BorderLayout.NORTH);
        content.add(myDisplay, BorderLayout.CENTER);
        content.add(shipPanel, BorderLayout.SOUTH);
        pack();
        
        myCoords = Collections.synchronizedList(new ArrayList<Coordinate>());
        myNumCoordsNeeded = 0;
        expectingShipPresses = false;
        expectingShotPresses = false;
        
        setVisible(true);
    }
    protected void makeMenu() {
        JMenuBar bar = new JMenuBar();
        bar.add(makeFileMenu());
        bar.add(makeAboutMenu());
        setJMenuBar(bar);
    }
    protected JMenu makeFileMenu(){
        JMenu fileMenu = new JMenu("File");
        	JMenuItem jmi = new JMenuItem("New Game"){
        		public void actionPerformed(ActionEvent ev){
            		myModel.newGame();
            	}
        	};
        	
            fileMenu.add(jmi);
            
            fileMenu.add(new AbstractAction("Change Name"){
                public void actionPerformed(ActionEvent ev){
                   myPlayer.setName(JOptionPane.showInputDialog("Please enter your name"));
                   setTitle(myPlayer.getName());
                }
                });
            fileMenu.add(new AbstractAction("Quit"){
                public void actionPerformed(ActionEvent ev){
                    System.exit(0);
                }
                });
        return fileMenu;
    }
    protected JMenu makeAboutMenu(){
    	JMenu aboutMenu = new JMenu("About");
    	aboutMenu.add(new AbstractAction("About"){
    		public void actionPerformed(ActionEvent ev){
    			JOptionPane.showMessageDialog(null, "BATTLESHIP\nMatt Johnson\nAl Waldron\nAlex Hunter\nPat Eibl");
    		}
    	});
    	return aboutMenu;
    }
    
	public void setModel(IBattleshipModel ibm)
    {
    	myModel = ibm;
    }

	
	public void addBattleship(BattleshipPlacement loc) {
		for (int idx = 0; idx <  loc.getPoints().size(); idx++){
			myShipPanel.showShip(loc.getPoints().get(idx).myX, loc.getPoints().get(idx).myY);
		}
	}

	public boolean expectingInput(BattlePanel bp)
	{
		if(bp == myShipPanel)
			return expectingShipPresses;
		if(bp == myShotPanel)
			return expectingShotPresses;
		return false;
	}
	
	public void addCoordinate(Coordinate c, BattlePanel bp)
	{
		myCoords.add(c);
	}
	
	public boolean enoughCoordinates()
	{
		return (myCoords.size() < myNumCoordsNeeded);
	}
	
	public void needMove()
	{
		expectingShotPresses = true;
		boolean done = false;
		while(!done)
		{
			while(myCoords.isEmpty())
			{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			System.err.println("Got coord");
			for(Coordinate c : myCoords)
				if(myPlayer.addMove(new BattleshipMove(myPlayer, c)))
				{
					done = true;
					break;
				}
			myCoords.clear();
		}
		expectingShotPresses = false;
	}
	
	public void setTurn(IBattleshipPlayer itsYourTurn) {
		if(itsYourTurn == myPlayer)
		{
			myNumCoordsNeeded = 1;
			expectingShotPresses = true;
		}
		else
		{
			expectingShotPresses = false;
			showMessage("Opponent's Turn", MessageType.INFO);
		}
	}

	public void gameOver(IBattleshipPlayer winner) {
		showMessage((winner.getName()+" Wins"),MessageType.POPUP);
	}
	
	public ArrayList<Coordinate> needPlacement(ShipShape shape) {
		expectingShipPresses = true;
		
		System.err.println("placing "+shape.getName());
		boolean triedAndFailed = false;
		ArrayList<Coordinate> points = new ArrayList<Coordinate>();
		do
		{
			for(Coordinate c : points)
				myShipPanel.clear(c.myX, c.myY);
			points.clear();
			if(triedAndFailed)
				showMessage("Error In Placing "+shape.getName()+"!  Try Again", MessageType.POPUP);
			showMessage("Placing " + shape.getName() + " click once to set the origin and once to set the direction", MessageType.INFO);
			while(myCoords.size() < 2)
			{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
				//should check if ship is already there
				//if ((myCoords.size() == 1) && (myCoords.get(0))
				if (myCoords.size() == 1) myShipPanel.showRedSquare(myCoords.get(0).myX, myCoords.get(0).myY);
			}
			points.addAll(myCoords);
			Coordinate orig = points.get(0);
			Coordinate dir = points.get(1);
			if(orig.myX == dir.myX && orig.myY == dir.myY){
				points.clear();
			}
			if(orig.myX == dir.myX && orig.myY > dir.myY){
				points.clear();
				for (int i = 0; i < shape.getPoints().size(); i++){//left
					if(orig.myY  - i < 0 ) {
						points.clear();
						break;
					}
					points.add(new Coordinate(orig.myX, orig.myY - i));
				
				}
				
			}
			if(orig.myX == dir.myX && orig.myY < dir.myY){//right
				points.clear();
				for (int i = 0; i < shape.getPoints().size(); i++){
					if(orig.myY  + i >myShipPanel.getDim().myY ){
						points.clear();
						break;
					}
					points.add(new Coordinate(orig.myX, orig.myY + i));
					
				}
			
			}
			if(orig.myX > dir.myX && orig.myY == dir.myY){//up
				points.clear();
				for (int i = 0; i < shape.getPoints().size(); i++){
					if(orig.myX  - i < 0 ) {
						points.clear();
						break;
					}
					points.add(new Coordinate(orig.myX - i, orig.myY));
					
				}
			
			}
			if(orig.myX < dir.myX && orig.myY == dir.myY){//down
				points.clear();
				for (int i = 0; i < shape.getPoints().size(); i++){
					if(orig.myX + i > myShipPanel.getDim().myX ) {
						points.clear();
						break;
					}
					points.add(new Coordinate(orig.myX + i, orig.myY));
					
				}
				
			}
			myCoords.clear();
			triedAndFailed = true;
			myShipPanel.clear(orig.myX, orig.myY);
		} while (!myModel.isShipPlacementValid(new BattleshipPlacement(myPlayer, points), shape));
		
		expectingShipPresses = false;
		return points;		
	}

	public void newGame() {
	}

	public void reset(BoardDimensions bd) {
		
	}
	
	public void showShot(BattleshipPlacement ship){
		
	}
	
	public void shipSunk(BattleshipPlacement ship, ShipShape shape) {
		BattlePanel bp;
		String whose = null;
		if(ship.getPlayer() == myPlayer) //My ship was sunk
		{
			bp = myShipPanel;
			whose = "Your";
		}
		else
		{
			bp = myShotPanel;
			whose = ship.getPlayer().getName()+"'s";
		}
		showMessage((whose+" "+shape.getName()+" Was Sunk!"), MessageType.INFO);
		for(Coordinate c : ship.getPoints()){
			bp.setState(c, CellState.SUNK);
		}
		for(Coordinate c : ship.getPoints()){
			bp.setState(c, CellState.SUNK);
		}
	}

	public void showMessage(String msg, MessageType type) {
		if(type == MessageType.INFO || type == MessageType.ERROR)
			myDisplay.setText(msg);
		else if(type == MessageType.POPUP)
			JOptionPane.showMessageDialog(null, msg, POPUPTitle, JOptionPane.PLAIN_MESSAGE);
	}

	public void showMove(BattleshipMove move, CellState newState) {
		if(move.getPlayer() == myPlayer){ //I just moved
			//showMessage(myPlayer.getName() + "just moved", MessageType.POPUP);
			myShotPanel.setState(move.getCoordinate(), newState);
		}
		else
			myShipPanel.setState(move.getCoordinate(), newState);
	}

	public void updateScore(IBattleshipPlayer player, int newScore) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void showHit(ShipShape mySS, BattleshipPlacement ship, Coordinate coord) {
		BattlePanel bp;
		if(ship.getPlayer() == myPlayer)
		{
			bp = myShipPanel;
		}
		else
		{
			bp = myShotPanel;
		}
		bp.setState(coord, CellState.HIT);
		showMessage((ship.getPlayer().getName()+"'s "+mySS.getName()+" was HIT!"), MessageType.INFO);
	}
	public void showMiss(Coordinate coord, IBattleshipPlayer player){
		BattlePanel bp;
		String who;
		if(player == myPlayer) 
		{
			bp = myShipPanel;
			who = "Opponent";
		}
		else
		{
			bp = myShotPanel;
			who = "You";
		}
		bp.setState(coord, CellState.MISS);
		showMessage((who + " Missed!"), MessageType.INFO);
	}
}
