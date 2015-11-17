package g1;

import java.awt.Graphics;

//has at least 1 moving part
//rest of list can be empty or another list
public class ConsMP implements ILoMP {

	MovingPart first;
	ILoMP rest;

	ConsMP(MovingPart first, ILoMP rest) {
		this.first = first;
		this.rest = rest;
	}

	// thread the first, thread the rest
	public void thread() {
		new Thread(first).start();
		rest.thread();
	}

	// draw the first, draw the rest
	public void draw(Graphics g) {
		first.draw(g);
		rest.draw(g);
	}

	// does the player collide with the first?
	// does the player collide with the rest?
	public boolean collides(Player p) {
		return (first.collides(p) || this.rest.collides(p));
	}

	// set the first moving part to run or not
	// set the rest to run or not
	public void setRun(boolean b) {
		first.setRun(b);
		rest.setRun(b);
	}

}
