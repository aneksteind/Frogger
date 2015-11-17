package g1;

import java.util.Random;


// function object for creating a moving part
public interface IMovingPartMaker {
	MovingPart makeMP(int n, int lane);
}

abstract class MakePart {
	// any given lane starts at a random location
	static Random laneStart = new Random();

	// determines direction and speed of moving part depending on which lane
	// it's in
	public static int partDir(int lane) {
		if (lane % 2 == 0) {
			return lane % 3 + 1;
		} else {
			return -1 * (lane % 3 + 1);
		}
	}

	// builds a moving part at a semi-random grid point, dependent on the lane
	public static GridPosn buildGP(int n, int lane) {
		if (lane % 2 == 0) {
			return new GridPosn(9 * n + laneStart.nextInt(4), lane);
		} else {
			return new GridPosn(9 * n + laneStart.nextInt(5), lane);
		}
	}
}

// function objects, make a particular moving part
// inherit methods to build a moving part at a certain speed
// and position

// make a car
class MakeCar extends MakePart implements IMovingPartMaker {
	public MovingPart makeMP(int n, int lane) {
		return new Car(buildGP(n, lane), lane, partDir(lane));
	}
}

// make a log
class MakeLog extends MakePart implements IMovingPartMaker {
	public MovingPart makeMP(int n, int lane) {
		return new Log(buildGP(n, lane), lane, partDir(lane));
	}
}


// make a turtle
class MakeTurtle extends MakePart implements IMovingPartMaker {
	public MovingPart makeMP(int n, int lane) {
		return new Turtle(buildGP(n, lane), lane, partDir(lane));
	}
}
