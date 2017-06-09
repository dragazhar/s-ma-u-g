package fsm;

/**
 * String similarities/distances 
 * @author dragazhar
 *
 */
public class StringDistances {
    public static final int HAMMING_DISTANCE = 0;
    public static final int MAX_PREFIX = 1;

    /**
     * Applies the chosen string distance function to given strings
     * 
     * @param s1
     *            string one
     * @param s2
     *            string two
     * @param stringFunctionType
     *            function type
     * @return
     */
    public static int apply(String s1, String s2, int stringFunctionType) {
	int sim = 0;
	switch (stringFunctionType) {
	case HAMMING_DISTANCE:
	    sim = hammingDistanceSimilarity(s1, s2);
	    break;
	case MAX_PREFIX:
	    sim = maximalEqualPrefix(s1, s2);
	    break;
	}

	return sim;
    }

    /**
     * Returns Hamming similarity - number of the same letters in the word.
     * 
     * @param s1
     *            string one
     * @param s2
     *            string two
     * @return Hamming similarity
     */

    private static int hammingDistanceSimilarity(String s1, String s2) {
	int sim = 0;
	if (s1.length() <= s2.length()) {
	    for (int i = 0; i < s1.length(); i++) {
		if (s1.charAt(i) == s2.charAt(i)) {
		    sim++;
		}
	    }
	} else {
	    for (int i = 0; i < s2.length(); i++) {
		if (s1.charAt(i) == s2.charAt(i)) {
		    sim++;
		}
	    }
	}

	return sim;
    }

    /**
     * Returns the length of maximal equal prefix. Work ends when first error
     * found
     * 
     * @param s1
     *            string one
     * @param s2
     *            string two
     * @return length of maximal equal prefix
     */
    private static int maximalEqualPrefix(String s1, String s2) {
	int prefix = 0;
	if (s1.length() <= s2.length()) {
	    for (int i = 0; i < s1.length(); i++) {
		if (s1.charAt(i) == s2.charAt(i))
		    prefix++;
		else
		    return prefix;
	    }
	} else {
	    for (int i = 0; i < s2.length(); i++) {
		if (s1.charAt(i) == s2.charAt(i))
		    prefix++;
		else
		    return prefix;

	    }
	}

	return prefix;

    }
}
