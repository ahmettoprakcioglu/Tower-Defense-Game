import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Block extends Rectangle {
	public int airID;
	public int groundID;

	public Block(int x, int y, int width, int height, int groundID, int airID) {
		setBounds(x, y, width, height);
		this.groundID = groundID;
		this.airID = airID;
	}

	public void draw(Graphics g) {
		g.drawRect(x, y, width, height);
		if (airID != Value.airAir) {

		}
	}
}
