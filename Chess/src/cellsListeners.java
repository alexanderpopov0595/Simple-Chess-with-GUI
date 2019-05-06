
import java.awt.Color;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class cellsListeners implements ActionListener {
	//1. Count of pressed buttons (max - 2)
	static int count;
	//2. Color of first chosen cell
	static Color color1;
	//3. Color of second chosen cell
	static Color color2;
	//4. Selected button
	static JButtonCells selectedButton;
	//5. First selected button
	static JButtonCells firstSelectedButton;
	//6. Write which button was pressed in commandField
	static String command;	
	@Override
	public void actionPerformed(ActionEvent ae) {	
		//1. Cast selected button from array to JButtonCells to get Y and X coordinates of button
		selectedButton=(JButtonCells) ae.getSource();		
		//2. Increase count of pressed button
		count++;
		//3. Check is that first or second button was pressed			
			//3.1. If it's first button was pressed (count=1)
			if(count==1) {
				//3.2. Save that cell's background
				color1=selectedButton.getBackground();
				//3.3. Write button coordinates to Y and X coordinates in Player class
				Player.Y1=selectedButton.Y;
				Player.X1=selectedButton.X;
			
				//3.4. Check did player chose right figure
					//3.5 If he chose right
					if(Player.checkYourFigure()) {
						//3.6. Set that button's color to green
						selectedButton.setBackground(Color.GREEN);	
						//3.7. Save first button (to be restored)
						firstSelectedButton=selectedButton;
						//3.8. Block makeMove button, if player chose play using the buttons
						command=Board.chessBoard[0][Player.X1]+Board.chessBoard[Player.Y1][0];
						Board.commandField.setText(Board.commandField.getText()+command);
						//Board.makeMove.setEnabled(false);
					}
					//3.6.If he chose wrong
					else {
						//3.6. Set that button's color to red
						selectedButton.setBackground(Color.RED);
						//3.7. Display error message
						JOptionPane.showMessageDialog(Board.frame, Player.errorMessage);
						//3.8 Decrease count of pressed button ( cause player should chose first button again)
						count=0;
						//3.9. Set button's color to default
						selectedButton.setBackground(color1);
					}
					
			}
			//3.2 If it's second button was pressed (count==2)
			if(count==2) {
				//3.3. Save that cell's background
				color2=selectedButton.getBackground();
				//3.4. Write button coordinates to Y and X coordinates in Player class
				Player.Y2=selectedButton.Y;
				Player.X2=selectedButton.X;
				
				//3.5.Check did player moves his figure right
					//3.6 If he did
					if( Player.checkFigureMove(Player.Y1, Player.X1, Player.Y2, Player.X2) && Player.checkYourMove(Player.Y2, Player.X2) ) {
						//3.7. Move figure to that point
						Player.move(Player.Y1, Player.X1, Player.Y2,Player.X2);
							//3.8. Check if player's king has check
								//3.9. If he has has no check
								if(Player.check()) {									
									//3.5. If pawn moved to opposite edge of board -change pawn to queen
									Player.pawnToQueen();
									//3.6. Change player's turn
									Player.whitePlayerTurn=!Player.whitePlayerTurn;
									//3.7. Display the board changes
									Board.drawBoard();	
									
									//3.8. Check has other player's king a check after that move
										//3.9. If he has check							
										if(!Player.check()) {								
											//4.0. Check is there a mate
												//4.1. If there no mate
												if(Player.checkMate()) {											
													//4.2. Display error message (check)
														JOptionPane.showMessageDialog(Board.frame, Player.errorMessage);
												}
												//4.2. If there a mate
												else {											
													//4.3. Display error message (mate)
													JOptionPane.showMessageDialog(Board.frame, Player.errorMessage);
												}
										}
								 }
								//4.0. If he has check
								else {
									//4.1. Decline last player's move
									Player.declineMove();
									//4.2. Display error message
									JOptionPane.showMessageDialog(Board.frame, Player.errorMessage);
									//4.3. Descrease count
									count=0;
								}				
						
					}
					//3.7. If he didn't
					else {
						//3.8. Set that button's color to red
						selectedButton.setBackground(Color.RED);
						//3.9. Display error message
						JOptionPane.showMessageDialog(Board.frame, Player.errorMessage);
						
					}
				//4.0 Decrease count of pressed buttons
						count=0;
						//3.9. Set buttons's colors to default					
						firstSelectedButton.setBackground(color1);						
						selectedButton.setBackground(color2);				
			}		
	}

}
