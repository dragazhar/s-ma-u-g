package visualization;

import java.util.TreeMap;

import search_algorithm.bodies.Key;
import search_algorithm.bodies.Point;

public class NamedSeriesOfPoints {
    String seriesName = "";
    TreeMap<Key, Point> seriesPoints = new TreeMap<Key, Point>();

    public NamedSeriesOfPoints(String seriesName,TreeMap<Key, Point> points) {
	super();
	this.seriesName = seriesName;
	this.seriesPoints = points;
    }

}
