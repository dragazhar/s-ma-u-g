package binary_predictor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



import fsm.Alphabet;
import fsm.FSM;
import fsm.StringDistances;
import fsm.Transition;

public class Predictor extends FSM {

    static Alphabet inputs = new Alphabet("01".toCharArray());
    static Alphabet outputs = new Alphabet("01".toCharArray());

    double score = 0;

    private String inString = "";
    private String outString = "";

    public Predictor() {
	super();
	this.setInputAlphabet(inputs);
	this.setOutputAlphabet(outputs);
    }

    public void createPredictor(FSM fsm) {

	this.setStates(fsm.states);
	this.setTransitions(fsm.transitions);
	this.setTransitionTable(fsm.transitionTable);

    }
    
    public void evaluate(String environment) {
	double sscore = 0;
	    String ins = "";
	    String out1s = "";
	    String out2s = "";

	    ins = environment.substring(0, environment.length()-1);
	    out1s = environment.substring(1,environment.length());

	    this.setInString(ins);
	    this.runMachine();
	    
	    out2s = this.getOutString();
	    this.setOutString("");
	   sscore = StringDistances.apply(out1s, out2s,
		    StringDistances.HAMMING_DISTANCE);
	   
	   this.score=sscore/out1s.length();

    }
    

    public void makeTransition(char inputChar) {
	Transition tr = getTransition(transitionTable[currentState.getStateID()][this
		.getInputAlphabet().findPosition(inputChar)]);
	if (tr == null) {
	    this.setRunning(false);
	    System.err.println("Machine stopped, transition not found");
	    return;
	} else {
	    currentState = tr.getToState();
	    // Adds to output string on transition
	    setOutString(getOutString() + tr.getOutputSymbol());
	}
    }

    public void runMachine() {
	// output string
	this.setOutString("");
	// set current state to initial
	currentState = states.get(0);
	// Runs for every input input symbol
	for (int i = 0; i < inString.length(); i++) {
	    if (!this.isRunning()) {
		System.err.println("End of work");
		return;
	    } else {
		makeTransition(inString.charAt(i));
	    }
	}

    }

    public double getScore() {

	return this.score;
    }

    public void printToFile(String fileName, String environment) throws IOException {
	PrintWriter res = null;
	try {
	    res = new PrintWriter(new BufferedWriter(new FileWriter(new File(
		    fileName))));

	    res.println("<html>");
	    res.println("<body><font face=\"courier new\">");

	    res.println(this.toHTMLString());
	    res.println("<br/>Score: " + this.getScore() + "<br/>");


	    
	    String ins = "";
		String out1s = "";
		String out2s = "";
		int sscore=0;
		  ins = environment.substring(0, environment.length()-1);
		    out1s = environment.substring(1,environment.length());

		    this.setInString(ins);
		    this.runMachine();
		    
		    out2s = this.getOutString();
		    this.setOutString("");
		   sscore = StringDistances.apply(out1s, out2s,
			    StringDistances.HAMMING_DISTANCE);
		   
		   this.score=sscore/out1s.length();
		   res.println("<table border=\"1\">");
		   res.println("<tr><td>Environment: </td><td>"+ins+"</td></tr>");
		   res.println("<tr><td> - </td><td>"+"-"+out1s+"</td></tr>");
		   res.println("<tr><td>Predicted:  </td><td>"+"-"+out2s+"</td></tr>");
		   res.println("<tr></table>");
		    res.println("</font>");
	    res.println("</body>");
	    res.println("</html>");
	} catch (IOException e) {
	    System.err.println("Error in input/output process...");
	} finally {
	    res.close();
	}
    }

    public String getInString() {
	return inString;
    }

    public void setInString(String inString) {
	this.inString = inString;
    }

    public String getOutString() {
	return outString;
    }

    public void setOutString(String outString) {
	this.outString = outString;
    }

}
