package g1;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Turtle extends MovingPart implements Runnable {

	static int width = 3 * World.GRIDSIZE;

	Turtle(GridPosn posn, int lane, int speed) {
		this.posn = posn;
		this.lane = lane;
		this.speed = speed;
		this.x = this.posn.posnX();
		this.y = this.posn.posnY();
	}

	// imports turtle image and draws it
	public void draw(Graphics g) {
		ImageIcon i1 = new ImageIcon(
		        "./sprites/turtle.png");
		Image turtle = i1.getImage();
		drawMP(g, turtle);

	}
}
