package configuration;

import java.io.IOException;

import search_algorithm.search_problem.ProblemDescriptions;
import search_algorithm.search_problem.SearchProblem;

public class Benchmarks {

    public static SearchProblem returnBenchmark() throws IOException {
	SearchProblem sp = null;
	switch (Config.BENCHMARK) {
	case ConfigValues.AB_RECOGNIZER:
	    sp = ProblemDescriptions.abRecognizer();
	    break;
	case ConfigValues.AAB_RECOGNIZER:
	    sp = ProblemDescriptions.aabRecognizer();
	    break;
	case ConfigValues.UP_DOWN_COUNTER:
	    sp = ProblemDescriptions.upDownCounter();
	    break;
	case ConfigValues.HALVES:
	    sp = ProblemDescriptions.halves();
	    break;
	case ConfigValues.PARITY_CHECKER:
	    sp = ProblemDescriptions.parityChecker();
	    break;
	case ConfigValues.TWO_UNIT_DELAY:
	    sp = ProblemDescriptions.twoUnitDelay();
	    break;
	case ConfigValues.ANT_JMT_6:
	    sp = ProblemDescriptions.ant1();
	    break;
	case ConfigValues.ANT_SFT_7:
	    sp = ProblemDescriptions.ant2();
	    break;
	case ConfigValues.PREDICTOR_11100:
	    sp = ProblemDescriptions.predictor11100();
	    break;
	case ConfigValues.PREDICTOR_001111:
	    sp = ProblemDescriptions.predictor001111();
	    break;
	case ConfigValues.PREDICTOR_MOHHAMED100:
	    sp = ProblemDescriptions.predictorMohhamed100();
	    break;
	case ConfigValues.PREDICTOR_1111010010111101001:
	    sp = ProblemDescriptions.predictor1111010010111101001();
	    break;
	default:
	    sp = ProblemDescriptions.abRecognizer();
	    break;
	}
	return sp;

    }

}
