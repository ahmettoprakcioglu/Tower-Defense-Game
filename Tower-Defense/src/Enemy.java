
public class Enemy {
	
	private int x;
	private int y;
	private int ID ;
	public Enemy(int x, int y, int ID) {
		this.x = x;
		this.y = y;
		this.ID = ID;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
