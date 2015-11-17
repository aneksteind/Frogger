package g1;

import java.awt.Graphics;

// represents an empty list of moving parts
// draws nothing, threads nothing,
// nothing is colliding, and nothing is running
public class MTLoMP implements ILoMP {

	MTLoMP() {
	}

	public void draw(Graphics g) {
	}

	public void thread() {
	}

	public boolean collides(Player p) {
		return false;
	}

	public void setRun(boolean b) {
	}

}
