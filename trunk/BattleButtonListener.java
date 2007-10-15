import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


public class BattleButtonListener implements ActionListener {

    private BattlePanel myPanel;
    private boolean waiting;
    
    public BattleButtonListener(BattlePanel panel){
        myPanel = panel;
    }
    

    
    
    //@Override
    public void actionPerformed(ActionEvent e) {
        
        BattleButton bb = (BattleButton) e.getSource();
        //JOptionPane.showMessageDialog(null, bb.toString());
        
            myPanel.processPress(bb);
        }

    }


