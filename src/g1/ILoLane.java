package g1;

import java.awt.Graphics;

//a list of lanes, can be empty or a constructed list
public interface ILoLane {
	public void draw(Graphics g);

	public void thread();

	public boolean collides(Player p);

	public void setRun(boolean b);
}
