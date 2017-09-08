package com.fuelalg;

import org.jfree.JCommon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 05.09.2017.
 */
public class ChartData {

    private ArrayList<Double> dataX;
    private ArrayList<Double> dataY;

    public ChartData() {
        dataX = new ArrayList<>();
        dataY = new ArrayList<>();
    }

    public void addData(double _dataX, double _dataY) {
        dataX.add(_dataX);
        dataY.add(_dataY);
    }

    public static JFreeChart createChart(String descrX,
                                         String descrY1,
                                         String descrY2,
                                         String descrChart,
                                         String descrChart1,
                                         String descrChart2,
                                         ChartData ChartData1,
                                         ChartData ChartData2) {

        XYPlot plot = new XYPlot();

        XYSeriesCollection dataset1 =addSeries(ChartData1, descrChart1);
        XYSeriesCollection dataset2 = addSeries(ChartData2, descrChart2);

        plot.setDataset(0, dataset1);
        plot.setDataset(1, dataset2);
        //customize the plot with renderers and axis
        plot.setRenderer(0, new XYSplineRenderer());//use default fill paint for first series
        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        splinerenderer.setSeriesFillPaint(0, Color.BLUE);
        plot.setRenderer(1, splinerenderer);
        plot.setRangeAxis(0, new NumberAxis(descrY1));
        plot.setRangeAxis(1, new NumberAxis(descrY2));
        plot.setDomainAxis(new NumberAxis(descrX));

        //Map the data to the appropriate axis
        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);

        //generate the chart
        JFreeChart chart = new JFreeChart(descrChart, Font.getFont("Arial"), plot, true);
        chart.setBackgroundPaint(Color.WHITE);
        return chart;
    }

    private static XYSeriesCollection addSeries(ChartData ChartData1, String descr) {
        XYSeries series1 = new XYSeries(descr);
        double[][] data = new double[2][ChartData1.dataY.size()];
        for (int x = 0; x < ChartData1.dataX.size(); x++) {
            series1.add(ChartData1.dataX.get(x), ChartData1.dataY.get(x));
        }
        XYSeriesCollection dataset1 = new XYSeriesCollection();
        dataset1.addSeries(series1);
        return dataset1;
    }
}
