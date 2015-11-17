package g1;

import java.awt.Graphics;

// represents an empty list of lanes/roads
// draws nothing, threads nothing,
// nothing is colliding, and nothing is running
public class MTLoLane implements ILoLane {

	public void draw(Graphics g) {

	}

	public void thread() {

	}

	public boolean collides(Player p) {
		return false;
	}

	@Override
	public void setRun(boolean b) {

	}

}
