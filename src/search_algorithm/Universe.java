package search_algorithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import representation.OutputFunctionSR;
import representation.TransitionFunctionCSR;

public class Universe {
    Multiverse mVerse;

    public int[] flag;
    public TreeMap<Key, Point> points = new TreeMap<Key, Point>();
    public ArrayList<Body> bodies = new ArrayList<Body>();

    long sizeT = 0;
    long sizeO = 0;
    long sizeTO = 0;

    int[] mask;

    int iRangeT = 0;

    private double averageMassSumm = 0;
    private double maxMass = 0;

    boolean explored = false;

    public Point bestPoint;

    public Universe(Multiverse mVerse, int[] flag, int range) {
	super();
	this.mVerse = mVerse;
	this.flag = flag;

	this.sizeT = mVerse.mVerseCSR.sizeOfFlagSpace(flag);
	this.sizeO = mVerse.sizeOfOutputFunctionSpace();
	this.sizeTO = this.sizeT * this.sizeO;

	this.mask = mVerse.mVerseCSR.genAddressMask(flag);
	this.iRangeT = range;

    }

    public void clearPoints() {
	points = null;
	bodies = null;
    }

    public int localPointTtoInteger(int[] coordinatesT) {
	int code = 0;
	code = TransitionFunctionCSR.localAddressOfSequence(coordinatesT, flag);
	return code;
    }

    public int localPointOtoInteger(int[] coordinatesO) {

	int code = 0;
	int base = mVerse.searchProblem.getM();
	code = OutputFunctionSR.globalAddressOfSequence(coordinatesO, base);

	return code;
    }

    public void generateRNDPoints(double pc1, double pc2) {
	if (pc1 < 1.0) {
	    int nT = (int) Math.floor(this.sizeT * pc1);
	    if (nT < 1)
		nT = 1;

	    for (int i = 0; i < nT; i++) {
		if (mVerse.found)
		    return;
		int[] coordT = generateRNDCoordinatesT();
		int nO = 1;
		if (mVerse.searchProblem.getM() > 0) {
		    nO = (int) Math.floor(this.sizeO * pc2);
		    if (nO < 1)
			nO = 1;
		}
		int keyT = this.localPointTtoInteger(coordT);
		int keyU = this.iRangeT;

		for (int j = 0; j < nO; j++) {
		    int[] coordO = generateRNDCoordinatesO();

		    Point point = new Point(coordT, coordO);

		    int keyO = this.localPointOtoInteger(coordO);
		    Key key = new Key(keyU, keyT, keyO);
		    point.setKey(key);
		    point.generated = 0;
		    double score = 0;

		    // make first point as best
		    if (i == 0) {
			score = mVerse.searchProblem.getPointScore(point);
			point.setValue(score);
			this.bestPoint = point;
			this.maxMass = point.getValue();
		    }
		    if (!points.containsKey(key)) {
			score = mVerse.searchProblem.getPointScore(point);
			point.setValue(score);
			points.put(key, point);

			if (point.getValue() > bestPoint.getValue()) {
			    this.bestPoint.copyPoint(point);
			    this.maxMass = point.getValue();
			}
			averageMassSumm += point.getValue();
		    }

		    if (score == 1.0) {
			mVerse.found = true;
			mVerse.solution = point;
			return;
		    }

		}
	    }
	} else {
	    generateAllPoints();
	}
    }

    public void generateAllPoints() {
	ArrayList<int[]> pointsT = mVerse.mVerseCSR.enumerateSeqByFlag(flag);
	ArrayList<int[]> pointsO = new ArrayList<int[]>();
	if (mVerse.searchProblem.getM() > 0) {
	    pointsO = OutputFunctionSR.enumerateAll(mVerse.dimensionO,
		    mVerse.searchProblem.getM());
	} else {

	    int[] coordO = generateRNDCoordinatesO();
	    pointsO.add(coordO);
	}
	for (int[] pT : pointsT) {

	    for (int[] pO : pointsO) {
		Point point = new Point(pT, pO);
		int keyU = this.iRangeT;
		int keyT = this.localPointTtoInteger(pT);
		int keyO = this.localPointOtoInteger(pO);
		Key key = new Key(keyU, keyT, keyO);
		point.setKey(key);
		double score = mVerse.searchProblem.getPointScore(point);
		point.setValue(score);
		if (points.size() == 0) {
		    this.bestPoint = point;
		    this.maxMass = score;
		}
		if (!points.containsKey(key)) {
		    points.put(key, point);
		    if (point.getValue() > bestPoint.getValue()) {
			 this.bestPoint.copyPoint(point);
			this.maxMass = point.getValue();
		    }
		    averageMassSumm += point.getValue();
		}
		if (score == 1.0) {
		    mVerse.solution = point;
		}

	    }
	}
    }

    public Coordinates generateRNDCoordinates() {
	Coordinates rnd;
	int[] coordT = generateRNDCoordinatesT();
	int[] coordO = generateRNDCoordinatesO();
	rnd = new Coordinates(coordT, coordO);
	return rnd;

    }

    public int[] generateRNDCoordinatesT() {
	return mVerse.mVerseCSR.genRandSeq(flag);
    }

    public int[] generateRNDCoordinatesO() {
	int[] coordinatesO = new int[mVerse.dimensionO];
	if (mVerse.searchProblem.getM() > 0) {
	    Random rn = new Random();
	    for (int i = 0; i < mVerse.dimensionO; i++) {

		coordinatesO[i] = rn.nextInt(mVerse.searchProblem.getM());
	    }
	} else {
	    for (int i = 0; i < mVerse.dimensionO; i++) {
		coordinatesO[i] = 0;
	    }
	}
	return coordinatesO;

    }

    public boolean compareFlags(int[] flagg) {
	boolean equal = false;
	int l = 0;
	for (int i = 0; i < flagg.length; i++) {
	    if (this.flag[i] == flagg[i]) {
		l++;
	    }

	}
	if (l == flagg.length) {
	    equal = true;
	}
	return equal;

    }

    public double getAverageMass() {
	if (points.isEmpty()) {
	    return 0.0;
	} else
	    return (double)this.averageMassSumm /(double) points.size();
    }

    public boolean isExplored() {
	return explored;
    }

    public void setExplored(boolean explored) {
	this.explored = explored;
    }

    public double getMaxMass() {
	return maxMass;
    }

    public void setMaxMass(double maxMass) {
	this.maxMass = maxMass;
    }

    @Override
    public String toString() {
	String s = "Universe: ";
	s += mVerse.mVerseCSR.flagtoString(flag);
	s += "\t";
	s += "Size: ";
	s += this.sizeT;
	s += " * ";
	s += this.sizeO;
	s += " \t";
	s += "From: " + this.iRangeT + " to: "
		+ (this.iRangeT + this.sizeT - 1);
	return s;

    }

    public String toString2() {
	String s = "Universe: ";
	s += mVerse.mVerseCSR.flagtoString(flag);
	s += "\t";
//	s += "Average mass: ";
//	s += this.getAverageMass();
//	s += " \t";
	s += "Max mass: ";
	s += this.getMaxMass();
	s += " \t";
	s += "Points: " + this.points.size();
	return s;

    }

}