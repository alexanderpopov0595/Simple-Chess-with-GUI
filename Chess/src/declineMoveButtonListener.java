import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
public class declineMoveButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//1. Call  decline method
		Player.declineMove();
		//2. Change player's turn
		Player.whitePlayerTurn=!Player.whitePlayerTurn;
		//3.Display changed chessboard
		Board.drawBoard();
		//3. Show information message
		JOptionPane.showMessageDialog(Board.frame, "Move was declined!");
		//4. Set button to enable (cause it can save only one board previous change
		Board.declineMove.setEnabled(false);
		
				
	}
}



