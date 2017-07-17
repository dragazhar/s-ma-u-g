package representation;

import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.util.logging.Logger;

import logger.SLogger;

/**
 * 
 * Canonical string representation for transition function
 * 
 * @author dragazhar
 * 
 */
public class TransitionFunctionCSR {
    private final static Logger LOGGER = Logger
	    .getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static ArrayList<int[]> allPossibleFlags = new ArrayList<int[]>();
    static int[] currentFlag;
    static long numberOfFlags;

    private static int numOfStates;
    private static int sizeOfInputAlphabet;
    private static int seqSize;

    private static ArrayList<int[]> allPossibleSeq = new ArrayList<int[]>();
    static int[] currentSeq;
    static int[] finalSeq;

    /**
     * Initializing representation
     * 
     * @param numOfStates
     *            seqSegmentsAmount
     * @param sizeOfInputAlphabet
     *            maximal possible value at sequence
     */
    public TransitionFunctionCSR(int p_numOfStates, int p_sizeOfInputAlphabet) {
	numOfStates = p_numOfStates;
	sizeOfInputAlphabet = p_sizeOfInputAlphabet;

	this.setSeqSize(this.computeSeqLength());
	currentFlag = new int[this.getNumOfStates()];
	currentSeq = new int[this.getSeqSize()];

	// Computed number of flags (Fuss-Catalan numbers)
	numberOfFlags = computeNumberOfFlags(numOfStates, sizeOfInputAlphabet);

    }

    /**
     * Number of flags, computed by Fuss-Catalan numbers
     * 
     * @param n
     * @param k
     * @return
     */
    public long computeNumberOfFlags(int n, int k) {
	long bc = binomialCoefficient(k * n, n);
	long fr = (k - 1) * n + 1;
	long cnk = bc / fr;
	return cnk;
    }

    /**
     * Number of combinations (binomial coefficient)
     * 
     * @param n
     * @param r
     * @return
     */
    private static long binomialCoefficient(int n, int r) {
	long t = 1;

	int m = n - r; // r = Math.max(r, n - r);
	if (r < m) {
	    r = m;
	}

	for (int i = n, j = 1; i > r; i--, j++) {
	    t = t * i / j;
	}

	return t;
    }

    /**
     * Generate first correct flag: for example for n=5 (states), k=2 (input
     * alphabet): length of the flag: states-1, the position of the states - as
     * far as possible from the beginning of the sequence: (1, 3, 5, 7): * 1 |*
     * 2| * 3| * 4| * *
     */
    public static void initFlag() {
	for (int i = 1; i < numOfStates; i++) {
	    currentFlag[i] = sizeOfInputAlphabet * i - 1;
	}
	allPossibleFlags.add(currentFlag.clone());
    }

    /**
     * Assign current flag value
     * 
     * @param flag
     */
    public static void setCurrentFlag(int[] flag) {
	for (int i = 1; i < currentFlag.length; i++) {
	    currentFlag[i] = flag[i];
	}
    }

    /**
     * Check if the current flag is final on not. The final flag condition: all
     * states are as close as possible to the beginning of the sequence. For
     * example for n=5 (states), k=2 (input alphabet): (0, 1, 2, 3): 1 2 |3 4| *
     * *| * *| * *
     * 
     * @return final flag true or false
     */
    public static boolean isFinalFlag() {
	boolean ff = false;
	int count = 0;
	for (int i = 1; i < numOfStates; i++) {
	    if (currentFlag[i] == i - 1)
		count++;
	}
	if (count == (numOfStates - 1))
	    ff = true;
	return ff;
    }

    /**
     * Generates the next flag in the sequence of flags by moving positions from
     * right to left recursively. Example (1, 3, 5, 7)->(1, 3, 5, 6)-> (1, 3, 4,
     * 7)->(1, 3, 4, 6)...
     * 
     * @param i
     */
    public static void nextFlag(int i) {
	if (i == 1) {
	    currentFlag[i] = currentFlag[i] - 1;
	} else {
	    if ((currentFlag[i] - 1) == currentFlag[i - 1]) {
		currentFlag[i] = sizeOfInputAlphabet * i - 1;
		nextFlag(i - 1);
	    } else {
		currentFlag[i] = currentFlag[i] - 1;
	    }

	}
    }

