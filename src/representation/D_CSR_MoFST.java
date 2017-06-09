package representation;

import fsm.Alphabet;
import fsm.FSM;
import fsm.State;
import fsm.Transition;

public class D_CSR_MoFST {
    int[] transitionFunction;
    int n = 0; // number of states
    int k = 0; // size of input alphabet
    int m = 0; // size of output alphabet

    public D_CSR_MoFST(int n, int k) {
	this.transitionFunction = new int[n * k];
	this.n = n;
	this.k = k;

    }

    public void setTransitionFunction(int[] representationT) {
	for (int i = 0; i < representationT.length; i++) {
	    this.transitionFunction[i] = representationT[i];
	}
    }
    
    public FSM decode(Alphabet alpha, Alphabet beta) {
	FSM decodedFSM=new FSM();
	// set alphabets
	decodedFSM.setInputAlphabet(alpha);
	decodedFSM.setOutputAlphabet(beta);
	// add states
	for (int i = 0; i < n; i++) {
	    State q = new State(i,
		   '-',
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
