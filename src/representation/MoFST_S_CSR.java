package representation;

import fsm.Alphabet;
import fsm.FSM;
import fsm.State;
import fsm.Transition;

/**
 * Moore machine represented by canonical string with statically defined output function
 * @author dragazhar
 *
 */
public class MoFST_S_CSR {
    int[] transitionFunction;
    int[] outputFunction;
    int n = 0; // number of states
    int k = 0; // size of input alphabet
    int m = 0; // size of output alphabet

    public MoFST_S_CSR(int n, int k) {
	this.transitionFunction = new int[n * k];
	this.n = n;
	this.k = k;

    }

    /**
     * Define transition function
     * @param representationT
     */
    public void setTransitionFunction(int[] representationT) {
	for (int i = 0; i < representationT.length; i++) {
	    this.transitionFunction[i] = representationT[i];
	}
    }
    


    /**
     * Define output function
     * @param outputFunction
     */
    public void setOutputFunction(int[] outputFunction) {
        this.outputFunction = outputFunction;
    }
    
    
    /**
     * Decode from strings to machine
     * @param alpha
     * @param beta
     * @return
     */
    public FSM decode(Alphabet alpha, Alphabet beta) {
	FSM decodedFSM=new FSM();
	// set alphabets
	decodedFSM.setInputAlphabet(alpha);
	decodedFSM.setOutputAlphabet(beta);
	// add states
	for (int i = 0; i < n; i++) {
	    State q = new State(i,
		    decodedFSM.getOutputAlphabet().returnSymbolAtPosition(this.outputFunction[i]),
		    false, false);
	    if (i == 0) {
		q.setInitialState(true);
	    }
	    decodedFSM.addState(q);
	}
	// set transition function
	decodedFSM.initTransitionTable();
	for (int i = 0; i < n; i++) {
	    State fromState = decodedFSM.getState(i);
	    for (int j = 0; j < alpha.getSizeOfAlphabet(); j++) {
		State toState = decodedFSM.getState(this.transitionFunction[i
			* (alpha.getSizeOfAlphabet()) + j]);
		char inChar = decodedFSM.getInputAlphabet().returnSymbolAtPosition(j);
		Transition qToQRandChar = new Transition(fromState, toState,
			inChar, '-');
		decodedFSM.addTransition(qToQRandChar);
	    }
	}
	  return decodedFSM;
    }


}
