package configuration;

import java.util.logging.Level;

public class Config {
    // DGSO controls
    public static final double GENERATE_PROCENT_TRANSITION_FUNCTION = 0.5;
    public static final double GENERATE_PROCENT_OUTPUT_FUNCTION = 0.5;
    public static final double PROBABILITY_OF_RANDOM_MOVE = 0.1;
    public static final int MAX_UNIVERSE_AGE = 100;

    // Visualization
    public static final int SIZE_OF_POINT = 10;
    public static final boolean VISUALIZE = true;

    // Multiverse controls
    public static final boolean EXPLORE_ALL = true;
    

    // Task
    public static final int BENCHMARK = ConfigValues.AAB_RECOGNIZER;
    public static final int RUN_TIMES = ConfigValues.RUN_ONCE;

    // Logger
    public static final Level LOG_LEVEL = Level.INFO;
    public static final String LOG_TXT_FILENAME = "log.txt";
    public static final String LOG_HTML_FILENAME = "log.html";

}
