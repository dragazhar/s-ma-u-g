package configuration;

import java.util.logging.Level;

public class Config {
    // DGSO controls
    public static final double PROBABILITY_OF_RANDOM_MOVE = 0.1;
    public static final int MAX_UNIVERSE_AGE = 50;

    // Visualization
    public static final int SIZE_OF_POINT = 10;
    public static final boolean VISUALIZE = false;

    // Multiverse controls
    public static final double GENERATE_PROCENT_TRANSITION_FUNCTION = 0.2;
    public static final double GENERATE_PROCENT_OUTPUT_FUNCTION = 0.2;

    public static final boolean EXPLORE_ALL = true;
    public static final boolean EXIT_INIT_AT_MAX = false;

    // Task
    public static final int BENCHMARK = ConfigValues.PREDICTOR_001111;
    public static final int RUN_TIMES = ConfigValues.RUN_ONCE;

    // Logger
    public static final Level LOG_LEVEL = Level.WARNING;
    public static final String LOG_TXT_FILENAME = "log.txt";
    public static final String LOG_HTML_FILENAME = "log.html";
    public static final boolean HTML_OUTPUT = false;

    // Artificial ant
    public static final int TIME_STEPS = 100;
    // Filtering points on the representation level using domain knowledge
    public static boolean FILTER_POINTS = true;

}
