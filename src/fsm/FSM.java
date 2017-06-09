package fsm;

import java.util.ArrayList;

/**
 * Finite State Machine
 * 
 * @author dragazhar
 * 
 */
public class FSM {

    /**
     * Input alphabet - alpha
     */
    Alphabet inputAlphabet;
    /**
     * Output alphabet - beta
     */
    Alphabet outputAlphabet;
    /**
     * Set of states
     */
    public ArrayList<State> states;
    /**
     * Set of transitions
     */
    public ArrayList<Transition> transitions;
    /**
     * Transition table
     */
    public int[][] transitionTable;
    /**
     * Current state
     */
    public State currentState;

    /**
     * Defines the FSM
     */
    public FSM() {
	inputAlphabet = new Alphabet();
	outputAlphabet = new Alphabet();
	states = new ArrayList<State>();
	transitions = new ArrayList<Transition>();
    }

    /**
     * Initializes transition table with -1
     */
    public void initTransitionTable() {
	transitionTable = new int[states.size()][inputAlphabet
		.getSizeOfAlphabet()];
	for (int i = 0; i < states.size(); i++) {
	    for (int j = 0; j < inputAlphabet.getSizeOfAlphabet(); j++) {
		transitionTable[i][j] = -1;
	    }
	}
    }

    /**
     * Add state to states
     * 
     * @param stateToAdd
     */
    public void addState(State stateToAdd) {
	states.add(stateToAdd);

    }

    /**
     * Number of states
     * 
     * @return the number of states in the machine
     */
    public int getNumOfStates() {
	return states.size();
    }

    /**
     * Add transition to state
     * 
     * @param trans
     */
    public void addTransition(Transition trans) {
	transitions.add(trans);

	transitionTable[trans.getFromState().getStateID()][inputAlphabet
		.findPosition(trans.getInputSymbol())] = transitions
		.indexOf(trans);
    }

    /**
     * Get transition on given position
     * 
     * @param position
     * @return transition
     */
    public Transition getTransition(int position) {
	if (position != -1) {
	    return transitions.get(position);
	} else {
	    return null;
	}
    }

    /**
     * @return number of transitions
     */
    public int getNumOfTransitions() {
	return transitions.size();
    }

    /**
     * @param position
     *            of the state
     * @return state at position
     */
    public State getStateAtPosition(int position) {
	return states.get(position);
    }

    /**
     * Returns input alphabet
     * 
     * @return alphabet
     */
    public Alphabet getInputAlphabet() {
	return inputAlphabet;
    }

    /**
     * Sets input alphabet
     * 
     * @param inputAlphabet
     */
    public void setInputAlphabet(Alphabet inputAlphabet) {
	this.inputAlphabet = inputAlphabet;
    }

    /**
     * Returns out alphabet
     * 
     * @return alphabet
     */
    public Alphabet getOutputAlphabet() {
	return outputAlphabet;
    }

    /**
     * Sets out alphabet
     * 
     * @param outputAlphabet
     */
    public void setOutputAlphabet(Alphabet outputAlphabet) {
	this.outputAlphabet = outputAlphabet;
    }

    /**
     * return state at position
     * 
     * @param position
     *            given position of the state
     * @return state at position
     */
    public State getState(int position) {
	return states.get(position);
    }

    /**
     * Is machine still running
     */
    private boolean running = true;

    /**
     * @return true if machine running
     */
    public boolean isRunning() {
	return running;
    }

    /**
     * Set machine running or not
     * 
     * @param running
     */
    public void setRunning(boolean running) {
	this.running = running;
    }

    /**
     * Formating transition table to HTML
     * 
     * @return html string
     */
    public StringBuffer toHTMLString() {
	StringBuffer s = new StringBuffer();
	s.append("<table class=\".fsm__transition-table\">");
	s.append("<tr>");
	s.append("<th>From state</th>");
	s.append("<th>To state</th>");
	s.append("<th>Input</th>");
	s.append("<th>Output</th>");
	s.append("</tr>");

	for (int i = 0; i < getNumOfTransitions(); i++) {
	    s.append("<tr>");
	    Transition tr = getTransition(i);
	    s.append("<td>" + tr.getFromState().getStateID() + "/"
		    + tr.getFromState().getStateValue() + "</td>");
	    s.append("<td>" + tr.getToState().getStateID() + "/"
		    + tr.getToState().getStateValue() + "</td>");
	    s.append("<td>" + tr.getInputSymbol() + "</td>");
	    s.append("<td>" + tr.getOutputSymbol() + "</td>");

	    s.append("</tr>");
	}
	s.append("</table>");
	return s;

    }

    /**
     * Assigns states
     * 
     * @param states
     */
    public void setStates(ArrayList<State> states) {
	this.states = states;
    }

    /**
     * Assigns transitions
     * 
     * @param transitions
     */
    public void setTransitions(ArrayList<Transition> transitions) {
	this.transitions = transitions;
    }

    /**
     * Assigns transition table
     * 
     * @param transitionTable
     */
    public void setTransitionTable(int[][] transitionTable) {
	this.transitionTable = transitionTable;
    }
}
