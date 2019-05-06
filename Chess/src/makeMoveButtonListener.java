import java.awt.event.*;

public class makeMoveButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		//1. Call make move method
		Player.makeMove();
		//2. Set decline button enable (cause this move can be declined
		Board.declineMove.setEnabled(true);
		//3. Set first button's color to original
		cellsListeners.firstSelectedButton.setBackground(cellsListeners.color1);
		//4. Decrease button's count
		cellsListeners.count=0;
	}
}
	