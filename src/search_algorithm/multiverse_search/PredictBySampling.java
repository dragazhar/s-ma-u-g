package search_algorithm.multiverse_search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import search_algorithm.Multiverse;
import search_algorithm.Universe;
import search_algorithm.search_problem.SearchProblem;
import configuration.Benchmarks;


import logger.SLogger;

public class PredictBySampling {
    private final static Logger LOGGER = Logger
	    .getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static final int RUN_TIMES = 1000;
    public static final double GENERATE_PROCENT_MIN = 0.05f;
    public static final double GENERATE_PROCENT_MAX = 0.3f;
    public static final double GENERATE_PROCENT_STEP = 0.05f;
    
    public static double procent=GENERATE_PROCENT_MIN;
    
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
	LOGGER.warning(sp.getName());
	LOGGER.warning(canonicalVerse.toString());

	HyperSpaceSearch.initMVerse(canonicalVerse, 1.0, 1.0);

	Universe bUniverse = null;
	LOGGER.warning("id" + "\t" + "maximal");
	for (int i = 0; i < canonicalVerse.getSizeofMultiverse(); i++) {
	    bUniverse = canonicalVerse.parallelUniverses.get(i);
	    LOGGER.warning(i + "\t" + bUniverse.getMaxMass());

	    if (bUniverse.getMaxMass() == 1.0) {
		bestUniversesID.add(i);
	    }
	}
	LOGGER.warning("Universes with solutions: " + bestUniversesID.toString());

	LOGGER.fine("id" + "\t" + "maximal");

	
	
	LOGGER.warning("gen_proc \t success \t avg.size");
	do {
	    int correctlyFound = 0;
	     ArrayList<Long> pointsChecked = new ArrayList<Long>();
	     System.out.println("pc "+procent); 
	     
	for (int step = 0; step < RUN_TIMES; step++) {
	
	    Multiverse testVerse = new Multiverse();
	    testVerse.create(sp);
	    int predictedBestId = 0;
	    double predictedBestMax = 0;
	    HyperSpaceSearch.initMVerse(testVerse,
		    procent,
		    procent);
	    for (int i = 0; i < testVerse.getSizeofMultiverse(); i++) {
		bUniverse = testVerse.parallelUniverses.get(i);
		LOGGER.finest(i + "\t" + bUniverse.getAverageMass() + "\t"
			+ bUniverse.getMaxMass());
		if (bUniverse.getMaxMass() > predictedBestMax) {
		    predictedBestId = i;
		    predictedBestMax = bUniverse.getMaxMass();
		}
	    }
	    long checked = testVerse.computeActualSearchSpaceSize();

	    LOGGER.fine(predictedBestId + "\t" + predictedBestMax);
	    pointsChecked.add(checked);
	    if (bestUniversesID.contains(predictedBestId)) {
		correctlyFound++;
	    }

	}
	double average = 0;
	for (int i = 0; i < pointsChecked.size(); i++) {
	    average += (double) pointsChecked.get(i)
		    / (double) pointsChecked.size();
	}

	LOGGER.info("Correctly predicted: " + correctlyFound + " out of "
		+ RUN_TIMES + ": "
		+ ((double) correctlyFound / (double) RUN_TIMES));

	LOGGER.info("Average amount of points: " + average + " out of "
		+ canonicalVerse.searchSpaceSize);
    
	LOGGER.warning(procent+"\t" +(double) correctlyFound / (double) RUN_TIMES+"\t "+(double)average/(double)canonicalVerse.searchSpaceSize);
	
	procent+=GENERATE_PROCENT_STEP;
	} while (procent<=GENERATE_PROCENT_MAX);
	}
}
