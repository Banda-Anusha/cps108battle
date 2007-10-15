import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;

public class BattleshipView extends JFrame implements IBattleshipView{
	
	private static final String POPUPTitle = "HEY!";
	
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
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel content = (JPanel) getContentPane();
        myDisplay = new JTextArea(1,40);
        
        content.setLayout(new BorderLayout());
        content.add(shotPanel, BorderLayout.NORTH);
        content.add(myDisplay, BorderLayout.CENTER);
        content.add(shipPanel, BorderLayout.SOUTH);
        pack();
        //setVisible(true); 
        
        myCoords = Collections.synchronizedList(new ArrayList<Coordinate>());
        myNumCoordsNeeded = 0;
        expectingShipPresses = false;
        expectingShotPresses = false;
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
	
	public void setTurn(IBattleshipPlayer itsYourTurn) {
		myCoords.clear();
		if(itsYourTurn == myPlayer)
		{
			myNumCoordsNeeded = 1;
			expectingShotPresses = true;
			showMessage("Your Turn", MessageType.INFO);
			while(myCoords.isEmpty())
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			expectingShotPresses = false;
			System.err.println("Got coord");
			myPlayer.addMove(new BattleshipMove(myPlayer, myCoords.get(0)));
			myCoords.clear();
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
			showMessage("Click to place " + shape.getName() + "(" + shape.getPoints().size() + " clocks)", MessageType.INFO);
			while(myCoords.size() < shape.getPoints().size())
			{
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			points.addAll(myCoords);
			myCoords.clear();
			triedAndFailed = true;
		} while (!myModel.isShipPlacementValid(new BattleshipPlacement(myPlayer, points), shape));
		
		expectingShipPresses = false;
		return points;		
	}

	public void newGame() {
        setVisible(true);
		
	}

	public void reset(BoardDimensions bd) {
		// TODO Auto-generated method stub
		
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
			whose = "Their";
		}
		for(Coordinate c : ship.getPoints())
			bp.setState(c, CellState.SUNK);
		showMessage((whose+" "+shape.getName()+" Was Sunk!"), MessageType.WARNING);
	}

	public void showMessage(String msg, MessageType type) {
		if(type == MessageType.INFO || type == MessageType.ERROR)
			myDisplay.setText(msg);
		else if(type == MessageType.POPUP)
			JOptionPane.showMessageDialog(null, msg, POPUPTitle, JOptionPane.PLAIN_MESSAGE);
	}

	public void showMove(BattleshipMove move, CellState newState) {
		if(move.getPlayer() == myPlayer) //I just moved
			myShotPanel.setState(move.getCoordinate(), newState);
		else
			myShipPanel.setState(move.getCoordinate(), newState);
	}

	public void updateScore(IBattleshipPlayer player, int newScore) {
		// TODO Auto-generated method stub
		
	}
}
