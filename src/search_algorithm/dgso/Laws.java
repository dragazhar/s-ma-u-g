package search_algorithm.dgso;

import search_algorithm.Universe;
import search_algorithm.bodies.Coordinates;
import configuration.Config;

public class Laws {

    static double getG(Universe uVerse, int cEpoch) {
	int tEpoch = getMaxAge(uVerse);
	return (1 - cEpoch / tEpoch);
    }

    public static double computeForce(Universe uVerse, int cEpoch, double m1,
	    double m2, Coordinates p1, Coordinates p2) {
	double force = 0.0;
	int dist = distance(p1, p2);
	if (dist > 0) {
	    force = (getG(uVerse, cEpoch) * m1 * m2) / (distance(p1, p2));
	} else
	    force = 1.0;
	return force;
    }

    public static int getMaxAge(Universe uVerse) {
	double age = 0;
	// double coeff = (uVerse.sizeTO / uVerse.points.size());
	// double coeff = (uVerse.sizeTO / uVerse.bodies.size());
	// age = Math.floor(coeff);
	age = Config.MAX_UNIVERSE_AGE;
	return (int) age;
    }

    static public Coordinates accelerate(double force1, double force2,
	    Coordinates velocity, Coordinates pBest, Coordinates gBest) {
	Coordinates newVelocity = new Coordinates(velocity.coordinatesT,
		velocity.coordinatesO);
	// step1 point best
	for (int i = 0; i < velocity.coordinatesT.length; i++) {
	    if (Math.random() < force1) {
		newVelocity.coordinatesT[i] = pBest.coordinatesT[i];
	    }
	}

	for (int i = 0; i < velocity.coordinatesO.length; i++) {
	    if (Math.random() < force1) {
		newVelocity.coordinatesO[i] = pBest.coordinatesO[i];
	    }
	}
	// step2 global best
	for (int i = 0; i < velocity.coordinatesT.length; i++) {
	    if (Math.random() < force2) {
		newVelocity.coordinatesT[i] = gBest.coordinatesT[i];
	    }
	}

	for (int i = 0; i < velocity.coordinatesO.length; i++) {
	    if (Math.random() < force2) {
		newVelocity.coordinatesO[i] = gBest.coordinatesO[i];
	    }
	}

	return newVelocity;
    }

    static public Coordinates move(double inertialM, Coordinates position,
	    Coordinates velocity) {
	Coordinates newPosition = new Coordinates(position.coordinatesT,
		position.coordinatesO);

	for (int i = 0; i < velocity.coordinatesT.length; i++) {
	    if (Math.random() < (1 - inertialM)) {
		newPosition.coordinatesT[i] = velocity.coordinatesT[i];
	    }
	}

	for (int i = 0; i < velocity.coordinatesO.length; i++) {
	    if (Math.random() < (1 - inertialM)) {
		newPosition.coordinatesO[i] = velocity.coordinatesO[i];
	    }
	}

	return newPosition;
    }

    static public Coordinates rndMove(double prob, Coordinates position,
	    Coordinates rnd) {
	Coordinates newPosition = new Coordinates(position.coordinatesT,
		position.coordinatesO);
	for (int i = 0; i < position.coordinatesT.length; i++) {
	    if (Math.random() < prob) {
		newPosition.coordinatesT[i] = rnd.coordinatesT[i];
	    }
	}

	for (int i = 0; i < position.coordinatesO.length; i++) {
	    if (Math.random() < prob) {
		newPosition.coordinatesO[i] = rnd.coordinatesO[i];
	    }
	}
	return newPosition;
    }

    static public int distance(Coordinates p1, Coordinates p2) {
	int distT = 0;
	int distO = 0;

	for (int i = 0; i < p1.coordinatesT.length; i++) {
	    distT += distanceInDimentionD(p1.coordinatesT[i],
		    p2.coordinatesT[i]);
	}

	for (int i = 0; i < p1.coordinatesO.length; i++) {
	    distO += distanceInDimentionD(p1.coordinatesO[i],
		    p2.coordinatesO[i]);
	}
	return distT + distO;
    }

    static int distanceInDimentionD(int p1d, int p2d) {
	int dist = 0;
	if (p1d != p2d) {
	    dist = 1;
	}
	return dist;
    }

}
