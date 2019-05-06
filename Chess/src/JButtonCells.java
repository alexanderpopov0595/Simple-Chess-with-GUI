import javax.swing.*;

public class JButtonCells extends JButton{
	//1. Y and X coordinates
	int Y;
	int X;
	//2. Button name
	String name;
	//3. Constructor
	JButtonCells(String name,int Y, int X){
		this.name=name;		
		this.Y=Y;
		this.X=X;
	}

}