    /**
     * Generate all possible flags
     */
    public void enumerateFlags() {
	initFlag();
	while (!isFinalFlag()) {
	    nextFlag(numOfStates - 1);
	    allPossibleFlags.add(currentFlag.clone());
	}
    }

    /**
     * Compute sequence length
     * 
     * @return sequence length
     */
    private int computeSeqLength() {
	int n = 0;
	n = numOfStates * sizeOfInputAlphabet;
	return n;
    }

    /**
     * Initial sequence in given flag area
     */
    public static void initSeq() {
	for (int i = 0; i < seqSize; i++) {
	    currentSeq[i] = 0;
	}
	for (int j = 1; j < currentFlag.length - 1; j++) {

	    currentSeq[currentFlag[j]] = j;
	}
	currentSeq[currentFlag[currentFlag.length - 1]] = currentFlag.length - 1;

	allPossibleSeq.add(currentSeq.clone());
	finalSeq = genLastSeq();
    }

    /**
     * Generate last possible sequence for current flag
     * 
     * @return sequence
     */
    public static int[] genLastSeq() {
	int[] lastSeq = new int[seqSize];

	for (int i = 0; i < seqSize; i++) {
	    lastSeq[i] = -1;
	}
	for (int j = 1; j <= currentFlag.length - 1; j++) {
	    lastSeq[currentFlag[j]] = j;
	}
	int fi = currentFlag.length - 1;
	int si = seqSize - 1;
	while (si > -1) {
	    if (lastSeq[si] != fi) {
		lastSeq[si] = fi;

		si--;
	    } else {
		fi--;
		si--;

	    }

	}
	return lastSeq;
    }

    /**
     * Generate last possible sequence for given flag (TODO: really similar to
     * previous, can be combined?)
     * 
     * @param flag
     * @return
     */
    public static int[] genLastSeq(int[] flag) {
	int[] lastSeq = new int[seqSize];

	for (int i = 0; i < seqSize; i++) {
	    lastSeq[i] = -1;
	}
	for (int j = 1; j <= flag.length - 1; j++) {
	    lastSeq[flag[j]] = j;
	}
	int fi = flag.length - 1;
	int si = seqSize - 1;
	while (si > -1) {
	    if (lastSeq[si] != fi) {
		lastSeq[si] = fi;

		si--;
	    } else {
		fi--;
		si--;

	    }

	}
	return lastSeq;
    }

    /**
     * Checks is current sequence is final or not
     * 
     * @return
     */
    private static boolean isFinalSeq() {
	boolean fs = true;
	for (int i = 0; i < currentSeq.length; i++) {
	    if (currentSeq[i] != finalSeq[i]) {
		fs = false;
	    }
	}
	return fs;
    }

    /**
     * How many sequences can be generated by using this flag
     * 
     * @param flag
     * @return
     */
    public static long sizeOfFlagSpace(int[] flag) {
	long size = 1;
	setCurrentFlag(flag);
	int[] finalSeq = genLastSeq();
	for (int i = 0; i < finalSeq.length; i++) {
	    if (finalSeq[i] > 0)
		size *= (finalSeq[i] + 1);
	}
	for (int i = 1; i < numOfStates; i++) {
	    size /= (i + 1);
	}
	return size;
    }

    private static int nearestFlag(int i) {
	int flag = 0;
	for (int j = 1; j < currentFlag.length; j++) {
	    if (currentFlag[j] < i) {
		flag = currentFlag[j];
	    }

	}
	return flag;
    }

    private static boolean isInFlag(int i) {
	boolean isInside = false;
	for (int j = 1; j < currentFlag.length; j++) {
	    if (currentFlag[j] == i) {
		isInside = true;
	    }
	}
	return isInside;
    }

    /**
     * Generate next sequence
     * 
     * @param a
     * @param b
     */
    public static void nextICDFA(int a, int b) {
	int i = a * sizeOfInputAlphabet + b;
	if (a < numOfStates - 1) {
	    while (isInFlag(i)) {
		b--;
		i--;
		if (b < 0) {
		    b = sizeOfInputAlphabet - 1;
		    a--;
		}
	    }

	}
	int flag = nearestFlag(i);
	if (currentSeq[i] == currentSeq[flag]) {
	    currentSeq[i] = 0;
	    if (b == 0) {
		nextICDFA(a - 1, sizeOfInputAlphabet - 1);
	    } else {
		nextICDFA(a, b - 1);
	    }

	} else {
	    currentSeq[i] = currentSeq[i] + 1;
	}

    }

