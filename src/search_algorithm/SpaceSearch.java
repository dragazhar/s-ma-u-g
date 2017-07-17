package search_algorithm;

import search_algorithm.bodies.Body;
import search_algorithm.bodies.Coordinates;
import search_algorithm.bodies.Key;
import search_algorithm.bodies.Point;
import search_algorithm.dgso.Laws;
import configuration.Config;

public class SpaceSearch {
    public static Point search(Universe uVerse) {
	Point gPoint = uVerse.bestPoint;

	return gPoint;
    }

    public static void placeBodies(Universe uVerse) {
	for (Point p : uVerse.points.values()) {
	    uVerse.bodies.add(new Body(p));
	}
    }

    public static Point searchGravI(Universe uVerse) {
	placeBodies(uVerse);
	int tEpoch = Laws.getMaxAge(uVerse);
	//System.out.println("Max age: " + tEpoch);
	int cEpoch = 0;
	boolean newPoints = true;
	while ((cEpoch <= tEpoch) && (uVerse.bestPoint.getValue() < 1.0)) {
	    newPoints = false;
	    Coordinates globalBestPosition = new Coordinates(
			uVerse.bestPoint.coordinatesT,
			uVerse.bestPoint.coordinatesO);
		double globalBestMass = uVerse.bestPoint.getValue();
		//System.out.println("Best for "+ cEpoch+"="+globalBestPosition+" "+ globalBestMass);
		//System.out.println("best point"+uVerse.bestPoint);
		
		computeNewPositions(uVerse, cEpoch,  globalBestPosition, globalBestMass);
	  
	    for (Body b : uVerse.bodies) {
		// add explored point to search space
		Point point = new Point(b.getNewPosition().coordinatesT,
			b.getNewPosition().coordinatesO);
		double score = 0;
		point.generated = cEpoch;

		int keyT = uVerse.localPointTtoInteger(point.coordinatesT);
			int keyU=uVerse.universeId;
		int keyO = uVerse.localPointOtoInteger(point.coordinatesO);
		Key key = new Key(keyU, keyT, keyO);
		point.setKey(key);
		if (!uVerse.points.containsKey(key)) {
		    score = uVerse.mVerse.searchProblem.getPointScore(point);
		    point.setValue(score);
		    //System.out.println(key+" "+String.format("%.2f",score)+" updated: " +point+" "+uVerse.bestPoint);
		  //  System.out.println("best: "+uVerse.bestPoint+"\n b: "+globalBestPosition+" "+ globalBestMass);
		    uVerse.points.put(key, point);
		    newPoints = true;
		    if(score==1.0){
			//System.out.println(score);
		    }
		} else {
		    score = uVerse.points.get(key).getValue();
		    point.setValue(score);

		}

		b.setMass(score);
		// update local best
		b.updateLocalBest();
		
		// update global best
		if (point.getValue() > uVerse.bestPoint.getValue()) {
		   // System.out.println("update besty");
		    //System.out.println(key+" "+point);
		    //System.out.println("Before: "+key+" "+uVerse.bestPoint);
		    uVerse.bestPoint.copyPoint(point);
		    //System.out.println(point+" for update");
		    //System.out.println("After: "+key+" "+uVerse.bestPoint);
		}
		b.replaceByNewPosition();
		b.replaceByNewVelocity();
		if (point.getValue() == 1.0) {
//		    System.out.println("Solution found at epoch: "
//			    + (cEpoch + 1));
		}

	    }
	    if (!newPoints) {
		//System.out.println("No new points at epoch " + (cEpoch + 1));
		break;
	    }
	    cEpoch++;
	}
	return uVerse.bestPoint;

	
    }
    private static void computeNewPositions(Universe uVerse, int cEpoch, Coordinates globalBestPosition, double globalBestMass)
	{
	    for (Body b : uVerse.bodies) {
	  		double f1 = Laws.computeForce(uVerse, cEpoch, b.getMass(),
	  			b.getBestMass(), b.getPosition(), b.getBestPosition());
	  		
	  		double f2 = Laws.computeForce(uVerse, cEpoch, b.getMass(),
	  			globalBestMass, b.getPosition(), globalBestPosition);

	  		b.setNewVelocity(Laws.accelerate(f1, f2, b.getVelocity(),
	  			b.getBestPosition(), globalBestPosition)) ;
	  		b.setNewPosition( Laws.move(b.getMass(), b.getPosition(), b.getNewVelocity()));
	  		b.setNewPosition(Laws.rndMove(Config.PROBABILITY_OF_RANDOM_MOVE, b.getNewPosition(),
	  			uVerse.generateRNDCoordinates()));
	  	    }
	}

}
