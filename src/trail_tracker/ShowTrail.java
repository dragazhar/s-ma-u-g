package trail_tracker;

import java.awt.Color;
import java.util.ArrayList;

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

public class ShowTrail {

    static ArrayList<Double> color = new ArrayList<Double>();

    public static void draw(String trailName, Grid trailGrid) {
	color = new ArrayList<Double>();
	XYSeriesCollection xyDataset = new XYSeriesCollection();
	XYSeries series = new XYSeries(trailName);
	for (int i = 0; i < 32; i++) {
	    for (int j = 0; j < 32; j++) {
		double value = trailGrid.getGridValue(31-i, j);
		int shift = 20;
		series.add(shift+i,shift+j);
		color.add(value);

	    }

	}
	xyDataset.addSeries(series);
	JFreeChart chart = ChartFactory.createScatterPlot(trailName, "", "",
		xyDataset, PlotOrientation.HORIZONTAL, true, true, false);

	Plot plot = chart.getPlot();
	plot.setBackgroundPaint(Color.black);

	XYPlot xyPlot = chart.getXYPlot();
	ValueAxis domain = xyPlot.getDomainAxis();
	domain.setVisible(false);
	ValueAxis range = xyPlot.getRangeAxis();
	range.setVisible(false);

	xyPlot.setRangeGridlinesVisible(false);
	xyPlot.setDomainGridlinesVisible(false);
	xyPlot.setSeriesRenderingOrder(SeriesRenderingOrder.REVERSE);
	chart.removeLegend();

	MyRenderer renderer = new MyRenderer();
	renderer.setDotWidth(12);
	renderer.setDotHeight(12);
	xyPlot.setRenderer(renderer);

	ChartFrame frame1 = new ChartFrame(trailName, chart);

	frame1.setVisible(true);
	frame1.setSize(700, 700);
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
		Double colorValue = color.get(col);
		if (colorValue == 1.0) {
		    return Color.red;
		} else if (colorValue == 0.0) {
			 return Color.darkGray;
		} else if (colorValue == 2.0) {
		    return Color.white;
		} else if (colorValue == 3.0) {
		    return Color.green;
		} else {
		    return Color.white;
		}

	    } else {
		return Color.white;

	    }

	}
    }
}
