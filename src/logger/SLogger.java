package logger;

import java.io.IOException;
import java.util.logging.*;
import configuration.Config;
import logger.HTMLFormatter;

public class SLogger {

    static private FileHandler fileTxt;
    static private TXTFormatter formatterTxt;
    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() throws IOException {

	// get the global logger to configure it
	Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	// suppress the logging output to the console
	Logger rootLogger = Logger.getLogger("");
	Handler[] handlers = rootLogger.getHandlers();

	if (handlers[0] instanceof ConsoleHandler) {
	    rootLogger.removeHandler(handlers[0]);
	}

	logger.setLevel(Config.LOG_LEVEL);
	fileTxt = new FileHandler(Config.LOG_TXT_FILENAME);
	if (Config.HTML_OUTPUT) {
	    fileHTML = new FileHandler(Config.LOG_HTML_FILENAME);
	}

	// create a TXT formatter
	formatterTxt = new TXTFormatter();//SimpleFormatter();
	fileTxt.setFormatter(formatterTxt);
	logger.addHandler(fileTxt);

	// create an HTML formatter
	if (Config.HTML_OUTPUT) {
	    formatterHTML = new HTMLFormatter();
	    fileHTML.setFormatter(formatterHTML);
	    logger.addHandler(fileHTML);
	}
    }

}
