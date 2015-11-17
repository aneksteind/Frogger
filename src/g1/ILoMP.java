package g1;

import java.awt.Graphics;

//a list of moving parts, can be empty or a constructed list
public interface ILoMP {
	public void draw(Graphics g);

	public void thread();

	public boolean collides(Player p);

	public void setRun(boolean b);
}
