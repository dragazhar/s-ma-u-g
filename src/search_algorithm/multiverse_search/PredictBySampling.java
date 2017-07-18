package search_algorithm.multiverse_search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import search_algorithm.Multiverse;
import search_algorithm.Universe;
import search_algorithm.search_problem.SearchProblem;
import configuration.Benchmarks;
import configuration.Config;

import logger.SLogger;

public class PredictBySampling {
    private final static Logger LOGGER = Logger
	    .getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static ArrayList<Integer> bestUniversesID = new ArrayList<Integer>();

    public static void main(String[] args) throws IOException {
	// Create logger
	try {
	    SLogger.setup();
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new RuntimeException("Problems with creating the log files");
	}
	Multiverse canonicalVerse = new Multiverse();
	SearchProblem sp = Benchmarks.returnBenchmark();

	canonicalVerse.create(sp);
	LOGGER.info(canonicalVerse.toString());

	HyperSpaceSearch.initMVerse(canonicalVerse, 1.0, 1.0);

	Universe bUniverse = null;
	LOGGER.info("id" + "\t" + "maximal");
	for (int i = 0; i < canonicalVerse.getSizeofMultiverse(); i++) {
	    bUniverse = canonicalVerse.parallelUniverses.get(i);
	    LOGGER.info(i +  "\t"
		    + bUniverse.getMaxMass());

	    if (bUniverse.getMaxMass() == 1.0) {
		bestUniversesID.add(i);
	    }
	}
	LOGGER.info("Universes with solutions: " + bestUniversesID.toString());

	LOGGER.fine("id" + "\t" + "maximal");

	int correctlyFound = 0;
	for (int step = 0; step < Config.RUN_TIMES; step++) {
	    System.out.println("step: "+ step);
	    Multiverse testVerse = new Multiverse();
	    testVerse.create(sp);
	    int predictedBestId = 0;
	    double predictedBestMax = 0;
	    long checked=0;
	    HyperSpaceSearch.initMVerse(testVerse,  Config.GENERATE_PROCENT_TRANSITION_FUNCTION,Config.GENERATE_PROCENT_OUTPUT_FUNCTION);
	    for (int i = 0; i < testVerse.getSizeofMultiverse(); i++) {
		bUniverse = testVerse.parallelUniverses.get(i);
		LOGGER.finest(i + "\t" + bUniverse.getAverageMass() + "\t"
			+ bUniverse.getMaxMass());
		if (bUniverse.getMaxMass() > predictedBestMax) {
		    predictedBestId = i;
		    predictedBestMax = bUniverse.getMaxMass();
		}
		checked=+bUniverse.points.size(); 
	    }

	    LOGGER.fine(predictedBestId + "\t" + predictedBestMax);
	    //System.out.println(checked);
	    if (bestUniversesID.contains(predictedBestId)) {
		correctlyFound++;
	    }
	   
	    
	}
	LOGGER.info("Correctly predicted: " + correctlyFound + " out of "
		    + Config.RUN_TIMES + ": "
		    + ((double)correctlyFound / (double)Config.RUN_TIMES));


    }
}
