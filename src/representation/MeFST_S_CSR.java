package representation;

import fsm.Alphabet;
import fsm.FSM;
import fsm.State;
import fsm.Transition;

/**
 * 
 * Mealy machine represented by canonical string with static output function
 * @author dragazhar
 *
 */
public class MeFST_S_CSR {
    int[] transitionFunction;
    int[] outputFunction;
    int n = 0; // number of states
    int k = 0; // size of input alphabet
    int m = 0; // size of output alphabet

    /**
     * Initialize 
     * @param n
     * @param k
     */
    public MeFST_S_CSR(int n, int k) {
	this.transitionFunction = new int[n * k];
	this.outputFunction = new int[n * k];
	this.n = n;
	this.k = k;

    }

    /**
     * Setup transition function string 
     * @param representationT
     */
    public void setTransitionFunction(int[] representationT) {
	for (int i = 0; i < representationT.length; i++) {
	    this.transitionFunction[i] = representationT[i];
	}
    }

    /**
     * Setup output function string
     * @param representationO
     */
    public void setOutputFunction(int[] representationO) {
	if (representationO != null) {
	    for (int i = 0; i < representationO.length; i++) {
		this.outputFunction[i] = representationO[i];
	    }
	} else {
	    for (int i = 0; i < this.outputFunction.length; i++) {
		this.outputFunction[i] = 0;
	    }
	}
    }
    /**
     * 
     * Decode from string representation to FSM
     * @param alpha
     * @param beta
     * @return
     */
    public FSM decode(Alphabet alpha, Alphabet beta) {
	FSM decodedFSM=new FSM();
	

	decodedFSM.setInputAlphabet(alpha);
	decodedFSM.setOutputAlphabet(beta);
	
	// add states
	for (int i = 0; i <n; i++) {
	    State q = new State(i, '-', false, false);
	    if (i == 0) {
		q.setInitialState(true);
	    }
	    decodedFSM.addState(q);
	}
	// set transition function
	decodedFSM.initTransitionTable();
	for (int i = 0; i <n; i++) {
	    State fromState =decodedFSM.getState(i);
	    for (int j = 0; j <k; j++) {
		State toState =decodedFSM.getState(this.transitionFunction[i
			* k + j]);
		char inChar = decodedFSM.getInputAlphabet().returnSymbolAtPosition(j);
		char outChar = decodedFSM.getOutputAlphabet()
			.returnSymbolAtPosition(
				this.outputFunction[i
					* k + j]);

		Transition qToQRandChar = new Transition(fromState, toState,
			inChar, outChar);
		decodedFSM.addTransition(qToQRandChar);
	    }
	}
    return decodedFSM;
    }

}
