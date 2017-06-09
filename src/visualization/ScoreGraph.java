package visualization;

import java.util.ArrayList;

import java.util.TreeMap;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import search_algorithm.Point;
import search_algorithm.Key;

public class ScoreGraph {
    
    static double min=100.0;
    static double max=100.0;
    
    static XYSeries addSeries(String seriesName,
	    TreeMap<Key, Point> seriesPoints) {
	XYSeries series = new XYSeries(seriesName);
	for (Point p : seriesPoints.values()) {
	    double x = (double) p.getKey().getKeyT();
	    double y = p.getValue() * 100;
	    series.add(x, y);
	    if(y<min){
		min=y;
	    }
	    max=x;
	}

	return series;
    }

    public static void drawData(String chartName, String xName, String yName,
	    ArrayList< NamedSeriesOfPoints> data) {
	XYSeriesCollection xyDataset = new XYSeriesCollection();
	JFreeChart chart = ChartFactory.createXYLineChart(chartName, xName,
		yName, xyDataset, PlotOrientation.VERTICAL, true, true, false);
	
	

	for ( NamedSeriesOfPoints s : data) {
	    xyDataset.addSeries(addSeries(s.seriesName, s.seriesPoints));
	}
	XYPlot xyPlot = chart.getXYPlot();
	ValueAxis rangeAxis = xyPlot.getRangeAxis();
	rangeAxis.setLowerBound(min) ; 
	rangeAxis.setUpperBound(100) ; 
	ValueAxis domainAxis = xyPlot.getDomainAxis();
	domainAxis.setUpperBound(max)  ; 


	
	
	chart.removeLegend();
	
	ChartFrame frame1 = new ChartFrame(chartName, chart);
	frame1.setVisible(true);
	frame1.setSize(800,200);
	frame1.getFocusOwner();
    }

}
