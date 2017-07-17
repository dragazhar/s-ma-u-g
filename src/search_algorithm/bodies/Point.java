package search_algorithm.bodies;

import java.util.Arrays;


public class Point extends Coordinates{
    public Point(int[] coordinatesT, int[] coordinatesO) {
	super(coordinatesT,coordinatesO);
    }
    public int generated=0;
    private Key key = new Key(0, 0, 0);
    
    private  double value = -1;

     public double getValue() {
 	return value;
     }

     public void setValue(double value) {
 	this.value = value;
     }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void copyPoint(Point p){
	this.setCoordinates(p.coordinatesT, p.coordinatesO);
	this.setKey(p.getKey());
	this.setValue(p.getValue());
	
    }
    @Override
    public String toString() {
	return key+" [TF=" + Arrays.toString(coordinatesT)
		+ ", OF=" + Arrays.toString(coordinatesO)
		+ ", value=" + String.format("%.2f", value) + "]";
    }
     
    
     
}
