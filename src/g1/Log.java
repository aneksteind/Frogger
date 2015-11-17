package g1;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Log extends MovingPart implements Runnable {

	static int width = 3 * World.GRIDSIZE;

	Log(GridPosn posn, int lane, int speed) {
		this.posn = posn;
		this.lane = lane;
		this.speed = speed;
		this.x = this.posn.posnX();
		this.y = this.posn.posnY();
	}

	// imports images of log
	// draw all 3 parts of the log side by side
	public void draw(Graphics g) {
		ImageIcon left = new ImageIcon("./sprites/logleft.png");
		ImageIcon center = new ImageIcon("./sprites/logcenter.png");
		ImageIcon right = new ImageIcon("./sprites/logright.png");
		Image lleft = left.getImage();
		Image lcenter = center.getImage();
		Image lright = right.getImage();

		g.drawImage(lleft, this.x, this.y, null);
		g.drawImage(lcenter, this.x + World.GRIDSIZE, this.y, null);
		g.drawImage(lright, this.x + 2 * World.GRIDSIZE, this.y, null);
	}
}
