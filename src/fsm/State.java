package fsm;

/**
 * State 
 * @author dragazhar
 *
 */
public class State {
    /**
     * State identifier
     */
    private int stateID;

    /**
     * State value - the output symbol (for Moore machine)
     */
    private char stateValue;

    /**
     * If there are several possible values at state (the size of the array
     * equals to output alphabet size), this array will store the information
     * about how many times the current output was used. Decorated state
     */
    private int[] stateAttributes;

    /**
     * Is this state initial?
     */
    private boolean initialState;

    /**
     * Is this state final?
     */
    private boolean finalState;

    /**
     * Constructor
     * 
     * @param stateID
     *            state identifier
     * @param stateValue
     *            value at the state
     * @param initialState
     *            is this state initial?
     * @param finalState
     *            is this state final?
     */
    public State(int stateID, char stateValue, boolean initialState,
	    boolean finalState) {
	this.stateID = stateID;
	this.stateValue = stateValue;
	this.initialState = initialState;
	this.finalState = finalState;
    }

    /**
     * Decorated state. Initializes the attributes of decorated state.
     * 
     * @param betaSize
     *            size of the output alphabet
     */
    public void initStateAttributes(int betaSize) {
	stateAttributes = new int[betaSize];
	for (int i = 0; i < stateAttributes.length; i++) {
	    stateAttributes[i] = 0;
	}
    }

    /**
     * Decorated state. Increments the value at given position
     * 
     * @param position
     *            position of the attribute
     */
    public void incAttrValueAtPosition(int position) {
	stateAttributes[position]++;
    }

    /**
     * Decorated state. Search for the attribute with the max value
     * 
     * @return the number of the attribute with max value
     */
    public int findPositionOfMaxAttr() {
	int positionOfMax = 0;
	for (int i = 1; i < stateAttributes.length; i++) {
	    if (stateAttributes[i] > stateAttributes[positionOfMax]) {
		positionOfMax = i;
	    }
	}

	return positionOfMax;
    }

    /**
     * Get state ID
     * 
     * @return state identifier
     */
    public int getStateID() {
	return stateID;
    }

    /**
     * Set State id
     * 
     * @param stateID
     */
    public void setStateID(int stateID) {
	this.stateID = stateID;
    }

    /**
     * Get State value
     * 
     * @return state value
     */
    public char getStateValue() {
	return stateValue;
    }

    /**
     * Set State value (for Moore machine)
     * 
     * @param stateValue
     *            output symbol stored at state
     */
    public void setStateValue(char stateValue) {
	this.stateValue = stateValue;
    }

    /**
     * Get InitialState
     * 
     * @return true if state is initial
     */
    public boolean isInitialState() {
	return initialState;
    }

    /**
     * Set InitialState
     * 
     * @param initialState
     *            true if state is initial otherwise - false
     */
    public void setInitialState(boolean initialState) {
	this.initialState = initialState;
    }

    /**
     * Get state final
     * 
     * @return true if state is final
     */
    public boolean isFinalState() {
	return finalState;
    }

    /**
     * Set state final/not final
     * 
     * @param finalState
     *            true if state is final
     */
    public void setFinalState(boolean finalState) {
	this.finalState = finalState;
    }

    /**
     * Decorated state.
     * 
     * @return values of state attributes
     */
    public int[] getStateAttributes() {
	return stateAttributes;
    }

    /**
     * Decorated state. Set values for the attributes
     * 
     * @param stateAttr
     */
    public void setStateAttributes(int[] stateAttr) {
	this.stateAttributes = stateAttr;
    }

    /**
     * Decorated state. Set value to the attribute at given position
     * 
     * @param index
     *            position of the attribute
     * @param value
     *            value of the attribute
     */
    public void setStateAttributeAtPosition(int index, int value) {
	this.stateAttributes[index] = value;
    }

    /**
     * Decorated state. Get value of the attribute at given position
     * 
     * @param index
     *            position of the attribute
     * @return value at the position
     */
    public int getStateAttrubuteAtPosition(int index) {
	return this.stateAttributes[index];
    }

    /*
     * Print out state (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
	String s = new String();
	s = "State: " + stateID + "/" + stateValue + " i?=" + initialState
		+ " f?=" + finalState;
	return s;
    }

}
