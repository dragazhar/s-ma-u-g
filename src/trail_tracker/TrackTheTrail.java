package trail_tracker;

import java.io.IOException;

import configuration.Config;
import configuration.ConfigValues;

import representation.MeFST_S_CSR;

import search_algorithm.bodies.Point;
import search_algorithm.search_problem.SearchProblem;

public class TrackTheTrail extends SearchProblem {
    Trail trail;
    int timeSteps = Config.TIME_STEPS;

    public TrackTheTrail(int n, String dataFileName, String name)
	    throws IOException {
	super();
	trail = new Trail(dataFileName);
	this.setName(name);
	this.setN(n);
	this.setAlpha(TrailTracker.events);
	this.setBeta(TrailTracker.actions);
	this.setK(TrailTracker.events.getSizeOfAlphabet());
	this.setType(ConfigValues.MEALY_MACHINE);
	this.setM(TrailTracker.actions.getSizeOfAlphabet());

    }

    @Override
    public double getPointScore(Point p) {

	if (Config.FILTER_POINTS && !this.filterPointByRepresentation(p)) {
	    return 0.0;
	}

	int[] transition = p.coordinatesT;
	int[] output = p.coordinatesO;

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(this.getN(),
		this.getK());
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		TrailTracker.events, TrailTracker.actions));

	ant.setAntGrid(trail.trailGrid);
	ant.setMaxFood(trail.food);
	ant.setMaxTime(this.timeSteps);
	ant.walk();

	return ant.getScore();
    }

    // exclude all point, where ant does not eat food when it sees it
    public boolean filterPointByRepresentation(Point p) {
	int[] output = p.coordinatesO;
	int i = 1;
	while (i <= output.length) {
	    if (output[i] != 0)
		return false;
	    i += 2;
	}
	return true;
    }

    @Override
    public void printSolution(Point p) throws IOException {
	int[] transition = p.coordinatesT;
	int[] output = p.coordinatesO;
	System.out.println("point" + p);
	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(this.getN(),
		this.getK());
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));

	ant.antGrid = trail.trailGrid;
	ant.setMaxFood(trail.food);
	ant.setMaxTime(this.timeSteps);
	ant.walk();
	ant.printToFile("results\\" + this.getName() + ".html");
	ShowTrail.draw(this.getName(), ant.antGrid);

    }

    public static TrailTracker ant5S81T314A() {
	int[] transition = { 1, 0, 2, 0, 3, 0, 4, 0, 0, 0 };
	int[] output = { 2, 0, 1, 0, 1, 0, 2, 0, 0, 0 };

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(5, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static TrailTracker ant4S42T() {
	int[] transition = { 1, 0, 2, 0, 3, 0, 0, 0 };
	int[] output = { 2, 0, 1, 0, 1, 0, 2, 0 };

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(4, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static TrailTracker ant7S190S() {
	int[] transition = { 2, 4, 0, 3, 4, 6, 1, 4, 5, 1, 1, 6, 1, 3 };
	int[] output = { 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 1, 0, 0, 0 };
	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(7, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static TrailTracker ant8S193S() {
	int[] transition = { 1, 2, 3, 4, 5, 1, 5, 2, 5, 6, 7, 7, 0, 1, 0, 6 };
	int[] output = { 1, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 2, 0 };

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(8, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static TrailTracker ant8S199S() {
	int[] transition = { 1, 2, 3, 4, 0, 2, 5, 5, 6, 6, 7, 1, 5, 6, 0, 4 };
	int[] output = { 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0 };

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(8, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static TrailTracker antStest091S() {
	int[] transition = { 1, 0, 2, 0, 3, 0, 4, 0, 0, 0 };
	int[] output = { 2, 0, 1, 0, 1, 0, 2, 0, 0, 0 };

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(5, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static TrailTracker ant7s() {
	int[] transition = { 1, 2, 3, 1, 1, 3, 4, 3, 5, 2, 0, 6, 0, 4 };
	int[] output = { 2, 0, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 0, 2 };

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(7, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static TrailTracker ant6s() {
	int[] transition = { 1, 2, 3, 4, 5, 2, 2, 0, 3, 0, 0, 4 };
	int[] output = { 1, 0, 2, 0, 2, 0, 0, 2, 0, 0, 1, 0 };

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(6, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static void test1() throws IOException {
	Trail trail = new Trail("trainingData\\SantaFeTrail.txt");
	TrailTracker ant = antSantaFe();
	ant.antGrid = trail.trailGrid;
	ant.setMaxFood(trail.food);
	ant.setMaxTime(350);

	ant.walk();
	System.out.print("Eaten: " + ant.food);
	System.out.println(" with score(" + ant.getScore() + ")");
	System.out.println(ant.steps);

	ant.printToFile("results\\ant.html");
	ShowTrail.draw("ant on Santa Fe Trail", ant.antGrid);
    }

    public static void test2() throws IOException {
	Trail trail = new Trail("trainingData\\JohnMuirTrail.txt");

	TrailTracker ant = ant6s();
	ant.antGrid = trail.trailGrid;
	ant.setMaxFood(trail.food);
	ant.setMaxTime(400);

	ant.walk();
	System.out.print("Eaten: " + ant.food);
	System.out.println(" with score(" + ant.getScore() + ")");
	System.out.println(ant.steps);

	ant.printToFile("results\\ant.html");
	ShowTrail.draw("ant on John Muir Trail", ant.antGrid);
    }

    public static void test3() throws IOException {
	Trail trail = new Trail("trainingData\\JohnMuirTrail.txt");

	TrailTracker ant = ant8();
	ant.antGrid = trail.trailGrid;
	ant.setMaxFood(trail.food);
	ant.setMaxTime(200);

	ant.walk();
	System.out.print("Eaten: " + ant.food);
	System.out.println(" with score(" + ant.getScore() + ")");
	System.out.println(ant.steps);

	ant.printToFile("results\\ant.html");
	ShowTrail.draw("ant on John Muir Trail", ant.antGrid);
    }

    public static void test4() throws IOException {
	int[] transition = { 1, 0, 2, 2, 3, 2, 4, 4, 0, 4 };
	int[] output = { 2, 0, 1, 0, 1, 0, 2, 0, 0, 0 };

	TrackTheTrail sp = new TrackTheTrail(5,
		"trainingData\\SantaFeTrail.txt", "testFiltering");
	Point p = new Point(transition, output);
	System.out.println(sp.getPointScore(p));
    }

    // [1, 0, 2, 2, 3, 2, 4, 4, 0, 4], OF=[2, 0, 1, 0, 1, 0, 2, 0, 0, 0]
    public static TrailTracker antSantaFe() {
	int[] transition = { 1, 0, 2, 2, 3, 2, 4, 4, 0, 4 };
	// int[] output = { 2, 0, 1, 0, 1, 0, 2, 0, 0, 0 };
	int[] output = { 2, 1, 1, 0, 1, 0, 2, 0, 0, 0 };
	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(5, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static TrailTracker ant8() {
	int[] transition = { 1, 2, 3, 4, 1, 2, 5, 4, 6, 0, 7, 2, 0, 6, 0, 2 };
	int[] output = { 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 2, 1, 0, 0 };

	MeFST_S_CSR trailTrackerRepresentation = new MeFST_S_CSR(8, 2);
	trailTrackerRepresentation.setTransitionFunction(transition);
	trailTrackerRepresentation.setOutputFunction(output);

	TrailTracker ant = new TrailTracker();
	ant.createTrailTracker(trailTrackerRepresentation.decode(
		ant.getInputAlphabet(), ant.getOutputAlphabet()));
	return ant;
    }

    public static void main(String[] args) throws IOException {

	test4();

    }

}
