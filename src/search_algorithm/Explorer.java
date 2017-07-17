package search_algorithm;

import java.io.IOException;
import java.util.ArrayList;

import java.util.logging.Logger;

import configuration.Benchmarks;
import configuration.Config;

import logger.SLogger;


import search_algorithm.bodies.Point;
import search_algorithm.search_problem.SearchProblem;
import visualization.NamedSeriesOfPoints;
import visualization.ScoreGraph;
import visualization.ScoreGraph2D;

public class Explorer {
    private final static Logger LOGGER = Logger
	    .getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
	// Create logger
	try {
	    SLogger.setup();
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new RuntimeException("Problems with creating the log files");
	}

	
	for (int step=0; step<Config.RUN_TIMES; step++){
	Multiverse mVerse = new Multiverse();
	SearchProblem sp = Benchmarks.returnBenchmark();

	mVerse.create(sp);
	LOGGER.info(mVerse.toString());
	long pointInMultiverseChecked=0;
	int nExplored = 0;
	if (Config.EXPLORE_ALL) {
	    HyperSpaceSearch.initMVerse(mVerse, Config.GENERATE_PROCENT_TRANSITION_FUNCTION,Config.GENERATE_PROCENT_OUTPUT_FUNCTION);
	    while ((!mVerse.found)
		    && (nExplored < mVerse.getSizeofMultiverse())) {
		
		Universe bUniverse = HyperSpaceSearch.search(mVerse);
		//LOGGER.info("Best universe: ");
		//LOGGER.info(bUniverse.toString2());
		long pointBefore=bUniverse.points.size();
		double bestBefore=bUniverse.getMaxMass();
	//	LOGGER.info("b"+bUniverse.points.size()+" "+String.format("%.2f",bUniverse.getMaxMass()));
		Point solution = SpaceSearch.searchGravI(bUniverse);
		bUniverse.setExplored(true);
		pointInMultiverseChecked+=bUniverse.points.size();
		//System.out.println(pointInMultiverseChecked+" "+bUniverse.points.size());
//	LOGGER.info("Points in universe checked: "
//		+ bUniverse.points.size());
//	LOGGER.info(pointBefore+"; "+ bUniverse.points.size()+"; "+String.format("%.2f",bestBefore)+"; "+String.format("%.2f",solution.getValue()));
		if (!Config.VISUALIZE) {
		    bUniverse.clearPoints();
		}
		if (mVerse.solution == null) {
		    // mVerse.solution = solution;
		    mVerse.solution = new Point(solution.coordinatesT,
			    solution.coordinatesO);
		    mVerse.solution.setValue(solution.getValue());
		} else {
		    if (solution.getValue() >= mVerse.solution.getValue()) {
			// mVerse.solution = solution;
			mVerse.solution = new Point(solution.coordinatesT,
				solution.coordinatesO);
			mVerse.solution.setValue(solution.getValue());
		    }
		}
		
//		System.out.print("Solution: \t");
//		System.out.println(solution);

		nExplored++;

		if (solution.getValue() == 1.0) {
		    mVerse.found = true;

		}
		//System.out.println(nExplored + " universes explored");
	    }
	    
	}

	else {
	   // int[] flag = {0, 0, 2, 4, 6};
	     int[] flag = { 0, 0, 1, 2, 3, 6, 8, 9 };
	    System.out.println("by flag");
	    Universe bUniverse = HyperSpaceSearch.searchByFlag(mVerse, flag);
	    bUniverse.generateRNDPoints(Config.GENERATE_PROCENT_TRANSITION_FUNCTION, Config.GENERATE_PROCENT_OUTPUT_FUNCTION);
	    System.out.println(bUniverse.toString2());
	    Point solution = SpaceSearch.searchGravI(bUniverse);

	    // check thiss!!!!
	    mVerse.solution = new Point(solution.coordinatesT,
		    solution.coordinatesO);

	    System.out.print("Solution: \t");
	    System.out.println(solution);

	    if (solution.getValue() == 1.0) {
		mVerse.found = true;

	    }
	}

//	if (mVerse.solution.getValue() == 1.0) {
//	    System.out.println("MAXIMAL SOLUTION FOUND " + mVerse.solution);
//
//	} else {
//	    System.out.println("Solution found with value: "
//		    + mVerse.solution.getValue() + " " + mVerse.solution);
//	}

	//sp.printSolution(mVerse.solution);
    LOGGER.info("points cheked: "+pointInMultiverseChecked+" explored: "+nExplored+" found "+ mVerse.found);  
	// Visualization
	if (Config.VISUALIZE) {
	    LOGGER.info("Points checked: "
		    + mVerse.computeActualSearchSpaceSize());
	    LOGGER.info("Explored "
		    + (double) mVerse.computeActualSearchSpaceSize()
		    / mVerse.searchSpaceSize + " of points (out of 1.0)");
	   ArrayList<NamedSeriesOfPoints> data = new ArrayList<NamedSeriesOfPoints>();
	    for (Universe u : mVerse.parallelUniverses) {
		NamedSeriesOfPoints s = new NamedSeriesOfPoints(
			mVerse.mVerseCSR.flagtoString(u.flag), u.points);
		data.add(s);
	    }
	    if (sp.getM() == 0) {
		ScoreGraph.drawData("", "point", "Score", data);
	    } else {
		ScoreGraph2D.draw("", data, true, Config.SIZE_OF_POINT);
	    }
	}
	System.out.println("finished "+step+" step");
    }

	}
}
