import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Store  {
	public static int shopWidth = 2;
	public static int buttonSize = 52;
	public static int cellSpace = 30;
	public Rectangle[] button = new Rectangle[shopWidth];
	
	public Store() {
		define();
	}

	public void define() {
		for (int i = 0; i < button.length; i++) {
			button[i] = new Rectangle(
					((Screen.myWidth / 2) - ((3 * (buttonSize + cellSpace)) / 2) + ((buttonSize + cellSpace)))+325,200 + (i * 100), buttonSize, buttonSize);

		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		
		for (int i = 0; i < button.length; i++) {
			g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
		}
		
	}
	
	public void drawlight(Graphics g) {
		g.setColor(Color.GRAY);
		
		for (int i = 0; i < button.length ; i++) {
			g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
		}
		
	}


	
}
