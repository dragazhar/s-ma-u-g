package search_algorithm;

import java.util.Arrays;

/**
 * 
 * Information about position of the point 
 * @author dragazhar
 *
 */
public class Coordinates {
    /**
     * 
     * Initial coordinates
     * @param coordinatesT
     * @param coordinatesO
     */
    public Coordinates(int[] coordinatesT, int[] coordinatesO) {
	super();
	this.coordinatesT = new  int[coordinatesT.length];
	this.coordinatesO = new int [coordinatesO.length];
	for (int i=0;i<coordinatesT.length; i++){
	    this.coordinatesT[i]=coordinatesT[i];
	}
	for (int i=0;i<coordinatesO.length; i++){
	    this.coordinatesO[i]=coordinatesO[i];
	}
    }

    /**
     * transition function 
     */
    public int[] coordinatesT=null;
    /**
     * output function
     */
    public int[] coordinatesO=null;
    
    public void setCoordinates(int[] coordinatesT, int[] coordinatesO) {
	//System.err.println("set="+Arrays.toString(this.coordinatesT)+" "+Arrays.toString(coordinatesT));
	this.coordinatesT = new  int[coordinatesT.length];
	this.coordinatesO = new int [coordinatesO.length];
	for (int i=0;i<coordinatesT.length; i++){
	    this.coordinatesT[i]=coordinatesT[i];
	}
	for (int i=0;i<coordinatesO.length; i++){
	    this.coordinatesO[i]=coordinatesO[i];
	}
    }
    
    @Override
    public String toString() {
	return "Coord [TF=" + Arrays.toString(coordinatesT)
		+ ", OF=" + Arrays.toString(coordinatesO)
		+ "]";
    }
    
    

}
