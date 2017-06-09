package representation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import logger.SLogger;

/**
 * String representation for output function
 * 
 * @author dragazhar
 * 
 */
public class OutputFunctionSR {
    private final static Logger LOGGER = Logger
	    .getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * List of all possible sequences
     */
    private static ArrayList<int[]> allPossibleSeq = new ArrayList<int[]>();

    /**
     * Number of segments in the sequence
     */
    private static int seqSegmentAmount;

    /**
     * Max possible value at the segment
     */
    private static int seqSegmentMaxValue;

    /**
     * Initialize the sequence
     * 
     * @param seqSize
     * @param seqValue
     */
    public OutputFunctionSR(int seqSize, int seqValue) {

	seqSegmentAmount = seqSize;
	seqSegmentMaxValue = seqValue;

    }

    /**
     * Recursive function. To enumerate all strings with length 3 we must fix
     * the first char and enumerate all strings with length 2: 0: 00, 01, 10, 11
     * (000,001,010,011) and 1: 00, 01, 10, 11 (100, 101, 110, 111)
     * 
     * @param n
     * @param seq
     */
    public static void enumerate(int n, ArrayList<Integer> seq) {
	if (n == 0) {
	    int[] seqA = new int[seq.size()];
	    for (int i = 0; i < seq.size(); i++) {
		seqA[i] = seq.get(i);
	    }
	    allPossibleSeq.add(seqA);

	} else {
	    for (int i = 0; i < seqSegmentMaxValue; i++) {
		ArrayList<Integer> seq2 = new ArrayList<Integer>();

		for (int j = 0; j < seq.size(); j++) {
		    seq2.add(seq.get(j));
		}
		seq2.add(i);
		enumerate(n - 1, seq2);
	    }
	}
    }

    public static ArrayList<int[]> enumerateAll(int s, int v) {
	seqSegmentAmount = s;
	seqSegmentMaxValue = v;
	enumerate(seqSegmentAmount, new ArrayList<Integer>());
	return allPossibleSeq;

    }

    /**
     * Local address for given sequence, for example:
     * (2)011=0*2^2+1*2^1+1*2^0=2+1=3 or (4)033=0*4^2+3*4^1+3*4^0=12+3=15
     * 
     * @param coordinates
     * @return
     */
   private static int globalAddressOfSequence(int[] coordinates) {
	int code = 0;
	int base = seqSegmentMaxValue;
		globalAddressOfSequence(coordinates, base);
	return code;
    }
    public static int globalAddressOfSequence(int[] coordinates, int base) {
	int code = 0;
		for (int i = coordinates.length - 1; i >= 0; i--) {
	    long coeff = (long) (Math.pow((double) base,
		    (double) (coordinates.length - 1 - i)));
	    code += coordinates[i] * coeff;
	}
	return code;
    }
    

    /**
     * Number of all possible sequences: codeBase^seqSize
     * 
     * @return number of all possible sequences
     */
    private static long getNumberAllPossibleSeq() {
	long total = 0;
	total = getNumberAllPossibleSeq(seqSegmentAmount, seqSegmentMaxValue);
	return total;
    }
    
    public static long getNumberAllPossibleSeq(int seqSize, int seqValue) {
	long total = 0;
	total = (long) (Math.pow((double) seqValue,
		(double) seqSize));

	return total;
    }

    /**
     * Debug. Prints out all sequences
     */
    public static void printAllSeq() {
	LOGGER.info("Total generated: "
		+ allPossibleSeq.size());
	for (int i = 0; i < allPossibleSeq.size(); i++) {
	    int[] sequence = allPossibleSeq.get(i);

	    LOGGER.info(printSeq(sequence).toString());

	}
    }

    public static StringBuffer printSeq(int[] sequence) {
	StringBuffer s = new StringBuffer();
	for (int j = 0; j < sequence.length - 1; j++) {
	    s.append(sequence[j] + ", ");
	}

	s.append(sequence[sequence.length - 1] + "\t");
	s.append(":Seq" + globalAddressOfSequence(sequence));
	return s;

    }

    /**
     * Formating output function SR to HTML
     * 
     * @return html string
     */
    /*
     * public static StringBuffer toHTMLString(int[] sequence) { StringBuffer s
     * = new StringBuffer();
     * s.append("<table class=\"sr__output-function\">\n"); s.append("<tr>\n");
     * for (int i = 0; i < sequence.length; i++) { s.append("<td>" + sequence[i]
     * + "</td>\n"); }
     * 
     * s.append("<td>: Seq" + localAddressOfSequence(sequence) + "</td>\n");
     * 
     * s.append("</tr>\n"); s.append("</table>\n"); return s;
     * 
     * }
     */

    /**
     * Test function
     * 
     */

    private static void testEnumerateAll() {
	try {

	    SLogger.setup();

	} catch (IOException e) {

	    e.printStackTrace();

	    throw new RuntimeException("Problems with creating the log files");

	}

	OutputFunctionSR.enumerateAll(3, 4);
	LOGGER.info("Initial data: size=3 maxValue=4");
	LOGGER.info("Total computed: "+getNumberAllPossibleSeq());
	OutputFunctionSR.printAllSeq();
	/*
	 * System.err.println(OutputFunctionSR.toHTMLString(allPossibleSeq.get(2)
	 * ));
	 */

    }

    public static void main(String[] args) {
	testEnumerateAll();
    }

}
