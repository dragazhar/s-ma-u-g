package system_identification;

import fsm.Alphabet;
import fsm.Transition;

public class MooreFSTDecorated extends MooreFST {

    public MooreFSTDecorated(Alphabet alpha, Alphabet beta) {
	super(alpha, beta);
    }
    


    public void decoratingDFAasMooreMachine(Behaviour behaviour) {
	for (int i = 0; i < this.getNumOfStates(); i++) {
	    this.states.get(i).initStateAttributes(
		    this.getOutputAlphabet().getSizeOfAlphabet());
	}
	for (int i = 0; i < behaviour.inputData.size(); i++) {
	    String ins = "";
	    String outs = "";
	    ins = behaviour.inputData.get(i);
	    outs = behaviour.outputData.get(i);

	    updateAttributesString(ins, outs);

	}
	double dataConsistancy = 0;
	for (int i = 0; i < this.getNumOfStates(); i++) {
	    this.getState(i).setStateValue(
		    this.getOutputAlphabet().returnSymbolAtPosition(
			    this.getState(i).findPositionOfMaxAttr()));

	    dataConsistancy += this.getState(i).getStateAttrubuteAtPosition(
		    this.getState(i).findPositionOfMaxAttr());
	}

	this.setDataConsistancy(dataConsistancy / behaviour.ouputDataSize);

    }

    private void updateAttributesString(String inString, String outString) {
	this.currentState = this.states.get(0);
	this.currentState.incAttrValueAtPosition(this.getOutputAlphabet()
		.findPosition(outString.charAt(0)));

	for (int i = 0; i < inString.length(); i++) {
	    updateAttributesTransition(inString.charAt(i),
		    outString.charAt(i + 1));

	}

    }

    private void updateAttributesTransition(char inputChar, char outputChar) {
	Transition tr = this
		.getTransition(this.transitionTable[this.currentState
			.getStateID()][this.getInputAlphabet().findPosition(
			inputChar)]);
	if (tr == null) {
	    System.err.println("Machine stopped, transition not found");
	    return;
	} else {
	    this.currentState = tr.getToState();

	    this.currentState.incAttrValueAtPosition(this.getOutputAlphabet()
		    .findPosition(outputChar));

	}
    }

}
