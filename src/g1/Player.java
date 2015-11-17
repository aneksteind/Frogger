package g1;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends MovingPart implements Runnable {

	// which direction is the frog moving in
	int xDirection, yDirection;
	// coordinates of frog
	public Posn position;
	// width of frog
	int width = World.GRIDSIZE;
	// how many lives remain
	public int lives;
	// is the frog currently jumping from one grid square to another
	public boolean isJumping;
	// the direction the frog faces
	boolean up = true, down, left, right;

	// jumping and stationary images of the frog in every direction
	ImageIcon i1 = new ImageIcon("./sprites/frog stop.png");
	ImageIcon i2 = new ImageIcon("./sprites/frog jump.png");
	ImageIcon iright = new ImageIcon("./sprites/jumpright.png");
	ImageIcon ileft = new ImageIcon("./sprites/jumpleft.png");
	ImageIcon idown = new ImageIcon("./sprites/jumpdown.png");
	ImageIcon sright = new ImageIcon("./sprites/stopright.png");
	ImageIcon sleft = new ImageIcon("./sprites/stopleft.png");
	ImageIcon sdown = new ImageIcon("./sprites/stopdown.png");

	Image stop = i1.getImage();
	Image jump = i2.getImage();
	Image jumpright = iright.getImage();
	Image jumpleft = ileft.getImage();
	Image jumpdown = idown.getImage();
	Image stopright = sright.getImage();
	Image stopleft = sleft.getImage();
	Image stopdown = sdown.getImage();

	Player(Posn position) {
		this.position = position;
		this.lives = 3;
	}

	// x position
	public int posnX() {
		return this.position.x;
	}

	// y position
	public int posnY() {
		return this.position.y;
	}

	// (-) -> left, (+) -> right, magnitude determines jump distance
	public void setXDirection(int dir) {
		xDirection = dir;
	}

	// (-) -> up, (+) -> down, magnitude determines jump distance
	public void setYDirection(int dir) {
		yDirection = dir;
	}

	// returns lives of the player
	int getLives() {
		return this.lives;
	}

	// set position to a given Posn
	void setPosn(Posn position) {
		this.position = position;
	}

	// resets player's lives
	void resetLives() {
		this.lives = 3;
	}

	// in which lane of traffic is the frog
	int lane() {
		return this.position.y / World.GRIDSIZE;
	}

	// determines how fast the frog moves, based on the lane
	int speed(int lane) {
		return MakePart.partDir(lane);
	}

	// keyEvent handler for the frog, changes position and orientation
	public void keyMove(int keyCode, KeyEvent e) {
		if (keyCode == e.VK_LEFT) {
			setXDirection(-1);
			left = true;
			right = false;
			down = false;
			up = false;
			// moves player one grid-size to the left (-x)
			position = new Posn(this.position.x + (xDirection * World.GRIDSIZE),
			        this.position.y);
		}
		if (keyCode == e.VK_RIGHT) {
			setXDirection(1);
			left = false;
			right = true;
			down = false;
			up = false;
			// moves player one grid-size to the right (+x)
			position = new Posn(this.position.x + (xDirection * World.GRIDSIZE),
			        this.position.y);
		}
		if (keyCode == e.VK_UP) {
			setYDirection(-1);
			left = false;
			right = false;
			down = false;
			up = true;
			// moves player one grid-size upward (-y)
			position = new Posn(this.position.x,
			        this.position.y + (yDirection * World.GRIDSIZE));
		}
		if (keyCode == e.VK_DOWN) {
			setYDirection(1);
			left = false;
			right = false;
			down = true;
			up = false;
			// moves player one grid-size downward (+y)
			position = new Posn(this.position.x,
			        this.position.y + (yDirection * World.GRIDSIZE));
		}
		// keep frog still if leaving frame
		if (this.position.x < 3 * World.GRIDSIZE) {
			position = new Posn(3 * World.GRIDSIZE, position.y);
		}
		if (this.position.x + World.GRIDSIZE > World.WIDTH
		        - 3 * World.GRIDSIZE) {
			position = new Posn(World.WIDTH - 4 * World.GRIDSIZE, position.y);
		}
		if (this.position.y < World.GRIDSIZE) {
			position = new Posn(position.x, World.GRIDSIZE);
		}
		if (this.position.y + World.GRIDSIZE > World.HEIGHT) {
			position = new Posn(position.x, World.HEIGHT - World.GRIDSIZE);
		}
	}

	// set player to being in the middle of a jump or stationary
	// used to determine whether program draws frog as jumping or not
	public void setJumping(boolean jumping) {
		this.isJumping = jumping;
	}

	// if the player is on a log or turtle
	// the player moves based on that part's speed
	public void move() {
		if (collides(World.turtles) || collides(World.logs)) {
			position.x += speed(lane());
		}
	}

	// does the player collide with any part in the game?
	public boolean collides(ILoLane parts) {
		return parts.collides(this);
	}

	// knocks a life off the current value if the frog is hit
	// by a car or lands in the water, places player at
	// appropriate position
	public void lifeLoss() {
		if (this.collides(World.cars) || this.inWater()) {
			lives--;
			// if reached checkpoint, set position to checkpoint
			// else set at start position
			if (checkPointReached()) {
				position = World.checkPoint;
			} else {
				position = World.startPointPosn;
			}
		}
		// display message depending on outcome
		if (gameWon()) {
			World.endGame("You won!");
		}
		if (lives <= 0)
			World.endGame("You lose!");
	}

	// has the player reached the checkpoint
	boolean checkPointReached() {
		return this.position.y <= World.checkPointPosn.y;
	}

	// the game is won once the player reaches the final grid position 
	boolean gameWon() {
		return this.position.y <= World.endPosn.y;
	}

	// is the player in the water
	boolean inWater() {
		return (!collides(World.turtles)) && !collides(World.logs)
		        && (lane() >= 2) && (lane() <= 6);
	}

	// draws the frog at the corresponding orientation
	public void draw(Graphics g) {
		if (this.isJumping) {
			if (this.xDirection > 0) {
				drawMP(g, jumpright);
			} else if (this.xDirection < 0) {
				drawMP(g, jumpleft);
			} else if (this.yDirection > 0) {
				drawMP(g, jumpdown);
			} else {
				drawMP(g, jump);
			}
		} else {
			if (right) {
				drawMP(g, stopright);
			} else if (left) {
				drawMP(g, stopleft);
			} else if (down) {
				drawMP(g, stopdown);
			} else {
				drawMP(g, stop);
			}
		}
	}

	// draws the frog
	public void drawMP(Graphics g, Image frog) {
		g.drawImage(frog, this.position.x, this.position.y, null);
	}
}
