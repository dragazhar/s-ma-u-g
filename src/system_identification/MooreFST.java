package system_identification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


import fsm.Alphabet;
import fsm.FSM;
import fsm.StringDistances;
import fsm.Transition;

public class MooreFST extends FSM {

    public MooreFST(Alphabet alpha, Alphabet beta) {
	super();
	this.setInputAlphabet(alpha);
	this.setOutputAlphabet(beta);
    }
    public void create(FSM fsm) {

	this.setStates(fsm.states);
	this.setTransitions(fsm.transitions);
	this.setTransitionTable(fsm.transitionTable);

    }

    private String inString = "";
    private String outString = "";

    private double dataConsistancy = 0;

    public void makeTransition(char inputChar) {
	Transition tr = getTransition(transitionTable[currentState.getStateID()][this
		.getInputAlphabet().findPosition(inputChar)]);
	if (tr == null) {
	    this.setRunning(false);
	    System.err.println("Machine stopped, transition not found");
	    return;
	} else {
	    currentState = tr.getToState();
	  
	    // Makes transition and adds output symbol to output string
	    setOutString(getOutString() + tr.getToState().getStateValue());
	}
    }

    public void runMachine() {
	// output string
	this.setOutString("");
	// Set current state to initial
	currentState = states.get(0);
	this.setRunning(true);

	// Adds output of initial state
	setOutString(getOutString() + currentState.getStateValue());
	// Runs for the whole input
	for (int i = 0; i < inString.length(); i++) {
	    if (!this.isRunning()) {
		return;
	    } else {
		makeTransition(inString.charAt(i));
	    }
	}

    }

    public void evaluate(Behaviour behaviour) {
	dataConsistancy = 0;
	for (int i = 0; i < behaviour.inputData.size(); i++) {
	    String ins = "";
	    String out1s = "";
	    String out2s = "";

	    ins = behaviour.inputData.get(i);
	    out1s = behaviour.outputData.get(i);

	    this.setInString(ins);
	    this.runMachine();
	    out2s = this.getOutString();
	    this.setOutString("");
	    int sd = StringDistances.apply(out1s, out2s, StringDistances.HAMMING_DISTANCE);
	    dataConsistancy += sd;

	}
	this.dataConsistancy = dataConsistancy / behaviour.ouputDataSize;

    }
    public double getScore(){
	return this.dataConsistancy;
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

    public double getDataConsistancy() {
	return dataConsistancy;
    }

    public void setDataConsistancy(double dataConsistancy) {
	this.dataConsistancy = dataConsistancy;
    }
    public   void printToFile(String fileName, Behaviour behaviour) throws IOException {
   	PrintWriter res = null;
   	try {
   	    res = new PrintWriter(new BufferedWriter(new FileWriter(new File(
   		    fileName))));

   	    res.println("<html>");
   	    res.println("<body><font face=\"courier new\">");

   	    res.println(this.toHTMLString());
   	    res.println("</font>");
   	    
   	 res.println("<table border=\"1\">");
   	res.println("<caption>Checking data...</caption>");
   	res.println("<tr bgcolor=\"#999999\">");
   	res.println("<th>Input</th>");
   	res.println("<th>Expected output</th>");
   	res.println("<th>Produced output</th>");
   	res.println("<th>String distance</th>");
   	res.println("</tr>");
	    for (int i = 0; i < behaviour.inputData.size(); i++) {
		String ins = "";
		String out1s = "";
		String out2s = "";
		res.println("<tr>");
		ins =behaviour.inputData.get(i);

		res.println("<td>" + ins + "</td>");
		out1s = behaviour.outputData.get(i);
		res.println("<td>" + out1s + "</td>");

		this.setInString(ins);
		this.runMachine();
		out2s = this.getOutString();
		this.setOutString("");
		res.println("<td>" + out2s + "</td>");
		int hd = StringDistances.apply(out1s, out2s,StringDistances.HAMMING_DISTANCE);
		res.println("<td>" + (out1s.length() - hd)
			+ "</td>");
		res.println("</tr>");

	    }
	    res.println("</table>");
	    this.evaluate(behaviour);
res.print("<p>"+ this.getScore()+"</p>");
   	    res.println("</body>");
   	    res.println("</html>");
   	} catch (IOException e) {
   	    System.err.println("Error in input/output process...");
   	} finally {
   	    res.close();
   	}
       }

}
