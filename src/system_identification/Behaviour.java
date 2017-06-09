package system_identification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fsm.Alphabet;

public class Behaviour {

    // Alphabets
    public  Alphabet alpha;
    public  Alphabet beta;
    // Behavior
    public  ArrayList<String> inputData;
    public ArrayList<String> outputData;

    public int ouputDataSize = 0;

    public Behaviour(String fileName) throws IOException {
	readTrainingData(fileName);
	alpha = new Alphabet();
	beta = new Alphabet();
	addCharsToAplhabet(alpha, inputData);
	addCharsToAplhabet(beta, outputData);

	ouputDataSize = countOutputDataSize();

    }

    private void addCharsToAplhabet(Alphabet alpha, ArrayList<String> list) {
	for (int i = 0; i < list.size(); i++) {
	    String li = list.get(i);
	    for (int j = 0; j < li.length(); j++) {
		alpha.addToAlphabet(li.charAt(j));
	    }
	}
    }

    public void readTrainingData(String fileName) throws IOException {
	inputData = new ArrayList<String>();
	outputData = new ArrayList<String>();
	if (fileName == null) {
	    fileName = "trainingData\\data.txt";
	}
	BufferedReader dataR = new BufferedReader(new FileReader(new File(
		fileName)));
	try {

	    String s = new String();
	    while ((s = dataR.readLine()) != null) {
		String in = "";
		String out = "";
		boolean end = false;
		for (int i = 0; i < s.length(); i++) {

		    if ((s.charAt(i) == ',') || (s.charAt(i) == ' ')) {
			end = true;
		    } else {
			if (!end) {
			    in += s.charAt(i);
			} else {
			    out += s.charAt(i);
			}
		    }

		}

		inputData.add(in);
		outputData.add(out);
	    }

	} catch (IOException e) {
	    System.err.println("Error in input/output process...");
	} finally {
	    dataR.close();
	}

    }

    private int countOutputDataSize() {
	int mv = 0;
	for (int i = 0; i < outputData.size(); i++) {
	    mv += outputData.get(i).length();
	}
	return mv;
    }

}
