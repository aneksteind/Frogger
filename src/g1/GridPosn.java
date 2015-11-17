package g1;

// scales a Posn to the size of a grid square
public class GridPosn {
	int x, y;

	GridPosn(int x, int y) {
		this.x = x;
		this.y = y;
	}

	Posn gridToPosn() {
		return new Posn(this.x * World.GRIDSIZE, this.y * World.GRIDSIZE);
	}

	//conversion from GridPosn to Posn
	int posnX() {
		return this.gridToPosn().x;
	}

	int posnY() {
		return this.gridToPosn().y;
	}
}
