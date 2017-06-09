package search_algorithm;

import java.io.IOException;

import fsm.Alphabet;

public abstract class SearchProblem {
    public static final int MOORE_MACHINE=0;
    public static final int MEALY_MACHINE=1;

    private String name="";

    private int n = 0;
    private int k = 0;
    private int m = 0;
    private int type=MOORE_MACHINE;
    
    private Alphabet alpha =new Alphabet();
    private Alphabet beta =new Alphabet();
    

    
    public abstract double getPointScore(Point p);
    
    public abstract void printSolution(Point p) throws IOException;

    public SearchProblem() {
	
	super();
    }
    
    public int getN() {
	return n;
    }

    public void setN(int n) {
	this.n = n;
    }

    public int getK() {
	return k;
    }

    public void setK(int k) {
	this.k = k;
    }

    public int getM() {
	return m;
    }

    public void setM(int m) {
	this.m = m;
    }

    public Alphabet getAlpha() {
        return alpha;
    }

    public void setAlpha(Alphabet alpha) {
        this.alpha = alpha;
    }

    public Alphabet getBeta() {
        return beta;
    }

    public void setBeta(Alphabet beta) {
        this.beta = beta;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

}