    /**
     * Recursively enumerate all sequences in the area defined by flag
     * 
     * @param flag
     * @return
     */
    public static ArrayList<int[]> enumerateSeqByFlag(int[] flag) {
	ArrayList<int[]> flagSeq = new ArrayList<int[]>();
	setCurrentFlag(flag);
	initSeq();
	flagSeq.add(currentSeq.clone());
	while (!isFinalSeq()) {
	    nextICDFA(numOfStates - 1, sizeOfInputAlphabet - 1);
	    flagSeq.add(currentSeq.clone());
	}

	return flagSeq;
    }

    /**
     * Generate random sequence in the flag area
     * 
     * @param flag
     * @return
     */
    public int[] genRandSeq(int[] flag) {
	int[] rndSeq = new int[this.getSeqSize()];
	Random rn = new Random();
	for (int i = 0; i < this.getSeqSize(); i++) {
	    rndSeq[i] = -1;
	}
	for (int j = 1; j <= flag.length - 1; j++) {
	    rndSeq[flag[j]] = j;
	}
	int fi = flag.length - 1;
	int si = this.getSeqSize() - 1;
	while (si > -1) {
	    if (rndSeq[si] != fi) {
		rndSeq[si] = rn.nextInt(fi + 1);

		si--;
	    } else {
		fi--;
		si--;

	    }

	}
	return rndSeq;
    }

    /**
     * Generate base pattern for flag 1 * 2 * * * -> [0, 27, 0, 9, 3, 1] with
     * n=3 and k=2
     * 
     * @param flag
     * @return
     */
    public static int[] genAddressMask(int[] flag) {
	int[] lastSeq = genLastSeq(flag);
	int[] combSeq = new int[seqSize];
	int[] rangeSeq = new int[seqSize];

	for (int i = 0; i < seqSize; i++) {
	    combSeq[i] = lastSeq[i] + 1;

	}

	for (int i = 0; i < flag[1]; i++) {
	    combSeq[i] = 0;
	}
	for (int j = 1; j <= flag.length - 1; j++) {
	    combSeq[flag[j]] = 0;
	}

	rangeSeq[seqSize - 1] = 1;
	for (int i = seqSize - 2; i > flag[1]; i--) {
	    int comb = 1;
	    for (int j = i + 1; j < seqSize; j++) {
		if (combSeq[j] > 0)
		    comb *= combSeq[j];

	    }
	    rangeSeq[i] = comb;
	}
	for (int j = 1; j <= flag.length - 1; j++) {
	    rangeSeq[flag[j]] = 0;
	}
	return rangeSeq;
    }

    /**
     * Compute global position number of the sequence by knowing the flag space
     * 
     * @param sequence
     * @param flag
     * @return
     */
    public static int localAddressOfSequence(int[] sequence, int[] flag) {
	int code = 0;
	int[] mask = genAddressMask(flag);
	for (int i = 0; i < sequence.length; i++) {
	    code += sequence[i] * mask[i];
	}
	return code;
    }

    /**
     * Reconstruct flag from sequence
     * 
     * @param sequence
     * @return
     */
    public int[] findFlagBySequence(int[] sequence) {
	int[] flag = new int[this.getNumOfStates()];
	for (int i = sequence.length - 1; i >= 0; i--) {
	    flag[sequence[i]] = i;
	}
	return flag;
    }

    /**
     * Enumerate all sequences
     */
    public void enumerate() {
	initFlag();
	while (!isFinalFlag()) {
	    nextFlag(numOfStates - 1);
	    allPossibleFlags.add(currentFlag.clone());
	}
	for (int i = 0; i < allPossibleFlags.size(); i++) {
	    setCurrentFlag(allPossibleFlags.get(i));
	    initSeq();
	    while (!isFinalSeq()) {
		nextICDFA(numOfStates - 1, sizeOfInputAlphabet - 1);
		allPossibleSeq.add(currentSeq.clone());

	    }

	}
    }

