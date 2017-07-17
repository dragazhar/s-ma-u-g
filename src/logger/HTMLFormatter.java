package logger;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class HTMLFormatter extends Formatter {
    // this method is called for every log records
    public String format(LogRecord rec) {
	StringBuffer buf = new StringBuffer(1000);
	buf.append("<tr>\n");
	// colorize any levels >= WARNING in red
	if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
	    buf.append("\t<td class=\"log__text_warning\">");
	    buf.append(rec.getLevel());
	} else {
	    buf.append("\t<td>");
	    buf.append(rec.getLevel());
	}
	buf.append("</td>\n");
	buf.append("\t<td>");
	buf.append(calcDate(rec.getMillis()));
	buf.append("</td>\n");
	buf.append("\t<td>");
	buf.append(formatMessage(rec));
	buf.append("</td>\n");
	buf.append("</tr>\n");
	return buf.toString();

    }

    private String calcDate(long millisecs) {
	SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
	Date resultdate = new Date(millisecs);
	return date_format.format(resultdate);

    }

    // this method is called just after the handler using this
    // formatter is created

    public String getHead(Handler h) {
	return "<!DOCTYPE html>\n<head>\n"
		+ "<link rel=\"stylesheet\" href=\"css/smaug.css\"/>"
		+ "</head>\n" + "<body>\n" + "<table class=\"log__table\">\n"
		+ "<tr>\n" + "\t<th>Loglevel</th>\n" + "\t<th>Time</th>\n"
		+ "\t<th>Log Message</th>\n" + "</tr>\n";
    }

    // this method is called just after the handler using this
    // formatter is closed
    public String getTail(Handler h) {
	return "</table>\n</body>\n</html>";
    }
}
