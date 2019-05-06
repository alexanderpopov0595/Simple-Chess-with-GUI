
import javax.swing.JOptionPane;

public class Player {
	 //0. Error message
	static String errorMessage=null;
	// 1. Define which player' turn. White player always goes first
	static boolean whitePlayerTurn = true;
	// 2. Write player's command from command field to that variable
	static String command;
	// 3. Chosen coordinates
	// 3.1. Start coordinate (1-8 Axis)
	static int Y1;	
	// 3.2. Start coordinate (A-H Axis
	static int X1;
	// 3.3. End coordinate (1-8 Axis)
	static int Y2;
	// 3.4. End coordinate (A-H Axis)
	static int X2;
	// 4. Turn number. It's used to let pawn goes more than 1 cell, if it's first player's turn.	
	static int count = 0;
	// 5. Array with previous chessboard state. Used for decline move method
	static String[][] chessBoardPrevious=new String[Board.chessBoard.length][Board.chessBoard[0].length];
	// 6. This method copies values of chessBoard array to chessBoardPrevious array
	public static void savePreviousChessBoardState() {
		//1. Run through every value in chessboard array
		for (int i=0; i<Board.chessBoard.length;i++) {
			for (int j=0; j<Board.chessBoard[0].length;j++) {
				//2.Copy every value from chessBoard array to chessBoardPrevious array
				chessBoardPrevious[i][j]=Board.chessBoard[i][j];
			}			
		}
	}
	// 6.1. This method copies values of chessBoardPrevious array to chessBoard array
	public static void getPreviousChessBoardState() {
		//1. Run through every value in previous chessboard array
				for (int i=0; i<Player.chessBoardPrevious.length;i++) {
					for (int j=0; j<Player.chessBoardPrevious[0].length;j++) {
						//2.Copy every value from chessBoardPrevious array to chessBoard array
						Board.chessBoard[i][j]=chessBoardPrevious[i][j];
					}			
				}
		
	}
	// 7. This method moves figure from Y1X1 to Y2X2. This method gets Y1,X1 and Y2, X2 to be used in mate() method
	public static void move(int Y1, int X1, int Y2, int X2) {
		// 1. Save chessboard previous state			
		savePreviousChessBoardState();		
		// 2. Move element at Y1X1 to Y2X2
		Board.chessBoard[Y2][X2] = Board.chessBoard[Y1][X1];
		// 3. Clear Y1X1 position (cause figure moved from it)
		Board.chessBoard[Y1][X1] = " ";
		// 4.Clear commandField window
		Board.commandField.setText("");
		//5. Increment count of turns							
		count++;
		
	}
	// 8. This method gets player's command from command field
	public static void getCommand() {
		
		//1.Get text from commandField and write it into command variable		
		command=Board.commandField.getText();	
		
	}
	// 9. This method transformate visual chessboard coordinates from command variable to chessboard array coordinates
	public static void getCoordinates() {
		//1. Get X1 coordinate from command
			//1.1. X1 placed at 0 position in command
			String x1=command.substring(0,1);		
		//2. Get Y1 char OxAxisCommand 
			//2.2. Y1 placed at 1 position in command (String Y1=command.substring(1))
				/* This is a digital coordinate, so it should be converted to int (Integer.parseInt(Y1))
				 * The order of cells in chessboard array and in visual chessboard is opposite to each other
				 * To compare them it's necessary to substract coordinate from "opossite" side of board
				 * Except 1 from chessboard array length cause rows contains 1-8 Axis at begining of the chessboard array				 
				 */				
			Y1=(Board.chessBoard.length-1)- Integer.parseInt(command.substring(1,2));
		//3. Get X2 coordinate from command
			//3.1. X2 placed at 2 position in command
				String x2=command.substring(2,3);			
		//4. Get Y2 coordinate from command the same way as Y1
			//4.1. Y2 placed at 3 position in command
			Y2=(Board.chessBoard.length-1)- Integer.parseInt(command.substring(3));
		//5. Then search for X1 and X2 position in chessboard array
			/*ChessBoard[0].length - horizontal length of board, contains A-H
			 *i=1 cause first element of array is empty corner 
			 *Board.chessBoard[0].length-1 - cause the last element in a row a empty corner too 
			 */			 		
			for (int i = 1; i < Board.chessBoard[0].length; i++) {					
					//5.1. When x1 found			
					if (x1.equals(Board.chessBoard[0][i])) {		
						//5.1.1. Write it as X1 coordinate
								X1= i ;
						}
					//5.2. When x2 found
					if (x2.equals(Board.chessBoard[0][i])) {
						//5.2.1. Write it as X2 coordinate
							X2= i ;
						}				
			}		
	}
	// 10. This method checks did player chose figure correct
	public static boolean checkYourFigure() {
		//1.First check which player's turn
			//1.1. If it's white player's turn
			if(whitePlayerTurn) {
				//2. Check did player chose white figure instead black figure or empty cell
					//2.1. If he chose white figure
					if(Board.chessBoard[Y1][X1].matches("\u2659||\u2656||\u2658||\u2657||\u2655||\u2654")) {
						//2.2. checkYourFigure is right							
							//2.3. Return true
							return true;
					}
					//2.2. If he chose black figure or empty cell
					else {
						//2.3. checkYourFIgure is false							
							//2.4. Set error message
							errorMessage="Chose white figures!";
							return false;						
					}
			
			}
			//1.2. If it's black player's turn
			else {
				//2. Check did player chose black figure instead white figure or empty cell
					//2.1. If he chose black figure
					if(Board.chessBoard[Y1][X1].matches("\u265C||\u265E||\u265D||\u265B||\u265A||\u265D||\u265E||\u265C||\u265F")) {
						//2.2. checkYourFigure is right
							//2.3. Return true
							return true;
					}
					//2.2. If he chose white figure or empty cell
					else {
						//2.3. checkYourFigures is false							
							//2.4. Set error's message
							errorMessage="Chose black figures!";
								return false;							
					}			
			}		
	}
	// 11. This method check did player chose right destination. This method gets Y2 and X2 to be used in mate method
	public static boolean checkYourMove(int Y2, int X2) {
		//1.First check which player's turn
		//1.1. If it's white player's turn
		if(whitePlayerTurn) {
			//2. Check did player chose black figure or empty cell instead white figure, empty corner or Axis (it's necessary check for mate method, where this method is used
				//2.1. If player chose one of his white figures, empty corners or Axis
				if(Board.chessBoard[Y2][X2].matches("\u2659||\u2656||\u2658||\u2657||\u2655||\u2654||\u0000")||Board.chessBoard[Y2][X2].matches("[1-8A-H]")) {
					//2.2. checkYourMove is false							
						//2.3. Set error message
						errorMessage="You can't attack your white figures!";
						return false;
				}
				//2.2. If player chose one of black figures or empty cell
				else {
					//2.3. checkYourMove is true						
						//2.4. Return true;
						return true;						
				}		
		}
		//1.2. If it's black player's turn
		else {
			//2. Check did player chose white figure or empty cell instead black figure, empty corner or Axis (it's necessary check for mate method, where this method is used
				//2.1. If player chose one of his white figures, empty corners or Axis
				if(Board.chessBoard[Y2][X2].matches("\u265C||\u265E||\u265D||\u265B||\u265A||\u265D||\u265E||\u265C||\u265F||\u0000")||Board.chessBoard[Y2][X2].matches("[1-8A-H]") ) {
					//2.2. checkYourMove is false							
						//2.3. Set error message
						errorMessage="You can't attack your black figures!";
						return false;
				}
				//2.2. If he chose one of white figures or empty cell
				else {
					//2.3. checkYourMove is true						
						//2.4. Return true;
						return true;							
				}			
		}		
		
	}
	// 12. This method checks did player move his pawn right.This method gets Y1X1 and Y2X2 (to be used in mate method)
	public static boolean checkPawnMove(int Y1, int X1, int Y2, int X2) {
		//1.1. Pawn's rules for move and for attack are different from each other. Check both cases
			//1.2. Check if pawn doesn't attack	
	
				if(Board.chessBoard[Y2][X2].equals(" ")) {		
					//1.2. Pawn can move only straight forward and only one cell (or two cells if that is first player's turn). But forward for white pawn - is going "up" and for black pawn -is going "down"			
						//1.3. If pawn moves right, pawn is white and it's a first player's turn  
						if(count<2 && X1==X2 &&  Y2==(Y1-2)) {			
							//1.4. checkPawnMove is true
								//1.5. Return true
								return true;				
						}
						//1.4. If pawn moves right, it's a white pawn, but it's not first player's turn							
						else if (X1==X2 &&  Y2==(Y1-1)) {				
							//1.5. checkPawnMove is true
								//1.6. Return true
								return true;					
						}			
						//1.5. If pawn moves right, pawn is black and it's player's first turn 					
						else if( Board.chessBoard[Y1][X1].equals("\u265F")&&count<2&&X1==X2 &&  Y2==(Y1+2)) {				
							//1.5. checkPawnMove is true
								//1.6. Return true
								return true;
						}
						//1.6. If pawn moves right, it's black pawn, but it's not player's first turn
						else if (Board.chessBoard[Y1][X1].equals("\u265F")&&X1==X2 &&  Y2==(Y1+1)) {			
							//1.5. checkPawnMove is true
								//1.6. Return true
								return true;				
						}
						//1.7. If pawn moves wrong
						else {	
							//1.8. checkPawnMove is false
								//1.9. Set error message
								errorMessage="Pawn doesn't go like that!";
								return false;						
						}		
				}
			//1.3. If Pawn attacks				
				else {
					
					//1.4. Pawn can attacks only by diagonal (enemy if from left or right side of pawn). But right and left for white pawn and black is different from each other. Check both cases
						//1.5. If pawn is white
						if(((X2==(X1-1))||X2==(X1+1))&&Y2==(Y1-1)) {
							//1.6. checkPawnMove is true
								//1.7. Return true
								return true;
						}
						//1.6. If pawn is black
						else if(Board.chessBoard[Y1][X1].equals("\u265F")&&((X2==(X1-1))||X2==(X1+1))&&Y2==(Y1+1)) {				
						//1.7. checkPawnMove is true
							//1.8. Return true
								return true;
						}			
						//1.7. If pawn attacks wrong
					else {							
						//1.8. checkPawnMove is false
							//1.9. Set error message
							errorMessage="Pawn doesn't attack like that!";
							return false;
						}
				}
	}
	// 13. This method checks did player move his rook right. This method gets Y1X1 and Y2X2 (to be used in mate method)
	public static boolean checkRookMove(int Y1, int X1, int Y2, int X2) {
		//1. Rook can move only in two direction - in Y or X
			//1.1. If rook moves in Y direction
			if(X1==X2) {
				//2. Check is there any other figures on rook's way
					//2.1. Last position doesn't checked cause if there will be other player's figure, method will return false (but it must be true)
						//2.1.1. If rook moves only one cell forward - the last position doesn't check. In that case check goes to checkYourMove method, but there returns true
						if (Math.abs(Y2-Y1)==1) {
							//2.3. checkRookMove is true
								//2.4. Return true
								return true;
						}
					//2.2. If rook moves down at board (Y coordinate in the array increments)
						else if(Y2>Y1) {
							//2.3. Start searching for other figures from next cell (cause it's player's rook at first position)
							for (int i=Y1+1; i<Y2;i++) {								
								//2.4. If next cell in that direction isn't empty
								if(!Board.chessBoard[i][X2].equals(" ")) {						
									//2.5. checkRooksMove is false
										//2.6. Set error message
										errorMessage="There is a figure on rook's way. Rook can't go there!";
										return false;														
								}
								//2.5. If next cell in that direction is empty - continue searching
							}
							//2.6. If there are no figures on rook's way
								//2.7. checkRookMove is true
									//2.8. Return true								
									return true;
						}
					//2.3. f rook moves up at board (Y coordinate in the array decrements)
						else {
							 //2.4. Start searching for other figures from next cell (cause it's player's rook at first position)
							 for (int i=Y2+1; i<Y1;i++) {	
								//2.5. If next cell in that direction isn't empty
								if(!Board.chessBoard[i][X2].equals(" ")) {
									//2.6. checkRooksMove is false
										//2.7. Set error message	
										errorMessage="There is a figure on rook's way. Rook can't go there!";
										return false;						
								}
								//2.6. If next cell in that direction is empty - continue searching
							 }	
							//2.7. If there are no figures on rook's way
								//2.8. checkRookMove is true
									//2.9. Return true								
									return true;		
						}				
			}
			//1.2. If rook moves in X direction
			else if(Y1==Y2) {
				//2. Check is there any other figures on rook's way
					//2.1. Last position doesn't checked cause if there will be other player's figure, method will return false (but it must be true)
						//2.1.1. If rook moves only one cell forward - the last position doesn't check. In that case check goes to checkYourMove method, but there returns true
						if (Math.abs(X2-X1)==1) {
							//2.3. checkRookMove is true
								//2.4. Return true
								return true;
						}
					//2.2. If rook moves right at board (X coordinate in the array increments)
						else if(X2>X1) {		
							//2.3. Start searching for other figures from next cell (cause it's player's rook at first position)
							for (int i=X1+1; i<X2;i++) {
								//2.4. If next cell in that direction isn't empty								
								if(!Board.chessBoard[Y2][i].equals(" ")) {
									//2.5. checkRooksMove is false
										//2.6. Set error message	
										errorMessage="There is a figure on rook's way. Rook can't go there!";
										return false;					
								}
								//2.5. If next cell in that direction is empty - continue searching
							}
							//2.6. If there are no figures on rook's way
								//2.7. checkRookMove is true
									//2.8. Return true								
									return true;
						}
					//2.3. If rook moves left at board (X coordinate in the array decrements)
						else {	
							 //2.4. Start searching for other figures from next cell (cause it's player's rook at first position)
							for (int i=X2+1; i<X1;i++) {
								//2.5. If next cell in that direction isn't empty								
								if(!Board.chessBoard[Y2][i].equals(" ")) {
									//2.6. checkRooksMove is false
										//2.7. Set error message
										errorMessage="There is a figure on rook's way. Rook can't go there!";
										return false;						
								}
								//2.6. If next cell in that direction is empty - continue searching				
							}
							//2.7. If there are no figures on rook's way
								//2.8. checkRookMove is true
									//2.9. Return true								
									return true;				
						}	
						
			}
			//1.3. If rook doesn't move in Y or X directiona
				//1.4. checkRookMove is false
					//1.5. Set error message
					errorMessage="Rook doesn't go like that!";
					return false;
	}
	// 14. This method checks did player move his horse right. This method gets Y1X1 and Y2X2 (to be used in mate method)
	public static boolean checkHorseMove(int Y1, int X1, int Y2, int X2) {
		//1. There're two possible ways how horse can move.		
			//1.1	Y2 and Y1 differ by 3 AND X2 and X1 differ by 1 OR Y2 and Y1 differ by 1 AND X2 and X1 differ by 2	
		//2. Check is horse moving in one of that possible ways
			//2.2. If horse is moving in one of that possible ways
			if ((Math.abs(Y2-Y1)==2 && Math.abs(X2-X1)==1) ||(Math.abs(Y2-Y1)==1 && Math.abs(X2-X1)==2)) {	
				//2.2. checkHorseMove is true
					//2.3. Return true
					return true;
			}
			//2.3. If horse isn't moving in one of that possible ways
				//2.4. checkHorseMove is false
					//2.5. Set error message
					errorMessage="Horse doesn't go like that!";
					return false;
		
		  	
		 
	}
	// 15. This method checks did player move his bishop right. This method gets Y1X1 and Y2X2 (to be used in mate method)
	public static boolean checkBishopMove(int Y1, int X1, int Y2, int X2) {
		//1. Bishop can move only by diagonal (changes in coordinates Y1-Y2 and X1-X2 must be the same)
		//2. Check is bishop moving by diagonal
			//2.1 If bishop moves by diagonal
			if (Math.abs(Y2-Y1)==Math.abs(X2-X1)) {
				//3.Bishop can move in 4 directions (Y+X+, Y+X-,Y-X-,Y-X+). Define in which direction bishop moves and search for other figures in that way
				//Last position doesn't checked cause if there will be other player's figure, method will return false (but it must be true)
				//Define in which direction bishop moves and search for other figures in that way
						//3.1. If bishop moves only one cell  (the last position doesn't check. In that case check goes to checkYourMove method, but there returns true)
						if(Math.abs(Y2-Y1)==1 && Math.abs(X2-X1)==1) {	
							//3.2. checkBishopMove is true
								//3.3. Return true
								return true;
						}
						//3.2. If bishop moves in Y+X+ direction
						else if ((Y2-Y1)>0 && (X2-X1)>0) {
							//3.3. Search for any other figures at his way
							//Check before reach Y (or X) coordinate summary change							
							for (int i=1; i<Math.abs(Y2-Y1);i++) {
								//3.4. Check is there any other figures on bishop way and increment the coordinates to check next cell
									//3.5. If there is any figure
									if(!Board.chessBoard[Y1+i][X1+i].equals(" ")) {
										//3.6 checkBishopMove is false
											//3.7. Set error message
											errorMessage="There is a figure on bishop's way! Bishop can't go there!";
											return false;					
									}
									//3.6. If at that cell there is no figure - continue searching								
							}
							//3.7. If there are no figures at bishop way
								//3.8. checkBishopMove is true
									//3.9. Return true
									return true;				
						}
						//3.3. If bishop moves in Y+X- direction
						else if((Y2-Y1)>0 && (X2-X1)<0) {
							//3.4. Search for any other figures at his way
							//Check before reach Y (or X) coordinate summary change							
							for (int i=1; i<Math.abs(Y2-Y1);i++) {
								//3.5. Check is there any other figures on bishop way and increment the coordinates to check next cell
									//3.6. If there is any figure								
									if(!Board.chessBoard[Y1+i][X1-i].equals(" ")) {						
										//3.7 checkBishopMove is false
											//3.8. Set error message
											errorMessage="There is a figure on bishop's way! Bishop can't go there!";
											return false;
									}
									//3.7. If at that cell there is no figure - continue searching	
							}
							//3.8. If there are no figures at bishop way
								//3.9. checkBishopMove is true
									//4. Return true
									return true;					
						}
						//3.4. If bishop moves in Y-X- direction
						else if((Y2-Y1)<0 &&(X2-X1)<0) {
							//3.5. Search for any other figures at his way
							//Check before reach Y (or X) coordinate summary change								
							for (int i=1; i<Math.abs(Y2-Y1);i++) {
								//3.6. Check is there any other figures on bishop way and increment the coordinates to check next cell
									//3.7. If there is any figure
									if(!Board.chessBoard[Y1-i][X1-i].equals(" ")) {
										//3.8 checkBishopMove is false
											//3.9. Set error message
											errorMessage="There is a figure on bishop's way! Bishop can't go there!";
											return false;						
									}
									//3.8. If at that cell there is no figure - continue searching
								}
							//3.9. If there are no figures at bishop way
								//4.0. checkBishopMove is true
									//4.1. Return true
									return true;					
						}
						//3.5. If bishop moves in Y-X+ direction
						else {
							//3.6. Search for any other figures at his way
							//Check before reach Y (or X) coordinate summary change						
							for (int i=1; i<Math.abs(Y2-Y1);i++) {
								//3.7. Check is there any other figures on bishop way and increment the coordinates to check next cell
									//3.8. If there is any figure								
									if(!Board.chessBoard[Y1-i][X1+i].equals(" ")) {
										//3.9 checkBishopMove is false
											//4. Set error message
											errorMessage="There is a figure on bishop's way! Bishop can't go there!";
											return false;						
									}
									//3.9. If at that cell there is no figure - continue searching								
							}
							//4. If there are no figures at bishop way
								//4.1. checkBishopMove is true
									//4.2. Return true
									return true;			
						}					
			}
			//2.2 If bishop doesn't move by diagonal
				//2.3. checkBishopMove is false
					//2.4. Set error message
				errorMessage="Bishop doesn't go like that!";
					return false;			
	}
	// 16. This method checks did player move his king right. This method gets Y1X1 and Y2X2 (to be used in mate method)
	public static boolean checkKingMove(int Y1, int X1, int Y2, int X2) {
		//1. King can move in any direction, but only at one cell
		//2. Check is king movement is right. At destination point there could be player's figure. It checks in checkYourFigure method
			//2.1. If king movement is right
			if(Math.abs(X2-X1)<=1 && Math.abs(Y2-Y1)<=1) {
				//2.2. checkKingMove is true
					//2.3. Return true
						return true;			
			}
			//2.2. If king movement if false
				//2.3. checkKingMove is false
					//2.4. Set error message
					errorMessage="King doesn't go like that!";
					return false;				
	}
	// 17. This method checks did player move his queen right. This method gets Y1X1 and Y2X2 (to be used in mate method)
	public static boolean checkQueenMove (int Y1, int X1, int Y2, int X2) {
		//1. Queen can move like bishop or like rook
		//2. Check if queen moves like bishop
			//2.1 If queen moves like bishop
			if (checkBishopMove(Y1,X1,Y2,X2)) {
				//2.2. checkQueenMove is true
					//2.3. Return true
						return true;
				}
			//2.2. If queen doesn't move like bishop
			else {
				//2.3. Check if queen moves like rook
					//2.4. If queen moves like rook					
					if(checkRookMove(Y1, X1, Y2, X2)) {
						//2.5. checkQueenMove is true
							//2.6. Return true
							return true;
					}
					//2.5. If queen doesn't move like rook
						//2.6. checkQueenMove is false
							//2.7. Set error message
							errorMessage="Queen doesn't go like that!";
							return false;					
				}
	}
	// 18. This method checks did player move his figure right (according with rules to each figure). This method gets Y1X1 and Y2X2 (to be used in mate method)
	public static boolean checkFigureMove(int Y1, int X1, int Y2, int X2) {
		//1. Define which figure is need for check (it's figure at Y1X1 point
		String figure=Board.chessBoard[Y1][X1];
		//2. Define is that white or black figure and which exactly and then return value of checkMove method for that figure	
			//2.1.Case for white or black pawn
			if (figure.equals("\u2659")|| figure.equals("\u265F")) {
				//2.2. If player's figure is pawn - return value of checkPawnMove
				return checkPawnMove(Y1,  X1, Y2, X2);
			}
			//2.2. Case for white or black rook
			else if( figure.equals("\u2656")||figure.equals("\u265C")) {
				//2.3. If player's figure is rook - return value of checkRookMove
				return checkRookMove(Y1,X1,Y2,X2);
			}
			//2.3. Case for white or black horse
			else if(figure.equals("\u2658")||figure.equals("\u265E")) {
				//2.4. If player's figure is horse - return value of checkHorseMove
				return checkHorseMove(Y1, X1,  Y2,  X2);
			}
			//2.4. Case for white or black bishop
			else if (figure.equals("\u2657")|| figure.equals("\u265D")) {
				//2.5. If player's figure is bishop - return value of checkBishopMove
				return checkBishopMove(Y1,X1,Y2,X2);
			}
			//2.5.  Case for white or black king
			else if(figure.equals("\u2654")||figure.equals("\u265A")) {
				//2.6. If player's figure is king - return value of checkKingMove
				return checkKingMove(Y1, X1, Y2, X2);
			}
			//2.6. Case for white or black queen
			else if (figure.equals("\u2655")||figure.equals("\u265B")) {
				//2.7. If player's figure is queen - return value of checkQueenMove
				return checkQueenMove(Y1,X1,Y2,X2);
			}
			//2.7. Additional check. If it's not a figure
				//2.8. checkFigureMove is false
					//2.9. Return false			
					return false;					
	}
	// 19. This method checks can enemy's pawn make check to king. This method gets the king's coordinate as Y2 and X2
	public static boolean checkPawn(int Y2, int X2) {
		//1. First define which player's turn
			//1.1. If it's white player's turn
			if(whitePlayerTurn) {
				//2. Search for black pawns
			    for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If black pawn found
							if(Board.chessBoard[i][j].equals("\u265F")) {								
								//2.2. Check can black pawn go to white king position (pawn coordinates - is i and j, king - is Y2 and X2)
									//2.3. If black pawn can go to white king position
										if(checkPawnMove(i,j,Y2,X2)) {
											//2.4. checkPawn is false
												//2.5. Return false
												return false;
										}
										//2.4. If black pawn can't go to white king position - continue searching
							}
							//2.5. If black pawn is not found - continue searching						
					}										
				}
			    //2.6. If black pawns are not found or they can't go to white king's position
					//2.7. checkPawn is true
						//2.8. Return true
						return true;	
			}
			//1.2. If it's black player's turn
			else {
				//2. Search for white pawns
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If white pawn found
							if(Board.chessBoard[i][j].equals("\u2659")) {
								//2.2. Check can white pawn go to black king position (pawn coordinates - is i and j, king - is Y2 and X2)
									//2.3. If white pawn can go to black king position
									if(checkPawnMove(i,j,Y2,X2)) {
										//2.4. checkPawn is false
											//2.5. Return false
											return false;
									}
									//2.4. If white pawn can't go to black king position - continue searching								
							}
							//2.5. If white pawn is not found - continue searching						
					}						
				}
				//2.6. If white pawns are not found or they can't go to black king's position
					//2.7. checkPawn is true
						//2.8. Return true
							return true;	
				
			}
	}
	// 20. This method checks can enemy's rook make check to king. This method gets the king's coordinate as Y2 and X2
	public static boolean checkRook(int Y2, int X2) {
		//1. First define which player's turn
			//1.1. If it's white player's turn
			if(whitePlayerTurn) {
				//2. Search for black rooks
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
					//2.1. If black rook is found
						if(Board.chessBoard[i][j].equals("\u265C")) {
							//2.2. Check can black rook go to white king position (Rook coordinates - is i and j, king - is Y2 and X2)
								//2.3. If black rook can go to white king position
								if(checkRookMove(i,j,Y2,X2)) {
									//2.4. checkRook is false
										//2.5. Return false
										return false;
								}
								//2.4. If black rook can't go to white king position - continue searching
						}
						//2.5. If black rook not found - continue searching						
					}										
				}
				//2.6. If black rooks are not found or they can't go to white king's position
					//2.7. checkRook is true
						//2.8. Return true
						return true;	
			}
			//1.2. If it's black player's turn
			else {
				//2. Search for white rooks
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If white rook is found
							if(Board.chessBoard[i][j].equals("\u2656")) {
								//2.2. Check can white rook go to black king position (Rook coordinates - is i and j, king - is Y2 and X2)
									//2.3. If white rook can go to black king position
									if(checkRookMove(i,j,Y2,X2)) {
										//2.4. checkRook is false
											//2.5. Return false
											return false;
									}
									//2.4. If white rook can't go to black king position - continue searching								
							}
							//2.2. If white rook is not found - continue searching							
					}					
				}
				//2.4. If white rooks are not found or they can't go to black king's position
					//2.5. checkRook is true
						//2.6. Return true
						return true;			
			}
	}
	// 21. This method checks can enemy's horse make check to king. This method gets the king's coordinate as Y2 and X2
	public static boolean checkHorse(int Y2, int X2) {
		//1. First define which player's turn
			//1.1. If it's white player's turn
			if(whitePlayerTurn) {
				//2. Search for black horses
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If black horse is found
							if(Board.chessBoard[i][j].equals("\u265E")) {
								//2.2. Check can black horse go to white king position (horse coordinates - is i and j, king - is Y2 and X2)
									//2.3. If black horse can go to white king position
									if(checkHorseMove(i,j,Y2,X2)) {
										//2.4. checkHorse is false
											//2.5. Return false
											return false;
									}
									//2.4. If black horse can't go to white king position - continue searching
							}
							//2.5. If black horse is not found - continue searching
						}										
				}
				//2.6. If black horses are not found or they can't go to white king's position
					//2.7. checkHorse is true
						//2.8. Return true
						return true;	
			}
			//1.2. If it's black player's turn
			else {
				//2. Search for white horses
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If white horse is found
						if(Board.chessBoard[i][j].equals("\u2658")) {
							//2.2. Check can white horse go to black king position (horse coordinates - is i and j, king - is Y2 and X2)
								//2.3. If white horses can go to black king position
								if(checkHorseMove(i,j,Y2,X2)) {
									//2.4. checkHorse is false
										//2.5. Return false
										return false;
								}
								//2.4. If white horse can't go to black king position - continue searching
						}
						//2.5. If white horse is not found - continue searching							
					}					
				}
				//2.6. If white horses are not found or they can't go to black king's position
					//2.7. checkHorse is true
						//2.8. Return true
						return true;			
			}
	}
	// 22. This method checks can enemy's bishop make check to king. This method gets the king's coordinate as Y2 and X2
	public static boolean checkBishop(int Y2, int X2) {
		//1. First define which player's turn
			//1.1. If it's white player's turn
			if(whitePlayerTurn) {
				//2. Search for black bishops
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If black bishop is found
						if(Board.chessBoard[i][j].equals("\u265D")) {
							//2.2. Check can black bishop go to white king position (bishop coordinates - is i and j, king - is Y2 and X2)
								//2.3. If black bishop can go to white king position
								if(checkBishopMove(i,j,Y2,X2)) {
									//2.4. checkBishop is false
										//2.5. Return false
										return false;
								}
								//2.4. If black bishop can't go to white king position - continue searching
						}
						//2.5. If black bishop is not found - continue searching
					}										
				}
				//2.6. If black bishops are not found or they can't go to white king's position
					//2.7. checkBishop is true
						//2.8. Return true
						return true;	
			}
			//1.2. If it's black player's turn
			else {
				//2. Search for white bishops
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If white bishop is found
							if(Board.chessBoard[i][j].equals("\u2657")) {
								//2.2. Check can white bishop go to black king position (bishop coordinates - is i and j, king - is Y2 and X2)
									//2.3. If white bishop can go to black king position
									if(checkBishopMove(i,j,Y2,X2)) {
										//2.4. checkBishop is false
											//2.5. Return false
											return false;
									}
									//2.4. If white bishop can't go to black king position - continue searching
							}
						//2.5. If white bishop is not found - continue searching							
					}					
				}
				//2.6. If white bishops are not found or they can't go to black king's position
					//2.7. checkBishop is true
						//2.8. Return true
						return true;			
		}
}
	// 23. This method checks can enemy's king make check to king. This method gets the king's coordinate as Y2 and X2
	public static boolean checkKing(int Y2, int X2) {
		//1. First define which player's turn
			//1.1. If it's white player's turn
			if(whitePlayerTurn) {
				//2. Search for black king
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If black king is found
							if(Board.chessBoard[i][j].equals("\u265A")) {
								//2.2. Check can black king go to white king position (king coordinates - is i and j, king - is Y2 and X2)
									//2.3. If black king can go to white king position
									if(checkKingMove(i,j,Y2,X2)) {
										//2.4. checkKing is false
											//2.5. Return false
											return false;
									}
									//2.4. If black king can't go to white king position - -continue searching)				
							}
							//2.5. If black king is not found - continue searching
					}										
				}
				//2.6. If black kings is not found or they can't go to black king's position
					//2.6. checkKing is true
						//2.7. Return true
							return true;
				
			}
			//1.2. If it's black player's turn
			else {
				//2. Search for white king
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If white king is found
							if(Board.chessBoard[i][j].equals("\u2654")) {
								//2.2. Check can white king go to black king position (king coordinates - is i and j, king - is Y2 and X2)
									//2.3. If white king can go to black king position
									if(checkKingMove(i,j,Y2,X2)) {
										//2.4. checkKing is false
											//2.5. Return false
											return false;
									}
									//2.4. If white king can't go to black king position - continue searching
							}
							//2.5. If white king is not found - continue searching							
					}					
				}
				//2.6. If white kings is not found or they can't go to black king's position
					//2.7. checkKing is true
						//2.8. Return true
							return true;			
			}
	}
	// 24. This method checks can enemy's queen make check to king. This method gets the king's coordinate as Y2 and X2
	public static boolean checkQueen (int Y2, int X2) {
		//1. First define which player's turn
			//1.1. If it's white player's turn
			if(whitePlayerTurn) {
				//2. Search for black queen
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If black queen is found
							if(Board.chessBoard[i][j].equals("\u265B")) {
								//2.2. Check can black queen go to white king position (queen coordinates - is i and j, king - is Y2 and X2)
									//2.3. If black queen can go to white king position
									if(checkQueenMove(i,j,Y2,X2)) {
										//2.4. checkQueen is false
											//2.5. Return false
											return false;
									}
									//2.4. If black queen can't go to white king position - -continue searching)				
							}
							//2.5. If black queen is not found - continue searching	
					}										
				}
				//2.6. If black queens are not found or they can't go to black king's position
					//2.7. checkQueen is true
						//2.6. Return true
							return true;			
			}
			//1.2. If it's black player's turn
			else {
			//2. Search for white queen
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If white queen is found
							if(Board.chessBoard[i][j].equals("\u2655")) {
								//2.2. Check can white queen go to black king position (queen coordinates - is i and j, king - is Y2 and X2)
									//2.3. If white queen can go to black king position
									if(checkQueenMove(i,j,Y2,X2)) {
										//2.4. checkQueen is false
											//2.5. Return false
											return false;
									}
									//2.4. If white queen can't go to black king position - continue searching
							}
							//2.5. If white queen is not found - continue searching							
					}					
				}
				//2.6. If white queens are not found or they can't go to black king's position
					//2.7. checkQueen is true
						//2.8. Return true
							return true;			
		}
	}
	// 25. This method checks has player's king a check
	public static boolean check() {
		//1. Define which player's turn
			//1.1. If it's white player's turn
			if (whitePlayerTurn) {
				//2. Search for white king location
				for(int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1;j<Board.chessBoard[0].length;j++) {
						//2.1. If white king is found
						if (Board.chessBoard[i][j].equals("\u2654")) {							
							//2.2. Check has king a check from any enemy's figure. The king coordinates is i,j and it gives as Y2 and X2
								//2.3. If king has no check from any of enemy's figure
								if( checkRook(i,j) && checkHorse(i,j)&&checkBishop(i,j)&&checkQueen(i,j)&& checkPawn(i,j)&&checkKing(i,j)) {
									//2.4. check is true
										//2.5. Return true
										return true;
								}
								//2.3. If king has check from any of enemy's figure
									//2.4. Set error message
										errorMessage="White player's king has a check!";
										//2.5. Return false
										return false;							
						}
						//2.2. If white king isn't found - continues searching
					}
				}
				//2.3. If white king isn't found on board - it's can't be. Return true or false
				//return true;
						
				
				
			}
			//1.2. If it's black player's turn
			else {
				//2. Search for black king location
				for(int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1;j<Board.chessBoard[0].length;j++) {
						//2.1. If black king is found
						if (Board.chessBoard[i][j].equals("\u265A")) {
							//2.2. Check has king a check from any enemy's figure. The king coordinates is i,j and it gives as Y2 and X2
								//2.3. If king has no check from any of enemy's figure
								if( checkRook(i,j) && checkHorse(i,j)&&checkBishop(i,j)&&checkQueen(i,j)&&checkPawn(i,j)&&checkKing(i,j) ) {
									//2.4. check is true
										//2.5. Return true
										return true;
								}
								//2.3. If king has check from any of enemy's figure
								//2.4. Set error message
									errorMessage="Black player's king has a check!";
									//2.5. Return false
									return false;
						}
						//2.2. If black king isn't found - continues searching
					}
				}
				//2.3. If white king isn't found on board - it's can't be. Return true or false
			//	return true;
			}
			return false;
	}
	// 26. This method check can player's pawn save king from mate.
	public static boolean checkMatePawn() {
		//1. First define which player's turn
			//1.1. If it's white player's turn
			if (whitePlayerTurn) {
				//2. Search for white pawns
				for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If white pawn found
							if(Board.chessBoard[i][j].equals("\u2659")) {
								//2.2. Then get coordinates of every possible positions on the board to check can pawn move there
								for (int k=1; k<Board.chessBoard.length-1;k++) {
									for (int l=1; l<Board.chessBoard[0].length-1;l++) {
										//2.3 Check can pawn (Y1=i, X1=j) go to that position (Y2=k, X2=l)
											//2.4. If pawn can
											if(checkPawnMove(i,j,k,l) && checkYourMove(k,l)) {
												//2.5. Move pawn to that position
												
												move(i,j,k,l);
												//2.6. Check has king the check after that possible player's move
													//2.7. If king has no check
													if(check()) {
														
														//2.8. Decline that possible move
														declineMove();
														//2.9. matePawn is true
															//3. Return true
																return true;
													}
													//2.8. If king still has check
														//2.9. Decline that possible move
														declineMove();														
														//3.0. Try other position
											}
											//2.5. If pawn can't - try other position
									}
								}
								//2.6. If this pawn can't save king from check - search another pawn
							}
					}
				}
				//2.7. If no pawn can save king from check
					//2.8. matePawn is false
						//2.9. Return false
						return false;
			}
				
				
			//1.2. If it's black player's turn
			else {
				//2. Search for black pawns
			    for (int i=1; i<Board.chessBoard.length-1;i++) {
					for (int j=1; j<Board.chessBoard[0].length-1;j++) {
						//2.1. If black pawn found
							if(Board.chessBoard[i][j].equals("\u265F")) {
								//2.2. Then get coordinates of every possible positions on the board to check can pawn move there
								for (int k=1; k<Board.chessBoard.length-1;k++) {
									for (int l=1; l<Board.chessBoard[0].length-1;l++) {
										//2.3 Check can pawn (Y1=i, X1=j) go to that position (Y2=k, X2=l)
											//2.4. If pawn can
											if(checkPawnMove(i,j,k,l) && checkYourMove(k,l)) {
												//2.5. Move pawn to that position
												move(i,j,k,l);												
												//2.6. Check has king the check after that possible player's move
													//2.7. If king has no check
													if(check()) {
														//2.8. Decline that possible move														
														declineMove();														
														//2.9. matePawn is true
															//3. Return true
																return true;
													}
													//2.8. If king still has check
														//2.9. Decline that possible move													
														declineMove();
														
														//3.0. Try other position
											}
											//2.5. If pawn can't - try other position
									}
								}
								//2.6. If this pawn can't save king from check - search another pawn
							}
					}
				}
				//2.7. If no pawn can save king from check
					//2.8. matePawn is false
						//2.9. Return false
						return false;
			}
	}
	// 27. This method check can player's rook save king from mate.
	public static boolean checkMateRook() {
			//1. First define which player's turn
				//1.1. If it's white player's turn
				if (whitePlayerTurn) {
					//2. Search for white rooks
					for (int i=1; i<Board.chessBoard.length-1;i++) {
						for (int j=1; j<Board.chessBoard[0].length-1;j++) {
							//2.1. If white rook found
								if(Board.chessBoard[i][j].equals("\u2656")) {
									//2.2. Then get coordinates of every possible positions on the board to check can rook move there
									for (int k=1; k<Board.chessBoard.length-1;k++) {
										for (int l=1; l<Board.chessBoard[0].length-1;l++) {
											//2.3 Check can rook (Y1=i, X1=j) go to that position (Y2=k, X2=l)
												//2.4. If rook can
												if(checkRookMove(i,j,k,l) && checkYourMove(k,l)) {
													//2.5. Move rook to that position
													
													move(i,j,k,l);
													//2.6. Check has king the check after that possible player's move
														//2.7. If king has no check
														if(check()) {															
															//2.8. Decline that possible move
															declineMove();
															//2.9. mateRook is true
																//3. Return true
																	return true;
														}
														//2.8. If king still has check
															//2.9. Decline that possible move
															declineMove();														
															//3.0. Try other position
												}
												//2.5. If rook can't - try other position
										}
									}
									//2.6. If this rook can't save king from check - search another rook
								}
						}
					}
					//2.7. If no rook can save king from check
						//2.8. mateRook is false
							//2.9. Return false
							return false;
				}
					
					
				//1.2. If it's black player's turn
				else {
					//2. Search for black rooks
				    for (int i=1; i<Board.chessBoard.length-1;i++) {
						for (int j=1; j<Board.chessBoard[0].length-1;j++) {
							//2.1. If black rook found
								if(Board.chessBoard[i][j].equals("\u265C")) {
									//2.2. Then get coordinates of every possible positions on the board to check can rook move there
									for (int k=1; k<Board.chessBoard.length-1;k++) {
										for (int l=1; l<Board.chessBoard[0].length-1;l++) {
											//2.3 Check can rook (Y1=i, X1=j) go to that position (Y2=k, X2=l)
												//2.4. If rook can
												if(checkRookMove(i,j,k,l) && checkYourMove(k,l)) {
													//2.5. Move rook to that position
													move(i,j,k,l);													
													//2.6. Check has king the check after that possible player's move
														//2.7. If king has no check
														if(check()) {
															//2.8. Decline that possible move															
															declineMove();														
															//2.9. mateRook is true
																//3. Return true
																	return true;
														}
														//2.8. If king still has check
															//2.9. Decline that possible move														
															declineMove();															
															//3.0. Try other position
												}
												//2.5. If rook can't - try other position
										}
									}
									//2.6. If this rook can't save king from check - search another rook
								}
						}
					}
					//2.7. If no rook can save king from check
						//2.8. mateRook is false
							//2.9. Return false
							return false;
				}
		} 
	// 28. This method check can player's horse save king from mate.
	public static boolean checkMateHorse() {
				//1. First define which player's turn
					//1.1. If it's white player's turn
					if (whitePlayerTurn) {
						//2. Search for white horses
						for (int i=1; i<Board.chessBoard.length-1;i++) {
							for (int j=1; j<Board.chessBoard[0].length-1;j++) {
								//2.1. If white horse found
									if(Board.chessBoard[i][j].equals("\u2658")) {
										//2.2. Then get coordinates of every possible positions on the board to check can horse move there
										for (int k=1; k<Board.chessBoard.length-1;k++) {
											for (int l=1; l<Board.chessBoard[0].length-1;l++) {
												//2.3 Check can horse (Y1=i, X1=j) go to that position (Y2=k, X2=l)
													//2.4. If horse can
													if(checkHorseMove(i,j,k,l) && checkYourMove(k,l)) {
														//2.5. Move horse to that position
														
														move(i,j,k,l);
														//2.6. Check has king the check after that possible player's move
															//2.7. If king has no check
															if(check()) {															
																//2.8. Decline that possible move
																declineMove();
																//2.9. mateHorse is true
																	//3. Return true
																		return true;
															}
															//2.8. If king still has check
																//2.9. Decline that possible move
																declineMove();														
																//3.0. Try other position
													}
													//2.5. If horse can't - try other position
											}
										}
										//2.6. If this Horse can't save king from check - search another horse
									}
							}
						}
						//2.7. If no horse can save king from check
							//2.8. mateHorse is false
								//2.9. Return false
								return false;
					}
						
						
					//1.2. If it's black player's turn
					else {
						//2. Search for black horses
					    for (int i=1; i<Board.chessBoard.length-1;i++) {
							for (int j=1; j<Board.chessBoard[0].length-1;j++) {
								//2.1. If black horse found
									if(Board.chessBoard[i][j].equals("\u265E")) {
										//2.2. Then get coordinates of every possible positions on the board to check can horse move there
										for (int k=1; k<Board.chessBoard.length-1;k++) {
											for (int l=1; l<Board.chessBoard[0].length-1;l++) {
												//2.3 Check can horse (Y1=i, X1=j) go to that position (Y2=k, X2=l)
													//2.4. If horse can
													if(checkHorseMove(i,j,k,l) && checkYourMove(k,l)) {
														//2.5. Move horse to that position
														move(i,j,k,l);													
														//2.6. Check has king the check after that possible player's move
															//2.7. If king has no check
															if(check()) {
																//2.8. Decline that possible move															
																declineMove();														
																//2.9. mateHorse is true
																	//3. Return true
																		return true;
															}
															//2.8. If king still has check
																//2.9. Decline that possible move														
																declineMove();															
																//3.0. Try other position
													}
													//2.5. If horse can't - try other position
											}
										}
										//2.6. If this horse can't save king from check - search another horse
									}
							}
						}
						//2.7. If no horse can save king from check
							//2.8. mateHorse is false
								//2.9. Return false
								return false;
					}
			} 
	// 29. This method check can player's bishop save king from mate.
	public static boolean checkMateBishop() {
			//1. First define which player's turn
				//1.1. If it's white player's turn
				if (whitePlayerTurn) {
					//2. Search for white bishops
					for (int i=1; i<Board.chessBoard.length-1;i++) {
						for (int j=1; j<Board.chessBoard[0].length-1;j++) {
							//2.1. If white bishop found
								if(Board.chessBoard[i][j].equals("\u2657")) {
									//2.2. Then get coordinates of every possible positions on the board to check can bishop move there
									for (int k=1; k<Board.chessBoard.length-1;k++) {
										for (int l=1; l<Board.chessBoard[0].length-1;l++) {
											//2.3 Check can bishop (Y1=i, X1=j) go to that position (Y2=k, X2=l)
												//2.4. If bishop can
												if(checkBishopMove(i,j,k,l) && checkYourMove(k,l)) {
													//2.5. Move bishop to that position
													
													move(i,j,k,l);
													//2.6. Check has king the check after that possible player's move
														//2.7. If king has no check
														if(check()) {
															
															//2.8. Decline that possible move
															declineMove();
															//2.9. mateBishop is true
																//3. Return true
																	return true;
														}
														//2.8. If king still has check
															//2.9. Decline that possible move
															declineMove();														
															//3.0. Try other position
												}
												//2.5. If bishop can't - try other position
										}
									}
									//2.6. If this bishop can't save king from check - search another bishop
								}
						}
					}
					//2.7. If no bishop can save king from check
						//2.8. mateBishop is false
							//2.9. Return false
							return false;
				}
					
					
				//1.2. If it's black player's turn
				else {
					//2. Search for black bishops
				    for (int i=1; i<Board.chessBoard.length-1;i++) {
						for (int j=1; j<Board.chessBoard[0].length-1;j++) {
							//2.1. If black bishop found
								if(Board.chessBoard[i][j].equals("\u265D")) {
									//2.2. Then get coordinates of every possible positions on the board to check can bishop move there
									for (int k=1; k<Board.chessBoard.length-1;k++) {
										for (int l=1; l<Board.chessBoard[0].length-1;l++) {
											//2.3 Check can bishop (Y1=i, X1=j) go to that position (Y2=k, X2=l)
												//2.4. If bishop can
												if(checkBishopMove(i,j,k,l) && checkYourMove(k,l)) {
													//2.5. Move bishop to that position
													move(i,j,k,l);												
													//2.6. Check has king the check after that possible player's move
														//2.7. If king has no check
														if(check()) {
															//2.8. Decline that possible move														
															declineMove();														
															//2.9. mateBishop is true
																//3. Return true
																	return true;
														}
														//2.8. If king still has check
															//2.9. Decline that possible move													
															declineMove();
															
															//3.0. Try other position
												}
												//2.5. If bishop can't - try other position
										}
									}
									//2.6. If this bishop can't save king from check - search another bishop
								}
						}
					}
					//2.7. If no bishop can save king from check
						//2.8. mateBishop is false
							//2.9. Return false
							return false;
				}
		}
	// 30. This method check can player's queen save king from mate.
	public static boolean checkMateQueen() {
			//1. First define which player's turn
				//1.1. If it's white player's turn
				if (whitePlayerTurn) {
					//2. Search for white queens
					for (int i=1; i<Board.chessBoard.length-1;i++) {
						for (int j=1; j<Board.chessBoard[0].length-1;j++) {
							//2.1. If white queen found
								if(Board.chessBoard[i][j].equals("\u2655")) {
									//2.2. Then get coordinates of every possible positions on the board to check can queen move there
									for (int k=1; k<Board.chessBoard.length-1;k++) {
										for (int l=1; l<Board.chessBoard[0].length-1;l++) {
											//2.3 Check can queen (Y1=i, X1=j) go to that position (Y2=k, X2=l)
												//2.4. If queen can
												if(checkQueenMove(i,j,k,l) && checkYourMove(k,l)) {
													//2.5. Move queen to that position
													
													move(i,j,k,l);
													//2.6. Check has king the check after that possible player's move
														//2.7. If king has no check
														if(check()) {
															
															//2.8. Decline that possible move
															declineMove();
															//2.9. mateQueen is true
																//3. Return true
																	return true;
														}
														//2.8. If king still has check
															//2.9. Decline that possible move
															declineMove();														
															//3.0. Try other position
												}
												//2.5. If queen can't - try other position
										}
									}
									//2.6. If this queen can't save king from check - search another queen
								}
						}
					}
					//2.7. If no queen can save king from check
						//2.8. mateQueen is false
							//2.9. Return false
							return false;
				}
					
					
				//1.2. If it's black player's turn
				else {
					//2. Search for black queens
				    for (int i=1; i<Board.chessBoard.length-1;i++) {
						for (int j=1; j<Board.chessBoard[0].length-1;j++) {
							//2.1. If black queen found
								if(Board.chessBoard[i][j].equals("\u265B")) {
									//2.2. Then get coordinates of every possible positions on the board to check can queen move there
									for (int k=1; k<Board.chessBoard.length-1;k++) {
										for (int l=1; l<Board.chessBoard[0].length-1;l++) {
											//2.3 Check can queen (Y1=i, X1=j) go to that position (Y2=k, X2=l)
												//2.4. If queen can
												if(checkQueenMove(i,j,k,l) && checkYourMove(k,l)) {
													//2.5. Move queen to that position
													move(i,j,k,l);												
													//2.6. Check has king the check after that possible player's move
														//2.7. If king has no check
														if(check()) {
															//2.8. Decline that possible move														
															declineMove();														
															//2.9. mateQueen is true
																//3. Return true
																	return true;
														}
														//2.8. If king still has check
															//2.9. Decline that possible move													
															declineMove();
															
															//3.0. Try other position
												}
												//2.5. If queen can't - try other position
										}
									}
									//2.6. If this queen can't save king from check - search another queen
								}
						}
					}
					//2.7. If no queen can save king from check
						//2.8. mateQueen is false
							//2.9. Return false
							return false;
				}
		}
	// 31. This method check can player's king save himself from  mate.
	public static boolean checkMateKing() {
			//1. First define which player's turn
				//1.1. If it's white player's turn
				if (whitePlayerTurn) {
					//2. Search for white king
					for (int i=1; i<Board.chessBoard.length-1;i++) {
						for (int j=1; j<Board.chessBoard[0].length-1;j++) {
							//2.1. If white king found
								if(Board.chessBoard[i][j].equals("\u2654")) {
									//2.2. Then get coordinates of every possible positions on the board to check can king move there
									for (int k=1; k<Board.chessBoard.length-1;k++) {
										for (int l=1; l<Board.chessBoard[0].length-1;l++) {
											//2.3 Check can king (Y1=i, X1=j) go to that position (Y2=k, X2=l)
												//2.4. If king can
												if(checkKingMove(i,j,k,l) && checkYourMove(k,l)) {
													//2.5. Move king to that position
													
													move(i,j,k,l);
													//2.6. Check has king the check after that possible player's move
														//2.7. If king has no check
														if(check()) {
															
															//2.8. Decline that possible move
															declineMove();
															//2.9. mateKing is true
																//3. Return true
																	return true;
														}
														//2.8. If king still has check
															//2.9. Decline that possible move
															declineMove();														
															//3.0. Try other position
												}
												//2.5. If king can't - try other position
										}
									}
									//2.6. If king can't go anywhere - stop searching
									
									return false;
								}
						}
					}
					//2.7. If king can't go anywhere - return false
						//2.8. mateKing is false
							//2.9. Return false
							return false;
				}
					
					
				//1.2. If it's black player's turn
				else {
					//2. Search for black king
				    for (int i=1; i<Board.chessBoard.length-1;i++) {
						for (int j=1; j<Board.chessBoard[0].length-1;j++) {
							//2.1. If black king found
								if(Board.chessBoard[i][j].equals("\u265A")) {
									//2.2. Then get coordinates of every possible positions on the board to check can king move there
									for (int k=1; k<Board.chessBoard.length-1;k++) {
										for (int l=1; l<Board.chessBoard[0].length-1;l++) {
											//2.3 Check can king (Y1=i, X1=j) go to that position (Y2=k, X2=l)
												//2.4. If king can
												if(checkKingMove(i,j,k,l) && checkYourMove(k,l)) {
													//2.5. Move king to that position
													move(i,j,k,l);												
													//2.6. Check has king the check after that possible player's move
														//2.7. If king has no check
														if(check()) {
															//2.8. Decline that possible move														
															declineMove();														
															//2.9. mateKing is true
																//3. Return true
																	return true;
														}
														//2.8. If king still has check
															//2.9. Decline that possible move													
															declineMove();
															
															//3.0. Try other position
												}
												//2.5. If king can't - try other position
										}
									}
									//2.6. If this king can't go anywhere - stop searching
									return false;
								}
						}
					}
					//2.7. If  king can't go anywhere
						//2.8. mateKing is false
							//2.9. Return false
							return false;
				}
		}
	// 32. This method checks has player's king a mate
	public static boolean checkMate() {
		//1. Check can any of player's figure saves the king from check
			//1.1. If one of mate methods is true - true (checkMatePawn | checkRooMate
			if (checkMateRook()||checkMateHorse()||checkMateBishop()||checkMateQueen()||checkMatePawn()||checkMateKing()) {
				//1.3. mate is true
					//1.4. Set error for current player
					if(whitePlayerTurn) {
						errorMessage="White player's king has a check!";
					}
					else {
						errorMessage="Black player's king has a check!";
					}
					return true;
			}
			//1.2. If no player's figure can save king from check
			else {
				//1.3. mate is false
					//1.4. Set error for current player
					if(whitePlayerTurn) {
						errorMessage="White player's king has a mate! Black player wins!";
					}
					else {
						errorMessage="Black player's king has a mate! White player wins!";
					}
					return false;
			}
				
		
	}
	// 33. This method transform pawn into queen
	public static void pawnToQueen() {
		//1. Define which player's turn
			//1.1. If it's white player's turn
			if (whitePlayerTurn) {
				//1.2. If pawn goes to the another edge of board
				if(Y2==1) {
					Board.chessBoard[Y2][X2]="\u2655";
					JOptionPane.showMessageDialog(Board.frame, "White pawn is transformed to white queen");
				}
				//1.3. Else do nothing
				
			}
			//1.2. If it's black player's turn
			else {
				//1.2. If pawn goes to the another edge of board
				if(Y2==8) {
					Board.chessBoard[Y2][X2]= "\u265B";
					JOptionPane.showMessageDialog(Board.frame, "Black pawn is transformed to black queen");
				}
				//1.3. Else do nothing				
			}
	}
	// 34.This method allows player to make move
	public static void makeMove() {			
		//1. Get command from command field
		getCommand();
		//2. Get coordinates from command
		getCoordinates();
		//3. Check did player chose his figure, move it to empty cell or other player's figure and moved his figure by the rules
			//3.1. If he do it right		
			if(checkYourFigure() && checkYourMove(Y2, X2)&& checkFigureMove(Y1, X1, Y2, X2) ) {
				//3.2. Move figure (to check will there be a check if player make this move)
				move(Y1, X1, Y2,X2);
				//3.3. Check if player's king has check
					//3.4. If he has has no check
					if(check()) {
						//3.5. If pawn moved to opposite edge of board -change pawn to queen
						pawnToQueen();
						//3.6. Change player's turn
						whitePlayerTurn=!whitePlayerTurn;
						//3.7. Display the board changes
						Board.drawBoard();
						//3.8. Check has other player's king a check after that move
							//3.9. If he has check							
							if(!check()) {								
								//4.0. Check is there a mate
									//4.1. If there no mate
									if(checkMate()) {
										System.out.println("After checkmate true "+errorMessage);
										//4.2. Display error message (check)
										JOptionPane.showMessageDialog(Board.frame, errorMessage);
									}
									//4.2. If there a mate
									else {
										System.out.println("After checkmate false "+errorMessage);
										//4.3. Display error message (mate)
										JOptionPane.showMessageDialog(Board.frame, errorMessage);
									}
							}
					}
					//3.4. If he has check
					else {
						//3.5. Decline last player's move
						declineMove();
						//3.6. Display error message
						JOptionPane.showMessageDialog(Board.frame, errorMessage);
						//3.7. Clear commandField
						Board.commandField.setText("");
					}		
			}
			//3.2. If player move his figure wrong	
			else {
				//3.3. Display error message
				JOptionPane.showMessageDialog(Board.frame, errorMessage);
				//3.4. Clear commandField
				Board.commandField.setText("");
			}		
	}
	//35. This method runs the new game
	public static void newGame() {
		// 1.Move all figures to their default positions by replacing current chessBoard
		// array's state to original
		Board.chessBoard = Board.chessBoardNewGame;
		// 2. Set turn to white player (because white player always goes first)
		whitePlayerTurn = true;
		// 3. Set count of turn numbers to 0
		count=0;
		// 4. Display changes
		// 4.1. Display chessboard with original figure's locations
		Board.drawBoard();
		// 4.3. Clear commandField window
		Board.commandField.setText("");
		// 4.4. Display white player's turn
		Board.playerTurn.setText("White player's turn");
		// 5. Show information message
		JOptionPane.showMessageDialog(Board.frame, "New Game started!");
	}
	//36. This method decline last player's move
	public static void declineMove() {
		// 1. Replace current chessboard state with her previous state
		getPreviousChessBoardState();			
		// 2. Decrement turn's numbers count		 
		count--;		
							
	}
	
}