    /**
     * Transforming from (1, 2, 5, 7) to * 1 2 * * 3 * 4 * *
     * 
     * @param flag
     * @return string
     */
    public String flagtoString(int[] flag) {
	String flagS = "";
	int[] seqPattern = new int[this.getSeqSize()];
	for (int k = 0; k < seqPattern.length; k++) {
	    seqPattern[k] = -1;
	}
	flagS += "(";
	for (int j = 1; j < flag.length - 1; j++) {
	    flagS += (flag[j] + ", ");
	    seqPattern[flag[j]] = j;
	}

	flagS += (flag[flag.length - 1] + "): ");
	seqPattern[flag[flag.length - 1]] = flag.length - 1;
	for (int k = 0; k < seqPattern.length - 1; k++) {
	    if (seqPattern[k] == -1) {
		flagS += ("*" + " ");
	    } else
		flagS += (seqPattern[k] + " ");
	}
	if (seqPattern[seqPattern.length - 1] == -1) {
	    flagS += ("*" + " ");
	} else
	    flagS += (seqPattern[seqPattern.length - 1] + " ");
	return flagS;
    }

    // getters setters

    public int getNumOfStates() {
	return numOfStates;
    }

    public void setNumOfStates(int p_numOfStates) {
	numOfStates = p_numOfStates;
    }

    public int getSizeOfInputAlphabet() {
	return sizeOfInputAlphabet;
    }

    public void setSizeOfInputAlphabet(int p_sizeOfInputAlphabet) {
	sizeOfInputAlphabet = p_sizeOfInputAlphabet;
    }

    public int getSeqSize() {
	return seqSize;
    }

    public void setSeqSize(int p_seqSize) {
	seqSize = p_seqSize;
    }

    public long getNumberOfFlags() {
	return numberOfFlags;
    }

    public void setNumberOfFlags(long p_numberOfFlags) {
	numberOfFlags = p_numberOfFlags;
    }

    /**
     * Debug: print flag
     * 
     * @param flag
     */
    public StringBuffer printFlag(int[] flag) {
	StringBuffer s = new StringBuffer();
	s.append("Size: " + sizeOfFlagSpace(flag) + " ");
	s.append(flagtoString(flag) + "\n");
	return s;
    }

    /**
     * Debug: print all flags
     */
    public void printAllFlags() {
	for (int i = 0; i < allPossibleFlags.size(); i++) {
	    int[] flag = allPossibleFlags.get(i);
	    LOGGER.info(printFlag(flag).toString());
	}
    }

    /**
     * Debug: print sequence
     * 
     * @param sequence
     */
    public StringBuffer printSeq(int[] sequence) {
	StringBuffer s = new StringBuffer();
	for (int j = 0; j < sequence.length - 1; j++) {
	    s.append(sequence[j] + ", ");
	}
	s.append(sequence[sequence.length - 1] + " ");
	s.append(" nr:"
		+ localAddressOfSequence(sequence, findFlagBySequence(sequence))
		+ "\n");
	// printFlag(findFlagBySequence(sequence));
	return s;
    }

    /**
     * Debug: print all sequences
     */
    public void printAllSeq() {

	for (int i = 0; i < allPossibleSeq.size(); i++) {
	    int[] sequence = allPossibleSeq.get(i);
	    LOGGER.info(printSeq(sequence).toString());

	}

    }

    /**
     * Test function
     * 
     */
    static void test1EnumerateAllCSR() {
	int n = 3;
	int k = 2;

	TransitionFunctionCSR enumerator = new TransitionFunctionCSR(n, k);

	enumerator.enumerate();

	LOGGER.info("Number of flags: " + enumerator.allPossibleFlags.size());

	enumerator.printAllFlags();
	LOGGER.info("Number of sequences: " + enumerator.allPossibleSeq.size());
	enumerator.printAllSeq();
    }

    public static void main(String[] args) {
	try {

	    SLogger.setup();

	} catch (IOException e) {

	    e.printStackTrace();

	    throw new RuntimeException("Problems with creating the log files");

	}

	test1EnumerateAllCSR();

    }
}
