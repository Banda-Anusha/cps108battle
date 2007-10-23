import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;


public class BattlePanel extends JPanel {
    protected int myRows, myCols;
    protected JButton[][] myButtons;
    protected Border myEmptyBorder = BorderFactory.createEmptyBorder();
    
    protected IconFactoryFromDirectory myIconFactory; 
    protected final static String SUNK = "big-explosion.jpg";
    protected final static String WATER = "water.jpg";
    protected final static String SHIP = "bluesquare.jpg";
    protected final static String HIT = "hit.gif";
    protected final static String MISS = "miss.gif";
    protected final static String REDSQUARE = "redsquare.jpg";
    
    private BattleButton myLastPress;
    private boolean listening;
    private BattleshipView myView;
    
    
    public BattlePanel(BoardDimensions dim){
        super(new GridLayout(dim.getHeight()+1,dim.getWidth()+1,0,0));
        myRows = dim.getHeight()+1;
        myCols = dim.getWidth()+1;
        myIconFactory =  new IconFactoryFromDirectory("images"); //"/Users/ola/Desktop/courses/108/workspace/GUIs/src/images");
        makeButtons();
    }
    
    public void setView(BattleshipView view)
    {
    	myView = view; 
    }
    
    public void showMiss(int col, int row){
        myButtons[row+1][col+1].setIcon(myIconFactory.getIcon(MISS));
    }
    public void showSunk(int col, int row){
        myButtons[row+1][col+1].setIcon(myIconFactory.getIcon(SUNK));
    }
    public void showShip(int col, int row){
    	myButtons[row+1][col+1].setIcon(myIconFactory.getIcon(SHIP));
    }
    public void showHit(int col, int row){
    	myButtons[row+1][col+1].setIcon(myIconFactory.getIcon(HIT));
    }
    public void showWater(int col, int row)
    {
    	myButtons[row+1][col+1].setIcon(myIconFactory.getIcon(WATER));
    }
    public void showRedSquare(int col, int row)
    {
    	myButtons[row+1][col+1].setIcon(myIconFactory.getIcon(REDSQUARE));
    }   
    public void clear(int col, int row)
    {
    	if(((row+1) < myRows) && ((col+1) < myCols))
    		myButtons[row+1][col+1].setIcon(null);
    }
    public void clearAll()
    {
    	for(int i = 0; i < myRows; i++)
    		for(int j = 0; j < myCols; j++)
    			clear(i,j);
    }
    
    public void setState(Coordinate c, CellState newState)
    {
    	if(newState == CellState.HIT)
    		showHit(c.myX, c.myY);
    	else if(newState == CellState.MISS)
    		showMiss(c.myX, c.myY);
    	else if(newState == CellState.SHIP)
    		showShip(c.myX, c.myY);
    	else if(newState == CellState.SUNK)
    		showSunk(c.myX, c.myY);
    }
    
    public void processPress(BattleButton bb){
    	if (listening) myLastPress = bb;
    	listening = false;
    	if(myView.expectingInput(this))
    	{
    		myView.addCoordinate(new Coordinate((bb.getCol()-1),(bb.getRow()-1)), null);
    		//showShip((bb.getRow()-1), (bb.getCol()-1));
    	}
    }
    public Coordinate getDim(){
    	return (new Coordinate(myRows, myCols));
    }
    private void makeButtons(){
        myButtons = new JButton[myRows][myCols];
        BattleButtonListener bbl = new BattleButtonListener(this);
       
        for(int r=1; r < myRows; r++){
            for(int c=1; c < myCols; c++){
                myButtons[r][c] = new BattleButton(r,c);
                myButtons[r][c].addActionListener(bbl);
            }
        }
        for(int c=1; c < myCols; c++){
            myButtons[0][c] = new JButton(""+c);
            myButtons[0][c].setEnabled(false);
        }
        for(int r=1; r < myRows; r++){
            myButtons[r][0] = new JButton(""+(char)('A'+r-1));
            myButtons[r][0].setEnabled(false);
        }
        myButtons[0][0] = new JButton("");
        myButtons[0][0].setEnabled(false);
      
        // now add buttons to this panel to make them visible
        for(int r=0; r < myRows; r++){
            for(int c = 0; c < myCols; c++){
                add(myButtons[r][c]);
            }
        }
    }
}
