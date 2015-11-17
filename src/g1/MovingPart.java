package g1;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class MovingPart implements Runnable {

	// dimensions of a moving part
	int width;
	int height = World.GRIDSIZE;
	
	// x position, y position, and lane of the moving part
	int x, y, lane;
	Posn position = new Posn(x, y);
	int speed;
	// like a Posn, but in terms of grid square size
	GridPosn posn;

	//are the moving parts running?
	boolean running = true;
	
	// threads a single moving part
	public void run() {
		try {
			while (running) {
				move();
				lifeLoss();
				Thread.sleep(20);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			running = false;
		}
	}

	// how the loss of a beloved frog is handled
	public void lifeLoss(){};

	// moves the object and makes it reappear on the other
	// side of the screen when it has gone outside the window
	public void move() {
		x = (((x + speed) % World.WIDTH) + World.WIDTH) % World.WIDTH;
	}

	// draws the moving part
	public abstract void draw(Graphics g);

	// draws a moving part
	public void drawMP(Graphics g, Image partName) {
		g.drawImage(partName, this.x, this.y, null);
	}

	// does the moving part collide with the player
	public boolean collides(Player p) {
		return p.posnY() / World.GRIDSIZE == lane
		        && (this.x - p.posnX()) < p.width
		        && (p.posnX()) - (this.x + World.GRIDSIZE) < this.width
		                + 2 * World.GRIDSIZE;

	}
	
	// moving parts are initialized as moving
	public void setRun(boolean running){
		this.running = running;
	}

}
