import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame extends JFrame {
	public static String title = "Tower Defense Game Development";
	public static Dimension size = new Dimension(750, 550);

	public Frame() {
		setTitle(title);
		setSize(size);
		setResizable(false);
		setFocusable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}

	public void init() {

		setLayout(new GridLayout(1, 1, 0, 0));
		Screen screen = new Screen();
		Store store = new Store();
		screen.requestFocus();
		screen.addKeyListener(screen);
		screen.addMouseListener(screen);
		screen.addMouseMotionListener(screen);
		screen.setFocusable(true);
		screen.setFocusTraversalKeysEnabled(false);
		add(screen);
		setVisible(true);
	}

	public static void main(String[] args) {
		Frame frame = new Frame();

	}
}
