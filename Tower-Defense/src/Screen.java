import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;

public class Screen extends JPanel
		implements Runnable, KeyListener, MouseListener, MouseMotionListener, ActionListener {

	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Towers> tower1 = new ArrayList<Towers>();
	private ArrayList<Towers> tower2 = new ArrayList<Towers>();
	private ArrayList<Towers> kalicitowerlar1 = new ArrayList<Towers>();
	private ArrayList<Towers> kalicitowerlar2 = new ArrayList<Towers>();
	private ArrayList<Fire> atesler = new ArrayList<Fire>();
	boolean bisnsonrasil = false;
	boolean level1;
	boolean atesekle;
	int enemyID = 0;
	
	Timer timer = new Timer();
	TimerTask task_levels = new TimerTask() {

		@Override
		public void run() {

			if (level1) {

				for (int i = 0; i < 1; i++) {
					enemyID++;
					enemies.add(new Enemy(0, 220,enemyID));
					
				}

			}
			if (enemies.size() >= 5) {
				level1 = false;
			}
		}
	};

	TimerTask ates = new TimerTask() {

		@Override
		public void run() {
			
			if (enemies.size() == 0 ) {
				atesler.clear();
			}
			
			
			
			
			for (Towers kalicitowers : kalicitowerlar1) {
				try {
					for (Enemy enem : enemies) {
						if (new Rectangle(kalicitowers.getTowerX() - 65, kalicitowers.getTowerY() - 50, 200, 200)
								.intersects(new Rectangle(enem.getX(), enem.getY(), 20, 20))) {
							try {
								Fire fire = new Fire(kalicitowers.getTowerX(), kalicitowers.getTowerY());
								
								atesler.add(fire);
								atesekle = true;

							} catch (Exception e) {
								// TODO: handle exception
							}

						}
						
						for (Fire fires1 : atesler) {
							if (!(new Rectangle(fires1.getX(), fires1.getY(), atestopuimage.getWidth() / 35,
									atestopuimage.getHeight() / 35).intersects(
											new Rectangle(kalicitowers.getTowerX() - 40, kalicitowers.getTowerY() - 20, 150, 150)))) {
								//atesler.remove(fires1);
								//enemies.remove(enem);
								
							}

						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			
			

		}
	};

	TimerTask task_blood = new TimerTask() {

		@Override
		public void run() {

			if (bisnsonrasil) {
				heartbreak = false;
				bisnsonrasil = false;
			}

		}
	};

	public Thread thread1 = new Thread(this);

	public static Room room;
	public static Store store;
	public static int myWidth, myHeight;
	public static boolean isFirst = true;
	private BufferedImage image, image1, imageTower1, imageTower2, atestopuimage, heartbreakimage, menu0, menu1;
	int dirNameX = 550;
	int dirARttırX = 2;
	int atesdirY = 1;
	int atesdirX = 1;
	int imageTower1X = 650;
	int imageTower1Y = 175;
	int imageTower2X = 657;
	int imageTower2Y = 275;

	public Screen() {
		
		
		timer.schedule(task_blood, 0, 800);
		timer.schedule(task_levels, 0, 1300);
		timer.schedule(ates, 0, 700);

		Utilies utilies = new Utilies();

		thread1.start();

		try {
			image = ImageIO.read(new FileImageInputStream(new File("Image/mapson1.png")));
			image1 = ImageIO.read(new FileImageInputStream(new File("Image/enemy1.png")));
			imageTower1 = ImageIO.read(new FileImageInputStream(new File("Image/tower1.png")));
			imageTower2 = ImageIO.read(new FileImageInputStream(new File("Image/tower2.gif")));
			atestopuimage = ImageIO.read(new FileImageInputStream(new File("Image/atestopu.png")));
			heartbreakimage = ImageIO.read(new FileImageInputStream(new File("Image/blood.png")));
			menu0 = ImageIO.read(new FileImageInputStream(new File("Image/menu0.jpg")));
			menu1 = ImageIO.read(new FileImageInputStream(new File("Image/menu1.jpg")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void define() {
		store = new Store();
		room = new Room();
	}

	boolean heartbreak = false;
	boolean alanıgor = true;
	boolean alanıgor2 = false;

	public void paintComponent(Graphics g) {
		if (menu) {
			// menucizdirir
			if (menubuton) {
				g.drawImage(menu1, 0, 0, null);
			} else if (!(menubuton)) {

				g.drawImage(menu0, 0, 0, null);
			}
		}

		if (oyun) {
			if (isFirst) {
				myWidth = getWidth();
				myHeight = getHeight();

				define();
				isFirst = false;
			}
			setBackground(Color.BLACK);
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawImage(image, 0, 0, null);

			// DRAW�NG HEART BREAK
			if (heartbreak) {
				g.drawImage(heartbreakimage, 0, 0, null);
				bisnsonrasil = true;
			}

			

			// ATE� B�R YERDE S�L�NMEL� X VE Y (-)'LERE D���NCE
			try {
				for (Fire ates : atesler) {
					g.drawImage(atestopuimage, ates.getX(), ates.getY(), atestopuimage.getWidth() / 20,
							atestopuimage.getHeight() / 20, this);

				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			for (int i = 0; i < enemies.size(); i++) {
				g.drawImage(image1, enemies.get(i).getX(), enemies.get(i).getY(), 33, 33, this);

			}

			try {
				for (Enemy enem : enemies) {
					if (enem.getX() == 589) {
						enemies.remove(enem);
						Utilies.health--;
						heartbreak = true;

					}
				}

			} catch (Exception e) {
			}

			// room.draw(g); // Drawing the room.
			if (darkciz) {
				store.draw(g);
			} else {
				store.drawlight(g);

			}

			/*
			 * if (alan�gor) { BURADA TOWERLARDA DOLANIP KORD�NAT BEL�RLE VE �EMBER� ��Z
			 * 
			 * }
			 */

			g.drawImage(imageTower1, imageTower1X, imageTower1Y, imageTower1.getWidth() / 5,
					imageTower1.getHeight() / 5, this);
			g.drawImage(imageTower2, imageTower2X, imageTower2Y, imageTower2.getWidth() / 6,
					imageTower2.getHeight() / 6, this);

			for (Towers tower1 : tower1) {
				g.drawImage(imageTower1, tower1.getTowerX(), tower1.getTowerY(), imageTower1.getWidth() / 4,
						imageTower1.getHeight() / 4, this);

				g.setColor(Color.red);
				g.drawOval(tower1.getTowerX() - 65, tower1.getTowerY() - 50, 200, 200);

			}
			for (Towers tower2 : tower2) {
				g.drawImage(imageTower2, tower2.getTowerX(), tower2.getTowerY(), imageTower2.getWidth() / 5,
						imageTower2.getHeight() / 5, this);
				g.setColor(Color.red);
				g.drawOval(tower2.getTowerX() - 65, tower2.getTowerY() - 50, 200, 200);

			}

			for (Towers kalicitower1 : kalicitowerlar1) {
				g.drawImage(imageTower1, kalicitower1.getTowerX(), kalicitower1.getTowerY(), imageTower1.getWidth() / 4,
						imageTower1.getHeight() / 4, this);
				if (alanıgor) {
					g.setColor(Color.red);
					g.drawOval(kalicitower1.getTowerX() - 65, kalicitower1.getTowerY() - 50, 200, 200);
				}

			}

			for (Towers kalicitower2 : kalicitowerlar2) {
				g.drawImage(imageTower2, kalicitower2.getTowerX(), kalicitower2.getTowerY(), imageTower2.getWidth() / 5,
						imageTower2.getHeight() / 5, this);
			}

			// health
			g.setColor(Color.white);
			g.setFont(new Font("Health", Font.BOLD, 21));
			g.drawString("" + Utilies.health, 670, 68);

			// budget
			g.setColor(Color.white);
			g.setFont(new Font("Bullet", Font.BOLD, 21));
			g.drawString("" + Utilies.budget + "$", 670, 128);

			
			g.setColor(Color.white);
			g.setFont(new Font("Bullet", Font.BOLD, 21));
			g.drawString("LEVEL" + Utilies.level + "$", 250, 480);

			dirNameX += dirARttırX;

			if (dirNameX > -700) {
				dirARttırX = -dirARttırX;
			}
			if (dirNameX == -700) {
				dirNameX = 750;
			}
			if (dirNameX <= 750) {
				dirARttırX = -dirARttırX;
			}

			g.setColor(Color.white);
			g.setFont(new Font("Health", Font.ITALIC, 18));
			
		}
	}

	boolean menu = true;
	boolean oyun = false;
	boolean menubuton = true;
	
	@Override
	public void run() {
		
		
		while (menu) {

			repaint();
		}

		while (oyun) {
			System.out.println(atesler.size());
			
			
			if (Utilies.health > 0) {

				try {
					for (Enemy enemy : enemies) {

						if (enemy.getX() >= 0 && enemy.getX() < 82) {
							enemy.setX(enemy.getX() + 1);
						}

						else if (enemy.getX() == 82 && enemy.getY() > 92 && enemy.getY() <= 220) {
							enemy.setY(enemy.getY() - 1);

						} else if (enemy.getX() >= 82 && enemy.getX() < 208) {
							enemy.setX(enemy.getX() + 1);
						}

						else if (enemy.getX() == 208 && enemy.getY() >= 92 && enemy.getY() < 265) {
							enemy.setY(enemy.getY() + 1);

						} else if (enemy.getX() >= 208 && enemy.getX() < 370) {
							enemy.setX(enemy.getX() + 1);
						} else if (enemy.getX() == 370 && enemy.getY() > 180 && enemy.getY() <= 265) {
							enemy.setY(enemy.getY() - 1);

						} else if (enemy.getX() >= 370 && enemy.getX() < 589) {
							enemy.setX(enemy.getX() + 1);
						}

					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				

				try {
					for (Fire fire : atesler) {

						fire.setX(fire.getX() - atesdirX);
						fire.setY(fire.getY() - atesdirY);

					}
				} catch (Exception e) {
					// TODO: handle exception
				}

				try {

				} catch (Exception e) {
					// TODO: handle exception
				}

				try {
					thread1.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				repaint();

				// OYNANACAK ATE� B�R KERE EKLENMEL�
				// ************************************************ //

				/*
				 * for (Towers kal�c�towers : kalicitowerlar1) { for (Enemy enem : enemies) { if
				 * (kal�c�towers.getTowerX() + 100 >= enem.getX() && kal�c�towers.getTowerX() -
				 * 100 <= enem.getX() || kal�c�towers.getTowerY() + 100 >= enem.getY() &&
				 * kal�c�towers.getTowerY() - 100 <= enem.getY()) { Fire fire = new
				 * Fire(kal�c�towers.getTowerX(), kal�c�towers.getTowerY()); atesler.add(fire);
				 * } } }
				 */

				// ENEM�ES KORD�NATLARINI VER�R
				/*
				 * try { for (Enemy enem : enemies) { // System.out.println(" X :" + enem.getX()
				 * + " ---Y " + enem.getY()); }
				 * 
				 * } catch (Exception e) { }
				 */

			} else {
				String mesaj = "GAME OVER";
				JOptionPane.showMessageDialog(this, mesaj);
				System.exit(0);
			}

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();

		if (c == KeyEvent.VK_SPACE) {
			level1 = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	int x1;
	int y1;
	boolean tower1boolean = false;
	boolean tower2boolean = false;
	public static Point mse = new Point(0, 0);

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		int c = e.getButton();
		x1 = e.getX();
		y1 = e.getY();
		System.out.println("T�klan�lanX --> " + x1 + " T�klan�lanY --> " + y1);

		if (c == e.BUTTON1) {

			for (int i = 0; i < kalicitowerlar1.size(); i++) {
				if (new Rectangle(kalicitowerlar1.get(i).getTowerX(), kalicitowerlar1.get(i).getTowerY(), 70, 100)
						.intersects(new Rectangle(x1, y1, 3, 3))) {
					alanıgor = true;

				} else {
					alanıgor = false;
				}

			}

		}

		if (c == e.BUTTON1 && (x1 >= 288 && x1 <= 446) && (y1 >= 415 && y1 <= 468)) {
			menu = false;
			oyun = true;
		}

		if (c == e.BUTTON1 && (x1 >= 650 && x1 <= 700) && (y1 >= 200 && y1 <= 250) && Utilies.budget > 100) {
			Towers newtower1 = new Towers(650, 175, 100);
			tower1.add(newtower1);
			tower1boolean = true;
			Utilies.budget -= 100;
		}

		// level buton kordinat
		/*
		 * if (c == e.BUTTON1 && (x1 >= 25 && x1 <= 125) && (y1 >= 450 && y1 <= 500)) {
		 * 
		 * Utilies.level++; }
		 */

		if (c == e.BUTTON1 && (x1 >= 650 && x1 <= 700) && (y1 >= 300 && y1 <= 350) && Utilies.budget > 100) {
			Towers newtower2 = new Towers(657, 275, 100);
			tower2.add(newtower2);
			tower2boolean = true;
			Utilies.budget -= 200;
		}

		
		if (c == e.BUTTON3) {
			tower1.clear();
			tower2.clear();

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		/*
		 * int c = e.getButton();
		 * 
		 * if (c == e.BUTTON1) {
		 * 
		 * enemies.add(new Enemy(0, 220));
		 * 
		 * }
		 */

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (Towers tower1 : tower1) {
			kalicitowerlar1.add(tower1);
		}
		tower1.clear();
		tower1boolean = false;

		for (Towers tower2 : tower2) {
			kalicitowerlar2.add(tower2);
		}
		tower2.clear();
		tower2boolean = false;
	}

	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	boolean darkciz = true;
	int mx, my;

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();

		if ((mx >= 288 && mx <= 446) && (my >= 415 && my <= 468)) {
			menubuton = false;
		} else {
			menubuton = true;
		}

		// butonlar�n rengini de�i�tirir
		if ((mx >= 650 && mx <= 700) && (my >= 200 && my <= 250) && Utilies.budget > 100) {
			darkciz = false;
		} else {
			darkciz = true;
		}

		if (tower1boolean) {
			for (Towers tower1 : tower1) {
				tower1.setTowerX(e.getX() - 20);
				tower1.setTowerY(e.getY() - 20);
				alanıgor = true;
			}

		}

		else if (tower2boolean) {
			for (Towers tower2 : tower2) {
				tower2.setTowerX(e.getX() - 20);
				tower2.setTowerY(e.getY() - 20);
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
