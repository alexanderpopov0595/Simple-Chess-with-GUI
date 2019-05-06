import java.awt.event.*;

import javax.swing.*;

public class newGameButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Call newGame method
		Player.newGame();
	}
}
