
import java.awt.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel {

	public void paintComponent(Graphics g) {
		// 1. Get background image
		Image background = new ImageIcon("background\\Background.png").getImage();		
		// 2. Draw that image at mainPanel
		g.drawImage(background, 0, 0, this);
	}

}
