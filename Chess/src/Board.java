import java.awt.*;
import javax.swing.*;

public class Board {
	//1. Array with all chessboard elements (1-8 and A-H axis, all figures and empty cells
	static String[][] chessBoard= 	     {{ "\u0000", "A", "B", "C", "D", "E", "F", "G", "H","\u0000" },
										  { "8", "\u265C", "\u265E", "\u265D", "\u265B", "\u265A", "\u265D", "\u265E", "\u265C","8" }, 
										  { "7", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F","7" }, 
										  { "6", " ", " ", " ", " ", " ", " ", " ", " ", "6"}, 
										  { "5",  " ", " ", " ", " ", " ", " ", " ", " ","5" },  
										  { "4",  " ", " ", " ", " ", " ", " ", " ", " ","4" }, 
										  { "3",  " ", " ", " ", " ", " ", " ", " ", " ","3" },  
										  { "2", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659","2" }, 
										  { "1", "\u2656", "\u2658", "\u2657",  "\u2655",  "\u2654", "\u2657", "\u2658", "\u2656","1" },
										  { "\u0000", "A", "B", "C", "D", "E", "F", "G", "H","\u0000" }};
	//2. Copy of chessBoard array. It's used to start new game
	static String[][] chessBoardNewGame= {{ "\u0000", "A", "B", "C", "D", "E", "F", "G", "H","\u0000" },
										  { "8", "\u265C", "\u265E", "\u265D", "\u265B", "\u265A", "\u265D", "\u265E", "\u265C","8" }, 
										  { "7", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F", "\u265F","7" }, 
										  { "6", " ", " ", " ", " ", " ", " ", " ", " ", "6"}, 
										  { "5",  " ", " ", " ", " ", " ", " ", " ", " ","5" },  
										  { "4",  " ", " ", " ", " ", " ", " ", " ", " ","4" }, 
										  { "3",  " ", " ", " ", " ", " ", " ", " ", " ","3" },  
										  { "2", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659", "\u2659","2" }, 
										  { "1", "\u2656", "\u2658", "\u2657",  "\u2655",  "\u2654", "\u2657", "\u2658", "\u2656","1" },
										  { "\u0000", "A", "B", "C", "D", "E", "F", "G", "H","\u0000" }};
	//3. Main panel.Other elements will be added to this panel.
	static JPanel mainPanel;
	//4. This button displays which player's turn. It placed at top
	static JButton playerTurn;
	//5. This is array of buttons, which contains name of chessboard's element (from chessBoard array) and coordinate of button (to let make command by pressing the buttons)
	static JButtonCells[][] cells;
	//6. Panel with chessboard. Group all cell buttons in right oder
	static JPanel boardPanel;
	//7. At the field player can type his command
	static JTextField commandField;
	//8. Button to make turn
	static JButton makeMove;
	//9. Button to decline last move
	static JButton declineMove;
	//10. Button to start new game
	static JButton newGame;
	//11. Panel with those 3 buttons. Group them in right order
	static JPanel buttonsPanel;	
	//12. This methods create array of buttons and paint them in chess order (white and black color for cells and different color for Axis)
	static public void createCells() {
		//1. Color of first cell in every raw in changing from white to black. This variable is used to change color of first cell in case of even and odd rows
		boolean firstCellWhite=true;
		//2. Create array of buttons (cells). The size is the same as chessBoard array has				
		cells=new JButtonCells[chessBoard.length][chessBoard[0].length];
		//3. Start to create buttons (cells)
		for (int i=0; i<cells.length;i++) {
			//4. Check at every row is that row even or odd			
			if (i%2==0) {
				//4.1. If row is even - the first cell must be white. Set firstCellWhite to true
				firstCellWhite=true;
			}
			else {
				//4.1. If row is odd - the first cell must be black. Set firstCellWhite to false
				firstCellWhite=false;
			}
			//5.Continue to create buttons (cells) 
			for (int j=0; j<cells[0].length;j++) {
				//6. Create new button, which name equals to element from chessBoard array with the same position. Also give this button her coordinates in cells array
				cells[i][j]=new JButtonCells(chessBoard[i][j], i, j);
				//7.  Define which color chose for paint.
					//If it's first or last row in first or last column				
				if (i==0 || i==cells[0].length-1 || j==0 || j==cells.length-1) {
					//7.1 Paint this cell orange color
					cells[i][j].setBackground(Color.ORANGE);					
				}			
					//if it's other cells	
				else {
				//7.1. Check wich color must be fisrt - white or black
					//If it's white cell must be first
					if(firstCellWhite) {
						//7.2. Paint all odd cells white color					
						if (j%2!=0) {
							cells[i][j].setBackground(Color.WHITE);
						}					
						//7.3.Paint all even cells black color
						else {
							cells[i][j].setBackground(Color.LIGHT_GRAY);
						}
					}
					//If it's black cell must be first
					else {
						//7.2. Paint all odd cells white color	
						if (j%2!=0) {
							cells[i][j].setBackground(Color.LIGHT_GRAY);
						}
						//7.3. Paint all even cell black color					
						else {
							cells[i][j].setBackground(Color.WHITE);					
						}
					}				
				}
			}			
		}		
	} 
	//13. This method add cells to the boardPanel and add actionListeners to black and white cells
	static public void addCells() {
		//1. Run through whole cells array
		for (int i=0; i<cells.length; i++) {
			for (int j=0; j<cells[0].length;j++) {
				//2.1 If current cell  is not Axis cell
				if (i!=0 && i!=cells[0].length && j!=0 && j!=cells.length-1) {
					//2.2 Add actionListener to this cell
					cells[i][j].addActionListener(new cellsListeners());
				}
				//2.1. If current cell is Axis cell -d on't add action listener to that button				
			//3. Add button to boardPanel				
			boardPanel.add(cells[i][j]);
			}
		}
	}
	//14. This method displays chessboard at the screen
	static public void drawBoard() {
		//1. Run through whole cells array
		for (int i=0; i<cells.length;i++) {
			for (int j=0; j<cells[0].length;j++) {
				//1.2. All changes during the game write in chessBoard array. So write new figure's positions from chessBoard array to cells array as cell button name 
				cells[i][j].setText(chessBoard[i][j]); 
				//1.3. Set font for elements
				cells[i][j].setFont(new Font("TimesRoman", Font.BOLD, 30));
			}				
		}
		//2. Display which player's turn
			//2.1. If it's white player's turn
			if(Player.whitePlayerTurn) {
				//2.2. Display, that it's white player's turn
				Board.playerTurn.setText("White player's turn");
			}
			//2.2. If it's black player's turn
			else {
				//2.3. Display, that it's black player's turn
				Board.playerTurn.setText("Black player's turn");
			}
		
	}
	//15. Main frame 
	static JFrame frame;
	//16. This method runs ones at start of the game and create graphic interface	
	static public void GUI(){		
			//1.Create all containers			
			//1.2.Main panel
				//1.2.1. Create main panel
				mainPanel=new BackgroundPanel();				
			//1.3. Board panel
				//1.3.1. Create board panel
				boardPanel=new JPanel();
				//1.3.2. Make it transparent to see the main background (table)
				boardPanel.setOpaque(false);				
			//1.4. Buttons panel
				//1.4.1. Create buttons panel
				buttonsPanel=new JPanel();
				//1.4.2. Make it transparent to see the main background (table
				buttonsPanel.setOpaque(false);
			//1.5. Main frame
				//1.5.1. Create main frame
				frame=new JFrame("Simple chess");
				//1.5.2. Set size of frame
				frame.setSize(800,610);
				//1.5.3. Set, that programm must finish when player closes the window
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//1.5.4. Add main panel to frame							
				frame.add(mainPanel);				
				//1.5.5. Make frame visible
				frame.setVisible(true);	
				
				
		//2. Player turn 
			//2.1 Create playerTurn
			playerTurn=new JButton("Turn");		
			//2.2. Place it at center of screen
			playerTurn.setAlignmentX(Component.CENTER_ALIGNMENT);			
			//2.3 Add it to main panel
			mainPanel.add(playerTurn);
		//3. Cells
			//3.1. Create cells
			createCells();
			//3.2 Add cells and their listeners to board panel
			addCells();		
			//3.3. Change board panel layout to place cells in chess order
			boardPanel.setLayout(new GridLayout(cells.length,cells[0].length));
			//3.4. Add board panel to main panel
			mainPanel.add(boardPanel);
		//4. Command field
			//4.1.1 Create command field
			commandField=new JTextField(20);
			//4.1.2. Add it to main panel
			mainPanel.add(commandField);
		//4. Command buttons
			//4.1. Create make move button
			makeMove=new JButton("Make move");
			//4.2. Create decline move button
			declineMove=new JButton("Decline move");
			//4.3. Create new game button
			newGame=new JButton("New game");
			//4.4. Add all buttons to buttons panel
			buttonsPanel.add(makeMove);
			buttonsPanel.add(declineMove);
			buttonsPanel.add(newGame);	
			//4.5. Add buttons panel to main panel
			mainPanel.add(buttonsPanel);
			//4.6. Add action listeners to buttons
			makeMove.addActionListener(new makeMoveButtonListener());
			declineMove.addActionListener(new declineMoveButtonListener());
			newGame.addActionListener(new newGameButtonListener());			
	}
	//17. This method shows tutorial
	static public void showTutorial() {
		//1. Show that's a tutorial
		JOptionPane.showMessageDialog(Board.frame, "There's a little tutorial to a game", "Tutorial",JOptionPane.PLAIN_MESSAGE);
		//2. Player's turn
			//2.1. Get color of PlayerTurn button
			Color color1=playerTurn.getBackground();
			//2.2. Set color of PlayerTurn button to yellow
			playerTurn.setBackground(Color.YELLOW);
			//2.3. Show tutorial message
			JOptionPane.showMessageDialog(Board.frame, "At the top shows which player's turn", "Tutorial",JOptionPane.PLAIN_MESSAGE);
			//2.4 Set button's color to original
			playerTurn.setBackground(color1);
		//3. Button's control
			 //3.1 Color all buttons to yellow
			for (int i=0; i<cells.length-1; i++) {
				for (int j=0; j<cells[0].length;j++) {
					//2.1 If current cell  is not Axis cell
					if (i!=0 && i!=cells[0].length && j!=0 && j!=cells.length-1) {
						//2.2 Paint that button yellow
						cells[i][j].setBackground(Color.YELLOW);
					}
				}
			}
			//3.2. Show tutorial message
			JOptionPane.showMessageDialog(Board.frame, "You can move figures by clicking on the buttons", "Tutorial",JOptionPane.PLAIN_MESSAGE);
			//3.3. Change button's color back
				//3.4. Color2 is changing from white to black
				Color color2=Color.WHITE;
				//3.5. Run trough cell's array
				for (int i=0; i<cells.length; i++) {
					for (int j=0; j<cells[0].length;j++) {
					//3.6 If current cell  is not Axis cell
						if (i!=0 && i!=cells[0].length-1 && j!=0 && j!=cells.length-1) {
							//3.7 Paint that button with black or white
							cells[i][j].setBackground(color2);
							//3.8. Than change color
							if(color2==Color.WHITE) {
								color2=Color.LIGHT_GRAY;
							}
							else {
								color2=Color.WHITE;
							}						
						}
					}
					//3.9. After every raw change color too
					if(color2==Color.WHITE) {
						color2=Color.LIGHT_GRAY;
					}
					else {
						color2=Color.WHITE;
					}
				}
			//4. Command field			
				//4.1. Change command field's color
				commandField.setBackground(Color.YELLOW);
				//4.2. Show tutorial message
				JOptionPane.showMessageDialog(frame, "Also you can move figures by inputing command in text field", "Tutorial",JOptionPane.PLAIN_MESSAGE);
				//4.3. Set command field's color back
				commandField.setBackground(Color.WHITE);
			//5. Make move button
				//5.1. Change make move button's color
				makeMove.setBackground(Color.YELLOW);
				//5.2. Show tutorial message
				JOptionPane.showMessageDialog(frame,"To make move press 'Make move' button","Tutorial", JOptionPane.PLAIN_MESSAGE);
				//5.3. Set move button's color back
				makeMove.setBackground(color1);
			//6. Decline move button
				//6.1. Change decline move button's color
				declineMove.setBackground(Color.YELLOW);
				//5.2. Show tutorial message
				JOptionPane.showMessageDialog(frame,"To decline your last move press 'Decline move' button", "Tutorial" ,JOptionPane.PLAIN_MESSAGE);
				//5.3. Set move button's color back
				declineMove.setBackground(color1);
			//7. New game button
				//6.1. Change new game button's color
				newGame.setBackground(Color.YELLOW);
				//5.2. Show tutorial message
				JOptionPane.showMessageDialog(frame,"To start new game press 'New game' button", "Tutorial" ,JOptionPane.PLAIN_MESSAGE);
				//5.3. Set new game button's color back
				newGame.setBackground(color1);
				
		}
	}
