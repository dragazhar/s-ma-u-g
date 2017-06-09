package fsm;

/**
 * Transition 
 * TODO: add attributes as it was done for states
 * 
 * @author dragazhar
 * 
 */

public class Transition {
    /**
     * State where transition begins
     */
    private State fromState;

    /**
     * State where transition ends
     */
    private State toState;

    /**
     * Input symbol of the transition
     */
    private char inputSymbol;

    /**
     * Output symbol of the transition
     */
    private char outputSymbol;

    /**
     * Constructing transition
     * 
     * @param fromState
     *            state the transition is coming from
     * @param toState
     *            state the transition is coming to
     * @param inputSymbol
     *            symbol from input alphabet
     * @param outputSymbol
     *            symbol to output (for Mealy machines)
     */
    public Transition(State fromState, State toState, char inputSymbol,
	    char outputSymbol) {
	this.fromState = fromState;
	this.toState = toState;
	this.inputSymbol = inputSymbol;
	this.outputSymbol = outputSymbol;
    }

    /**
     * Get "from state"
     * 
     * @return the state transition begins
     */
    public State getFromState() {
	return fromState;
    }

    /**
     * Set state where transition ends
     * 
     * @param fromState
     *            given state
     */
    public void setFromState(State fromState) {
	this.fromState = fromState;
    }

    /**
     * Get "to state"
     * 
     * @return state the transition ends
     */
    public State getToState() {
	return toState;
    }

    /**
     * Set state where transition ends
     * 
     * @param toState
     *            given state
     */
    public void setToState(State toState) {
	this.toState = toState;
    }

    /**
     * Get input symbol
     * 
     * @return input symbol
     */
    public char getInputSymbol() {
	return inputSymbol;
    }

    /**
     * Set input symbol
     * 
     * @param inputSymbol
     *            concrete symbol from the alphabet
     */
    public void setInputSymbol(char inputSymbol) {
	this.inputSymbol = inputSymbol;
    }

    /**
     * Get output symbol
     * 
     * @return output symbol of the transition
     */
    public char getOutputSymbol() {
	return outputSymbol;
    }

    /**
     * Set output symbol
     * 
     * @param outputSymbol
     *            given symbol
     */
    public void setOutputSymbol(char outputSymbol) {
	this.outputSymbol = outputSymbol;
    }

    /*
     * Print out Transition (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
	String s = new String();
	s = "Transition: " + " " + fromState.getStateID() + ">>"
		+ toState.getStateID() + ":" + inputSymbol + "/" + outputSymbol;
	return s;
    }
}
