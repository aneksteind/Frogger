package g1;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Car extends MovingPart implements Runnable {

	int width = World.GRIDSIZE;

	// retrieve images of cars
	ImageIcon i1 = new ImageIcon("./sprites/car1.png");
	ImageIcon i2 = new ImageIcon("./sprites/car2.png");
	ImageIcon i3 = new ImageIcon("./sprites/car3.png");
	ImageIcon i4 = new ImageIcon("./sprites/car1r.png");
	ImageIcon i5 = new ImageIcon("./sprites/car2r.png");
	ImageIcon i6 = new ImageIcon("./sprites/car3r.png");

	// get images of cars
	Image car1 = i1.getImage();
	Image car2 = i2.getImage();
	Image car3 = i3.getImage();
	Image car1r = i4.getImage();
	Image car2r = i5.getImage();
	Image car3r = i6.getImage();

	Car(GridPosn posn, int lane, int speed) {
		this.posn = posn;
		this.lane = lane;
		this.speed = speed;
		this.x = this.posn.posnX();
		this.y = this.posn.posnY();
	}

	
	// draws a particular car it's lane and direction
	public void draw(Graphics g) {
		if (this.lane % 3 == 0) {
			if (this.speed < 0) {
				drawMP(g, car1);
			} else {
				drawMP(g, car1r);
			}
		} else if (this.lane % 3 == 1) {
			if (this.speed < 0) {
				drawMP(g, car2);
			} else {
				drawMP(g, car2r);
			}
		} else {
			if (this.speed < 0) {
				drawMP(g, car3);
			} else {
				drawMP(g, car3r);
			}
		}
	}

	// does the car collide with the player
	public boolean collides(Player p) {
		return p.posnY() / World.GRIDSIZE == lane
		        && (this.x - p.posnX()) < p.width
		        && (p.posnX()) - this.x < this.width;
	}

	@Override
	public void lifeLoss() {
		// TODO Auto-generated method stub

	}

}
