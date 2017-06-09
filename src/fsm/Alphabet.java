package fsm;

import java.util.ArrayList;
import java.util.Random;

/**
 * Alphabet
 * @author dragazhar
 *
 */
public class Alphabet {
    /**
     * Structure for storing alphabet
     */
    private ArrayList<Character> alphabet;

    /**
     * Size of the alphabet
     */
    private int sizeOfAlphabet = 0;

    /**
     * Constructor. Defining alphabet and the size of it
     */
    public Alphabet() {
	this.alphabet = new ArrayList<Character>();
	setSizeOfAlphabet();
    }

    public Alphabet(char[] al) {
	this.alphabet = new ArrayList<Character>();
	for (int i = 0; i < al.length; i++) {

	    this.alphabet.add(al[i]);
	}
	setSizeOfAlphabet();
    }

    /**
     * Add one symbol to the alphabet
     * 
     * @param symbol
     *            add this symbol to alphabet
     */
    public void addToAlphabet(char symbol) {
	if (!isInAlphabet(symbol)) {
	    alphabet.add(symbol);
	    setSizeOfAlphabet();
	}
    }

    /**
     * Returns char at given position in alphabet
     * 
     * @param position
     *            of the symbol
     * @return symbol in given position
     */
    public char returnSymbolAtPosition(int position) {
	char s;
	s = alphabet.get(position);
	return s;
    }

    /**
     * Is given symbol in alphabet?
     * 
     * @param symbol
     *            given symbol we are searching for in alphabet
     * @return true if symbol is in alphabet, else - false
     */
    public boolean isInAlphabet(char symbol) {
	char sym;
	boolean in = false;
	for (int i = 0; i < getSizeOfAlphabet(); i++) {
	    sym = alphabet.get(i);
	    if (sym == symbol) {
		in = true;
	    }

	}
	return in;
    }

    /**
     * Find position of the given symbol
     * 
     * @param s
     *            given symbol
     * @return position of the symbol
     */
    public int findPosition(char s) {

	return alphabet.indexOf(s);
    }

    /**
     * Generate random string in this alphabet
     * 
     * @param len
     *            length of required string
     * @return random string
     */
    public String generateRandomString(int len) {
	String s = "";
	Random rnd = new Random();
	for (int i = 0; i < len; i++) {
	    int value = rnd.nextInt(this.getSizeOfAlphabet());
	    s += this.returnSymbolAtPosition(value);
	}

	return s;
    }

    /**
     * Size of the alphabet - getter
     * 
     * @return size of the alphabet
     */

    public int getSizeOfAlphabet() {
	return sizeOfAlphabet;
    }

    /**
     * Size of the alphabet setter
     */
    public void setSizeOfAlphabet() {
	this.sizeOfAlphabet = alphabet.size();
    }

    /**
     * Alphabet getter
     * 
     * @return alphabet
     */
    public ArrayList<Character> getAlphabet() {
	return alphabet;
    }

    /**
     * Alphabet setter
     * 
     * @param alphabet
     */
    public void setAlphabet(ArrayList<Character> alphabet) {
	this.alphabet = alphabet;
    }

    /*
     * Print out Alphabet (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
	String s = "";
	for (int i = 0; i < alphabet.size(); i++) {
	    s += " " + alphabet.get(i);
	}

	return s;
    }
}
