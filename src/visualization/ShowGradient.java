package visualization;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class ShowGradient {
    
    static ArrayList<Double> color = new ArrayList<Double>();
    
    public static void draw() {

	XYSeriesCollection xyDataset = new XYSeriesCollection();
	XYSeries series = new XYSeries("");
	for(int i=0; i<=10; i++){
	  series.add(i*0.1, 1);
	  color.add(i*0.1);
	  }
	  xyDataset.addSeries(series);

	JFreeChart chart = ChartFactory.createScatterPlot("", "", "",
		xyDataset, PlotOrientation.VERTICAL, true, true, false);

	Plot plot = chart.getPlot();
	plot.setBackgroundPaint(Color.BLACK);

	XYPlot xyPlot = chart.getXYPlot();
	ValueAxis domain = xyPlot.getDomainAxis();
	domain.setVisible(true);
	ValueAxis range = xyPlot.getRangeAxis();
	range.setVisible(false);

	xyPlot.setRangeGridlinesVisible(false);
	xyPlot.setDomainGridlinesVisible(false);
	xyPlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);

	chart.removeLegend();
	


	    MyRenderer renderer = new MyRenderer();
	    renderer.setDotWidth(100);
	    renderer.setDotHeight(100);
	  xyPlot.setRenderer(renderer);


	ChartFrame frame1 = new ChartFrame("", chart);
	frame1.setVisible(true);
	frame1.setSize(700, 700);
	frame1.getFocusOwner();
    }
    
    public static void main(String[] args) {
	draw();

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
   		if (color.get(col) == 1.0) {
   		    return Color.white;
   		} else {
   		    double db = (color.get(col));
   		    float fl = (float) db;
   		    return Color.getHSBColor(0.0f, fl, fl);
   		}

   	    } else {
   		return super.getItemPaint(row, col);
   	    }

   	}
       }
}
