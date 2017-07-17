package system_identification;

import java.io.IOException;

import configuration.ConfigValues;

import representation.D_CSR_MoFST;
import representation.MeFST_S_CSR;
import representation.MoFST_S_CSR;

import search_algorithm.bodies.Point;
import search_algorithm.search_problem.SearchProblem;

public class ModelingBehaviour extends SearchProblem {
    Behaviour trainingData;

    public ModelingBehaviour(int n, int type, boolean searchForOutput,
	    String dataFileName, String name) throws IOException {
	super();
	trainingData = new Behaviour(dataFileName);
	this.setName(name);
	this.setN(n);
	this.setK(trainingData.alpha.getSizeOfAlphabet());
	this.setType(type);
	if (searchForOutput) {
	    this.setM(trainingData.beta.getSizeOfAlphabet());// S-CSR(FST)
	} else
	    this.setM(0); // D-CSR(FST)
	this.setAlpha(trainingData.alpha);
	this.setBeta(trainingData.beta);

    }

    public double getPointScore(Point p) {
	double score = 0;
	switch (this.getType()) {
	case ConfigValues.MOORE_MACHINE:
	    if (this.getM() == 0) {
		D_CSR_MoFST representation = new D_CSR_MoFST(this.getN(),
			this.getK());
		MooreFSTDecorated machine = new MooreFSTDecorated(
			this.getAlpha(), this.getBeta());
		int[] transition = p.coordinatesT;
		representation.setTransitionFunction(transition);
		machine.create(representation.decode(this.getAlpha(),
			this.getBeta()));
		machine.decoratingDFAasMooreMachine(this.trainingData);
		score = machine.getScore();
	    } else {
		MoFST_S_CSR representation = new MoFST_S_CSR(this.getN(),
			this.getK());
		MooreFST machine = new MooreFST(this.getAlpha(), this.getBeta());
		int[] transition = p.coordinatesT;
		representation.setTransitionFunction(transition);
		int[] output = p.coordinatesO;
		representation.setOutputFunction(output);
		machine.create(representation.decode(this.getAlpha(),
			this.getBeta()));
		machine.evaluate(trainingData);
		score = machine.getScore();
	    }
	    break;
	case ConfigValues.MEALY_MACHINE:
	    if (this.getM() == 0) {

	    } else {

		MeFST_S_CSR representation = new MeFST_S_CSR(this.getN(),
			this.getK());
		MealyFST machine = new MealyFST(this.getAlpha(), this.getBeta());
		int[] transition = p.coordinatesT;
		representation.setTransitionFunction(transition);
		int[] output = p.coordinatesO;
		representation.setOutputFunction(output);
		machine.create(representation.decode(this.getAlpha(),
			this.getBeta()));
		machine.evaluate(trainingData);
		score = machine.getScore();
	    }
	    break;

	}

	return score;
    }

    @Override
    public void printSolution(Point p) throws IOException {
	switch (this.getType()) {
	case ConfigValues.MOORE_MACHINE:
	    if (this.getM() == 0) {
		MooreFSTDecorated machine = new MooreFSTDecorated(
			this.getAlpha(), this.getBeta());
		D_CSR_MoFST representation = new D_CSR_MoFST(this.getN(),
			this.getK());
		int[] transition = p.coordinatesT;
		representation.setTransitionFunction(transition);
		machine.create(representation.decode(this.getAlpha(),
			this.getBeta()));

		machine.decoratingDFAasMooreMachine(this.trainingData);
		machine.evaluate(trainingData);
		machine.printToFile("results\\" + this.getName() + ".html",
			this.trainingData);
	    } else {
		MooreFST machine = new MooreFST(this.getAlpha(), this.getBeta());
		MoFST_S_CSR representation = new MoFST_S_CSR(this.getN(),
			this.getK());
		int[] transition = p.coordinatesT;
		representation.setTransitionFunction(transition);
		int[] output = p.coordinatesO;
		representation.setOutputFunction(output);
		machine.create(representation.decode(this.getAlpha(),
			this.getBeta()));
		machine.evaluate(trainingData);
		machine.printToFile("results\\" + this.getName() + ".html",
			this.trainingData);
	    }
	    break;
	case ConfigValues.MEALY_MACHINE:
	    MealyFST machine = new MealyFST(this.getAlpha(), this.getBeta());
	   MeFST_S_CSR representation = new MeFST_S_CSR(this.getN(),
		    this.getK());
	    int[] transition = p.coordinatesT;
	    representation.setTransitionFunction(transition);
	    int[] output = p.coordinatesO;
	    representation.setOutputFunction(output);
	    machine.create(representation.decode(this.getAlpha(),
		    this.getBeta()));
	    machine.evaluate(trainingData);
	    machine.printToFile("results\\" + this.getName() + ".html",
		    this.trainingData);

	    break;
	}
    }

    public static void main(String[] args) throws IOException {
	Behaviour aabRecognizerData = new Behaviour(
		"trainingData\\aab_recognizer_Moore.txt");

	MooreFSTDecorated aabRecognizer = new MooreFSTDecorated(
		aabRecognizerData.alpha, aabRecognizerData.beta);

	D_CSR_MoFST aabRecognizerRepresentation = new D_CSR_MoFST(4,
		aabRecognizerData.alpha.getSizeOfAlphabet());
	int[] transition = { 0, 1, 0, 2, 3, 2, 0, 1 };
	aabRecognizerRepresentation.setTransitionFunction(transition);

	aabRecognizer.create(aabRecognizerRepresentation.decode(
		aabRecognizerData.alpha, aabRecognizerData.beta));

	aabRecognizer.decoratingDFAasMooreMachine(aabRecognizerData);
	aabRecognizer.printToFile("results\\aabRecognizer.html",
		aabRecognizerData);
    }

}
