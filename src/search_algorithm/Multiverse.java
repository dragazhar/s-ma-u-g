package search_algorithm;

import java.util.ArrayList;

import configuration.ConfigValues;

import representation.OutputFunctionSR;
import representation.TransitionFunctionCSR;
import search_algorithm.bodies.Point;
import search_algorithm.search_problem.SearchProblem;

public class Multiverse {
    SearchProblem searchProblem;
    long sizeofMultiverse = 0;

    int dimensionT = 0;
    int dimensionO = 0;

    public long searchSpaceSize = 0;

    public Point solution;
    public boolean found = false;

    public TransitionFunctionCSR mVerseCSR;

    public ArrayList<Universe> parallelUniverses = new ArrayList<Universe>();
    boolean printPU = true;

    public void create(SearchProblem sp) {
	searchProblem = sp;
	mVerseCSR = new TransitionFunctionCSR(
		searchProblem.getN(), searchProblem.getK());

	computeDimensionality();

	sizeofMultiverse = mVerseCSR.computeNumberOfFlags(searchProblem.getN(),
		searchProblem.getK());

	mVerseCSR.enumerateFlags();
	for (int i = 0; i < sizeofMultiverse; i++) {
	    Universe uVerse = new Universe(this,
		    mVerseCSR.allPossibleFlags.get(i), i);
	    parallelUniverses.add(uVerse);

	}
	searchSpaceSize = computeSearchSpaceSize();
    }

    public void computeDimensionality() {
	dimensionT = searchProblem.getN() * searchProblem.getK();

	if (searchProblem.getM() == 0) {
	    dimensionO = 1;// D_CSR(FST)
	} else {
	    switch (searchProblem.getType()) {
	    case ConfigValues.MOORE_MACHINE: {
		dimensionO = searchProblem.getN();
		break;
	    }
	    case ConfigValues.MEALY_MACHINE: {
		dimensionO = searchProblem.getN() * searchProblem.getK();

		break;
	    }

	    }
	}
    }

    public long sizeOfOutputFunctionSpace() {
	long size = 0;
	if (searchProblem.getM() == 0) {
	    size = 1;
	} else {
	    size =  OutputFunctionSR.getNumberAllPossibleSeq(dimensionO, searchProblem.getM());

	}
	return size;
    }

    public long computeSearchSpaceSize() {
	long size = 0;
	for (Universe u : parallelUniverses) {
	    size += u.sizeT;
	}
	if (this.searchProblem.getM() > 0) {
	    size *=  OutputFunctionSR.getNumberAllPossibleSeq(dimensionO, searchProblem.getM());
	}
	return size;
    }

    public long computeActualSearchSpaceSize() {
	long size = 0;
	for (Universe u : parallelUniverses) {
	    size += u.points.size();
	}
	return size;
    }

    public String toString() {
	String s = "Multiverse: \n";
	s += "Input alphabet: <";
	s += searchProblem.getAlpha();
	s += " >\n";
	s += "Output alphabet: <";
	s += searchProblem.getBeta();
	s += " >\n";
	s += "Number of parallel Universes: ";
	s += sizeofMultiverse;
	s += "\n";
	s += "Size of search space: " + searchSpaceSize;
	s += "\n";
	if (printPU) {
	    for (int i = 0; i < sizeofMultiverse; i++) {
		s += (" " + i + " ");
		s += parallelUniverses.get(i);
		s += "\n";
	    }
	}

	return s;
    }

    public long getSizeofMultiverse() {
	return sizeofMultiverse;
    }
}
