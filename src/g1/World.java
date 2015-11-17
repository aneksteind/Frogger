package g1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class World extends JFrame {

	// is the game running?
	static boolean running;

	// message displayed upon winning/losing
	static String message = "";

	// sets dimensions of the world/size of grid square
	public static int GRIDSIZE = 20;
	// the world is 31x15
	public static int WIDTH = 31 * GRIDSIZE;
	public static int HEIGHT = 15 * GRIDSIZE;

	// all locations are of the top left corner of the part

	// starting line location
	public Posn startPosn = new Posn(0, HEIGHT - GRIDSIZE);
	// finish line location
	static public Posn endPosn = new Posn(0, GRIDSIZE);
	// river location
	public Posn waterPosn = new Posn(0, 2 * GRIDSIZE);
	// checkpoint location
	public static Posn checkPointPosn = new Posn(0, 7 * GRIDSIZE);
	// left black panel location
	public Posn leftPanelPosn = new Posn(0, 0);
	// right black panel location
	public Posn rightPanelPosn = new Posn(WIDTH - 3 * GRIDSIZE, 0);

	// double buffering declarations
	Image dbImage;
	Graphics dbg;

	// frog starting point and checkpoint position
	public static Posn startPointPosn = new Posn(15 * GRIDSIZE, 14 * GRIDSIZE);
	public static Posn menuPosn = new Posn(15 * GRIDSIZE, 9 * GRIDSIZE);
	public static Posn checkPoint = new Posn(15 * GRIDSIZE, 7 * GRIDSIZE);

	// initializes Player at start point
	public static Player player = new Player(menuPosn);

	// creates lanes of all moving parts
	public static ILoLane cars = buildLanes(8, 13, 1, new MakeCar());
	public static ILoLane turtles = buildLanes(2, 6, 2, new MakeTurtle());
	public static ILoLane logs = buildLanes(3, 5, 2, new MakeLog());

	World() {

		// creates window, adds key input detection
		setTitle("Game");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(new KeyInput());
		setBackground(Color.BLACK);
		this.running = false;
	}

	public class KeyInput extends KeyAdapter {

		// changes direction of player and moves player
		// based on arrow key pressed
		// starts the game if the spacebar is pressed
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			player.keyMove(keyCode, e);
			player.setJumping(true);
			if (!running && keyCode == e.VK_SPACE) {
				startGame();
			}
		}

		// stops player from moving when key is released
		public void keyReleased(KeyEvent e) {
			player.setXDirection(0);
			player.setYDirection(0);
			player.setJumping(false);
		}
	}

	// double buffering
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		draw(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	// builds lanes of a specified part
	// spread means part appears every n lanes
	static ILoLane buildLanes(int end, int start, int spread,
	        IMovingPartMaker part) {
		if (start < end) {
			return new MTLoLane();
		} else {
			return new ConsLane(buildLane(2, start, part),
			        buildLanes(end, start - spread, spread, part));
		}
	}

	// builds a single lane of a specified part
	public static ILoMP buildLane(int numParts, int lane,
	        IMovingPartMaker part) {
		if (numParts <= 0) {
			return new MTLoMP();
		} else {
			return new ConsMP(part.makeMP(numParts, lane),
			        buildLane(numParts - 1, lane, part));
		}
	}

	// main function, runs the game
	public static void main(String[] args) {
		new World();
	}

	// draws aspects of the game
	public void draw(Graphics g) {
		if (running) {
			// draw all static parts
			draw(g, startPosn, WIDTH, GRIDSIZE, Color.BLACK);
			draw(g, endPosn, WIDTH, GRIDSIZE, Color.BLACK);
			draw(g, waterPosn, WIDTH, 5 * GRIDSIZE, Color.BLUE);
			draw(g, startPosn, WIDTH, GRIDSIZE, Color.DARK_GRAY);
			// draw all moving parts
			turtles.draw(g);
			logs.draw(g);
			player.draw(g);
			cars.draw(g);
			// draw the left and right panels and display lives
			draw(g, leftPanelPosn, GRIDSIZE * 3, HEIGHT, Color.BLACK);
			draw(g, rightPanelPosn, GRIDSIZE * 3, HEIGHT, Color.BLACK);
			g.setColor(Color.WHITE);
			g.drawString("lives: " + player.getLives(), 10, 50);
			repaint();
		} else {
			// menu screen
			// draw "Frogger!" text
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("Frogger!", World.WIDTH / 2 - 60, World.HEIGHT / 2 - 70);
			// draw menu instruction
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("Press spacebar to start", World.WIDTH / 2 - 80,
			        World.HEIGHT - 40);
			g.drawString(message, World.WIDTH / 2 - 35, World.HEIGHT / 2);
			// draw the player on the menu screen
			player.draw(g);
			repaint();
		}
	}

	// draws an object at a given Posn
	public void draw(Graphics g, Posn p, int width, int height, Color c) {
		g.setColor(c);
		g.fillRect(p.x, p.y, width, height);
	}

	// threads moving parts only when the game is running
	public void startGame() {
		setRunning(true);
		player.setPosn(startPointPosn);
		cars.thread();
		turtles.thread();
		logs.thread();
		new Thread(player).start();

	}

	// stops threads, resets player lives, displays end-game message
	public static void endGame(String s) {
		player.resetLives();
		player.setPosn(menuPosn);
		setRunning(false);
		World.message = s;
	}

	// sets each moving part to either run or not run
	// toggles option to have world run or not run (changes display)
	public static void setRunning(boolean running) {
		World.running = running;
		player.setRun(running);
		cars.setRun(running);
		turtles.setRun(running);
		logs.setRun(running);
	}

}
