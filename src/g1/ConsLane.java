package g1;

import java.awt.Graphics;

//has at least 1 lane
//rest of list can be empty or another list
public class ConsLane implements ILoLane {
	ILoMP first;
	ILoLane rest;

	ConsLane(ILoMP first, ILoLane rest) {
		this.first = first;
		this.rest = rest;
	}

	// draw the first lane, draw the rest of the lanes
	public void draw(Graphics g) {
		first.draw(g);
		rest.draw(g);
	}

	// thread the first lane, thread the rest of the lanes
	public void thread() {
		first.thread();
		rest.thread();
	}

	// does the player collide with a part in the first lane
	// does the player collide with a part in the rest of the lanes
	public boolean collides(Player p) {
		return (first.collides(p)) || rest.collides(p);
	}

	// set first lane to running or not
	// set the rest to running or not
	public void setRun(boolean b) {
		first.setRun(b);
		rest.setRun(b);
	}

}
