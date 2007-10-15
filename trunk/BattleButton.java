import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class BattleButton extends JButton {
    
    private int myRow, myCol;
    private String myString;
    private static Dimension ourDimension = new Dimension(30,30);
    public BattleButton(int row, int col){
        myRow = row;
        myCol = col;
        myString = "" + (char)('A' + myRow - 1) + " " + myCol;
        this.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLUE));
        this.setPreferredSize(ourDimension);
        this.setMinimumSize(ourDimension);
        this.setSize(ourDimension);
    }
    
    public String toString(){
        return myString;
    }
    
    public int getRow(){
        return myRow;
    }
    
    public int getCol(){
        return myCol;
    }
}
