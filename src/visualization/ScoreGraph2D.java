package visualization;

import search_algorithm.bodies.Key;
import search_algorithm.bodies.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.axis.*;

import java.awt.Paint;

import org.jfree.chart.renderer.xy.XYDotRenderer;

public class ScoreGraph2D {
    static int max=0;
    static ArrayList<ArrayList<Double>> color = new ArrayList<ArrayList<Double>>();

    static XYSeries addSeries(String seriesName,
	    TreeMap<Key, Point> seriesPoints) {
	XYSeries series = new XYSeries(seriesName);
	ArrayList<Double> c = new ArrayList<Double>();
	
	for (Point p : seriesPoints.values()) {
	    double x = (double) (p.getKey().getKeyT()+p.getKey().getKeyU()+1);
	    double y = (double) (p.getKey().getKeyO()+1);
	    c.add(p.getValue());
	    series.add(x, y);
	   max= (int) p.getKey().getKeyT();
	}
	color.add(c);
	return series;
    }
    
   

    public static void draw(String chartName,
	    ArrayList<NamedSeriesOfPoints> data, boolean colorize, int pointSize) {

	XYSeriesCollection xyDataset = new XYSeriesCollection();

	for ( NamedSeriesOfPoints s : data) {
	    xyDataset.addSeries(addSeries(s.seriesName, s.seriesPoints));
	}

	JFreeChart chart = ChartFactory.createScatterPlot(chartName, "", "",
		xyDataset, PlotOrientation.VERTICAL, true, true, false);

	Plot plot = chart.getPlot();
	plot.setBackgroundPaint(Color.BLACK);

	XYPlot xyPlot = chart.getXYPlot();
	ValueAxis domain = xyPlot.getDomainAxis();
	domain.setVisible(false);
	ValueAxis range = xyPlot.getRangeAxis();
	range.setVisible(false);

	xyPlot.setRangeGridlinesVisible(false);
	xyPlot.setDomainGridlinesVisible(false);
	xyPlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
	
	
	domain.setUpperBound(max)  ; 
	chart.removeLegend();
	

	if (colorize) {
	    MyRenderer renderer = new MyRenderer();
	    renderer.setDotWidth(pointSize);
	    renderer.setDotHeight(pointSize);
	    xyPlot.setRenderer(renderer);
	} else {
	    XYDotRenderer renderer = new XYDotRenderer();
	    renderer.setDotWidth(pointSize);
	    renderer.setDotHeight(pointSize);
	    xyPlot.setRenderer(renderer);
	}

	ChartFrame frame1 = new ChartFrame(chartName, chart);
	frame1.setVisible(true);
	frame1.setSize(800, 200);
	frame1.getFocusOwner();
    }

  


    private static class MyRenderer extends XYDotRenderer {

	public MyRenderer() {
	    super();
	    // TODO Auto-generated constructor stub
	}

	/**
   	 * 
   	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Paint getItemPaint(int row, int col) {
	    if (color.size() > 0) {
		if (color.get(row).get(col) == 1.0) {
		    return Color.white;
		} 		
		else {
		    double db = (color.get(row).get(col));
		    float fl = (float) db;
		    return Color.getHSBColor(1.0f, fl, fl);
		}

	    } else {
		return super.getItemPaint(row, col);
	    }

	}
    }
}
